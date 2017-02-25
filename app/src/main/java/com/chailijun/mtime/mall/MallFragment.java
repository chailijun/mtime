package com.chailijun.mtime.mall;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chailijun.mtime.R;
import com.chailijun.mtime.activity.GoodsListActivity;
import com.chailijun.mtime.activity.WebActivity;
import com.chailijun.mtime.adapter.NetworkImageHolderView;
import com.chailijun.mtime.base.BaseFragment;
import com.chailijun.mtime.data.mall.MallIndex;
import com.chailijun.mtime.data.mall.RecommendProducts;
import com.chailijun.mtime.mall.index.CategoryGoodsAdapter;
import com.chailijun.mtime.mall.index.NavigatorAdapter;
import com.chailijun.mtime.mall.index.SpecialTopicListAdapter;
import com.chailijun.mtime.mall.index.TopicFragment;
import com.chailijun.mtime.mall.index.TopicSubListfragment;
import com.chailijun.mtime.utils.Constants;
import com.chailijun.mtime.utils.DensityUtil;
import com.chailijun.mtime.utils.Imager;
import com.chailijun.mtime.utils.Logger;

import java.util.ArrayList;
import java.util.List;

import static com.chailijun.mtime.R.id.rv_category;
import static com.chailijun.mtime.R.id.rv_mall;
import static com.chailijun.mtime.R.id.search_bg;


public class MallFragment extends BaseFragment implements MallContract.View, BaseQuickAdapter.RequestLoadMoreListener {

    public static final String ARGUMENT = "argument";

    private ImageView mGotop;
    private ImageView mTitleBg;
    private LinearLayout mLoading;

    private RecyclerView mRecyclerView;

    private MallAdapter mAdapter;

    private MallContract.Presenter mPresenter;

    private View loadingView;
    private View noDataView;
    private View errorView;
    private View mHeadView;
    private ConvenientBanner mTopbanner;

    private GridLayoutManager gridLayoutManager;

    private int pageIndex = 1;
    //RecyclerView y轴方向滚动距离总和
    private int totalDisY;

    //RecyclerView位置记录值
    private int lastOffset;
    private int lastPosition;

    public static MallFragment newInstance(String argument) {
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT, argument);

        MallFragment fragment = new MallFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_mall;
    }

    @Override
    public void initView(View view) {

        mGotop = $(view,R.id.gotop);
        mTitleBg = $(view,R.id.search_bg);
        mLoading = $(view,R.id.loading);

        mRecyclerView = $(view, rv_mall);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(gridLayoutManager = new GridLayoutManager(getActivity(),2));
//        mRecyclerView.setFocusable(false);

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

        final int screenHeight = DensityUtil.getScreenHeight();
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalDisY += dy;

                //设置搜索栏背景透明度
                if (totalDisY < 500){
                    mTitleBg.setAlpha(1.0f/500.0f * (float)totalDisY);
                }else {
                    mTitleBg.setAlpha(1.0f);
                }

                if (totalDisY > screenHeight){
                    mGotop.setVisibility(View.VISIBLE);

                    mGotop.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            gridLayoutManager.scrollToPositionWithOffset(0,0);
                            totalDisY = 0;
                            mTitleBg.setAlpha(0.0f);
                            mGotop.setVisibility(View.GONE);
                        }
                    });
                }else {
                    mGotop.setVisibility(View.GONE);
                }
            }

           /* @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(recyclerView.getLayoutManager() != null) {
                    getPositionAndOffset();
                }
            }*/
        });
    }

    /**
     * 记录RecyclerView当前位置
     */
    private void getPositionAndOffset() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        //获取可视的第一个view
        View topView = layoutManager.getChildAt(0);
        if(topView != null) {
            //获取与该view的顶部的偏移量
            lastOffset = topView.getTop();
            //得到该View的数组位置
            lastPosition = layoutManager.getPosition(topView);

            Logger.e(TAG,"lastOffset====="+lastOffset);
            Logger.e(TAG,"lastPosition==="+lastPosition);
        }
    }

    /**
     * 让RecyclerView滚动到指定位置
     */
    private void scrollToPosition() {
        if(mRecyclerView.getLayoutManager() != null && lastPosition >= 0) {
            ((LinearLayoutManager) mRecyclerView.getLayoutManager()).scrollToPositionWithOffset(lastPosition, lastOffset);
        }
    }

    private void onRefresh() {
        mAdapter.setEmptyView(loadingView);
        mPresenter.loadMallIndex();
    }

    private void initAdapter() {
        mAdapter = new MallAdapter(null);
//        mHeadView = getActivity().getLayoutInflater().inflate(R.layout.fragment_mall_head,
//                (ViewGroup) mRecyclerView.getParent(), false);
//        mAdapter.addHeaderView(mHeadView);
//        mAdapter.setHeaderAndEmpty(false);
        mAdapter.setOnLoadMoreListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
//        mPresenter.loadRecommendProducts("",1);
       /* scrollToPosition();
        mRecyclerView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });*/
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();

        if (mTopbanner != null){
            mTopbanner.stopTurning();
        }
    }

    @Override
    public void doBusiness(Context mContext) {
        mPresenter.loadMallIndex();
    }

    @Override
    public void widgetClick(View v) {

    }

    @Override
    public void onLoadMoreRequested() {
        Logger.e(TAG,"-->加载页码："+pageIndex);
        mPresenter.loadRecommendProducts("",pageIndex++);
    }

    @Override
    public void showLoading(boolean isActive) {
        mLoading.setVisibility(isActive?View.VISIBLE:View.GONE);
    }

    @Override
    public void showMallIndex(MallIndex index) {

        mHeadView = getActivity().getLayoutInflater().inflate(R.layout.fragment_mall_head,
                (ViewGroup) mRecyclerView.getParent(), false);
        mAdapter.addHeaderView(mHeadView);
//        mAdapter.setHeaderAndEmpty(false);

        setHeadData(index);
        mPresenter.loadRecommendProducts("",pageIndex++);
    }

    @Override
    public void showLoadingMallIndexError(String msg) {
        Logger.e(TAG,"商场索引加载失败:"+msg);
//        showToast("当前网络不可用，请检查后重试");
        mAdapter.setEmptyView(errorView);
    }

    @Override
    public void showNoIndex() {

    }

    @Override
    public void showRecomProducts(List<RecommendProducts.GoodsListBean> goodsList) {
        mAdapter.addData(goodsList);
        mAdapter.loadMoreComplete();
    }

    @Override
    public void showLoadingRecomProductsError(String msg) {
        mAdapter.loadMoreFail();
        pageIndex = pageIndex-- < 0 ? 1 : pageIndex;
    }

    @Override
    public void showNoRecomProducts() {
        //没有更多
        mAdapter.loadMoreEnd(false);//true is gone,false is visible
    }

    @Override
    public void setPresenter(MallContract.Presenter presenter) {
        mPresenter = presenter;
    }

    /**
     * 设置头部信息（商城索引）
     * @param index
     */
    private void setHeadData(MallIndex index) {
        setAdvBanner(index.getMall().getScrollImg());
        setNavigatorIcon(index.getMall().getNavigatorIcon());
        setCell(index.getMall());
        setSpecialTopicList(index.getSpecialTopicList());
        setTopic(index.getMall().getTopic());
        setCategory(index.getMall().getCategory());
    }




    /**
     * 商城索引--顶部循环滚动的广告条
     * @param scrollImg
     */
    private void setAdvBanner(List<MallIndex.MallBean.ScrollImgBean> scrollImg) {
        List<String> imageList = new ArrayList<>();
        List<String> urlList = new ArrayList<>();
        for (int i = 0; i < scrollImg.size(); i++) {
            imageList.add(scrollImg.get(i).getImage());
            urlList.add(scrollImg.get(i).getUrl());
        }

        mTopbanner = $(mHeadView,R.id.topAdvBanner);
        mTopbanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, imageList).setPageIndicator(new int[]{R.drawable.dot_indicator, R.drawable.dot_indicator_selector})
                .setOnItemClickListener(new TopAdvBannerListener(urlList))
                .startTurning(4000);
    }

    class TopAdvBannerListener implements com.bigkoo.convenientbanner.listener.OnItemClickListener{

        private List<String> urlList;
        public TopAdvBannerListener(List<String> urlList) {
            this.urlList = urlList;
        }

        @Override
        public void onItemClick(int position) {
            Intent intent = new Intent(getActivity(), WebActivity.class);
            intent.putExtra(Constants.GOTO_URL,urlList.get(position));
            startActivity(intent);
        }

    }

    /**
     * 商城索引--分类导航
     * @param navigatorIcon
     */
    private void setNavigatorIcon(List<MallIndex.MallBean.NavigatorIconBean> navigatorIcon) {
        RecyclerView rvNavigator = $(mHeadView,R.id.rv_navigator);
        NavigatorAdapter navigatorAdapter = new NavigatorAdapter(navigatorIcon);
        rvNavigator.setHasFixedSize(true);
        rvNavigator.setLayoutManager(new GridLayoutManager(getActivity(), 4, LinearLayoutManager.VERTICAL, false));
        rvNavigator.setAdapter(navigatorAdapter);

        rvNavigator.addOnItemTouchListener(new com.chad.library.adapter.base.listener.OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                MallIndex.MallBean.NavigatorIconBean navigatorIconBean
                        = (MallIndex.MallBean.NavigatorIconBean) adapter.getData().get(position);

                Intent intent = new Intent(getActivity(),GoodsListActivity.class);
                intent.putExtra(Constants.GOODS_URL,navigatorIconBean.getUrl());
                startActivity(intent);
            }
        });
    }

    /**
     * 商城索引--cellA、cellB、cellC
     * @param mall
     */
    private void setCell(MallIndex.MallBean mall) {
        ImageView cellA = $(mHeadView,R.id.cellA);
        ImageView cellB = $(mHeadView,R.id.cellB);
        ImageView cellC_0 = $(mHeadView,R.id.cellC_0);
        ImageView cellC_1 = $(mHeadView,R.id.cellC_1);

        Imager.load(getActivity(), mall.getCellA().getImg(), cellA);
        Imager.load(getActivity(), mall.getCellB().getImg(), cellB);

        Imager.load(getActivity(),mall.getCellC().getList().get(0).getImage(),cellC_0);
        Imager.load(getActivity(),mall.getCellC().getList().get(1).getImage(),cellC_1);
    }

    /**
     * 商城索引--特别主题商品列表
     * @param specialTopicList
     */
    private void setSpecialTopicList(List<MallIndex.SpecialTopicListBean> specialTopicList) {
        RecyclerView rv_specialTopicList = $(mHeadView,R.id.rv_specialTopicList);
        rv_specialTopicList.setHasFixedSize(true);

        SpecialTopicListAdapter adapter = new SpecialTopicListAdapter(specialTopicList);

        rv_specialTopicList.setLayoutManager(
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rv_specialTopicList.setAdapter(adapter);
    }

    private int tabPosition = -1;

    /**
     * 商城索引--主题商品
     * @param topic
     */
    private void setTopic(List<MallIndex.MallBean.TopicBean> topic) {


//        FragmentManager fragmentManager = getChildFragmentManager();
//        fragmentManager.findFragmentById()
//        FrameLayout container = $(mHeadView,R.id.container);
        final FragmentManager fragmentManager = getChildFragmentManager();
        ArrayList arrayList = (ArrayList) topic;
        fragmentManager.beginTransaction().add(R.id.container_topic,TopicFragment.newInstance(arrayList)).commit();


        /*final List<Fragment> subFragments = new ArrayList<>();
        TabLayout tabsTopic = $(mHeadView, R.id.tabs_topic);
        for (int i = 0; i < topic.size(); i++) {
            TabLayout.Tab tab = tabsTopic.newTab();

            ImageView topicImg = (ImageView) LayoutInflater
                    .from(getActivity())
                    .inflate(R.layout.fragment_mall_topic_tab, null);
            ViewGroup.LayoutParams params =
                    new ViewGroup.LayoutParams(DensityUtil.dp2px(80.0f), DensityUtil.dp2px(80.0f));
            topicImg.setLayoutParams(params);
            Imager.load(getActivity(), topic.get(i).getUncheckImage(), topicImg);
            tab.setCustomView(topicImg);

            tabsTopic.addTab(tab, i == (tabPosition == -1 ? 0 : tabPosition) ? true : false);

            TopicSubListfragment topicSubListfragment =
                    (TopicSubListfragment) fragmentManager.findFragmentById(R.id.fl_topic);
            if(topicSubListfragment == null){
                topicSubListfragment = TopicSubListfragment.newInstance(topic.get(i));
            }

            subFragments.add(topicSubListfragment);
        }
        //默认显示第一个Fragment
//        mHeadView.findf


        if (tabPosition == -1) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            if (subFragments.get(0).isAdded()) {
                transaction.show(subFragments.get(0));
            } else {
                transaction.add(R.id.fl_topic, subFragments.get(0));
            }
            transaction.commit();
        }

        tabsTopic.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                tabPosition = position;
                Logger.e(TAG, "选中position:" + position);

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                if (subFragments.get(position).isAdded()) {
                    transaction.show(subFragments.get(position));
                } else {
                    transaction.add(R.id.fl_topic, subFragments.get(position));
                }
                transaction.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Logger.e(TAG, "解除position:" + position);
                if (subFragments.get(position).isAdded()) {
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.hide(subFragments.get(position));
                    transaction.commit();
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });*/
    }

    /**
     * 商城索引--分类商品
     * @param category
     */
    private void setCategory(List<MallIndex.MallBean.CategoryBean> category) {
        RecyclerView rvCategory = $(mHeadView, rv_category);
        rvCategory.setHasFixedSize(true);
        rvCategory.setLayoutManager(
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        CategoryGoodsAdapter adapter = new CategoryGoodsAdapter(category);
        rvCategory.setAdapter(adapter);
    }
}
