package com.chailijun.mtime.find;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chailijun.baseadapter.ViewHolder;
import com.chailijun.baseadapter.interfaces.OnMultiItemClickListeners;
import com.chailijun.mtime.R;
import com.chailijun.mtime.activity.SystemVideoPlayerActivity;
import com.chailijun.mtime.adapter.FindTrailerAdapter;
import com.chailijun.mtime.base.BaseFragment;
import com.chailijun.mtime.data.find.Index;
import com.chailijun.mtime.mvp.interf.ITrailerListPresenter;
import com.chailijun.mtime.mvp.interf.ITrailerListView;
import com.chailijun.mtime.mvp.model.BaseData;
import com.chailijun.mtime.mvp.model.MediaItem;
import com.chailijun.mtime.mvp.model.movie.TrailerList;
import com.chailijun.mtime.mvp.model.news.IndexInfo;
import com.chailijun.mtime.mvp.presenter.TrailerListPresenter;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class FindTrailerFragment extends BaseFragment implements ITrailerListView<BaseData> {


    private static final String TRAILER = "trailer";

    private RecyclerView mRecyclerView;
    private FindTrailerAdapter mAdapter;

    private ITrailerListPresenter mPresenter;

    private IndexInfo.TrailerBean mTrailerIndex;

    //视频信息列表
    private ArrayList<MediaItem> mediaItems = new ArrayList<>();;
    private View initLoadFailedView;

    public static FindTrailerFragment newInstance(Index.TrailerBean trailerBean) {
        FindTrailerFragment fragment = new FindTrailerFragment();
        Bundle args = new Bundle();
        args.putSerializable(TRAILER,trailerBean);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        mTrailerIndex = (IndexInfo.TrailerBean) arguments.getSerializable(TRAILER);

        mPresenter = new TrailerListPresenter(this);
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
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_find);

        mAdapter = new FindTrailerAdapter(getActivity(),null,false);

        mAdapter.setInitLoadingView(R.layout.loading);
        initLoadFailedView = LayoutInflater.from(getActivity()).inflate(R.layout.loading_failed, (ViewGroup) mRecyclerView.getParent(), false);
        initLoadFailedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRefresh();
            }
        });

        mRecyclerView.setLayoutManager(
                new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnMultiItemClickListener(new OnMultiItemClickListeners<BaseData>() {
            @Override
            public void onItemClick(ViewHolder viewHolder, BaseData data, int position, int viewType) {

                //进入播放器页面
                Intent intent = new Intent(getActivity(),SystemVideoPlayerActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("videolist",mediaItems);
                intent.putExtras(bundle);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });
    }

    @Override
    public void doBusiness(Context mContext) {
        mPresenter.getTrailerList();
    }

    @Override
    public void widgetClick(View v) {

    }

    private void onRefresh() {
        mAdapter.setInitLoadingView(R.layout.loading);
        mPresenter.getTrailerList();
    }


    @Override
    public void addTrailerList(BaseData data) {

        if (data instanceof TrailerList){
            //所有预告片加载成功才会将index部分的内容一起显示
            List<BaseData> list = new ArrayList<>();
            if (mTrailerIndex != null){
                list.add(mTrailerIndex);

                //将视频资源添加到列表中
                addMediaItems(mTrailerIndex,0);
            }

            TrailerList trailerList = (TrailerList) data;
            if (trailerList.getTrailers() != null && trailerList.getTrailers().size() > 0){

                for (int i = 0; i < trailerList.getTrailers().size(); i++) {
                    list.add(trailerList.getTrailers().get(i));

                    //将视频资源添加到列表中
                    addMediaItems(trailerList.getTrailers().get(i),-1);
                }
                mAdapter.setNewData(list);
            }
        }
    }

    @Override
    public void loadFailed(String msg) {
        mAdapter.setInitLoadFailedView(initLoadFailedView);
    }

    /**
     * 将视频资源添加到列表中
     * @param videoSource
     * @param position 资源在列表中的位置
     */
    private void addMediaItems(BaseData videoSource,int position) {

        if (videoSource instanceof IndexInfo.TrailerBean){
            IndexInfo.TrailerBean source = (IndexInfo.TrailerBean) videoSource;

            MediaItem mediaItem = new MediaItem();

            mediaItem.setId(source.getMovieId());
            mediaItem.setName(source.getTitle());
            mediaItem.setUrl(source.getMp4Url());
            mediaItem.setHightUrl(source.getHightUrl());
            mediaItem.setImageUrl(source.getImageUrl());

            mediaItems.add(position,mediaItem);
        }

        if (videoSource instanceof TrailerList.TrailersBean){
            TrailerList.TrailersBean source = (TrailerList.TrailersBean) videoSource;

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
