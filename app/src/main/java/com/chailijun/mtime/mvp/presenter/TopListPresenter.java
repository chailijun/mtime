package com.chailijun.mtime.mvp.presenter;

import com.chailijun.mtime.api.ApiManage;
import com.chailijun.mtime.mvp.interf.ITopListPresenter;
import com.chailijun.mtime.mvp.interf.ITopListView;
import com.chailijun.mtime.mvp.model.BaseData;
import com.chailijun.mtime.utils.Logger;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class TopListPresenter extends BasePresenter implements ITopListPresenter{

    private ITopListView<BaseData> mTopListView;

    public TopListPresenter(ITopListView<BaseData> mTopListView) {
        this.mTopListView = mTopListView;
    }

    @Override
    public void getTopListFixedNew() {
        Subscription subscribe = ApiManage.getInstence().getHomeApi().getTopListFixedNew()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseData>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e("TopListPresenter", "getTopListFixedNew--->onError():" + e.getMessage());
                        mTopListView.loadFailed("load TopListFixedNew data error!");
                    }

                    @Override
                    public void onNext(BaseData baseData) {

                        mTopListView.addTopListFixedNew(baseData);
                    }
                });
        addSubscription(subscribe);
    }

    @Override
    public void getTopList(int pageIndex) {
        Subscription subscribe = ApiManage.getInstence().getHomeApi().getTopListOfAll(pageIndex)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseData>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e("TopListPresenter", "getTopList--->onError():" + e.getMessage());
                        mTopListView.loadFailed("load TopListOfAll data error!");
                    }

                    @Override
                    public void onNext(BaseData baseData) {

                        mTopListView.addTopList(baseData);
                    }
                });
        addSubscription(subscribe);
    }
}
