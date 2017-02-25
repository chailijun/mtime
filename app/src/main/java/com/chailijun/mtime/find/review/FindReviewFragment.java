package com.chailijun.mtime.find.review;

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
import com.chailijun.mtime.activity.ReviewDetailActivity;
import com.chailijun.mtime.base.BaseFragment;
import com.chailijun.mtime.data.find.Index;
import com.chailijun.mtime.data.find.review.Review;
import com.chailijun.mtime.data.find.trailer.TrailerItem;
import com.chailijun.mtime.utils.Constants;
import com.chailijun.mtime.utils.Imager;
import com.chailijun.mtime.utils.Logger;

import java.util.List;

public class FindReviewFragment extends BaseFragment implements FindReviewContract.View{

    public static final String ARGUMENT = "argument";

    private RecyclerView mRecyclerView;

    private View mHeadview;
    private View loadingView;
    private View errorView;

    private FindReviewAdapter mAdapter;

    private FindReviewContract.Presenter mPresenter;

    private Index.ReviewBean reviewBean;

    public static FindReviewFragment newInstance(Index.ReviewBean reviewBean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARGUMENT, reviewBean);

        FindReviewFragment fragment = new FindReviewFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        reviewBean = (Index.ReviewBean) bundle.getSerializable(ARGUMENT);

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

        mAdapter = new FindReviewAdapter(null);

        //头布局
        mHeadview = getActivity().getLayoutInflater()
                .inflate(R.layout.fragment_find_review_index, (ViewGroup) mRecyclerView.getParent(), false);
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
                Review review = (Review) adapter.getData().get(position);

                Intent intent = new Intent(getActivity(), ReviewDetailActivity.class);
                intent.putExtra(Constants.REVIEW_ID,review.getId());
                startActivity(intent);
            }
        });
    }

    private void onRefresh() {
        mAdapter.setEmptyView(loadingView);
        mPresenter.subscribe();
    }

    @Override
    public void doBusiness(Context mContext) {
        mAdapter.setEmptyView(loadingView);
        mPresenter.subscribe();
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
    public void showReview(List<Review> reviews) {
        Logger.d(TAG,"发现--影评 加载成功:"+reviews.size());

        mAdapter.setNewData(reviews);

    }

    @Override
    public void showLoadingReviewError(String msg) {
        Logger.e(TAG,"发现--影评 加载失败:"+msg);

        mAdapter.setEmptyView(errorView);
    }

    @Override
    public void setPresenter(FindReviewContract.Presenter presenter) {
        mPresenter = presenter;
    }

    /**
     * 初始化头部数据
     */
    private void initHeadData() {

        if (reviewBean == null){
            return;
        }

        ImageView headImg = $(mHeadview,R.id.iv_img);
        ImageView movieImg = $(mHeadview,R.id.movie_img);
        TextView movieName = $(mHeadview,R.id.movieName);
        TextView movieTitle = $(mHeadview,R.id.tv_title);

        Imager.load(getActivity(), reviewBean.getImageUrl(), headImg);
        Imager.load(getActivity(), reviewBean.getPosterUrl(), movieImg);

        movieName.setText(reviewBean.getMovieName().trim());
        movieTitle.setText(reviewBean.getTitle().trim());

        headImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ReviewDetailActivity.class);
                intent.putExtra(Constants.REVIEW_ID,reviewBean.getReviewID());
                startActivity(intent);
            }
        });
    }



}
