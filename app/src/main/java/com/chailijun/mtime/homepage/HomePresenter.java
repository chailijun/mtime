package com.chailijun.mtime.homepage;

import android.support.annotation.NonNull;

import com.chailijun.mtime.api.ApiManage;
import com.chailijun.mtime.data.HttpResult;
import com.chailijun.mtime.data.home.HomeIndex;
import com.chailijun.mtime.data.home.HomeList;
import com.chailijun.mtime.data.home.HomeListSuper;
import com.chailijun.mtime.data.home.HotPlayMovies;
import com.chailijun.mtime.json.HomeListDeserializer;
import com.chailijun.mtime.utils.Logger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class HomePresenter implements HomeContract.Presenter{

    private final Gson gson;
    @NonNull
    private HomeContract.View mHomeView;

    @NonNull
    private CompositeSubscription mSubscriptions;

    public HomePresenter(@NonNull HomeContract.View homeView) {
        mHomeView = homeView;

        mSubscriptions = new CompositeSubscription();
        mHomeView.setPresenter(this);

        GsonBuilder gsonb = new GsonBuilder();
        gsonb.registerTypeAdapter(HomeList.class, new HomeListDeserializer());
        gsonb.serializeNulls();
        gson = gsonb.create();
    }

    @Override
    public void subscribe() {
//        loadHomeIndex();
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }

    @Override
    public void loadHomeIndex(int locationId) {

        Subscription subscription = ApiManage.getInstence().getMyHostApi().getHomeIndex(locationId)
                .map(new Func1<HttpResult<HomeIndex>, HomeIndex>() {
                    @Override
                    public HomeIndex call(HttpResult<HomeIndex> httpResult) {
                        if (!httpResult.getCode().equals("1")){
                            throw new RuntimeException("网络请求数据为空！");
                        }
                        return httpResult.getData();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HomeIndex>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mHomeView.showLoadingIndexError(e.getMessage());
                    }

                    @Override
                    public void onNext(HomeIndex homeIndex) {

                        mHomeView.showIndex(homeIndex);
                    }
                });

        mSubscriptions.add(subscription);
    }

    @Override
    public void loadHotPlayMovies(int locationId) {

        Subscription subscription = ApiManage.getInstence().getHomeApi().getHotPlayMovies(locationId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HotPlayMovies>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mHomeView.showLoadingHotPlayMoviesError(e.getMessage());
                    }

                    @Override
                    public void onNext(HotPlayMovies movies) {
                        mHomeView.showHotPlayMovies(movies);
                        /*if (movies.getMovies() != null && movies.getMovies().size() > 0){
                        }else {
                            mHomeView.showNoMovie();
                        }*/
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void loadHomeList(int pageIndex) {

        Subscription subscription = ApiManage.getInstence().getHomeStrApi().getHomeList(pageIndex)
                .map(new Func1<String, List<HomeListSuper>>() {
                    @Override
                    public List<HomeListSuper> call(String json) {
                        if (json == null){
                            throw new RuntimeException("网络请求失败！");
                        }
                        HomeList homeList = gson.fromJson(json, HomeList.class);
                        return homeList.getData();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<HomeListSuper>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mHomeView.showLoadingHomeListError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<HomeListSuper> homeListSupers) {
                        processData(homeListSupers);
                    }
                });
        mSubscriptions.add(subscription);
    }

    private void processData(List<HomeListSuper> homeListSupers) {
        if (homeListSupers != null && homeListSupers.size() > 0){
            mHomeView.showHomeList(homeListSupers);
        }else {
            mHomeView.showNoHomeList();
        }
    }
}
