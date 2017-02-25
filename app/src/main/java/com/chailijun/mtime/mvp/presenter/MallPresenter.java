package com.chailijun.mtime.mvp.presenter;

import com.chailijun.mtime.api.ApiManage;
import com.chailijun.mtime.mvp.interf.IMallView;
import com.chailijun.mtime.mvp.model.BaseData;
import com.chailijun.mtime.utils.Constants;
import com.chailijun.mtime.utils.Logger;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MallPresenter extends BasePresenter{

    private IMallView<BaseData> mMallView;

    public MallPresenter(IMallView<BaseData> mMallView) {
        this.mMallView = mMallView;
    }

    /**
     * 获取商场索引内容
     */
    public void getMallIndex(){
        Subscription subscribe = ApiManage.getInstence().getMallApi().getMallIndex1()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseData>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e("MallIndexPresenter", "getMallIndex1--->onError():" + e.getMessage());
                        mMallView.loadFailed(Constants.MALL_INDEX);
                    }

                    @Override
                    public void onNext(BaseData baseData) {

                        mMallView.addMallIndex(baseData);
                    }
                });
        addSubscription(subscribe);
    }

    /**
     * 获取感兴趣的商品
     * @param goodsIds
     * @param pageIndex
     */
    public void getRecommendProducts(String goodsIds,int pageIndex){
        Subscription subscribe = ApiManage.getInstence().getHomeApi().getRecommendProducts1(goodsIds, pageIndex)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseData>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e("MallIndexPresenter", "getRecommendProducts1--->onError():" + e.getMessage());
                        mMallView.loadFailed(Constants.RECOMMEND_PRODUCTS);
                    }

                    @Override
                    public void onNext(BaseData baseData) {

                        mMallView.addRecommendProducts(baseData);
                    }
                });
        addSubscription(subscribe);
    }
}
