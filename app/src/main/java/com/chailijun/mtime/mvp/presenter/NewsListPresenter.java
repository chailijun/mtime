package com.chailijun.mtime.mvp.presenter;

import com.chailijun.mtime.api.ApiManage;
import com.chailijun.mtime.mvp.interf.INewsListPresenter;
import com.chailijun.mtime.mvp.interf.INewsListView;
import com.chailijun.mtime.mvp.model.BaseData;
import com.chailijun.mtime.utils.Logger;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class NewsListPresenter extends BasePresenter implements INewsListPresenter {

    private INewsListView<BaseData> mNewsListView;

    public NewsListPresenter(INewsListView<BaseData> mNewsListView) {
        this.mNewsListView = mNewsListView;
    }

    @Override
    public void getNewsList(int pageIndex) {
        Subscription subscribe = ApiManage.getInstence().getHomeApi().getNewsList1(pageIndex)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseData>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e("NewsListPresenter", "getNewsList1--->onError():" + e.getMessage());
                        mNewsListView.loadFailed("load NewsList data error!");
                    }

                    @Override
                    public void onNext(BaseData baseData) {

                        mNewsListView.addNewsList(baseData);
                    }
                });
        addSubscription(subscribe);
    }
}
