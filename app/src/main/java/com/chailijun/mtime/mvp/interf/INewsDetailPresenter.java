package com.chailijun.mtime.mvp.interf;


public interface INewsDetailPresenter extends IPresenter {

    void getNewsDetail(int newsId);
    void getNewsDetailFrist(int locationId, int newsId);
    void getNewsDetailSecond(int locationId, int newsId);
}
