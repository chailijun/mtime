package com.chailijun.mtime.find.news;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chailijun.mtime.R;
import com.chailijun.mtime.activity.BoxOfficeActivity;
import com.chailijun.mtime.activity.NewsDetailActivity;
import com.chailijun.mtime.base.BaseFragment;
import com.chailijun.mtime.data.find.Index;
import com.chailijun.mtime.data.find.news.NewsItem;
import com.chailijun.mtime.data.find.trailer.TrailerItem;
import com.chailijun.mtime.utils.Constants;
import com.chailijun.mtime.utils.Imager;
import com.chailijun.mtime.utils.Logger;

import java.util.List;

public class FindNewsFragment extends BaseFragment implements FindNewsContract.View, BaseQuickAdapter.RequestLoadMoreListener {

    public static final String ARGUMENT = "argument";

    private RecyclerView mRecyclerView;

    private View mHeadview;
    private View loadingView;
    private View errorView;

    private FindNewsAdapter mAdapter;

    private FindNewsContract.Presenter mPresenter;

    private Index.NewsBean newsBean;
    private int pageIndex = 1;

    public static FindNewsFragment newInstance(Index.NewsBean newsBean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARGUMENT, newsBean);

        FindNewsFragment fragment = new FindNewsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        newsBean = (Index.NewsBean) bundle.getSerializable(ARGUMENT);
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_find_recyclerview;
    }

    @Override
    public void initView(View view) {

        pageIndex = 1;

        mRecyclerView = $(view,R.id.rv_find);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new FindNewsAdapter(null);

        //头布局
        mHeadview = getActivity().getLayoutInflater()
                .inflate(R.layout.fragment_find_news_index, (ViewGroup) mRecyclerView.getParent(), false);
        initHeadData();

        //加载中
        loadingView = getActivity().getLayoutInflater()
                .inflate(R.layout.loading, (ViewGroup) mRecyclerView.getParent(), false);
        //加载错误
        errorView = getActivity().getLayoutInflater()
                .inflate(R.layout.loading_failed, (ViewGroup) mRecyclerView.getParent(), false);
        errorView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                onRefresh();
            }
        });

        mAdapter.addHeaderView(mHeadview);
        mAdapter.setHeaderAndEmpty(false);
        mAdapter.setOnLoadMoreListener(this);

        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                NewsItem newsItem = (NewsItem) adapter.getData().get(position);

                Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
                intent.putExtra(Constants.NEWS_ID,newsItem.getId());
                startActivity(intent);
            }
        });
    }

    private void onRefresh() {
        mAdapter.setEmptyView(loadingView);
        mPresenter.loadNews(pageIndex++);
    }

    @Override
    public void doBusiness(Context mContext) {
        mAdapter.setEmptyView(loadingView);
        mPresenter.loadNews(pageIndex++);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }

    @Override
    public void widgetClick(View v) {

    }

    @Override
    public void showNews(List<NewsItem> newsItems) {
        Logger.d(TAG,"发现--新闻 加载成功:"+newsItems.size());

        mAdapter.addData(newsItems);
        mAdapter.loadMoreComplete();

    }

    @Override
    public void showLoadingNewsError(String msg) {
        Logger.e(TAG,"发现--新闻 加载失败:"+msg);
//        mAdapter.setEmptyView(errorView);

        mAdapter.loadMoreFail();
        pageIndex = pageIndex-- < 0 ? 1 : pageIndex;
    }

    @Override
    public void showNoNews() {
        mAdapter.loadMoreEnd();
    }

    @Override
    public void setPresenter(FindNewsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    /**
     * 初始化头部数据
     */
    private void initHeadData() {
        if (newsBean == null){
            return;
        }

        ImageView headImg = $(mHeadview,R.id.iv_img);
        TextView headTitle = $(mHeadview,R.id.tv_title);

        Imager.load(getActivity(),newsBean.getImageUrl(), headImg);
        headTitle.setText(newsBean.getTitle());

        //index部分的点击事件
        headImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
                intent.putExtra(Constants.NEWS_ID,newsBean.getNewsID());
                startActivity(intent);
            }
        });

        //进入票房榜（内地）
        $(mHeadview,R.id.mainland_boxoffice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BoxOfficeActivity.class);
                intent.putExtra(Constants.AREA,Constants.MAINLAND);
                startActivity(intent);
            }
        });

        //进入票房榜（全球）
        $(mHeadview,R.id.gloable_boxoffice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BoxOfficeActivity.class);
                intent.putExtra(Constants.AREA,Constants.GLOBAL);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.loadNews(pageIndex++);
    }
}
