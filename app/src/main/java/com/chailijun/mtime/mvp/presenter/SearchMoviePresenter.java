package com.chailijun.mtime.mvp.presenter;

import android.util.Log;

import com.chailijun.mtime.api.ApiManage;
import com.chailijun.mtime.mvp.interf.ISearchMoviePresenter;
import com.chailijun.mtime.mvp.interf.ISearchMovieView;
import com.chailijun.mtime.mvp.model.BaseData;
import com.chailijun.mtime.mvp.model.HttpResult;
import com.chailijun.mtime.mvp.model.movie.SearchMovie;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class SearchMoviePresenter extends BasePresenter implements ISearchMoviePresenter{

    private ISearchMovieView<BaseData> mSearchMovieView;

    public SearchMoviePresenter(ISearchMovieView<BaseData> mSearchMovieView) {
        this.mSearchMovieView = mSearchMovieView;
    }

    @Override
    public void getSearchMovie(int sortType,
                               String areas,
                               String genreTypes,
                               int sortMethod,
                               String years,
                               int pageIndex) {
        Subscription subscribe = ApiManage.getInstence().getHomeApi()
                .getSearchMovie(sortType, areas, genreTypes, sortMethod, years, pageIndex)
                .map(new HttpResultFunc<SearchMovie>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SearchMovie>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mSearchMovieView.loadFailed(e.getMessage());
                    }

                    @Override
                    public void onNext(SearchMovie searchMovie) {
                        mSearchMovieView.addSearchMovie(searchMovie);
                    }
                });
        addSubscription(subscribe);
    }

    private class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {
        @Override
        public T call(HttpResult<T> httpResult) {
            Log.e("TAG", httpResult.getData().toString() + "");
            if (!httpResult.getCode().equals("1")) {
                throw new RuntimeException("返回的数据为空");
            }
            return httpResult.getData();
        }
    }

}
