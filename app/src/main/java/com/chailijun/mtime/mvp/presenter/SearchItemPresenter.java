package com.chailijun.mtime.mvp.presenter;

import com.chailijun.mtime.api.ApiManage;
import com.chailijun.mtime.mvp.interf.ISearchItemPresenter;
import com.chailijun.mtime.mvp.interf.ISearchItemView;
import com.chailijun.mtime.mvp.model.BaseData;
import com.chailijun.mtime.utils.Logger;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class SearchItemPresenter extends BasePresenter implements ISearchItemPresenter {

    private ISearchItemView<BaseData> mSearchItemView;

    public SearchItemPresenter(ISearchItemView<BaseData> mSearchItemView) {
        this.mSearchItemView = mSearchItemView;
    }

    @Override
    public void getSearchItem() {
        Subscription subscribe = ApiManage.getInstence().getHomeApi().getSearchItem()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseData>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e("SearchMoviePresenter", "getSearchItem--->onError():" + e.getMessage());
                        mSearchItemView.loadFailed("load SearchItem data error!");
                    }

                    @Override
                    public void onNext(BaseData baseData) {

                        mSearchItemView.addSearchItem(baseData);
                    }
                });
        addSubscription(subscribe);
    }

    @Override
    public void getHotKeyWords() {
        Subscription subscribe = ApiManage.getInstence().getHomeApi().getHotKeyWords()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseData>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e("SearchMoviePresenter", "getHotKeyWords--->onError():" + e.getMessage());
                        mSearchItemView.loadFailed("load HotKeyWords data error!");
                    }

                    @Override
                    public void onNext(BaseData baseData) {

                        mSearchItemView.addHotKeyWords(baseData);
                    }
                });
        addSubscription(subscribe);

    }
}
