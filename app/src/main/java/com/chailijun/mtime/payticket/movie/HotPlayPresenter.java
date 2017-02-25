package com.chailijun.mtime.payticket.movie;

import android.support.annotation.NonNull;


import com.chailijun.mtime.api.ApiManage;
import com.chailijun.mtime.data.payticket.LocationMovies;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * author： Chailijun
 * date  ： 2017/1/6 10:12
 * e-mail： 1499505466@qq.com
 */

public class HotPlayPresenter implements HotPlayContract.Presenter{

    @NonNull
    private HotPlayContract.View mHotPlayView;

    @NonNull
    private CompositeSubscription mSubscriptions;

    public HotPlayPresenter(@NonNull HotPlayContract.View movieView) {
        this.mHotPlayView = movieView;

        mSubscriptions = new CompositeSubscription();
        mHotPlayView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }

    @Override
    public void loadMovies(int locationId) {

        mHotPlayView.setLoadingIndicator(true);

        mSubscriptions.clear();
        Subscription subscription = ApiManage
                .getInstence().getHomeApi().getLocationMovies(locationId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LocationMovies>() {
                    @Override
                    public void onCompleted() {
                        mHotPlayView.setLoadingIndicator(false);
                    }

                    @Override
                    public void onError(Throwable e) {

                        mHotPlayView.setLoadingIndicator(false);
                        mHotPlayView.showLoadingMoviesError(e.getMessage());
                    }

                    @Override
                    public void onNext(LocationMovies locationMovies) {
                        processMovie(locationMovies);
                    }
                });
        mSubscriptions.add(subscription);
    }

    private void processMovie(LocationMovies locationMovies) {
        if (locationMovies.getMs() == null){
            mHotPlayView.showNoMovies();
        }else {
            mHotPlayView.showMovies(locationMovies.getMs());
        }
    }
}
