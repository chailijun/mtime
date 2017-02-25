package com.chailijun.mtime.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chailijun.baseadapter.ViewHolder;
import com.chailijun.baseadapter.interfaces.OnItemClickListener;
import com.chailijun.baseadapter.interfaces.OnLoadMoreListener;
import com.chailijun.baseadapter.interfaces.OnMultiItemClickListeners;
import com.chailijun.mtime.R;
import com.chailijun.mtime.adapter.MovieFilterAdapter;
import com.chailijun.mtime.adapter.SearchMovieAdapter;
import com.chailijun.mtime.customview.MyViewSwitcher;
import com.chailijun.mtime.moviedetail.MovieDetailActivity;
import com.chailijun.mtime.mvp.interf.ISearchMoviePresenter;
import com.chailijun.mtime.mvp.interf.ISearchMovieView;
import com.chailijun.mtime.mvp.model.BaseData;
import com.chailijun.mtime.mvp.model.GetSearchItem;
import com.chailijun.mtime.mvp.model.movie.SearchMovie;
import com.chailijun.mtime.mvp.presenter.SearchMoviePresenter;
import com.chailijun.mtime.utils.Constants;
import com.chailijun.mtime.utils.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 影片分类筛选
 */
public class SearchMovieActivity extends BaseActivity implements ISearchMovieView<BaseData> {

    @BindView(R.id.head_title)
    TextView head_title;
    @BindView(R.id.rv_movie)
    RecyclerView mRecyclerView;
//    @BindView(R.id.ll_head)
//    LinearLayout ll_head;

    @BindView(R.id.viewswitch)
    MyViewSwitcher mViewSwitch;

    @BindView(R.id.rv_area)
    RecyclerView rv_area;
    @BindView(R.id.rv_genreTypes)
    RecyclerView rv_genreTypes;
    @BindView(R.id.rv_years)
    RecyclerView rv_years;

    @BindView(R.id.movie_total)
    TextView movie_total;

    @BindView(R.id.rg_movie_sort)
    RadioGroup mMovieSort;
    @BindView(R.id.rb_hot)
    RadioButton rb_hot;
    @BindView(R.id.rb_rating)
    RadioButton rb_rating;
    @BindView(R.id.rb_time)
    RadioButton rb_time;

    private MovieFilterAdapter mAreaAdapter;
    private MovieFilterAdapter mGenreTypesAdapter;
    private MovieFilterAdapter mYearsAdapter;

    private SearchMovieAdapter mAdapter;

    private ISearchMoviePresenter mPresenter;

    private GridLayoutManager gridLayoutManager;

    /**
     * 电影分类标签（地区、类型、年代）
     */
    private GetSearchItem mSearchItem;

    private String areas;
    private String genreTypes;
    private String years;

    /**
     * 排序类型（0，1，2对应热度、评分、时间）
     */
    private int sortType = 0;
    /**
     * 排序方式（1，2对应降序、升序）
     */
    private int sortMethod = 1;

    /**
     * 升序排列时RadioButton右边的小图标
     */
    private Drawable upDrawable;

    /**
     * 降序排列时RadioButton右边的小图标
     */
    private Drawable downDrawable;
    /**
     * RadioButton右边默认小图标
     */
    private Drawable defaultDrawable;

    private int pageIndex = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_movie;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        getData();
        head_title.setText(getString(R.string.movie_filter));

        initHeadLayout();

        //添加一个假数据作为头部数据(为上半部分留出空间)
        SearchMovie.MovieModelListBean fakeData =
                new SearchMovie.MovieModelListBean();
        fakeData.setMovieId(-1);
        List<SearchMovie.MovieModelListBean> datas = new ArrayList<>();
        datas.add(0, fakeData);

        mAdapter = new SearchMovieAdapter(this, datas, true);

        mAdapter.setLoadMoreView(R.layout.loading_more);
        mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(boolean isReload) {
                loadMore();
            }
        });

        mRecyclerView.setLayoutManager(gridLayoutManager = new GridLayoutManager(this, 3));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnMultiItemClickListener(
                new OnMultiItemClickListeners<SearchMovie.MovieModelListBean>() {
            @Override
            public void onItemClick(ViewHolder viewHolder,
                                    SearchMovie.MovieModelListBean data,
                                    int position,
                                    int viewType) {
                if (viewType != SearchMovieAdapter.TYPE_HEAD){
                    Intent intent = new Intent(SearchMovieActivity.this,MovieDetailActivity.class);
                    intent.putExtra(Constants.MOVIE_ID,data.getMovieId());
                    startActivity(intent);
                }
            }
        });


        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                mViewSwitch.setMyVisibility(dy);
            }
        });

        //头尾布局各占一行
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (mAdapter.isHeaderView(position) || mAdapter.isFooterView(position)) ?
                        gridLayoutManager.getSpanCount() : 1;
            }
        });

        mPresenter = new SearchMoviePresenter(this);
//        mPresenter.getSearchMovie(0, "-1", "-1", 1, "-1", pageIndex++);

    }

    private void initHeadLayout() {

        List<BaseData> areasList = null;
        List<BaseData> genreTypesList = null;
        List<BaseData> yearsList = null;
        if (mSearchItem != null) {
            List<GetSearchItem.DataBean.AreaBean> areas = mSearchItem.getData().getArea();

            if (this.areas == null){
                areas.get(0).setSelected(true);
                this.areas = areas.get(0).getSubName();
            }else {
                for (int i = 0; i < areas.size(); i++) {
                    if (this.areas.equals(areas.get(i).getSubName())){
                        areas.get(i).setSelected(true);
                        break;
                    }
                }
            }
            areasList = new ArrayList<>();
            areasList.addAll(areas);

            List<GetSearchItem.DataBean.GenreTypesBean> genreTypes =
                    mSearchItem.getData().getGenreTypes();
            if (this.genreTypes == null){
                genreTypes.get(0).setSelected(true);
                this.genreTypes = genreTypes.get(0).getSubName();
            }else {
                for (int i = 0; i < genreTypes.size(); i++) {
                    if (this.genreTypes.equals(genreTypes.get(i).getSubName())){
                        genreTypes.get(i).setSelected(true);
                        break;
                    }
                }
            }
            genreTypesList = new ArrayList<>();
            genreTypesList.addAll(genreTypes);

            List<GetSearchItem.DataBean.YearsBean> years = mSearchItem.getData().getYears();
            if (this.years == null){
                years.get(0).setSelected(true);
                this.years = years.get(0).getSmallName();
            }else {
                for (int i = 0; i < years.size(); i++) {
                    if (this.years.equals(years.get(i).getSmallName())){
                        years.get(i).setSelected(true);
                        break;
                    }
                }
            }
            yearsList = new ArrayList<>();
            yearsList.addAll(years);
        }

        //（搜索分类）地区
        mAreaAdapter = new MovieFilterAdapter(this, areasList, false);
        rv_area.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rv_area.setHasFixedSize(true);
        rv_area.setAdapter(mAreaAdapter);
        mAreaAdapter.setOnItemClickListener(new SearchItemLisenter());

        //（搜索分类）类型
        mGenreTypesAdapter = new MovieFilterAdapter(this, genreTypesList, false);
        rv_genreTypes.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rv_genreTypes.setHasFixedSize(true);
        rv_genreTypes.setAdapter(mGenreTypesAdapter);
        mGenreTypesAdapter.setOnItemClickListener(new SearchItemLisenter());

        //（搜索分类）年代
        mYearsAdapter = new MovieFilterAdapter(this, yearsList, false);
        rv_years.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rv_years.setHasFixedSize(true);
        rv_years.setAdapter(mYearsAdapter);
        mYearsAdapter.setOnItemClickListener(new SearchItemLisenter());


        mMovieSort.check(R.id.rb_hot);
        mMovieSort.setOnCheckedChangeListener(new SortOnCheckedChangeListener());

        rb_hot.setOnClickListener(new SortOnClickListener());
        rb_rating.setOnClickListener(new SortOnClickListener());
        rb_time.setOnClickListener(new SortOnClickListener());

        upDrawable = ContextCompat.getDrawable(this,R.drawable.price_sort_up);
        upDrawable.setBounds(0, 0,
                upDrawable.getMinimumWidth(), upDrawable.getMinimumHeight());

        downDrawable = ContextCompat.getDrawable(this,R.drawable.price_sort_down);
        downDrawable.setBounds(0, 0,
                downDrawable.getMinimumWidth(), downDrawable.getMinimumHeight());

        defaultDrawable = ContextCompat.getDrawable(this,R.drawable.price_sort_default);
        defaultDrawable.setBounds(0, 0,
                defaultDrawable.getMinimumWidth(), defaultDrawable.getMinimumHeight());

    }

    private void loadMore() {
        mPresenter.getSearchMovie(sortType, areas, genreTypes, sortMethod, years, pageIndex++);
    }

    private void getData() {
        Intent intent = getIntent();

        mSearchItem = (GetSearchItem) intent.getExtras().getSerializable(Constants.SEARCH_ITEM);

        areas = intent.getStringExtra(Constants.AREA);
        genreTypes = intent.getStringExtra(Constants.GENRE_TYPES);
        years = intent.getStringExtra(Constants.YEARS);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        getData();
    }

    @Override
    public void addSearchMovie(BaseData data) {
        if (data instanceof SearchMovie) {
            SearchMovie searchMovie = (SearchMovie) data;

            List<SearchMovie.MovieModelListBean> movieModelList =
                    searchMovie.getMovieModelList();
            if (movieModelList != null && movieModelList.size() > 0) {

                mAdapter.setLoadMoreData(movieModelList);

                //保存电影至Bmob
                /*for (int i = 0; i < movieModelList.size(); i++) {
                    MovieUtil.movieModelListBeanToMovieBean(movieModelList.get(i));
                }*/
            }else {
                mAdapter.setLoadEndView(R.layout.load_end_no_more);
            }

            movie_total.setText(String.format(getString(R.string.movie_total), searchMovie.getTotalCount()));
        }
    }

    @Override
    public void loadFailed(String msg) {

        Logger.d(SearchMovieActivity.class.getSimpleName(),msg);
        Logger.e("TAG","size===="+mAdapter.getmDatas().size());

//        mAdapter.setLoadEndView(R.layout.load_end_no_more);
        pageIndex = (pageIndex--) <= 0 ? 1 : pageIndex;
    }

    @OnClick(R.id.back)
    public void onClick(View view) {
        finish();
    }

    class SearchItemLisenter implements OnItemClickListener<BaseData> {

        @Override
        public void onItemClick(ViewHolder viewHolder, BaseData data, int position) {

            if (data instanceof GetSearchItem.DataBean.AreaBean){
                GetSearchItem.DataBean.AreaBean areaBean = (GetSearchItem.DataBean.AreaBean) data;

                if(mAreaAdapter.getSelectedPos() != position){
                    areas = areaBean.getSubName();

                    setItemSelected(mAreaAdapter,position);
                }

            }
            if (data instanceof GetSearchItem.DataBean.GenreTypesBean){
                GetSearchItem.DataBean.GenreTypesBean genreTypesBean =
                        (GetSearchItem.DataBean.GenreTypesBean) data;
                if (mGenreTypesAdapter.getSelectedPos() != position){
                    genreTypes = genreTypesBean.getSubName();

                    setItemSelected(mGenreTypesAdapter,position);
                }

            }
            if (data instanceof GetSearchItem.DataBean.YearsBean){
                GetSearchItem.DataBean.YearsBean yearsBean = (GetSearchItem.DataBean.YearsBean) data;
                if (mYearsAdapter.getSelectedPos() != position){
                    years = yearsBean.getSmallName();

                    setItemSelected(mYearsAdapter,position);
                }
            }

            movieReSort();
        }
    }

    /**
     * 设置RecyclerView item选中状态
     * @param adapter
     * @param position
     */
    private void setItemSelected(MovieFilterAdapter adapter,int position) {
        //先取消上个item的勾选状态
        adapter.getmDatas().get(adapter.getSelectedPos()).setSelected(false);
        adapter.notifyItemChanged(adapter.getSelectedPos());
        //设置新Item的勾选状态
        adapter.setSelectedPos(position);
        adapter.getmDatas().get(position).setSelected(true);
        adapter.notifyItemChanged(position);
    }

    /**
     * 电影重新排序
     */
    private void movieReSort() {
        //重新请求，页码置1
        pageIndex = 1;
        mAdapter.removeAll();

        //添加一个假数据作为头部数据(为上半部分留出空间)
        SearchMovie.MovieModelListBean fakeData = new SearchMovie.MovieModelListBean();
        fakeData.setMovieId(-1);
        List<SearchMovie.MovieModelListBean> datas = new ArrayList<>();
        datas.add(0, fakeData);
        mAdapter.setNewData(datas);

        Logger.e("TAG","重新排序：sortType   ="+sortType);
        Logger.e("TAG","重新排序：sortMethod ="+sortMethod);
        Logger.e("TAG","重新排序：areas      ="+areas);
        Logger.e("TAG","重新排序：genreTypes ="+genreTypes);
        Logger.e("TAG","重新排序：years      ="+years);
        Logger.e("TAG","重新排序：pageIndex  ="+pageIndex);
        Logger.e("TAG","===================================");

        loadMore();

    }

    class SortOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            sortMethod = sortMethod == 2 ? 1 : 2;
            switch (v.getId()) {
                case R.id.rb_hot:
                    if (sortType == 0){
                        if (sortMethod == 1){
                            rb_hot.setCompoundDrawables(null, null, downDrawable, null);
                        }else if (sortMethod == 2){
                            rb_hot.setCompoundDrawables(null, null, upDrawable, null);
                        }
                    }

                    break;
                case R.id.rb_rating:
                    if (sortType == 1){
                        if (sortMethod == 1){
                            rb_rating.setCompoundDrawables(null, null, downDrawable, null);
                        }else if (sortMethod == 2){
                            rb_rating.setCompoundDrawables(null, null, upDrawable, null);
                        }
                    }

                    break;
                case R.id.rb_time:
                    if (sortType == 2){
                        if (sortMethod == 1){
                            rb_time.setCompoundDrawables(null, null, downDrawable, null);
                        }else if (sortMethod == 2){
                            rb_time.setCompoundDrawables(null, null, upDrawable, null);
                        }
                    }

                    break;
                default:
                    break;
            }
            movieReSort();
        }
    }

    class SortOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            sortMethod = 2;
            switch (checkedId) {
                case R.id.rb_hot:
                    rb_rating.setCompoundDrawables(null, null, defaultDrawable, null);
                    rb_time.setCompoundDrawables(null, null, defaultDrawable, null);
                    sortType = 0;
                    break;
                case R.id.rb_rating:
                    rb_hot.setCompoundDrawables(null, null, defaultDrawable, null);
                    rb_time.setCompoundDrawables(null, null, defaultDrawable, null);
                    sortType = 1;
                    break;
                case R.id.rb_time:
                    rb_hot.setCompoundDrawables(null, null, defaultDrawable, null);
                    rb_rating.setCompoundDrawables(null, null, defaultDrawable, null);
                    sortType = 2;
                    break;
                default:
                    break;
            }
        }
    }
}
