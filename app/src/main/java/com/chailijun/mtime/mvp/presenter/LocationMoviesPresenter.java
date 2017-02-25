package com.chailijun.mtime.mvp.presenter;

import com.chailijun.mtime.api.ApiManage;
import com.chailijun.mtime.mvp.interf.ILocationMoviesPresenter;
import com.chailijun.mtime.mvp.interf.ILocationMoviesView;
import com.chailijun.mtime.mvp.model.BaseData;
import com.chailijun.mtime.utils.Logger;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class LocationMoviesPresenter extends BasePresenter implements ILocationMoviesPresenter{

    private ILocationMoviesView<BaseData> mILocationMoviesView;

    public LocationMoviesPresenter(ILocationMoviesView<BaseData> mILocationMoviesView) {
        this.mILocationMoviesView = mILocationMoviesView;
    }

    @Override
    public void getLocationMovies(int locationId) {
        Subscription subscribe = ApiManage.getInstence().getHomeApi().getLocationMovies1(locationId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseData>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e("LocationMoviesPresenter", "getLocationMovies1--->onError():" + e.getMessage());
                        mILocationMoviesView.loadFailed("load locationMovies data error!");
                    }

                    @Override
                    public void onNext(BaseData baseData) {

                        mILocationMoviesView.addLocationMovies(baseData);
                    }
                });
        addSubscription(subscribe);
    }
}
