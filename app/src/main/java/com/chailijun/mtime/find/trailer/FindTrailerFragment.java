package com.chailijun.mtime.find.trailer;

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
import com.chailijun.mtime.activity.SystemVideoPlayerActivity;
import com.chailijun.mtime.base.BaseFragment;
import com.chailijun.mtime.data.find.Index;
import com.chailijun.mtime.data.find.trailer.TrailerItem;
import com.chailijun.mtime.mvp.model.MediaItem;
import com.chailijun.mtime.utils.Imager;
import com.chailijun.mtime.utils.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * author： Chailijun
 * date  ： 2017/1/15 17:06
 * e-mail： 1499505466@qq.com
 */

public class FindTrailerFragment extends BaseFragment implements FindTrailerContract.View{

    public static final String ARGUMENT = "argument";

    private RecyclerView mRecyclerView;

    private View mHeadview;
    private View loadingView;
    private View errorView;

    private FindTrailerAdapter mAdapter;

    private FindTrailerContract.Presenter mPresenter;

    private Index.TrailerBean trailerBean;
    //视频信息列表
    private ArrayList<MediaItem> mediaItems = new ArrayList<>();

    public static FindTrailerFragment newInstance(Index.TrailerBean trailerBean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARGUMENT, trailerBean);

        FindTrailerFragment fragment = new FindTrailerFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        trailerBean = (Index.TrailerBean) bundle.getSerializable(ARGUMENT);

        addMediaItems(trailerBean,0);
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
        mRecyclerView = $(view,R.id.rv_find);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new FindTrailerAdapter(null);

        //头布局
        mHeadview = getActivity().getLayoutInflater()
                .inflate(R.layout.fragment_find_trailer_index, (ViewGroup) mRecyclerView.getParent(), false);
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

        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                playVideo(position);
            }
        });
    }

    private void onRefresh() {
        mAdapter.setEmptyView(loadingView);
        mPresenter.loadTrailer();
    }

    @Override
    public void doBusiness(Context mContext) {
        mAdapter.setEmptyView(loadingView);
        mPresenter.loadTrailer();
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
    public void showTrailer(List<TrailerItem> trailers) {
        Logger.d(TAG,"发现--预告片 加载成功:"+trailers.size());

        mAdapter.setNewData(trailers);

        for (int i = 0; i < trailers.size(); i++) {
            addMediaItems(trailers.get(i),-1);
        }
    }

    @Override
    public void showLoadingTrailerError(String msg) {
        Logger.e(TAG,"发现--预告片 加载失败:"+msg);
        mAdapter.setEmptyView(errorView);
    }

    @Override
    public void setPresenter(FindTrailerContract.Presenter presenter) {
        mPresenter = presenter;
    }

    /**
     * 初始化头部数据
     */
    private void initHeadData() {
        ImageView headImg = $(mHeadview,R.id.iv_img);
        TextView headTitle = $(mHeadview,R.id.tv_title);

        Imager.load(getActivity(),trailerBean.getImageUrl(), headImg);
        headTitle.setText(trailerBean.getTitle());

        headImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playVideo(-1);
            }
        });
    }

    /**
     * 进入播放器页面并播放视频
     * @param position
     */
    private void playVideo(int position) {

        position = position > -1 ? position + 1 : 0;

        //进入播放器页面
        Intent intent = new Intent(getActivity(),SystemVideoPlayerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("videolist",mediaItems);
        intent.putExtras(bundle);
        intent.putExtra("position",position);
        startActivity(intent);
    }

    /**
     * 将视频资源添加到列表中
     * @param videoSource
     * @param position 资源在列表中的位置
     */
    private void addMediaItems(Object videoSource, int position) {

        if (videoSource instanceof Index.TrailerBean){
            Index.TrailerBean source = (Index.TrailerBean) videoSource;

            MediaItem mediaItem = new MediaItem();

            mediaItem.setId(source.getMovieId());
            mediaItem.setName(source.getTitle());
            mediaItem.setUrl(source.getMp4Url());
            mediaItem.setHightUrl(source.getHightUrl());
            mediaItem.setImageUrl(source.getImageUrl());

            mediaItems.add(position,mediaItem);
        }

        if (videoSource instanceof TrailerItem){
            TrailerItem source = (TrailerItem) videoSource;

            MediaItem mediaItem = new MediaItem();

            mediaItem.setId(source.getMovieId());
            mediaItem.setName(source.getMovieName());
            mediaItem.setUrl(source.getUrl());
            mediaItem.setHightUrl(source.getHightUrl());
            mediaItem.setImageUrl(source.getCoverImg());
            mediaItem.setDuration(source.getVideoLength()*1000);//秒-->毫秒

            mediaItems.add(mediaItem);
        }
    }
}
