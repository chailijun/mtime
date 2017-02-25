package com.chailijun.mtime.mvp.presenter;


import com.chailijun.mtime.api.ApiManage;
import com.chailijun.mtime.mvp.interf.INewsDetailPresenter;
import com.chailijun.mtime.mvp.interf.INewsDetailView;
import com.chailijun.mtime.mvp.model.BaseData;
import com.chailijun.mtime.utils.Constants;
import com.chailijun.mtime.utils.Logger;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NewsDetailPresenter extends BasePresenter implements INewsDetailPresenter {

    private boolean isReconnection;
    private long lastGetTime;

    private INewsDetailView<BaseData> mNewsDetailView;

    public NewsDetailPresenter(INewsDetailView<BaseData> mNewsDetailView) {
        this.mNewsDetailView = mNewsDetailView;
        isReconnection = false;
    }

    @Override
    public void getNewsDetail(final int newsId) {
        if (!isReconnection){
            lastGetTime = System.currentTimeMillis();
        }
        Subscription subscribe = ApiManage.getInstence().getHomeApi().getNewsDetail(newsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseData>() {
                    @Override
                    public void onCompleted() {
                        Logger.e("TAG","getNewsDetail-->onCompleted()");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e("TAG","getNewsDetail-->onError:"+e.getMessage());
                        //连接失败，指定时间内反复重连
                        if (System.currentTimeMillis() - lastGetTime < Constants.GET_DURATION) {
                            isReconnection = true;
                            getNewsDetail(newsId);
                        }else {
                            isReconnection = false;
                            mNewsDetailView.loadFailed(Constants.NEWS_DETAIL);
                        }
                    }

                    @Override
                    public void onNext(BaseData data) {
                        Logger.e("TAG","getNewsDetail-->onNext");
                        mNewsDetailView.addNewsDetail(data);
                    }
                });
        addSubscription(subscribe);
    }

    @Override
    public void getNewsDetailFrist(int locationId, int newsId) {
        Subscription subscribe = ApiManage.getInstence().getHomeApi().getNewsDetailFrist(locationId,newsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseData>() {
                    @Override
                    public void onCompleted() {
                        Logger.e("TAG","getNewsDetailFrist-->onCompleted()");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e("TAG","getNewsDetailFrist-->onError:"+e.getMessage());
                        mNewsDetailView.loadFailed(Constants.NEWS_DETAIL_FRIST);
                    }

                    @Override
                    public void onNext(BaseData data) {
                        Logger.e("TAG","getNewsDetailFrist-->onNext");
                        mNewsDetailView.addNewsDetailFrist(data);
                    }
                });
        addSubscription(subscribe);
    }

    @Override
    public void getNewsDetailSecond(int locationId, int newsId) {
        Subscription subscribe = ApiManage.getInstence().getHomeApi().getNewsDetailSecond(locationId,newsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseData>() {
                    @Override
                    public void onCompleted() {
                        Logger.e("TAG","getNewsDetailSecond-->onCompleted()");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e("TAG","getNewsDetailSecond-->onError:"+e.getMessage());
                        mNewsDetailView.loadFailed(Constants.NEWS_DETAIL_SECOND);
                    }

                    @Override
                    public void onNext(BaseData data) {
                        Logger.e("TAG","getNewsDetailSecond-->onNext");
                        mNewsDetailView.addNewsDetailSecond(data);
                    }
                });
        addSubscription(subscribe);
    }
}
