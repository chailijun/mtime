package com.chailijun.mtime.mvp.presenter;

import com.chailijun.mtime.api.ApiManage;
import com.chailijun.mtime.mvp.interf.ITrailerListPresenter;
import com.chailijun.mtime.mvp.interf.ITrailerListView;
import com.chailijun.mtime.mvp.model.BaseData;
import com.chailijun.mtime.utils.Logger;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class TrailerListPresenter extends BasePresenter implements ITrailerListPresenter{

    private ITrailerListView<BaseData> mTrailerListView;

    public TrailerListPresenter(ITrailerListView<BaseData> mTrailerListView) {
        this.mTrailerListView = mTrailerListView;
    }

    @Override
    public void getTrailerList() {
        Subscription subscribe = ApiManage.getInstence().getHomeApi().getTrailerList1()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseData>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e("TrailerListPresenter",
                                "getTrailerList1--->onError():" + e.getMessage());
                        mTrailerListView.loadFailed("load ReviewDetail data error!");
                    }

                    @Override
                    public void onNext(BaseData baseData) {

                        mTrailerListView.addTrailerList(baseData);
                    }
                });
        addSubscription(subscribe);
    }
}
