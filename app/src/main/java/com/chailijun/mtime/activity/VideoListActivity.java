package com.chailijun.mtime.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chailijun.baseadapter.ViewHolder;
import com.chailijun.baseadapter.base.CommonBaseAdapter;
import com.chailijun.baseadapter.interfaces.OnItemClickListener;
import com.chailijun.baseadapter.interfaces.OnLoadMoreListener;
import com.chailijun.mtime.R;
import com.chailijun.mtime.adapter.decoration.DividerItemDecoration;
import com.chailijun.mtime.mvp.interf.IVideoListPresenter;
import com.chailijun.mtime.mvp.interf.IVideoListView;
import com.chailijun.mtime.mvp.model.BaseData;
import com.chailijun.mtime.mvp.model.MediaItem;
import com.chailijun.mtime.mvp.model.movie.VideoListJson;
import com.chailijun.mtime.mvp.presenter.VideoListPresenter;
import com.chailijun.mtime.utils.Constants;
import com.chailijun.mtime.utils.Imager;
import com.chailijun.mtime.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class VideoListActivity extends BaseActivity implements IVideoListView<BaseData> {

    @BindView(R.id.head_title)
    TextView head_title;

    @BindView(R.id.recyclerview_video_list)
    RecyclerView recyclerView;

    private int movieId;
    private VideoListAdapter mAdapter;
    private IVideoListPresenter presenter;
    private View initLoadFailedView;
    private int pageIndex = 1;
    private int totalPageCount;

    //视频信息列表
    private ArrayList<MediaItem> mediaItems;

    @Override
    protected int getLayoutId() {

        return R.layout.activity_video_list;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        getData();

        head_title.setText(getString(R.string.trailer_behind));

        mAdapter = new VideoListAdapter(this,null,true);
        //设置第一次加载中的布局
        mAdapter.setInitLoadingView(R.layout.loading);
        //初始化第一次加载失败的布局
        initLoadFailedView = LayoutInflater.from(this).inflate(R.layout.loading_failed, (ViewGroup) recyclerView.getParent(), false);
        initLoadFailedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRefresh();
            }
        });
        //初始化加载更多布局
        mAdapter.setLoadMoreView(R.layout.loading_more);
        //设置加载更多触发的事件监听
        mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(boolean isReload) {
                loadMore();
            }
        });
        mAdapter.setOnItemClickListener(new MyOnItemClickListener());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));

        requestData();
    }

    private void requestData() {
        presenter = new VideoListPresenter(this);
//        if (movieId != 0){
//            presenter.getVideoList(movieId,pageIndex++);
//        }
    }

    private void onRefresh() {
        mAdapter.setInitLoadingView(R.layout.loading);
        presenter.getVideoList(movieId,1);
    }

    private void loadMore() {
        presenter.getVideoList(movieId,pageIndex++);
    }

    private void getData() {
        Intent intent = getIntent();
        movieId = intent.getIntExtra(Constants.MOVIE_ID,0);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        getData();
    }

    @OnClick(R.id.back)
    public void onClick(View view){
        finish();
    }

    @Override
    public void addVideoList(BaseData data) {
        if (data instanceof VideoListJson){
            VideoListJson videoList = (VideoListJson) data;
            totalPageCount = videoList.getTotalPageCount();

            if(videoList.getVideoList() != null && videoList.getVideoList().size()>0){
                //有数据
                //刷新数据
                mAdapter.setLoadMoreData(videoList.getVideoList());
                addMediaItems(videoList.getVideoList());
            }else {
                //请求页数达到上限
                //加载完成，更新footer view提示
                if (recyclerView.getChildCount() < 6){
                    mAdapter.setLoadEndView(R.layout.load_end_empty);
                }else {
                    mAdapter.setLoadEndView(R.layout.load_end_no_more);
                }
            }
        }
    }

    private void addMediaItems(List<VideoListJson.VideoListBean> videoList) {
        if (mediaItems == null){
            mediaItems = new ArrayList<>();
        }
        for (int i = 0; i < videoList.size(); i++) {
            VideoListJson.VideoListBean videoListBean = videoList.get(i);
            MediaItem mediaItem = new MediaItem();
            mediaItem.setId(videoListBean.getId());
            mediaItem.setName(videoListBean.getTitle());
            mediaItem.setUrl(videoListBean.getUrl());
            mediaItem.setHightUrl(videoListBean.getHightUrl());
            mediaItem.setImageUrl(videoListBean.getImage());
            mediaItem.setDuration(videoListBean.getLength()*1000);//秒-->毫秒
            mediaItems.add(mediaItem);
        }
    }

    @Override
    public void loadFailed(String msg) {
        if (pageIndex <= 2){//初次加载失败
            mAdapter.setInitLoadFailedView(initLoadFailedView);
        }else {//加载更多时失败
            mAdapter.setLoadFailedView(R.layout.load_failed_layout);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unsubcrible();
    }

    class VideoListAdapter extends CommonBaseAdapter<VideoListJson.VideoListBean>{

        public VideoListAdapter(Context context, List<VideoListJson.VideoListBean> datas, boolean isOpenLoadMore) {
            super(context, datas, isOpenLoadMore);
        }

        @Override
        protected void convert(ViewHolder holder, VideoListJson.VideoListBean data, int position) {
            Imager.load(VideoListActivity.this,data.getImage(),((ImageView) holder.getView(R.id.image)));
            holder.setText(R.id.video_title,data.getTitle());
            holder.setText(R.id.video_length, TimeUtils.stringForTime(data.getLength()*1000));
        }

        @Override
        protected int getItemLayoutId() {
            return R.layout.activity_video_list_item;
        }
    }

    class MyOnItemClickListener implements OnItemClickListener{

        @Override
        public void onItemClick(ViewHolder viewHolder, Object data, int position) {
            //进入播放器页面
            Intent intent = new Intent(VideoListActivity.this,SystemVideoPlayerActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("videolist",mediaItems);
            intent.putExtras(bundle);
            intent.putExtra("position",position);
            startActivity(intent);
        }
    }
}
