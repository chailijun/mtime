package com.chailijun.mtime.mvp.presenter;

import com.chailijun.mtime.api.ApiManage;
import com.chailijun.mtime.mvp.interf.IMovieComingNewPresenter;
import com.chailijun.mtime.mvp.interf.IMovieComingNewView;
import com.chailijun.mtime.mvp.model.BaseData;
import com.chailijun.mtime.utils.Logger;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MovieComingNewPresenter extends BasePresenter implements IMovieComingNewPresenter{

    private IMovieComingNewView<BaseData> mMovieComingNewView;

    public MovieComingNewPresenter(IMovieComingNewView<BaseData> mMovieComingNewView) {
        this.mMovieComingNewView = mMovieComingNewView;
    }

    @Override
    public void getMovieComingNew(int locationId) {
        Subscription subscribe = ApiManage.getInstence().getHomeApi().getMovieComingNew1(locationId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseData>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e("MovieComingNewPresenter", "getMovieComingNew1--->onError():" + e.getMessage());
                        mMovieComingNewView.loadFailed("load movieComingNew data error!");
                    }

                    @Override
                    public void onNext(BaseData baseData) {

                        mMovieComingNewView.addMovieComingNew(baseData);
                    }
                });
        addSubscription(subscribe);
    }
}
