package com.chailijun.mtime.homepage;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chailijun.mtime.MtimeApp;
import com.chailijun.mtime.R;
import com.chailijun.mtime.activity.BoxOfficeActivity;
import com.chailijun.mtime.activity.ReviewDetailActivity;
import com.chailijun.mtime.activity.TopListActivity;
import com.chailijun.mtime.activity.WebActivity;
import com.chailijun.mtime.api.ApiConstants;
import com.chailijun.mtime.data.home.list_item.FilmReview;
import com.chailijun.mtime.data.home.list_item.GuessMovie;
import com.chailijun.mtime.data.home.list_item.ImageList_51_1;
import com.chailijun.mtime.data.home.list_item.NewMovie;
import com.chailijun.mtime.data.home.list_item.ShortNews_51_0;
import com.chailijun.mtime.data.home.list_item.ShortNews_51_2;
import com.chailijun.mtime.data.home.list_item.TopList;
import com.chailijun.mtime.moviedetail.MovieDetailActivity;
import com.chailijun.mtime.activity.NewsDetailActivity;
import com.chailijun.mtime.city.SearchCityActivity;
import com.chailijun.mtime.activity.SearchItemActivity;
import com.chailijun.mtime.adapter.NetworkImageHolderView;
import com.chailijun.mtime.base.BaseFragment;
import com.chailijun.mtime.data.home.GotoPageBean;
import com.chailijun.mtime.data.home.HomeIndex;
import com.chailijun.mtime.data.home.HomeListSuper;
import com.chailijun.mtime.data.home.HotPlayMovies;
import com.chailijun.mtime.db.entity.City;
import com.chailijun.mtime.db.gen.CityDao;
import com.chailijun.mtime.hottest.HottestActivity;
import com.chailijun.mtime.utils.Constants;
import com.chailijun.mtime.utils.DensityUtil;
import com.chailijun.mtime.utils.Imager;
import com.chailijun.mtime.utils.Logger;
import com.chailijun.mtime.utils.SPUtil;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class HomeFragment extends BaseFragment implements
        HomeContract.View,BaseQuickAdapter.RequestLoadMoreListener {

    public static final String ARGUMENT = "argument";
    public static final int REQUSET = 1;
    private int pageIndex = 1;

    private RecyclerView mRecyclerView;

    private HomeAdapter mAdapter;

    private HomeContract.Presenter mPresenter;
    private View loadingView;
    private View noDataView;

    private View errorView;

    private LinearLayout loading;

    private int locationId;
    private CityDao cityDao;
    private City mCity;
    private ConvenientBanner topbanner;
    private ConvenientBanner advBanner;

    //搜索栏
    private TextView tv_search;

    private TextView mLocation;
    private TextView mTotalHotMovie;
    private TextView mTotalComingMovie;
    private TextView mTotalCinemaCount;
    private View mHeadview;

    /**热放电影RecyclerView*/
//    private RecyclerView mRVHotPlay;
    /**热放电影Adapter*/
    private HotPlayAdapter mHotPlayAdapter;
    private TextView noMovies;

    //
    private TextView hottest;
    private TextView boxoffice;
    private TextView trailer;
    private TextView live;

    /**底部导航栏*/
    private BottomNavigationBar navBar;

    public static HomeFragment newInstance(String argument) {
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT, argument);

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //初始化一些与视图无关的参数
        cityDao = MtimeApp.getInstances().getmDaoSession().getCityDao();
        locationId = SPUtil.getInt(Constants.LOCATION_ID);
        if (locationId !=0){
            getCity(locationId);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUSET && resultCode == RESULT_OK){

            locationId = data.getIntExtra(Constants.LOCATION_ID, 0);
            if (locationId != 0 && mPresenter != null) {
                getCity(locationId);

//                onRefresh();
                mPresenter.loadHotPlayMovies(locationId);
            }
        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_home_page;
    }

    @Override
    public void initView(View view) {

        navBar = (BottomNavigationBar) getActivity().findViewById(R.id.nav_bar);
//        loading = $(view,R.id.loading);
        mRecyclerView = $(view,R.id.rv_home);
        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        //加载中
        loadingView = getActivity().getLayoutInflater()
                .inflate(R.layout.loading, (ViewGroup) mRecyclerView.getParent(), false);
        //没有数据
        noDataView = getActivity().getLayoutInflater()
                .inflate(R.layout.loading_failed, (ViewGroup) mRecyclerView.getParent(), false);
        noDataView.setOnClickListener(v -> onRefresh());
        //加载错误
        errorView = getActivity().getLayoutInflater()
                .inflate(R.layout.loading_failed, (ViewGroup) mRecyclerView.getParent(), false);
        errorView.setOnClickListener(v -> onRefresh());

        //首页头布局
        mHeadview = getActivity().getLayoutInflater()
                .inflate(R.layout.home_page_head, (ViewGroup) mRecyclerView.getParent(), false);

        tv_search = $(mHeadview, R.id.tv_search);
        mLocation = $(mHeadview,R.id.tv_location);
        mTotalHotMovie = $(mHeadview, R.id.tv_totalHotMovie);
        mTotalComingMovie = $(mHeadview, R.id.tv_totalComingMovie);
        mTotalCinemaCount = $(mHeadview, R.id.tv_totalCinemaCount);

        //
        hottest = $(mHeadview,R.id.hottest);
        boxoffice = $(mHeadview,R.id.boxoffice);
        trailer = $(mHeadview,R.id.trailer);
        live = $(mHeadview,R.id.live);

        initHotPlayView(mHeadview);

        initAdapter();
        //onRefresh();
    }

    private void initAdapter() {
        mAdapter = new HomeAdapter(null);
        mAdapter.addHeaderView(mHeadview);
        mAdapter.setHeaderAndEmpty(false);

        mAdapter.setOnLoadMoreListener(this);
        mRecyclerView.setAdapter(mAdapter);

        //如果请求数据错误或数据为空则不显示头部
        mAdapter.setHeaderAndEmpty(false);

        mRecyclerView.addOnItemTouchListener(new NewsItemLisenter());
    }

    /**
     * 初始化热放电影视图
     * @param mHeadview
     */
    private void initHotPlayView(View mHeadview) {
        RecyclerView rvHotPlay = $(mHeadview, R.id.rv_hotplaymovie);
        noMovies = $(mHeadview,R.id.no_movies);

        mHotPlayAdapter = new HotPlayAdapter(null);

        rvHotPlay.setLayoutManager(
                new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        rvHotPlay.setHasFixedSize(true);

        rvHotPlay.setAdapter(mHotPlayAdapter);

        //热放电影的item点击事件
        rvHotPlay.addOnItemTouchListener(new com.chad.library.adapter.base.listener.OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                final HotPlayMovies.MoviesBean moviesBean
                        = (HotPlayMovies.MoviesBean) adapter.getData().get(position);

                //点击进入电影详细
                Intent intent = new Intent(getActivity(),MovieDetailActivity.class);
                intent.putExtra(Constants.MOVIE_ID,moviesBean.getMovieId());
                startActivity(intent);
            }
        });
    }

    @Override
    public void setListener() {
        super.setListener();

        //城市、热映总数、即将上映总数、同城影院总数 点击监听
        mLocation.setOnClickListener(this);
        mTotalHotMovie.setOnClickListener(this);
        mTotalComingMovie.setOnClickListener(this);
        mTotalCinemaCount.setOnClickListener(this);

        //搜索栏
        tv_search.setOnClickListener(this);

        hottest.setOnClickListener(this);
        boxoffice.setOnClickListener(this);
        trailer.setOnClickListener(this);
        live.setOnClickListener(this);
    }

    private void onRefresh() {
        mAdapter.setEmptyView(loadingView);
//        onLoadMoreRequested();
//        if (locationId != 0) mPresenter.loadHomeIndex(locationId);
        doBusiness(getActivity());
    }

    @Override
    public void doBusiness(Context mContext) {

        if (locationId != 0){
            mPresenter.loadHomeIndex(locationId);
            mPresenter.loadHotPlayMovies(locationId);
            mPresenter.loadHomeList(pageIndex++);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

        if (topbanner != null){
            topbanner.stopTurning();
        }
        if (advBanner != null){
            advBanner.stopTurning();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }

    /**
     * 根据城市id取对应的城市信息
     * @param locationId
     */
    private void getCity(int locationId) {
        mCity = cityDao.queryBuilder()
                .where(CityDao.Properties.Id.eq(locationId))
                .build()
                .unique();
    }

    @Override
    public void onLoadMoreRequested() {
        Logger.e(TAG,"-->加载页码："+pageIndex);
        mPresenter.loadHomeList(pageIndex++);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.tv_search://搜索栏
                startActivity(SearchItemActivity.class);
                break;
            case R.id.tv_location://城市
                Intent intent = new Intent(getActivity(), SearchCityActivity.class);
                startActivityForResult(intent,REQUSET);
                break;
            case R.id.tv_totalHotMovie://热映总数
                navBar.selectTab(Constants.NAV_PAYTICKET);
                break;
            case R.id.tv_totalComingMovie://即将上映总数

                break;
            case R.id.tv_totalCinemaCount://同城影院总数

                break;
            case R.id.hottest:
                Intent hottestIntent = new Intent(getActivity(), HottestActivity.class);
                startActivity(hottestIntent);
                break;
            case R.id.boxoffice:
                //进入全球票房
                Intent boxofficeIntent = new Intent(getActivity(), BoxOfficeActivity.class);
                boxofficeIntent.putExtra(Constants.AREA,Constants.GLOBAL);
                startActivity(boxofficeIntent);
                break;
            case R.id.trailer:
                //进入"发现"--"预告片"
                navBar.selectTab(Constants.NAV_FIND);
//                Intent intent1

                break;
            case R.id.live:
                Intent liveIntent = new Intent(getActivity(),WebActivity.class);
                liveIntent.putExtra(Constants.GOTO_URL, ApiConstants.live);
                startActivity(liveIntent);
                break;
            default:
                break;
        }

    }

    @Override
    public void showIndex(HomeIndex homeIndex) {
        Logger.d(TAG,"加载homeIndex成功:"+homeIndex.getSearchBarDescribe());

        setHeadData(mHeadview,homeIndex);
    }

    @Override
    public void showLoadingIndexError(String msg) {
        Logger.e(TAG,"加载homeIndex失败:"+msg);

        mAdapter.setEmptyView(errorView);
    }

    @Override
    public void showHotPlayMovies(HotPlayMovies movies) {
        Logger.d(TAG,"加载HotPlayMovies成功:"+movies.getMovies().size());

        mLocation.setText(mCity.getN());
        mTotalHotMovie.setText(String.format(getActivity()
                .getResources().getString(R.string.total_hot_movie), movies.getTotalHotMovie()));
        mTotalComingMovie.setText(String.format(getActivity()
                .getResources().getString(R.string.total_coming_movie), movies.getTotalComingMovie()));
        mTotalCinemaCount.setText(String.format(getActivity()
                .getResources().getString(R.string.total_cinema_count), movies.getTotalCinemaCount()));

        mHotPlayAdapter.setNewData(movies.getMovies());

        if (noMovies.getVisibility() == View.VISIBLE){
            noMovies.setVisibility(View.GONE);
        }
    }

    @Override
    public void showLoadingHotPlayMoviesError(String msg) {
        Logger.e(TAG,"加载HotPlayMovies失败:"+msg);

        if (noMovies.getVisibility() == View.GONE){
            noMovies.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showHomeList(List<HomeListSuper> list) {
        Logger.d(TAG,"加载homeList成功========="+list.size());
        mAdapter.addData(list);
        mAdapter.loadMoreComplete();
    }

    @Override
    public void showLoadingHomeListError(String msg) {
        Logger.e(TAG,"加载homeList失败:"+msg);

        mAdapter.loadMoreFail();
        pageIndex = pageIndex-- < 0 ? 1 : pageIndex;
    }

    @Override
    public void showNoHomeList() {

        mAdapter.loadMoreEnd(false);//true is gone,false is visible
    }

    @Override
    public void showLoading(boolean isActive) {
//        if (isActive){
//            loading.setVisibility(View.VISIBLE);
//        }else {
//            loading.setVisibility(View.GONE);
//        }
    }

    @Override
    public void showLoadingError(boolean active) {

    }


    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        mPresenter = presenter;
    }

    /**
     * 设置头部数据
     * @param view
     * @param homeIndex
     */
    private void setHeadData(View view, HomeIndex homeIndex) {
        setTopPosters(view,homeIndex.getTopPosters());
        setMallRecommends(view,homeIndex.getMallRecommends());
        setAdvList(view,homeIndex.getAdvList());
    }

    /**
     * 设置顶部海报数据
     * @param view
     * @param topPosters
     */
    private void setTopPosters(View view, List<HomeIndex.TopPostersBean> topPosters) {
        int size = topPosters.size();
        List<String> list = new ArrayList<>();
        List<GotoPageBean> infoList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(topPosters.get(i).getImg());
            infoList.add(topPosters.get(i).getGotoPage());
        }

        topbanner = $(view,R.id.topAdvBanner);
        int margin = DensityUtil.dp2px(10.0f);
        topbanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, list).setPageIndicator(new int[]{R.drawable.dot_indicator,
                R.drawable.dot_indicator_selector}, margin / 2, 0, margin / 2, 3 * margin)
                .setOnItemClickListener(new AdvBannerListener(infoList))
                .startTurning(4000);
    }

    /**
     * 设置推荐商品数据
     * @param view
     * @param mallRecommends
     */
    private void setMallRecommends(View view, List<HomeIndex.MallRecommendsBean> mallRecommends) {
        TextView goods1Title = $(view,R.id.goods1_title);
        TextView goods1TitleSmall = $(view,R.id.goods1_title_small);
        ImageView goods1Img = $(view,R.id.iv_goods1);

        TextView goods2Title = $(view,R.id.goods2_title);
        TextView goods2TitleSmall = $(view,R.id.goods2_title_small);
        ImageView goods2Img = $(view,R.id.iv_goods2);

        ImageView goods3Img = $(view,R.id.iv_goods3);

        TextView goods4Title = $(view,R.id.goods4_title);
        TextView goods4TitleSmall = $(view,R.id.goods4_title_small);
        ImageView goods4Img = $(view,R.id.iv_goods4);

        TextView goods5Title = $(view,R.id.goods5_title);
        TextView goods5TitleSmall = $(view,R.id.goods5_title_small);
        ImageView goods5Img = $(view,R.id.iv_goods5);

        TextView goods6Title = $(view,R.id.goods6_title);
        TextView goods6TitleSmall = $(view,R.id.goods6_title_small);
        ImageView goods6Img = $(view,R.id.iv_goods6);

        for (int i = 0; i < mallRecommends.size(); i++) {

            switch (mallRecommends.get(i).getPosition()){
                case 1:
                    Imager.load(getActivity(),mallRecommends.get(i).getImage(),goods1Img);
                    goods1Title.setText(mallRecommends.get(i).getTitle());
                    goods1Title.setTextColor(Color.parseColor(mallRecommends.get(i).getTitleColor()));
                    goods1TitleSmall.setText(mallRecommends.get(i).getTitleSmall());
                    break;
                case 2:
                    Imager.load(getActivity(),mallRecommends.get(i).getImage(),goods2Img);
                    goods2Title.setText(mallRecommends.get(i).getTitle());
                    goods2Title.setTextColor(Color.parseColor(mallRecommends.get(i).getTitleColor()));
                    goods2TitleSmall.setText(mallRecommends.get(i).getTitleSmall());
                    break;
                case 3:
                    Imager.load(getActivity(),mallRecommends.get(i).getImage(),goods3Img);
                    break;
                case 4:
                    Imager.load(getActivity(),mallRecommends.get(i).getImage(),goods4Img);
                    goods4Title.setText(mallRecommends.get(i).getTitle());
                    goods4Title.setTextColor(Color.parseColor(mallRecommends.get(i).getTitleColor()));
                    goods4TitleSmall.setText(mallRecommends.get(i).getTitleSmall());
                    break;
                case 5:
                    Imager.load(getActivity(),mallRecommends.get(i).getImage(),goods5Img);
                    goods5Title.setText(mallRecommends.get(i).getTitle());
                    goods5Title.setTextColor(Color.parseColor(mallRecommends.get(i).getTitleColor()));
                    goods5TitleSmall.setText(mallRecommends.get(i).getTitleSmall());
                    break;
                case 6:
                    Imager.load(getActivity(),mallRecommends.get(i).getImage(),goods6Img);
                    goods6Title.setText(mallRecommends.get(i).getTitle());
                    goods6Title.setTextColor(Color.parseColor(mallRecommends.get(i).getTitleColor()));
                    goods6TitleSmall.setText(mallRecommends.get(i).getTitleSmall());
                    break;
                default:
                    break;
            }

        }

    }

    /**
     * 设置广告条内容
     * @param view
     * @param advList
     */
    private void setAdvList(View view, List<HomeIndex.AdvListBean> advList) {
        int advSize = advList.size();
        List<String> advListImg = new ArrayList<>();
        List<GotoPageBean> gotoPage = new ArrayList<>();
        for (int i = 0; i < advSize; i++) {
            advListImg.add(advList.get(i).getImg());
            gotoPage.add(advList.get(i).getGotoPage());
        }
        advBanner = $(view,R.id.advBanner);
        advBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, advListImg).setPageIndicator(new int[]{R.drawable.dot_indicator,
                R.drawable.dot_indicator_selector})
                .setOnItemClickListener(new AdvBannerListener(gotoPage))
                .startTurning(4000);
    }

    /**
     * 广告条的点击监听
     */
    class AdvBannerListener implements OnItemClickListener{

        private List<GotoPageBean> infoList;

        public AdvBannerListener(List<GotoPageBean> infoList) {
            this.infoList = infoList;
        }

        @Override
        public void onItemClick(int position) {
            String type = infoList.get(position).getGotoType();
            switch (type){
                case "gotomovie":
                    Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
                    intent.putExtra(Constants.MOVIE_ID,infoList.get(position).getParameters().getMovieId());
                    startActivity(intent);
                    break;
                case "gotonews":
                    Intent intent1 = new Intent(getActivity(), NewsDetailActivity.class);
                    intent1.putExtra(Constants.NEWS_ID,infoList.get(position).getParameters().getNewId());
                    startActivity(intent1);
                    break;
                case "gotoweb":
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 新闻item的监听
     */
    class NewsItemLisenter extends com.chad.library.adapter.base.listener.OnItemClickListener{

        @Override
        public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
            HomeListSuper newsItem  = (HomeListSuper) adapter.getData().get(position);
            switch (newsItem.getItemType()) {
                case HomeListSuper.TYPE_NEW_MOVIE://欧美新片
                    if (newsItem instanceof NewMovie){
                        NewMovie newMovie = (NewMovie) newsItem;

                        Intent intent = new Intent(getActivity(),MovieDetailActivity.class);
                        intent.putExtra(Constants.MOVIE_ID,Integer.parseInt(newMovie.getId()));
                        startActivity(intent);
                    }
                    break;
                case HomeListSuper.TYPE_SHORT_NEWS0://简讯:0
                    if (newsItem instanceof ShortNews_51_0){
                        ShortNews_51_0 news_51_0 = (ShortNews_51_0) newsItem;

                        Intent intent = new Intent(getActivity(),NewsDetailActivity.class);
                        intent.putExtra(Constants.NEWS_ID,news_51_0.getId());
                        startActivity(intent);
                    }
                    break;
                case HomeListSuper.TYPE_LIST_IMAGES://图集
                    if (newsItem instanceof ImageList_51_1){
                        ImageList_51_1 imageList_51_1 = (ImageList_51_1) newsItem;

                        Intent intent = new Intent(getActivity(),NewsDetailActivity.class);
                        intent.putExtra(Constants.NEWS_ID,imageList_51_1.getId());
                        startActivity(intent);
                    }
                    break;
                case HomeListSuper.TYPE_SHORT_NEWS2://简讯:2
                    if (newsItem instanceof ShortNews_51_2){
                        ShortNews_51_2 news_51_2 = (ShortNews_51_2) newsItem;

                        Intent intent = new Intent(getActivity(),NewsDetailActivity.class);
                        intent.putExtra(Constants.NEWS_ID,news_51_2.getId());
                        startActivity(intent);
                    }
                    break;
                case HomeListSuper.TYPE_LIST_URL://猜电影、免费观影
                    if (newsItem instanceof GuessMovie){
                        GuessMovie guessMovie = (GuessMovie) newsItem;

                        Intent intent = new Intent(getActivity(), WebActivity.class);
                        intent.putExtra(Constants.GOTO_URL,guessMovie.getUrl());
                        startActivity(intent);
                    }
                    break;
                case HomeListSuper.TYPE_FILM_REVIEW://影评
                    if (newsItem instanceof FilmReview){
                        FilmReview filmReview = (FilmReview) newsItem;

                        Intent intent = new Intent(getActivity(), ReviewDetailActivity.class);
                        intent.putExtra(Constants.REVIEW_ID,Integer.parseInt(filmReview.getId()));
                        startActivity(intent);
                    }
                    break;
                case HomeListSuper.TYPE_TOP_LIST://榜单
                    if (newsItem instanceof TopList){
                        TopList topList = (TopList) newsItem;

                        Intent intent = new Intent(getActivity(), TopListActivity.class);
                        intent.putExtra(Constants.TOPLIST_ID,Integer.parseInt(topList.getId()));
                        startActivity(intent);
                    }

                    break;
            /*case 44:
                itemType = TYPE_LIST_VOTE;//投票
                break;*/
                default:
                    break;
            }
        }
    }
}
