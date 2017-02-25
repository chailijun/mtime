package com.chailijun.mtime.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chailijun.baseadapter.ViewHolder;
import com.chailijun.baseadapter.interfaces.OnLoadMoreListener;
import com.chailijun.baseadapter.interfaces.OnMultiItemClickListeners;
import com.chailijun.mtime.R;
import com.chailijun.mtime.adapter.TopListAdapter;
import com.chailijun.mtime.moviedetail.MovieDetailActivity;
import com.chailijun.mtime.mvp.interf.ITopListDetailsPresenter;
import com.chailijun.mtime.mvp.interf.ITopListDetailsView;
import com.chailijun.mtime.mvp.model.BaseData;
import com.chailijun.mtime.mvp.model.TopListDetails;
import com.chailijun.mtime.mvp.presenter.TopListDetailsPresenter;
import com.chailijun.mtime.utils.Constants;
import com.chailijun.mtime.utils.SPUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TopListActivity extends BaseActivity implements ITopListDetailsView<BaseData> {

    @BindView(R.id.rv_toplist)
    RecyclerView mRecyclerView;

    private int toplistId;

    private TopListAdapter mAdapter;
    private ITopListDetailsPresenter mPresenter;

    private View initLoadFailedView;
    private int pageIndex = 1;
    /**
     * 是否已经添加了头部数据
     */
    private boolean isAddedIndex;

    //api参数
    private int pageSubAreaId;
    private int locationId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_top_list;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        getData();

        mAdapter = new TopListAdapter(this, null, true);

        mAdapter.setInitLoadingView(R.layout.loading);
        initLoadFailedView = LayoutInflater.from(this).inflate(R.layout.loading_failed, (ViewGroup) mRecyclerView.getParent(), false);
        initLoadFailedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRefresh();
            }
        });
        mAdapter.setLoadMoreView(R.layout.loading_more);

        mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(boolean isReload) {
                loadMore();
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnMultiItemClickListener(new OnMultiItemClickListeners<BaseData>() {
            @Override
            public void onItemClick(ViewHolder viewHolder, BaseData data, int position, int viewType) {
                if (data instanceof TopListDetails.MoviesBean){
                    TopListDetails.MoviesBean moviesBean = (TopListDetails.MoviesBean) data;

                    Intent intent = new Intent(TopListActivity.this,MovieDetailActivity.class);
                    intent.putExtra(Constants.MOVIE_ID,moviesBean.getId());
                    startActivity(intent);
                }
            }
        });

        requestData();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.unsubcrible();
        }
    }

    private void requestData() {
        mPresenter = new TopListDetailsPresenter(this);
    }

    private void onRefresh() {

    }

    private void loadMore() {
        if (mPresenter != null ) {
            if (toplistId != 0 && pageSubAreaId == 0){
                mPresenter.getTopListDetails(toplistId, pageIndex++);

            }else if (toplistId == 0 && pageSubAreaId != 0 && locationId != 0){
                mPresenter.getTopListDetailsByRecommend(locationId,pageSubAreaId,pageIndex++);
            }
        }
    }

    private void getData() {
        Intent intent = getIntent();
        toplistId = intent.getIntExtra(Constants.TOPLIST_ID,0);
        pageSubAreaId = intent.getIntExtra(Constants.PAGESUB_AREA_ID,0);

        locationId = SPUtil.getInt(Constants.LOCATION_ID);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        getData();
    }

    @Override
    public void addTopListDetails(BaseData data) {
        if (data instanceof TopListDetails) {
            TopListDetails topListDetails = (TopListDetails) data;

            if (topListDetails.getTopList() != null && !isAddedIndex) {
                isAddedIndex = true;
                List<BaseData> list = new ArrayList<>();
                list.add(0, topListDetails.getTopList());
                mAdapter.setNewData(list);
            }

            if (topListDetails.getMovies() != null && topListDetails.getMovies().size() > 0) {
                List<BaseData> list = new ArrayList<>();
                for (int i = 0; i < topListDetails.getMovies().size(); i++) {
                    list.add(topListDetails.getMovies().get(i));
                }
                mAdapter.setLoadMoreData(list);
            }

        }
    }

    @Override
    public void loadFailed(String msg) {

    }

    @OnClick({R.id.back, R.id.share})
    public void onClick(View view) {
        if (view.getId() == R.id.back) {
            finish();
        } else if (view.getId() == R.id.share) {
            //share action
        }
    }
}
