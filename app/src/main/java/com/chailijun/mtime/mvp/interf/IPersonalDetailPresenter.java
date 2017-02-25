package com.chailijun.mtime.mvp.interf;


public interface IPersonalDetailPresenter extends IPresenter{

    void getPersonDetail(int personId, int cityId);
    void getPersonComment(int personId, int pageIndex);
    void getPersonMovie(int personId, int orderId,int pageIndex);
}
