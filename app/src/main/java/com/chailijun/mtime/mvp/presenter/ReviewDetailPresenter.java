package com.chailijun.mtime.mvp.presenter;

import com.chailijun.mtime.api.ApiManage;
import com.chailijun.mtime.mvp.interf.IReviewDetailPresenter;
import com.chailijun.mtime.mvp.interf.IReviewDetailView;
import com.chailijun.mtime.mvp.model.BaseData;
import com.chailijun.mtime.utils.Logger;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class ReviewDetailPresenter extends BasePresenter implements IReviewDetailPresenter {

    private IReviewDetailView<BaseData> mReviewDetailView;

    public ReviewDetailPresenter(IReviewDetailView<BaseData> mReviewDetailView) {
        this.mReviewDetailView = mReviewDetailView;
    }

    @Override
    public void getReviewDetail(int reviewId) {
        Subscription subscribe = ApiManage.getInstence().getHomeApi().getReviewDetail(reviewId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseData>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e("ReviewDetailPresenter", "getReviewDetail--->onError():" + e.getMessage());
                        mReviewDetailView.loadFailed("load ReviewDetail data error!");
                    }

                    @Override
                    public void onNext(BaseData baseData) {

                        mReviewDetailView.addReviewDetail(baseData);
                    }
                });
        addSubscription(subscribe);
    }
}
