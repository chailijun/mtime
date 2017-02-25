package com.chailijun.mtime.mvp.presenter;

import com.chailijun.mtime.mvp.interf.IPresenter;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;


public class BasePresenter implements IPresenter {

    private CompositeSubscription mCompositeSubscription;

    protected void addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(s);
    }

    @Override
    public void unsubcrible() {

        if (this.mCompositeSubscription != null) {
            this.mCompositeSubscription.unsubscribe();
        }
    }
}
