package com.chailijun.mtime.payticket.movie;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chailijun.mtime.R;
import com.chailijun.mtime.base.BaseFragment;
import com.chailijun.mtime.data.payticket.LocationMoviesItem;
import com.chailijun.mtime.moviedetail.MovieDetailActivity;
import com.chailijun.mtime.utils.Constants;
import com.chailijun.mtime.utils.Logger;
import com.chailijun.mtime.utils.SPUtil;

import java.util.List;

/**
 * author： Chailijun
 * date  ： 2017/1/9 22:30
 * e-mail： 1499505466@qq.com
 */

public class HotPalyFragment extends BaseFragment implements HotPlayContract.View{

    public static final String ARGUMENT = "argument";

    private RecyclerView mRecyclerView;

    private HotPlayAdapter mAdapter;

    private HotPlayContract.Presenter mPresenter;

    private View loadingView;
    private View noDataView;
    private View errorView;

    private int locationId;

    public static HotPalyFragment newInstance(String argument) {
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT, argument);

        HotPalyFragment fragment = new HotPalyFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_payticket_movie_hot_play;
    }

    @Override
    public void initView(View view) {
        mRecyclerView = $(view, R.id.rv_hotplay);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //加载中
        loadingView = getActivity().getLayoutInflater()
                .inflate(R.layout.loading, (ViewGroup) mRecyclerView.getParent(), false);
        //没有数据
        noDataView = getActivity().getLayoutInflater()
                .inflate(R.layout.empty_view, (ViewGroup) mRecyclerView.getParent(), false);
        noDataView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefresh();
            }
        });
        //加载错误
        errorView = getActivity().getLayoutInflater()
                .inflate(R.layout.loading_failed, (ViewGroup) mRecyclerView.getParent(), false);
        errorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefresh();
            }
        });

        initAdapter();
//        onRefresh();
    }

    private void onRefresh() {
        mAdapter.setEmptyView(loadingView);
//        locationId = SPUtil.getInt(Constants.LOCATION_ID);
        if (locationId != 0)
            mPresenter.loadMovies(locationId);
    }

    private void initAdapter() {
        mAdapter = new HotPlayAdapter(null);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                LocationMoviesItem item = (LocationMoviesItem) adapter.getData().get(position);

                Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
                intent.putExtra(Constants.MOVIE_ID,item.getId());
                startActivity(intent);
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    @Override
    public void doBusiness(Context mContext) {
        locationId = SPUtil.getInt(Constants.LOCATION_ID);
        if (locationId != 0)
            mPresenter.loadMovies(locationId);
    }

    @Override
    public void widgetClick(View v) {

    }


    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void showMovies(List<LocationMoviesItem> movies) {
        Logger.d(TAG,""+movies.get(0).getT());
        mAdapter.setNewData(movies);
    }

    @Override
    public void showLoadingMoviesError(String msg) {
        Logger.e(TAG,"热放电影--请求错误:"+msg);
        mAdapter.setEmptyView(errorView);
    }

    @Override
    public void showNoMovies() {
        Logger.e(TAG,"热放电影--没有数据");
        mAdapter.setEmptyView(noDataView);
    }

    @Override
    public void setPresenter(HotPlayContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
