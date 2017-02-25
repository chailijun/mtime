package com.chailijun.mtime.mvp.presenter;

import com.chailijun.mtime.api.ApiManage;
import com.chailijun.mtime.mvp.model.Advertisement;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class AdvertisementPresenter extends BasePresenter{

    public void getAdvertisement(int locationId, final HandlerAdvListener handlerAdvListener) {
        Subscription subscribe = ApiManage.getInstence().getHomeApi().getAdvertisement(locationId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Advertisement>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        handlerAdvListener.getAdvFailed(e.getMessage());
                    }

                    @Override
                    public void onNext(Advertisement advertisement) {
                        handlerAdvListener.getAdvSuccessed(advertisement);
                    }
                });
        addSubscription(subscribe);
    }

    public interface HandlerAdvListener{

        void getAdvFailed(String msg);

        void getAdvSuccessed(Advertisement advertisement);
    }
}
