package com.chailijun.mtime.homepage;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import com.chailijun.mtime.moviedetail.MovieDetailActivity;
import com.chailijun.mtime.utils.Constants;
import com.chailijun.mtime.utils.DensityUtil;
import com.chailijun.mtime.utils.Imager;
import com.chailijun.mtime.utils.Logger;
import com.chailijun.mtime.utils.SPUtil;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


@Deprecated
public class HomeFragment1 extends BaseFragment implements
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

    private TextView mLocation;
    private TextView mTotalHotMovie;
    private TextView mTotalComingMovie;
    private TextView mTotalCinemaCount;
    private View mHeadview;

    /**热放电影RecyclerView*/
    private RecyclerView mRVHotPlay;
    /**热放电影Adapter*/
    private HotPlayAdapter mHotPlayAdapter;

    public static HomeFragment1 newInstance(String argument) {
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT, argument);

        HomeFragment1 fragment = new HomeFragment1();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUSET && resultCode == RESULT_OK){

            locationId = (int) data.getLongExtra(Constants.LOCATION_ID, 0);
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
        loading = $(view,R.id.loading);
        mRecyclerView = $(view,R.id.rv_home);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //加载中
        loadingView = getActivity().getLayoutInflater()
                .inflate(R.layout.loading, (ViewGroup) mRecyclerView.getParent(), false);
        //没有数据
        noDataView = getActivity().getLayoutInflater()
                .inflate(R.layout.loading_failed, (ViewGroup) mRecyclerView.getParent(), false);
        noDataView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                onRefresh();
            }
        });
        //加载错误
        errorView = getActivity().getLayoutInflater()
                .inflate(R.layout.loading_failed, (ViewGroup) mRecyclerView.getParent(), false);
        errorView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                onRefresh();
            }
        });

        initAdapter();
        //onRefresh();
    }

    private void initAdapter() {
        mAdapter = new HomeAdapter(null);

        mHeadview = getActivity().getLayoutInflater()
                .inflate(R.layout.home_page_head, (ViewGroup) mRecyclerView.getParent(), false);

        $(mHeadview,R.id.tv_search).setOnClickListener(this);

        mLocation = $(mHeadview,R.id.tv_location);
        mTotalHotMovie = $(mHeadview, R.id.tv_totalHotMovie);
        mTotalComingMovie = $(mHeadview, R.id.tv_totalComingMovie);
        mTotalCinemaCount = $(mHeadview, R.id.tv_totalCinemaCount);

        //城市、热映总数、即将上映总数、同城影院总数 点击监听
        mLocation.setOnClickListener(this);
        mTotalHotMovie.setOnClickListener(this);
        mTotalComingMovie.setOnClickListener(this);
        mTotalCinemaCount.setOnClickListener(this);

        mAdapter.addHeaderView(mHeadview);


        mAdapter.setHeaderAndEmpty(false);
        mAdapter.setOnLoadMoreListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void onRefresh() {
        mAdapter.setEmptyView(loadingView);
//        onLoadMoreRequested();
        if (locationId != 0) mPresenter.loadHomeIndex(locationId);
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

    @Override
    public void doBusiness(Context mContext) {
        cityDao = MtimeApp.getInstances().getmDaoSession().getCityDao();
        locationId = SPUtil.getInt(Constants.LOCATION_ID);

        if (locationId != 0){
            getCity(locationId);
            mPresenter.loadHomeIndex(locationId);
        }

//        onLoadMoreRequested();
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
                BottomNavigationBar navBar = (BottomNavigationBar) getActivity().findViewById(R.id.nav_bar);
                navBar.selectTab(Constants.NAV_PAYTICKET);
                break;
            case R.id.tv_totalComingMovie://即将上映总数

                break;
            case R.id.tv_totalCinemaCount://同城影院总数

                break;
            default:
                break;
        }

    }

    @Override
    public void showLoading(boolean isActive) {
        if (isActive){
            loading.setVisibility(View.VISIBLE);
        }else {
            loading.setVisibility(View.GONE);
        }
    }

    @Override
    public void showLoadingError(boolean active) {

    }

    @Override
    public void showIndex(HomeIndex homeIndex) {
//        Logger.e(TAG,"加载homeIndex成功===="+homeIndex.getSearchBarDescribe());
//        Logger.e(TAG,"加载homeIndex成功===="+homeIndex.getHotPlayMovies().getCount());
//        showToast(homeIndex.getSearchBarDescribe());


        setHeadData(mHeadview,homeIndex);

        onLoadMoreRequested();
    }

    @Override
    public void showHomeList(List<HomeListSuper> list) {
        Logger.e(TAG,"加载homeList成功===="+list.size());
        mAdapter.addData(list);
        mAdapter.loadMoreComplete();
    }

    @Override
    public void showLoadingHomeListError(String msg) {
        Logger.e(TAG,"加载homeList失败："+msg);
        showToast("加载homeList失败："+msg);
        mAdapter.loadMoreFail();
        pageIndex = pageIndex-- < 0 ? 1 : pageIndex;
    }

    @Override
    public void showLoadingIndexError(String msg) {
        Logger.e(TAG,"加载homeIndex失败："+msg);
        showToast("当前网络不可用，请检查后重试");
        mAdapter.setEmptyView(errorView);
    }


    @Override
    public void showNoHomeList() {
        mAdapter.loadMoreEnd(false);//true is gone,false is visible
    }

    @Override
    public void showHotPlayMovies(HotPlayMovies movies) {
        mLocation.setText(mCity.getN());
        mTotalHotMovie.setText(String.format(getActivity()
                .getResources().getString(R.string.total_hot_movie), movies.getTotalHotMovie()));
        mTotalComingMovie.setText(String.format(getActivity()
                .getResources().getString(R.string.total_coming_movie), movies.getTotalComingMovie()));
        mTotalCinemaCount.setText(String.format(getActivity()
                .getResources().getString(R.string.total_cinema_count), movies.getTotalCinemaCount()));

        mHotPlayAdapter.setNewData(movies.getMovies());
    }

    @Override
    public void showLoadingHotPlayMoviesError(String msg) {

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
//        setHotPlay(view, homeIndex.getHotPlayMovies());
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
     * 设置头部热放电影数据
     * @param view
     * @param hotPlayMovies
     */
    private void setHotPlay(View view, HotPlayMovies hotPlayMovies) {
        mRVHotPlay = $(view, R.id.rv_hotplaymovie);
        mRVHotPlay.setLayoutManager(
                new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        mRVHotPlay.setHasFixedSize(true);
        mHotPlayAdapter = new HotPlayAdapter(hotPlayMovies.getMovies());
        mRVHotPlay.setAdapter(mHotPlayAdapter);

        //热放电影的item点击事件
        mRVHotPlay.addOnItemTouchListener(new com.chad.library.adapter.base.listener.OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                HotPlayMovies.MoviesBean moviesBean
                        = (HotPlayMovies.MoviesBean) adapter.getData().get(position);
                Intent intent = new Intent(getActivity(),MovieDetailActivity.class);
                intent.putExtra(Constants.MOVIE_ID,moviesBean.getMovieId());
                startActivity(intent);
            }
        });

        mLocation.setText(mCity.getN());
        mTotalHotMovie.setText(String.format(getActivity().getResources().getString(R.string.total_hot_movie), hotPlayMovies.getTotalHotMovie()));
        mTotalComingMovie.setText(String.format(getActivity().getResources().getString(R.string.total_coming_movie), hotPlayMovies.getTotalComingMovie()));
        mTotalCinemaCount.setText(String.format(getActivity().getResources().getString(R.string.total_cinema_count), hotPlayMovies.getTotalCinemaCount()));
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
                default:
                    break;
            }
        }
    }
}
