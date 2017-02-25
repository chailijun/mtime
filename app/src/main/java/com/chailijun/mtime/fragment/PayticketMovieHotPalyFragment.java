package com.chailijun.mtime.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chailijun.baseadapter.ViewHolder;
import com.chailijun.baseadapter.interfaces.OnItemClickListener;
import com.chailijun.mtime.R;
import com.chailijun.mtime.moviedetail.MovieDetailActivity;
import com.chailijun.mtime.adapter.PayticketMovieHotPalyAdapter;
import com.chailijun.mtime.mvp.interf.ILocationMoviesPresenter;
import com.chailijun.mtime.mvp.interf.ILocationMoviesView;
import com.chailijun.mtime.mvp.model.BaseData;
import com.chailijun.mtime.mvp.model.movie.LocationMoviesJson;
import com.chailijun.mtime.mvp.presenter.LocationMoviesPresenter;
import com.chailijun.mtime.utils.Constants;
import com.chailijun.mtime.utils.Logger;
import com.chailijun.mtime.utils.MsgEvent;
import com.chailijun.mtime.utils.SPUtil;

import butterknife.BindView;
import de.greenrobot.event.EventBus;

@Deprecated
public class PayticketMovieHotPalyFragment extends BaseFragment implements ILocationMoviesView<BaseData> {

    @BindView(R.id.rv_hotplay)
    RecyclerView mRecyclerview;

    private int locationId;
    private ILocationMoviesPresenter presenter;
    private PayticketMovieHotPalyAdapter mAdapter;

    @Override
    public String getFragmentName() {
        return PayticketCinemaFragment.class.getSimpleName();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        presenter.unsubcrible();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_payticket_movie_hot_play;
    }

    @Override
    protected void initViews(View rootView) {
        getData();

        mAdapter = new PayticketMovieHotPalyAdapter(getActivity(),null,false);
        mAdapter.setInitLoadingView(R.layout.loading);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerview.setLayoutManager(manager);
        mRecyclerview.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new OnItemClickListener<LocationMoviesJson.MsBean>() {
            @Override
            public void onItemClick(ViewHolder viewHolder, LocationMoviesJson.MsBean data, int position) {
                Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
                intent.putExtra(Constants.MOVIE_ID,data.getId());
                getActivity().startActivity(intent);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter = new LocationMoviesPresenter(this);
        if (locationId != 0){
            mAdapter.setInitLoadingView(R.layout.loading);
            presenter.getLocationMovies(locationId);
        }

    }

    private void getData() {
        locationId = SPUtil.getInt(Constants.LOCATION_ID);
    }

    @Override
    protected void lazyLoadData() {

    }

    public void onEventMainThread(MsgEvent msg) {
        if (msg.getWhat() == Constants.UPDATE_DATA){
            locationId = SPUtil.getInt(Constants.LOCATION_ID);
            if (presenter != null && locationId != 0){
                mAdapter.removeAll();
                mAdapter.setInitLoadingView(R.layout.loading);
                presenter.getLocationMovies(locationId);
            }
        }
    }

    @Override
    public void addLocationMovies(BaseData data) {
        if (data instanceof LocationMoviesJson){
            LocationMoviesJson locationMoviesJson = (LocationMoviesJson) data;

            mAdapter.setNewData(locationMoviesJson.getMs());
        }
    }

    @Override
    public void loadFailed(String msg) {
        Logger.e("CLJ","购票--电影--正在热映加载失败==="+msg);
//        mAdapter.setInitLoadFailedView(R.layout.loading_failed);
    }
}