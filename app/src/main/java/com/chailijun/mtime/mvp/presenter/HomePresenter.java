package com.chailijun.mtime.mvp.presenter;

import com.chailijun.mtime.api.ApiManage;
import com.chailijun.mtime.mvp.interf.IHomeView;
import com.chailijun.mtime.mvp.interf.IHomePresenter;
import com.chailijun.mtime.mvp.model.HomeBaseData;
import com.chailijun.mtime.mvp.model.HomeIndexJson;
import com.chailijun.mtime.mvp.model.HomeListJson;
import com.chailijun.mtime.utils.Constants;
import com.chailijun.mtime.utils.Logger;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class HomePresenter extends BasePresenter implements IHomePresenter{

    private boolean isReconnection;
    private long lastGetTime;

    private IHomeView<HomeBaseData> mHomeIndexView;

    public HomePresenter(IHomeView<HomeBaseData> mHomeIndexView) {
        this.mHomeIndexView = mHomeIndexView;
        isReconnection = false;
    }

    @Override
    public void getHomeIndex() {
        if (!isReconnection){
            lastGetTime = System.currentTimeMillis();
        }
        Subscription subscribe = ApiManage.getInstence().getCommApi().getHomeIndex()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HomeIndexJson>() {
                    @Override
                    public void onCompleted() {

                        Logger.e("HomePresenter","--->onCompleted()");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e("HomePresenter","--->onError()");
                        //连接失败，指定时间内反复重连
                        if (System.currentTimeMillis() - lastGetTime < Constants.GET_DURATION) {
                            isReconnection = true;
                            getHomeIndex();
                        }else {
                            isReconnection = false;
                            mHomeIndexView.loadFailed(Constants.HOME_INDEX);
                        }
                    }

                    @Override
                    public void onNext(HomeIndexJson homeIndexJson) {
                        Logger.e("HomePresenter","--->onNext()");
                        Logger.e("HomePresenter","--->onNext():"+homeIndexJson.getData().getLive().getTitle());
                        if (homeIndexJson.getData() == null){
                            return;
                        }
                        mHomeIndexView.addHomeIndex(homeIndexJson);
                    }
                });
        addSubscription(subscribe);
    }

    @Override
    public void getHomeList(int pageIndex) {
        Subscription subscribe = ApiManage.getInstence().getHomeApi().getHomeList1(pageIndex)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HomeListJson>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e("HomePresenter","getHomeList--->onError():"+e.getMessage());
                        mHomeIndexView.loadFailed(Constants.HOME_LIST);
                    }

                    @Override
                    public void onNext(HomeListJson homeListJson) {
                        mHomeIndexView.addHomeList(homeListJson);
                    }
                });
        addSubscription(subscribe);
    }
}
