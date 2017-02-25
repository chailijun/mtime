package com.chailijun.mtime.mvp.presenter;

import com.chailijun.mtime.api.ApiManage;
import com.chailijun.mtime.mvp.interf.IReviewPresenter;
import com.chailijun.mtime.mvp.interf.IReviewView;
import com.chailijun.mtime.mvp.model.BaseData;
import com.chailijun.mtime.data.find.review.Review;
import com.chailijun.mtime.utils.Logger;

import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class ReviewPresenter extends BasePresenter implements IReviewPresenter{

    private IReviewView<BaseData> mReviewView;

    public ReviewPresenter(IReviewView<BaseData> mReviewView) {
        this.mReviewView = mReviewView;
    }

    @Override
    public void getReview(boolean needTop) {
        Subscription subscribe = ApiManage.getInstence().getHomeApi().getReview(needTop)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Review>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e("ReviewPresenter", "getReview--->onError():" + e.getMessage());
                        mReviewView.loadFailed("load Review data error!");
                    }

                    @Override
                    public void onNext(List<Review> reviews) {

                        mReviewView.addReview(reviews);
                    }
                });
        addSubscription(subscribe);
    }
}
