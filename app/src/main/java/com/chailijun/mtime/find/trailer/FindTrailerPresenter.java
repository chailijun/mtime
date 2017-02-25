package com.chailijun.mtime.find.trailer;

import android.support.annotation.NonNull;

import com.chailijun.mtime.api.ApiManage;
import com.chailijun.mtime.data.find.trailer.TrailerItem;
import com.chailijun.mtime.data.find.trailer.TrailerList;
import com.chailijun.mtime.find.news.FindNewsContract;

import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * author： Chailijun
 * date  ： 2017/1/15 17:23
 * e-mail： 1499505466@qq.com
 */

public class FindTrailerPresenter implements FindTrailerContract.Presenter{

    @NonNull
    private FindTrailerContract.View mFindTrailer;

    @NonNull
    private CompositeSubscription mSubscriptions;

    public FindTrailerPresenter(@NonNull FindTrailerContract.View findTrailer) {
        mFindTrailer = findTrailer;

        mSubscriptions = new CompositeSubscription();
        mFindTrailer.setPresenter(this);
    }

    @Override
    public void subscribe() {
        loadTrailer();
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }

    @Override
    public void loadTrailer() {
        Subscription subscription = ApiManage.getInstence().getHomeApi().getTrailerList()
                .map(new Func1<TrailerList, List<TrailerItem>>() {
                    @Override
                    public List<TrailerItem> call(TrailerList trailerList) {
                        if (trailerList.getTrailers() != null && trailerList.getTrailers().size() > 0){
                            return trailerList.getTrailers();
                        }
                        throw new RuntimeException("请求预告数据为空！");
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<TrailerItem>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mFindTrailer.showLoadingTrailerError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<TrailerItem> trailerItems) {
                        mFindTrailer.showTrailer(trailerItems);
                    }
                });
        mSubscriptions.add(subscription);
    }
}
