package com.chailijun.mtime.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chailijun.baseadapter.ViewHolder;
import com.chailijun.baseadapter.base.CommonBaseAdapter;
import com.chailijun.baseadapter.interfaces.OnItemClickListener;
import com.chailijun.mtime.R;
import com.chailijun.mtime.adapter.MovieFilterAdapter;
import com.chailijun.mtime.adapter.SearchHistoryAdapter;
import com.chailijun.mtime.customview.ClearEditText;
import com.chailijun.mtime.customview.MyListView;
import com.chailijun.mtime.mvp.interf.ISearchItemPresenter;
import com.chailijun.mtime.mvp.interf.ISearchItemView;
import com.chailijun.mtime.mvp.model.BaseData;
import com.chailijun.mtime.mvp.model.GetSearchItem;
import com.chailijun.mtime.mvp.model.HotKeyWords;
import com.chailijun.mtime.mvp.presenter.SearchItemPresenter;
import com.chailijun.mtime.utils.Constants;
import com.chailijun.mtime.utils.SPUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import butterknife.BindView;

public class SearchItemActivity extends BaseActivity implements ISearchItemView<BaseData> {

    @BindView(R.id.edit_text)
    ClearEditText edit_text;
    @BindView(R.id.rv_area)
    RecyclerView rv_area;
    @BindView(R.id.rv_genreTypes)
    RecyclerView rv_genreTypes;
    @BindView(R.id.rv_years)
    RecyclerView rv_years;
    @BindView(R.id.rv_hotKeyWords)
    RecyclerView rv_hotKeyWords;
    //    @BindView(R.id.lv_history)
    MyListView lv_history;


    private ImageView back;

    //    @BindView(R.id.vs_history)
    ViewStub vs_history;

    private ISearchItemPresenter mPresenter;

    private MovieFilterAdapter mAreaAdapter;
    private MovieFilterAdapter mGenreTypesAdapter;
    private MovieFilterAdapter mYearsAdapter;

    private HotKeyWordsAdapter mHotKeyWordsAdapter;

    private SearchHistoryAdapter mSearchHistoryAdapter;
    private List<String> mSearchHistoryDatas = new ArrayList<>(0);
    /**
     * 搜索记录列表是否初始化
     */
    private boolean isInitSearchHistoryView;

    /**
     * 电影分类标签（地区、类型、年代）
     */
    private GetSearchItem mSearchItem;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_item;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {

        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(v -> finish());

        //（搜索分类）地区
        mAreaAdapter = new MovieFilterAdapter(this, null, false);
        rv_area.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rv_area.setHasFixedSize(true);
        rv_area.setAdapter(mAreaAdapter);
        mAreaAdapter.setOnItemClickListener(new SearchItemLisenter());

        //（搜索分类）类型
        mGenreTypesAdapter = new MovieFilterAdapter(this, null, false);
        rv_genreTypes.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rv_genreTypes.setHasFixedSize(true);
        rv_genreTypes.setAdapter(mGenreTypesAdapter);
        mGenreTypesAdapter.setOnItemClickListener(new SearchItemLisenter());

        //（搜索分类）年代
        mYearsAdapter = new MovieFilterAdapter(this, null, false);
        rv_years.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rv_years.setHasFixedSize(true);
        rv_years.setAdapter(mYearsAdapter);
        mYearsAdapter.setOnItemClickListener(new SearchItemLisenter());

        //热门关键字
        mHotKeyWordsAdapter = new HotKeyWordsAdapter(this, null, false);
        rv_hotKeyWords.setLayoutManager(
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL));
        rv_hotKeyWords.setHasFixedSize(true);
        rv_hotKeyWords.setAdapter(mHotKeyWordsAdapter);
        mHotKeyWordsAdapter.setOnItemClickListener(new OnItemClickListener<String>() {
            @Override
            public void onItemClick(ViewHolder viewHolder, String data, int position) {
                //搜索

                //保存搜索记录
                Set<String> set = SPUtil.getSet(Constants.SEARCH_HISTORY);
                set = new TreeSet<>(set);//此步骤关键，否则不能追加
                set.add(data);
                SPUtil.saveSet(Constants.SEARCH_HISTORY, set);
                getSearchHistoryData();
            }
        });

        //搜索历史
        initSearchHistory();

        //请求数据
        mPresenter = new SearchItemPresenter(this);
        mPresenter.getSearchItem();
        mPresenter.getHotKeyWords();
    }

    private void initSearchHistory() {

        String hint = SPUtil.get(Constants.MOVIE_EDIT_HINT, getString(R.string.edit_hint));
        edit_text.setHint(hint);

        Set<String> set = SPUtil.getSet(Constants.SEARCH_HISTORY);
        if (set != null && set.size() > 0) {
            //有历史记录
            Iterator<String> iterator = set.iterator();
            while (iterator.hasNext()) {
                mSearchHistoryDatas.add(iterator.next());
            }
            initSearchHistoryView();
        } else {
            //no history recode,do nothing
        }
    }

    /**
     * 初始化搜索历史的布局
     */
    private void initSearchHistoryView() {
        //确保只初始化一次
        if (isInitSearchHistoryView) {
            return;
        }
        isInitSearchHistoryView = true;

        //初始化ViewStub
        vs_history = (ViewStub) findViewById(R.id.vs_history);
        vs_history.inflate();
        final LinearLayout view = (LinearLayout) findViewById(R.id.search_history);
        lv_history = (MyListView) view.findViewById(R.id.lv_history);

        mSearchHistoryAdapter = new SearchHistoryAdapter(this, mSearchHistoryDatas, view);
        lv_history.setAdapter(mSearchHistoryAdapter);

        mSearchHistoryAdapter.addFooter(lv_history,
                new SearchHistoryAdapter.FooterOnClickLisenter() {
                    @Override
                    public void onClick() {
                        //清空搜索记录
                        mSearchHistoryAdapter.clearData();
                        SPUtil.saveSet(Constants.SEARCH_HISTORY, new TreeSet<String>());
                    }
                });
    }

    /**
     * 获取搜索记录
     */
    private void getSearchHistoryData() {

        initSearchHistoryView();

        Set<String> set = SPUtil.getSet(Constants.SEARCH_HISTORY);
        if (set != null && set.size() > 0) {
            mSearchHistoryDatas.clear();
            Iterator<String> iterator = set.iterator();
            while (iterator.hasNext()) {
                mSearchHistoryDatas.add(iterator.next());
            }

            mSearchHistoryAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void addSearchItem(BaseData data) {
        if (data instanceof GetSearchItem) {
            mSearchItem = (GetSearchItem) data;

            serArea(mSearchItem);

            setgenreTypes(mSearchItem);

            setYears(mSearchItem);
        }
    }

    /**
     * 电影分类（地区）
     * @param searchItem
     */
    private void serArea(GetSearchItem searchItem) {
        List<GetSearchItem.DataBean.AreaBean> areaList = searchItem.getData().getArea();
        if (areaList != null && areaList.size() > 0) {
            //默认第一个被选中
            areaList.get(0).setSelected(true);

            List<BaseData> list = new ArrayList<>();
            list.addAll(areaList);
            mAreaAdapter.setNewData(list);
        }
    }

    /**
     * 电影分类（类型）
     * @param searchItem
     */
    private void setgenreTypes(GetSearchItem searchItem) {
        List<GetSearchItem.DataBean.GenreTypesBean> genreTypes = searchItem.getData().getGenreTypes();
        if (genreTypes != null && genreTypes.size() > 0) {
            //默认第一个被选中
            genreTypes.get(0).setSelected(true);

            List<BaseData> list = new ArrayList<>();
            list.addAll(genreTypes);
            mGenreTypesAdapter.setNewData(list);
        }
    }

    /**
     * 电影分类（年代）
     * @param searchItem
     */
    private void setYears(GetSearchItem searchItem) {
        List<GetSearchItem.DataBean.YearsBean> years = searchItem.getData().getYears();
        if (years != null && years.size() > 0) {
            //默认第一个被选中
            years.get(0).setSelected(true);

            List<BaseData> list = new ArrayList<>();
            list.addAll(years);
            mYearsAdapter.setNewData(list);
        }
    }

    @Override
    public void addHotKeyWords(BaseData data) {
        if (data instanceof HotKeyWords) {
            HotKeyWords hotKeyWords = (HotKeyWords) data;

            mHotKeyWordsAdapter.setNewData(hotKeyWords.getKeywords());
        }
    }

    @Override
    public void loadFailed(String msg) {

    }

    class SearchItemLisenter implements OnItemClickListener<BaseData>{

        @Override
        public void onItemClick(ViewHolder viewHolder, BaseData data, int position) {
            Intent intent = new Intent(SearchItemActivity.this,SearchMovieActivity.class);

            if (data instanceof GetSearchItem.DataBean.AreaBean){
                GetSearchItem.DataBean.AreaBean areaBean = (GetSearchItem.DataBean.AreaBean) data;
                intent.putExtra(Constants.AREA,areaBean.getSubName());
            }
            if (data instanceof GetSearchItem.DataBean.GenreTypesBean){
                GetSearchItem.DataBean.GenreTypesBean genreTypesBean =
                        (GetSearchItem.DataBean.GenreTypesBean) data;
                intent.putExtra(Constants.GENRE_TYPES,genreTypesBean.getSubName());
            }
            if (data instanceof GetSearchItem.DataBean.YearsBean){
                GetSearchItem.DataBean.YearsBean yearsBean = (GetSearchItem.DataBean.YearsBean) data;
                intent.putExtra(Constants.YEARS,yearsBean.getSmallName());
            }

            if (mSearchItem != null){
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.SEARCH_ITEM,mSearchItem);
                intent.putExtras(bundle);
            }

            startActivity(intent);
        }
    }


    class HotKeyWordsAdapter extends CommonBaseAdapter<String> {

        public HotKeyWordsAdapter(Context context, List<String> datas, boolean isOpenLoadMore) {
            super(context, datas, isOpenLoadMore);
        }

        @Override
        protected void convert(ViewHolder holder, String data, int position) {
            holder.setText(R.id.tv_area, data);

            holder.getView(R.id.tv_area).setBackgroundResource(R.drawable.hot_key_words_bg_shape);
            holder.setTextColor(R.id.tv_area, ContextCompat.getColor(mContext, R.color.choose_movie_border));
        }

        @Override
        protected int getItemLayoutId() {
            return R.layout.activity_search_movie_choose_item;
        }
    }
}
