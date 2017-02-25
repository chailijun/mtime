package com.chailijun.mtime.mvp.presenter;

import com.chailijun.mtime.api.ApiManage;
import com.chailijun.mtime.mvp.interf.IMoviePresenter;
import com.chailijun.mtime.mvp.interf.IMovieView;
import com.chailijun.mtime.mvp.model.BaseData;
import com.chailijun.mtime.utils.Constants;
import com.chailijun.mtime.utils.Logger;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MoviePresenter extends BasePresenter implements IMoviePresenter {

    private IMovieView<BaseData> mMovieView;

    public MoviePresenter(IMovieView<BaseData> mMovieView) {
        this.mMovieView = mMovieView;
    }

    @Override
    public void getMovieDetail(int locationId, int movieId) {
        Subscription subscribe = ApiManage.getInstence().getTicketApi().getMovieDetail1(locationId, movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseData>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e("MoviePresenter", "getHomeList--->onError():" + e.getMessage());
                        mMovieView.loadFailed(Constants.MOVIE_DETAIL);
                    }

                    @Override
                    public void onNext(BaseData baseData) {

                        mMovieView.addMovieDetail(baseData);
                    }
                });
        addSubscription(subscribe);
    }

    @Override
    public void getExtendMovieDetail(int movieId) {
        Subscription subscribe = ApiManage.getInstence().getHomeApi().getExtendMovieDetail1(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseData>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e("MoviePresenter", "getExtendMovieDetail1--->onError():" + e.getMessage());
                        mMovieView.loadFailed(Constants.EXTEND_MOVIE_DETAIL);
                    }

                    @Override
                    public void onNext(BaseData baseData) {

                        mMovieView.addExtendMovieDetail(baseData);
                    }
                });
        addSubscription(subscribe);
    }

    @Override
    public void getHotComment(int movieId) {
        Subscription subscribe = ApiManage.getInstence().getTicketApi().getHotComment1(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseData>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e("MoviePresenter", "getHotComment1--->onError():" + e.getMessage());
                        mMovieView.loadFailed(Constants.MOVIE_HOT_COMMENT);
                    }

                    @Override
                    public void onNext(BaseData baseData) {

                        mMovieView.addHotComment(baseData);
                    }
                });
        addSubscription(subscribe);
    }
}
