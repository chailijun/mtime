package com.chailijun.mtime.find.news;

import android.support.annotation.NonNull;

import com.chailijun.mtime.api.ApiManage;
import com.chailijun.mtime.data.find.news.NewsItem;
import com.chailijun.mtime.data.find.news.NewsList;
import com.chailijun.mtime.data.find.trailer.TrailerItem;
import com.chailijun.mtime.data.find.trailer.TrailerList;

import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class FindNewsPresenter implements FindNewsContract.Presenter{

    @NonNull
    private FindNewsContract.View mFindNewsView;

    @NonNull
    private CompositeSubscription mSubscriptions;

    public FindNewsPresenter(@NonNull FindNewsContract.View findTrailer) {
        mFindNewsView = findTrailer;

        mSubscriptions = new CompositeSubscription();
        mFindNewsView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }

    @Override
    public void loadNews(int pageIndex) {
        Subscription subscription = ApiManage.getInstence().getHomeApi().getNewsList(pageIndex)
                .map(new Func1<NewsList, List<NewsItem>>() {
                    @Override
                    public List<NewsItem> call(NewsList newsList) {
                        if (newsList != null){
                            return newsList.getNewsList();
                        }
                        throw new RuntimeException("请求 发现--新闻 数据为空！");
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<NewsItem>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mFindNewsView.showLoadingNewsError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<NewsItem> newsItems) {
                        if (newsItems != null && newsItems.size() > 0){
                            mFindNewsView.showNews(newsItems);
                        }else {
                            mFindNewsView.showNoNews();
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }
}
