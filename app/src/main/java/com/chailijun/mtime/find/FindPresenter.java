package com.chailijun.mtime.find;

import android.support.annotation.NonNull;

import com.chailijun.mtime.api.ApiManage;
import com.chailijun.mtime.data.find.Index;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class FindPresenter implements FindContract.Presenter{

    @NonNull
    private FindContract.View mFindView;

    @NonNull
    private CompositeSubscription mSubscriptions;

    public FindPresenter(@NonNull FindContract.View findView) {
        mFindView = findView;

        mSubscriptions = new CompositeSubscription();
        mFindView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        loadIndex();
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }

    @Override
    public void loadIndex() {

        mFindView.showLoading(true);

        Subscription subscription = ApiManage.getInstence().getHomeApi().getFindIndex()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Index>() {
                    @Override
                    public void onCompleted() {
                        mFindView.showLoading(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mFindView.showLoading(false);
                        mFindView.showLoadingIndexError(e.getMessage());
                    }

                    @Override
                    public void onNext(Index index) {
                        mFindView.showIndex(index);
                    }
                });
        mSubscriptions.add(subscription);
    }
}
