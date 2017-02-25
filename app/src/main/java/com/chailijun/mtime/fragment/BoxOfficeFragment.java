package com.chailijun.mtime.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chailijun.baseadapter.ViewHolder;
import com.chailijun.baseadapter.interfaces.OnItemClickListener;
import com.chailijun.baseadapter.interfaces.OnLoadMoreListener;
import com.chailijun.mtime.R;
import com.chailijun.mtime.moviedetail.MovieDetailActivity;
import com.chailijun.mtime.adapter.BoxOfficeAdapter;
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


public class BoxOfficeFragment extends BaseFragment implements ITopListDetailsView<BaseData> {

    @BindView(R.id.summary)
    TextView summary;
    @BindView(R.id.rv_boxoffice)
    RecyclerView mRecyclerView;

    private int areaId;

    private int locationId;

    private int pageIndex = 1;

    private ITopListDetailsPresenter mPresenter;

    private BoxOfficeAdapter mAdapter;

    public static BoxOfficeFragment newInstance(int area) {
        BoxOfficeFragment fragment = new BoxOfficeFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.AREA, area);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        areaId = bundle.getInt(Constants.AREA);

        locationId = SPUtil.getInt(Constants.LOCATION_ID);

        mPresenter = new TopListDetailsPresenter(this);
    }

    @Override
    public String getFragmentName() {
        return BoxOfficeFragment.class.getSimpleName();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_boxoffice;
    }

    @Override
    protected void initViews(View rootView) {

        mAdapter = new BoxOfficeAdapter(getActivity(),null,true);
//        mAdapter.setInitLoadingView(R.layout.loading);
        mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(boolean isReload) {
                loadMore();
            }
        });

        mRecyclerView.setLayoutManager(
                new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new OnItemClickListener<BaseData>() {
            @Override
            public void onItemClick(ViewHolder viewHolder, BaseData data, int position) {
                if (data instanceof TopListDetails.MoviesBean){
                    TopListDetails.MoviesBean moviesBean = (TopListDetails.MoviesBean) data;
                    Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
                    intent.putExtra(Constants.MOVIE_ID,moviesBean.getId());
                    startActivity(intent);
                }
            }
        });

    }

    private void loadMore() {
        lazyLoadData();
    }

    @Override
    protected void lazyLoadData() {

        if (locationId != 0 && areaId != 0){
            mPresenter.getTopListDetailsByRecommend(locationId,areaId,pageIndex++);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mPresenter != null){
            mPresenter.unsubcrible();
        }
    }


    @Override
    public void addTopListDetails(BaseData data) {
        if (data instanceof TopListDetails){
            TopListDetails topListDetails = (TopListDetails) data;

            summary.setText(topListDetails.getTopList().getSummary());

            if (topListDetails.getMovies() != null && topListDetails.getMovies().size() > 0){
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
}
