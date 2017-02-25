package com.chailijun.mtime.mvp.presenter;

import com.chailijun.mtime.api.ApiManage;
import com.chailijun.mtime.mvp.interf.IPersonalDetailPresenter;
import com.chailijun.mtime.mvp.interf.IPersonalDetailView;
import com.chailijun.mtime.mvp.model.BaseData;
import com.chailijun.mtime.mvp.model.movie.PersonMovieJson;
import com.chailijun.mtime.utils.Logger;

import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class PersonalDetailPresenter extends BasePresenter implements IPersonalDetailPresenter{

    private IPersonalDetailView<BaseData> mPersonalDetailView;

    public PersonalDetailPresenter(IPersonalDetailView<BaseData> mPersonalDetailView) {
        this.mPersonalDetailView = mPersonalDetailView;
    }

    @Override
    public void getPersonDetail(int personId, int cityId) {
        Subscription subscribe = ApiManage.getInstence().getTicketApi().getPersonDetail(personId, cityId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseData>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e("PersonalDetailPresenter", "getPersonDetail--->onError():" + e.getMessage());
                        mPersonalDetailView.loadFailed("load personDetail data error!");
                    }

                    @Override
                    public void onNext(BaseData baseData) {

                        mPersonalDetailView.addPersonDetail(baseData);
                    }
                });
        addSubscription(subscribe);
    }

    @Override
    public void getPersonComment(int personId, int pageIndex) {
        Subscription subscribe = ApiManage.getInstence().getHomeApi().getPersonComment(personId, pageIndex)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseData>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e("PersonalDetailPresenter", "getPersonComment--->onError():" + e.getMessage());
                        mPersonalDetailView.loadFailed("load personComment data error!");
                    }

                    @Override
                    public void onNext(BaseData baseData) {

                        mPersonalDetailView.addPersonComment(baseData);
                    }
                });
        addSubscription(subscribe);
    }

    @Override
    public void getPersonMovie(int personId, int orderId, int pageIndex) {
        Subscription subscribe = ApiManage.getInstence().getHomeApi().getPersonMovie(personId, orderId, pageIndex)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<PersonMovieJson>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e("PersonalDetailPresenter", "getPersonMovie--->onError():" + e.getMessage());
                        mPersonalDetailView.loadFailed("load personMovie data error!");
                    }

                    @Override
                    public void onNext(List<PersonMovieJson> baseDatas) {

                        mPersonalDetailView.addPersonMovie(baseDatas);
                    }
                });
        addSubscription(subscribe);
    }
}
