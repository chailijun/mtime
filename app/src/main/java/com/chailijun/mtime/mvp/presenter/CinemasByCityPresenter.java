package com.chailijun.mtime.mvp.presenter;

import com.chailijun.mtime.api.ApiManage;
import com.chailijun.mtime.mvp.interf.ICinemasByCityPresenter;
import com.chailijun.mtime.mvp.interf.ICinemasByCityView;
import com.chailijun.mtime.mvp.model.BaseData;
import com.chailijun.mtime.mvp.model.CinemasByCityJson;
import com.chailijun.mtime.utils.Logger;

import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class CinemasByCityPresenter extends BasePresenter implements ICinemasByCityPresenter{

    private ICinemasByCityView<BaseData> mCinemasByCityView;

    public CinemasByCityPresenter(ICinemasByCityView<BaseData> mCinemasByCityView) {
        this.mCinemasByCityView = mCinemasByCityView;
    }

    @Override
    public void getCinemasByCity(int locationId) {
        Subscription subscribe = ApiManage.getInstence().getHomeApi().getCinemasByCity(locationId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<CinemasByCityJson>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e("CinemasByCityPresenter", "getCinemasByCity--->onError():" + e.getMessage());
                        mCinemasByCityView.loadFailed("load CinemasByCity data error!");
                    }

                    @Override
                    public void onNext(List<CinemasByCityJson> cinemasByCityJsons) {
                        mCinemasByCityView.addCinemasByCity(cinemasByCityJsons);
                    }
                });
        addSubscription(subscribe);
    }
}
