package com.chailijun.mtime.mvp.interf;

import com.chailijun.mtime.mvp.model.TopListFixedNew;

public interface ITopListPresenter extends IPresenter{

    void getTopListFixedNew();

    void getTopList(int pageIndex);
}
