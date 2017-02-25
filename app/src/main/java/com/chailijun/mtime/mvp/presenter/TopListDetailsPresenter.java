package com.chailijun.mtime.mvp.presenter;

import com.chailijun.mtime.api.ApiManage;
import com.chailijun.mtime.mvp.interf.ITopListDetailsPresenter;
import com.chailijun.mtime.mvp.interf.ITopListDetailsView;
import com.chailijun.mtime.mvp.model.BaseData;
import com.chailijun.mtime.utils.Logger;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class TopListDetailsPresenter extends BasePresenter implements ITopListDetailsPresenter{

    private ITopListDetailsView<BaseData> mTopListDetailsView;

    public TopListDetailsPresenter(ITopListDetailsView<BaseData> mTopListDetailsView) {
        this.mTopListDetailsView = mTopListDetailsView;
    }

    @Override
    public void getTopListDetailsByRecommend(int locationId, int pageSubAreaID, int pageIndex) {
        Subscription subscribe = ApiManage.getInstence().getHomeApi().getTopListDetailsByRecommend(locationId,pageSubAreaID,pageIndex)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseData>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e("TopListDetailsPresenter", "getTopListDetailsByRecommend--->onError():" + e.getMessage());
                        mTopListDetailsView.loadFailed("load TopListDetailsByRecommend data error!");
                    }

                    @Override
                    public void onNext(BaseData baseData) {

                        mTopListDetailsView.addTopListDetails(baseData);
                    }
                });
        addSubscription(subscribe);
    }

    @Override
    public void getTopListDetails(int topListId, int pageIndex) {
        Subscription subscribe = ApiManage.getInstence().getHomeApi().getTopListDetails(topListId, pageIndex)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseData>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e("TopListDetailsPresenter", "getTopListDetails--->onError():" + e.getMessage());
                        mTopListDetailsView.loadFailed("load TopListDetails data error!");
                    }

                    @Override
                    public void onNext(BaseData baseData) {

                        mTopListDetailsView.addTopListDetails(baseData);
                    }
                });
        addSubscription(subscribe);
    }
}
