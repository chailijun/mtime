package com.chailijun.mtime.moviedetail;

import android.support.annotation.NonNull;

import com.chailijun.mtime.api.ApiManage;
import com.chailijun.mtime.data.HttpResult;
import com.chailijun.mtime.data.moviedetail.ExtendMovieDetail;
import com.chailijun.mtime.data.moviedetail.HotComment;
import com.chailijun.mtime.data.moviedetail.MovieDetail;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class MovieDetailPresenter implements MovieDetailContract.Presenter {

    private static final int API_COUNT = 3;
    /**
     * 加载结束的标识：成功一次标识值加1，当前界面有3个api请求，即大于等于3时结束
     */
    private int flag;

    @NonNull
    private MovieDetailContract.View mMovieDetailView;

    @NonNull
    private CompositeSubscription mSubscriptions;

    public MovieDetailPresenter(@NonNull MovieDetailContract.View movieDetailView) {
        mMovieDetailView = movieDetailView;

        mSubscriptions = new CompositeSubscription();
        mMovieDetailView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        mMovieDetailView.showLoading(true);
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }

    @Override
    public void loadMovieDetail(int locationId, int movieId) {

        //加载开始
        mMovieDetailView.showLoading(true);

        Subscription subscription = ApiManage.getInstence().getTicketApi().getMovieDetail(locationId, movieId)
                .map(new Func1<HttpResult<MovieDetail>, MovieDetail>() {
                    @Override
                    public MovieDetail call(HttpResult<MovieDetail> httpResult) {
                        if (httpResult.getCode().equals("1")) {
                            return httpResult.getData();
                        }
                        throw new RuntimeException("电影详细数据为空");
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieDetail>() {
                    @Override
                    public void onCompleted() {
                        //加载结束
                        mMovieDetailView.showLoading(false);
                        mMovieDetailView.showLoadingError(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        //加载失败
                        mMovieDetailView.showLoadingError(true);
                        mMovieDetailView.showLoadingMovieDetailError(e.getMessage());
                    }

                    @Override
                    public void onNext(MovieDetail movieDetail) {

                        mMovieDetailView.showMovieDetail(movieDetail);
                    }
                });
        mSubscriptions.add(subscription);

    }

    /**
     * 加载开始
     */
    private void loadStart(){
        if (flag == 0){
            mMovieDetailView.showLoading(true);
        }
    }

    /**
     * 判断加载是否结束
     */
    private void loadEnd(){
        if (flag >= API_COUNT - 1){
            flag = 0;
            mMovieDetailView.showLoading(false);
        }else {
            flag++;
        }
    }

    @Override
    public void loadHotComment(int movieId) {

        //加载开始
        mMovieDetailView.showLoading(true);

        Subscription subscription = ApiManage.getInstence().getTicketApi().getHotComment(movieId)
                .map(new Func1<HttpResult<HotComment>, HotComment>() {
                    @Override
                    public HotComment call(HttpResult<HotComment> httpResult) {
                        if (httpResult.getCode().equals("1")) {
                            return httpResult.getData();
                        }
                        throw new RuntimeException("电影短评数据为空");
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HotComment>() {
                    @Override
                    public void onCompleted() {
                        //加载结束
                        mMovieDetailView.showLoading(false);
                        mMovieDetailView.showLoadingError(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        //加载失败
                        mMovieDetailView.showLoadingError(true);
                        mMovieDetailView.showLoadingHotCommentError(e.getMessage());
                    }

                    @Override
                    public void onNext(HotComment hotComment) {

                        mMovieDetailView.showHotComment(hotComment);
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void loadExtendMovieDetail(int movieId) {

        //加载开始
        mMovieDetailView.showLoading(true);

        Subscription subscription = ApiManage.getInstence().getHomeApi().getExtendMovieDetail(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ExtendMovieDetail>() {
                    @Override
                    public void onCompleted() {
                        //加载结束
                        mMovieDetailView.showLoading(false);
                        mMovieDetailView.showLoadingError(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        //加载失败
                        mMovieDetailView.showLoadingError(true);
                        mMovieDetailView.showLoadingExtendMovieDetailError(e.getMessage());
                    }

                    @Override
                    public void onNext(ExtendMovieDetail extendMovieDetail) {

                        mMovieDetailView.showExtendMovieDetail(extendMovieDetail);
                    }
                });
        mSubscriptions.add(subscription);
    }
}
