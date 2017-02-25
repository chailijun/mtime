package com.chailijun.mtime.activity;

import android.content.Context;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.chailijun.baseadapter.ViewHolder;
import com.chailijun.baseadapter.base.CommonBaseAdapter;
import com.chailijun.baseadapter.interfaces.OnLoadMoreListener;
import com.chailijun.mtime.R;
import com.chailijun.mtime.mvp.interf.IMallView;
import com.chailijun.mtime.mvp.model.BaseData;
import com.chailijun.mtime.mvp.model.mall.RecommendProductsJson;
import com.chailijun.mtime.mvp.presenter.MallPresenter;
import com.chailijun.mtime.utils.Imager;
import com.chailijun.mtime.utils.Logger;

import java.util.List;

import butterknife.BindView;

/*public class TestActivity extends AppCompatActivity {

    @BindView(R.id.content)
    FrameLayout content;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        unbinder = ButterKnife.bind(this);

        init();
    }

    private void init() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.content,HotPlayMoviesFragment.newInstance("HotPlayMovie"));
        transaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}*/
public class TestActivity extends BaseActivity implements IMallView<BaseData> {

    //可能感兴趣的商品
    @BindView(R.id.rv_RecommendProducts)
    RecyclerView rv_RecommendProducts;
    private RecommendProductsAdapter recommendProductsAdapter;
    private MallPresenter presenter;
    private int pageIndex = 1;
    private GridLayoutManager gridLayoutManager;

    @Override
    protected int getLayoutId() {

        return R.layout.activity_test;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        //可能感兴趣的商品（推荐商品）
        recommendProductsAdapter = new RecommendProductsAdapter(this,null,true);
        recommendProductsAdapter.setLoadMoreView(R.layout.loading_more);
        recommendProductsAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(boolean isReload) {
                loadMore();
            }
        });
        rv_RecommendProducts.setHasFixedSize(true);
        rv_RecommendProducts.setLayoutManager(gridLayoutManager = new GridLayoutManager(this,2));
//        rv_RecommendProducts.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        rv_RecommendProducts.setAdapter(recommendProductsAdapter);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (recommendProductsAdapter.isHeaderView(position) || recommendProductsAdapter.isFooterView(position)) ?
                        gridLayoutManager.getSpanCount() : 1;
            }
        });

        presenter = new MallPresenter(this);
        presenter.getRecommendProducts("",pageIndex++);
    }

    private void loadMore() {
        if (presenter != null){
            Logger.e("TEST","加载更多页码："+pageIndex);
            presenter.getRecommendProducts("",pageIndex++);
        }
    }

    @Override
    public void addMallIndex(BaseData data) {

    }

    @Override
    public void addRecommendProducts(BaseData data) {
        Logger.e("TEST","推荐商品加载成功");

        if (data instanceof RecommendProductsJson){
            RecommendProductsJson recommendProductsJson = (RecommendProductsJson) data;
            Logger.e("TEST","推荐商品加载成功："+recommendProductsJson.getGoodsIds());
            Logger.e("TEST","推荐商品加载成功："+recommendProductsJson.getGoodsList().size());
            Logger.e("TEST","推荐商品加载成功："+recommendProductsJson.getGoodsList());
            Logger.e("TEST","推荐商品加载成功："+recommendProductsJson.getGoodsList() == null?"null":"非空");
//            recommendProductsAdapter.setNewData(recommendProductsJson.getGoodsList());
            Logger.e("TEST","刷新前item总数："+recommendProductsAdapter.getItemCount());
            if(recommendProductsAdapter.getItemCount() == 0){

                recommendProductsAdapter.setNewData(recommendProductsJson.getGoodsList());
            }else {

                recommendProductsAdapter.setLoadMoreData(recommendProductsJson.getGoodsList());
            }
            if (recommendProductsJson.getGoodsList() != null && recommendProductsJson.getGoodsList().size() > 0){
            }
            Logger.e("TEST","刷新后item总数："+recommendProductsAdapter.getItemCount());
        }
    }

    @Override
    public void loadFailed(String msg) {
        Logger.e("TEST",msg);
    }

    class RecommendProductsAdapter extends CommonBaseAdapter<RecommendProductsJson.GoodsListBean> {

        private Context context;
        public RecommendProductsAdapter(Context context, List<RecommendProductsJson.GoodsListBean> datas, boolean isOpenLoadMore) {
            super(context, datas, isOpenLoadMore);
            this.context = context;
        }

        public boolean isHeaderView(int position) {
            return 0 == position ? true : false;//此处只添加一个header，若需添加多个header此处重新实现
        }

        @Override
        protected void convert(ViewHolder holder, RecommendProductsJson.GoodsListBean data, int position) {
            Imager.load(context,data.getImage(), (ImageView) holder.getView(R.id.goods_img));
        }

        @Override
        protected int getItemLayoutId() {
            return R.layout.fragment_mall_recommend_products;
        }
    }

}
