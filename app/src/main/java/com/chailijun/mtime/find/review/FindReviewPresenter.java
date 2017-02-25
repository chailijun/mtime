package com.chailijun.mtime.find.review;

import android.support.annotation.NonNull;

import com.chailijun.mtime.api.ApiManage;
import com.chailijun.mtime.data.find.review.Review;
import com.chailijun.mtime.data.find.trailer.TrailerItem;
import com.chailijun.mtime.data.find.trailer.TrailerList;

import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class FindReviewPresenter implements FindReviewContract.Presenter{

    @NonNull
    private FindReviewContract.View mFindReviewView;

    @NonNull
    private CompositeSubscription mSubscriptions;

    public FindReviewPresenter(@NonNull FindReviewContract.View findTrailer) {
        mFindReviewView = findTrailer;

        mSubscriptions = new CompositeSubscription();
        mFindReviewView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        loadReview(false);
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }

    @Override
    public void loadReview(boolean needTop) {
        Subscription subscription = ApiManage.getInstence().getHomeApi().getReview(needTop)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Review>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mFindReviewView.showLoadingReviewError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<Review> reviews) {
                        mFindReviewView.showReview(reviews);
                    }
                });
        mSubscriptions.add(subscription);
    }
}
