package com.chailijun.mtime.hottest;

import android.support.annotation.NonNull;

import com.chailijun.mtime.api.ApiManage;
import com.chailijun.mtime.data.hottest.Hottest;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class HottestPresenter implements HottestContract.Presenter{

    @NonNull
    private HottestContract.View mHottestView;

    @NonNull
    private CompositeSubscription mSubscriptions;

    public HottestPresenter(@NonNull HottestContract.View hottestView) {
        mHottestView = hottestView;

        mSubscriptions = new CompositeSubscription();
        mHottestView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }

    @Override
    public void loadHotest(int locationId, int pageIndex) {

        mSubscriptions.clear();

        Subscription subscription = ApiManage.getInstence().getHomeApi().getHottest(locationId, pageIndex)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Hottest>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mHottestView.showLoadingHotestError(e.getMessage());
                    }

                    @Override
                    public void onNext(Hottest hottest) {
                        if (hottest.getList() != null && hottest.getList().size() > 0){
                            mHottestView.showHotest(hottest);
                        }else {
                            mHottestView.showNoData();
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }
}
