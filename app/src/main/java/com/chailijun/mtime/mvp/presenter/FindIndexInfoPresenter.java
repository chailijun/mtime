package com.chailijun.mtime.mvp.presenter;

import com.chailijun.mtime.api.ApiManage;
import com.chailijun.mtime.mvp.interf.IFindIndexInfoPresenter;
import com.chailijun.mtime.mvp.interf.IFindIndexInfoView;
import com.chailijun.mtime.mvp.model.news.IndexInfo;
import com.chailijun.mtime.utils.Logger;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class FindIndexInfoPresenter extends BasePresenter implements IFindIndexInfoPresenter{

    private IFindIndexInfoView<IndexInfo> mFindIndexInfoView;

    public FindIndexInfoPresenter(IFindIndexInfoView<IndexInfo> mFindIndexInfoView) {
        this.mFindIndexInfoView = mFindIndexInfoView;
    }

    @Override
    public void getIndexInfo() {
        Subscription subscribe = ApiManage.getInstence().getHomeApi().getRecommendationIndexInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<IndexInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e("FindIndexInfoPresenter", "getIndexInfo--->onError():" + e.getMessage());
                        mFindIndexInfoView.loadFailed("load GetRecommendationIndexInfo data error!");
                    }

                    @Override
                    public void onNext(IndexInfo indexInfo) {
                        mFindIndexInfoView.addIndexInfo(indexInfo);
                    }
                });
        addSubscription(subscribe);
    }
}
