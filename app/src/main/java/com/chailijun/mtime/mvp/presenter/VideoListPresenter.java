package com.chailijun.mtime.mvp.presenter;

import com.chailijun.mtime.api.ApiManage;
import com.chailijun.mtime.mvp.interf.IVideoListPresenter;
import com.chailijun.mtime.mvp.interf.IVideoListView;
import com.chailijun.mtime.mvp.model.BaseData;
import com.chailijun.mtime.utils.Logger;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class VideoListPresenter extends BasePresenter implements IVideoListPresenter {

    private IVideoListView<BaseData> mVideoListView;

    public VideoListPresenter(IVideoListView<BaseData> mVideoListView) {
        this.mVideoListView = mVideoListView;
    }

    @Override
    public void getVideoList(int movieId, int pageIndex) {
        Subscription subscribe = ApiManage.getInstence().getHomeApi().getVideoList(movieId,pageIndex)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseData>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e("VideoListPresenter", "getVideoList--->onError():" + e.getMessage());
                        mVideoListView.loadFailed("load videoList data error!");
                    }

                    @Override
                    public void onNext(BaseData baseData) {

                        mVideoListView.addVideoList(baseData);
                    }
                });
        addSubscription(subscribe);
    }
}
