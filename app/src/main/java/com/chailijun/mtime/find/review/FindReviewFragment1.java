package com.chailijun.mtime.find.review;

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
import com.chailijun.mtime.activity.ReviewDetailActivity;
import com.chailijun.mtime.adapter.FindReviewAdapter;
import com.chailijun.mtime.base.BaseFragment;
import com.chailijun.mtime.data.find.Index;
import com.chailijun.mtime.mvp.interf.IReviewPresenter;
import com.chailijun.mtime.mvp.interf.IReviewView;
import com.chailijun.mtime.mvp.model.BaseData;
import com.chailijun.mtime.data.find.review.Review;
import com.chailijun.mtime.mvp.model.news.IndexInfo;
import com.chailijun.mtime.mvp.presenter.ReviewPresenter;
import com.chailijun.mtime.utils.Constants;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class FindReviewFragment1 extends BaseFragment implements IReviewView<BaseData> {


    private RecyclerView mRecyclerView;

    private static final String REVIEW = "review";

    private IndexInfo.ReviewBean mReviewIndex;

    private IReviewPresenter mPresenter;
    private FindReviewAdapter mAdapter;
    private View initLoadFailedView;

    public static FindReviewFragment1 newInstance(Index.ReviewBean reviewBean) {
        FindReviewFragment1 fragment = new FindReviewFragment1();
        Bundle args = new Bundle();
        args.putSerializable(REVIEW,reviewBean);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        mReviewIndex = (IndexInfo.ReviewBean) arguments.getSerializable(REVIEW);

        mPresenter = new ReviewPresenter(this);
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

        mAdapter = new FindReviewAdapter(getActivity(),null,false);

        mAdapter.setInitLoadingView(R.layout.loading);
        initLoadFailedView = LayoutInflater.from(getActivity())
                .inflate(R.layout.loading_failed, (ViewGroup) mRecyclerView.getParent(), false);
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
                int reviewId = 0;
                if (data instanceof IndexInfo.ReviewBean){
                    IndexInfo.ReviewBean reviewBean = (IndexInfo.ReviewBean) data;
                    reviewId = reviewBean.getReviewID();
                }
                if (data instanceof Review){
                    Review review = (Review) data;
                    reviewId = review.getId();
                }

                Intent intent = new Intent(getActivity(), ReviewDetailActivity.class);
                intent.putExtra(Constants.REVIEW_ID,reviewId);
                startActivity(intent);
            }
        });
    }

    @Override
    public void doBusiness(Context mContext) {
        mPresenter.getReview(false);
    }

    @Override
    public void widgetClick(View v) {

    }

    private void onRefresh() {
        mAdapter.setInitLoadingView(R.layout.loading);
        mPresenter.getReview(false);
    }


    @Override
    public void addReview(List<Review> reviews) {
        if (reviews != null && reviews.size() > 0){

            if (mReviewIndex != null){
                List<BaseData> list = new ArrayList<>();
                list.add(mReviewIndex);
                mAdapter.setNewData(list);
            }

            List<BaseData> list = new ArrayList<>();
            for (int i = 0; i < reviews.size(); i++) {
                list.add(reviews.get(i));
            }
            mAdapter.setLoadMoreData(list);
        }
    }

    @Override
    public void loadFailed(String msg) {
        mAdapter.setInitLoadFailedView(initLoadFailedView);
    }
}
