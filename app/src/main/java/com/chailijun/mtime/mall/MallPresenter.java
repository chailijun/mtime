package com.chailijun.mtime.mall;

import android.support.annotation.NonNull;

import com.chailijun.mtime.api.ApiManage;
import com.chailijun.mtime.data.HttpResult;
import com.chailijun.mtime.data.mall.MallIndex;
import com.chailijun.mtime.data.mall.RecommendProducts;
import com.chailijun.mtime.homepage.HomeContract;
import com.chailijun.mtime.mvp.model.mall.RecommendProductsJson;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;



public class MallPresenter implements MallContract.Presenter{

    @NonNull
    private MallContract.View mMallView;

    @NonNull
    private CompositeSubscription mSubscriptions;

    public MallPresenter(@NonNull MallContract.View mallView) {
        this.mMallView = mallView;

        mSubscriptions = new CompositeSubscription();
        mMallView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        loadMallIndex();
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }

    @Override
    public void loadMallIndex() {
        mSubscriptions.clear();
        mMallView.showLoading(true);
        Subscription subscription = ApiManage.getInstence().getMallApi().getMallIndex()
                .map(new Func1<HttpResult<MallIndex>, MallIndex>() {
                    @Override
                    public MallIndex call(HttpResult<MallIndex> mallIndexHttpResult) {
                        if (mallIndexHttpResult != null && mallIndexHttpResult.getCode().equals("1")){
                            return mallIndexHttpResult.getData();
                        }
                        throw new RuntimeException("商场索引数据请求异常！");
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MallIndex>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mMallView.showLoading(false);
                        mMallView.showLoadingMallIndexError(e.getMessage());
                    }

                    @Override
                    public void onNext(MallIndex index) {
                        mMallView.showLoading(false);
                        mMallView.showMallIndex(index);
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void loadRecommendProducts(String goodsIds, int pageIndex) {
        mSubscriptions.clear();
        Subscription subscription = ApiManage.getInstence().getHomeApi().getRecommendProducts(goodsIds, pageIndex)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RecommendProducts>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mMallView.showLoadingRecomProductsError(e.getMessage());
                    }

                    @Override
                    public void onNext(RecommendProducts recommendProducts) {
                        if (recommendProducts.getGoodsList()!= null &&
                                recommendProducts.getGoodsList().size() > 0){
                            mMallView.showRecomProducts(recommendProducts.getGoodsList());
                        }else {
                            mMallView.showNoRecomProducts();
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }
}
