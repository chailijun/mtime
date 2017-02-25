package com.chailijun.mtime.mvp.interf;

import com.chailijun.mtime.mvp.presenter.BasePresenter;


public interface INewsListPresenter extends IPresenter {

    void getNewsList(int pageIndex);
}
