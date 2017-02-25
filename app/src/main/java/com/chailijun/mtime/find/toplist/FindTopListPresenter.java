package com.chailijun.mtime.find.toplist;

import android.support.annotation.NonNull;

import com.chailijun.mtime.api.ApiManage;
import com.chailijun.mtime.data.find.toplist.TopList;
import com.chailijun.mtime.data.find.toplist.TopListItem;
import com.chailijun.mtime.find.trailer.FindTrailerContract;

import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class FindTopListPresenter implements FindTopListContract.Presenter{

    @NonNull
    private FindTopListContract.View mTopListView;

    @NonNull
    private CompositeSubscription mSubscriptions;

    public FindTopListPresenter(@NonNull FindTopListContract.View topListView) {
        mTopListView = topListView;

        mSubscriptions = new CompositeSubscription();
        mTopListView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }

    @Override
    public void loadTopList(int pageIndex) {
        Subscription subscription = ApiManage.getInstence().getHomeApi().getTopList(pageIndex)
                .map(new Func1<TopList, List<TopListItem>>() {
                    @Override
                    public List<TopListItem> call(TopList topList) {
                        if (topList != null){
                            return topList.getTopLists();
                        }
                        throw new RuntimeException("排行榜数据为空！");
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<TopListItem>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mTopListView.showLoadingTopListError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<TopListItem> topListItems) {
                        if (topListItems != null && topListItems.size() > 0){
                            mTopListView.showTopList(topListItems);
                        }else {
                            mTopListView.showNoTopList();
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }
}
