package com.chailijun.mtime.payticket.movie;

import android.support.annotation.NonNull;

import com.chailijun.mtime.api.ApiManage;
import com.chailijun.mtime.data.payticket.MovieComingNew;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * author： Chailijun
 * date  ： 2017/1/10 10:32
 * e-mail： 1499505466@qq.com
 */

public class ComingMoviePresenter implements ComingMovieContract.Presenter{

    @NonNull
    private ComingMovieContract.View mComingMovieView;

    @NonNull
    private CompositeSubscription mSubscriptions;

    public ComingMoviePresenter(@NonNull ComingMovieContract.View comingMovieView) {
        this.mComingMovieView = comingMovieView;

        mSubscriptions = new CompositeSubscription();
        mComingMovieView.setPresenter(this);
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
        Subscription subscription = ApiManage.getInstence().getHomeApi().getMovieComingNew(locationId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieComingNew>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mComingMovieView.showLoadingMoviesError(e.getMessage());
                    }

                    @Override
                    public void onNext(MovieComingNew movieComingNew) {
                        mComingMovieView.showMovies(movieComingNew);
                    }
                });
        mSubscriptions.add(subscription);
    }
}
