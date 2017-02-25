package com.chailijun.mtime.mvp.presenter;


import com.chailijun.mtime.api.ApiManage;
import com.chailijun.mtime.mvp.interf.IHotPlayMoviesView;
import com.chailijun.mtime.mvp.interf.IHotPlayMoviewsPresenter;
import com.chailijun.mtime.mvp.model.HotPlayMoviesJson;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class HotPlayMoviewsPresenter extends BasePresenter implements IHotPlayMoviewsPresenter {

    private IHotPlayMoviesView<HotPlayMoviesJson> mHotPlayMoviesView;

    public HotPlayMoviewsPresenter(IHotPlayMoviesView<HotPlayMoviesJson> mHotPlayMoviesView) {
        this.mHotPlayMoviesView = mHotPlayMoviesView;
    }

    @Override
    public void getHotPlayMovies(int locationId) {
        Subscription subscribe = ApiManage.getInstence().getHomeApi().getHotPlayMovies1(locationId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HotPlayMoviesJson>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mHotPlayMoviesView.loadFailed("load hotPlayMovies data failed!");
                    }

                    @Override
                    public void onNext(HotPlayMoviesJson hotPlayMoviesJson) {
                        mHotPlayMoviesView.addHotPlayMovies(hotPlayMoviesJson);
                    }
                });
        addSubscription(subscribe);
    }
}
