package com.chailijun.mtime.payticket.movie;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chailijun.mtime.R;
import com.chailijun.mtime.moviedetail.MovieDetailActivity;
import com.chailijun.mtime.adapter.decoration.SuspensionDecoration;
import com.chailijun.mtime.base.BaseFragment;
import com.chailijun.mtime.data.payticket.MovieComingNew;
import com.chailijun.mtime.utils.Constants;
import com.chailijun.mtime.utils.Logger;
import com.chailijun.mtime.utils.SPUtil;

import java.util.List;

/**
 * author： Chailijun
 * date  ： 2017/1/9 22:30
 * e-mail： 1499505466@qq.com
 */
public class ComingMovieFragment extends BaseFragment implements ComingMovieContract.View{

    public static final String ARGUMENT = "argument";

    private RecyclerView mRecyclerView;

    private ComingMovieContract.Presenter mPresenter;
    private ComingMovieAdapter mAdapter;
    private int locationId;

    private View loadingView;
    private View noDataView;
    private View errorView;

    public static ComingMovieFragment newInstance(String argument) {
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT, argument);

        ComingMovieFragment fragment = new ComingMovieFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_payticket_movie_coming_movie;
    }

    @Override
    public void initView(View view) {
        mRecyclerView = $(view,R.id.rv_coming);
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

        mAdapter = new ComingMovieAdapter(null);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                MovieComingNew.MoviecomingsBean moviecomingsBean
                        = (MovieComingNew.MoviecomingsBean) adapter.getData().get(position);
                Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
                intent.putExtra(Constants.MOVIE_ID,moviecomingsBean.getId());
                getActivity().startActivity(intent);


            }
        });

    }

    private void onRefresh() {
        mAdapter.setEmptyView(loadingView);
        if (locationId != 0)
            mPresenter.loadMovies(locationId);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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
    public void showMovies(MovieComingNew movies) {
        Logger.e(TAG,"即将上映--"+movies.getAttention().size());

        setHeadData(movies.getAttention(),movies.getMoviecomings().size());

        mAdapter.setNewData(movies.getMoviecomings());

        //设置“即将上映”RecyclerView的ItemDecoration，达到分类标题、悬停顶部的效果
        mRecyclerView.addItemDecoration(new SuspensionDecoration(getActivity()
                ,movies.getMoviecomings()).setHeaderViewCount(1));

    }

    @Override
    public void showLoadingMoviesError(String msg) {
        Logger.e(TAG,"即将上映--请求错误:"+msg);

        mAdapter.setEmptyView(errorView);
    }

    @Override
    public void showNoMovies() {

    }

    @Override
    public void setPresenter(ComingMovieContract.Presenter presenter) {
        mPresenter = presenter;
    }

    /**
     * 设置头部信息（最受关注）
     * @param attention
     * @param comingMovieTotal 即将上映的电影总数
     */
    private void setHeadData(List<MovieComingNew.AttentionBean> attention,int comingMovieTotal) {
        View view = getActivity().getLayoutInflater()
                .inflate(R.layout.fragment_payticket_movie_coming_movie_item_head,
                        (ViewGroup) mRecyclerView.getParent(), false);

        TextView comingMovieCount = $(view,R.id.comingMovieCount);
        comingMovieCount.setText(String.format(getString(R.string.coming_movie_count),comingMovieTotal));

        RecyclerView recyclerView = $(view,R.id.rv_attention);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        MostAttentionAdapter adapter = new MostAttentionAdapter(attention);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                MovieComingNew.AttentionBean attentionBean
                        = (MovieComingNew.AttentionBean) adapter.getData().get(position);
                Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
                intent.putExtra(Constants.MOVIE_ID,attentionBean.getId());
                getActivity().startActivity(intent);
            }
        });

        mAdapter.setHeaderView(view);
    }
}
