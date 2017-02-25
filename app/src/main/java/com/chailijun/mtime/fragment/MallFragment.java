package com.chailijun.mtime.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chailijun.baseadapter.interfaces.OnLoadMoreListener;
import com.chailijun.mtime.R;
import com.chailijun.mtime.adapter.MallAdapter;
import com.chailijun.mtime.mvp.interf.IMallView;
import com.chailijun.mtime.mvp.model.BaseData;
import com.chailijun.mtime.mvp.model.mall.MallIndexJson;
import com.chailijun.mtime.mvp.model.mall.RecommendProductsJson;
import com.chailijun.mtime.mvp.presenter.MallPresenter;
import com.chailijun.mtime.utils.Constants;
import com.chailijun.mtime.utils.DensityUtil;
import com.chailijun.mtime.utils.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@Deprecated
public class MallFragment extends BaseFragment implements IMallView<BaseData> {

    @BindView(R.id.rv_mall)
    RecyclerView rv_mall;
    @BindView(R.id.gotop)
    ImageView gotop;
    @BindView(R.id.search_bg)
    ImageView search_bg;

    private MallAdapter mallAdapter;
    private MallPresenter presenter;
    private GridLayoutManager gridLayoutManager;
    private int pageIndex = 1;
    private View initLoadFailedView;

    //RecyclerView y轴方向滚动距离总和
    private int totalDisY;

    public static MallFragment newInstance(String param1) {
        MallFragment fragment = new MallFragment();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        String string = bundle.getString("agrs1");
        Logger.e("CLJ",string+" 初始化");
    }

    @Override
    public String getFragmentName() {
        return MallFragment.class.getSimpleName();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mall;
    }

    @Override
    protected void initViews(View rootView) {
        mallAdapter = new MallAdapter(getActivity(), null, true,getChildFragmentManager());
        //设置第一次加载中的布局
        mallAdapter.setInitLoadingView(R.layout.loading);
        //初始化第一次加载失败的布局
        initLoadFailedView = LayoutInflater.from(getActivity()).inflate(R.layout.loading_failed, (ViewGroup) rv_mall.getParent(), false);
        initLoadFailedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRefresh();
            }
        });
        //初始化加载更多布局
        mallAdapter.setLoadMoreView(R.layout.loading_more);

        mallAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(boolean isReload) {
                loadMore();

            }
        });

        rv_mall.setLayoutManager(gridLayoutManager = new GridLayoutManager(getActivity(), 2));
        rv_mall.setHasFixedSize(true);
        rv_mall.setAdapter(mallAdapter);

        //头尾布局各占一行
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (mallAdapter.isHeaderView(position) || mallAdapter.isFooterView(position)) ?
                        gridLayoutManager.getSpanCount() : 1;
            }
        });

        final int screenHeight = DensityUtil.getScreenHeight();
        rv_mall.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalDisY += dy;

//                Logger.e("TAG","dx:"+dx+"  dy:"+dy);
//                Logger.e("TAG","mallRecyclerView Y轴滚动距离："+totalDisY);

                //设置搜索栏背景透明度
                if (totalDisY < 500){
                    search_bg.setAlpha(1.0f/500.0f * (float)totalDisY);
                }else {
                    search_bg.setAlpha(1.0f);
                }

                if (totalDisY > screenHeight){
                    gotop.setVisibility(View.VISIBLE);

                    gotop.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            gridLayoutManager.scrollToPositionWithOffset(0,0);
//                            rv_mall.smoothScrollToPosition(0);//光滑滚动
                            totalDisY = 0;
                            search_bg.setAlpha(0.0f);
                            gotop.setVisibility(View.GONE);
                        }
                    });
                }else {
                    gotop.setVisibility(View.GONE);
                }
            }
        });
    }


    private void onRefresh() {
        mallAdapter.setInitLoadingView(R.layout.loading);
        presenter.getMallIndex();
    }

    private void loadMore() {
        if (presenter != null){
            Logger.e("TAG","加载更多页码："+pageIndex);
            presenter.getRecommendProducts("",pageIndex++);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter = new MallPresenter(this);
        presenter.getMallIndex();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mallAdapter.relaseBanner();

        if (presenter != null){
            presenter.unsubcrible();
        }
    }

    @Override
    protected void lazyLoadData() {

    }

    @Override
    public void addMallIndex(BaseData data) {
        if (data instanceof MallIndexJson){
            MallIndexJson mallIndexJson = (MallIndexJson) data;
            List<BaseData> datas = new ArrayList<>();
            datas.add(mallIndexJson);
            mallAdapter.setNewData(datas);
        }
    }

    @Override
    public void addRecommendProducts(BaseData data) {
        if (data instanceof RecommendProductsJson){
            RecommendProductsJson recommendProductsJson = (RecommendProductsJson) data;

            if (recommendProductsJson.getGoodsList() != null && recommendProductsJson.getGoodsList().size() > 0){
                List<BaseData> datas = new ArrayList<>();
                for (int i = 0; i < recommendProductsJson.getGoodsList().size(); i++) {
                    datas.add(recommendProductsJson.getGoodsList().get(i));
                }
                mallAdapter.setLoadMoreData(datas);
            }
        }

    }

    @Override
    public void loadFailed(String msg) {
        Logger.e("TAG",msg+"加载失败");
        if (msg.equals(Constants.MALL_INDEX)) {
            //设置第一次加载失败的布局
            mallAdapter.setInitLoadFailedView(initLoadFailedView);
        } else {
            //加载失败，更新footer view提示
            mallAdapter.setLoadFailedView(R.layout.load_failed_layout);
            pageIndex--;
        }
    }
}
