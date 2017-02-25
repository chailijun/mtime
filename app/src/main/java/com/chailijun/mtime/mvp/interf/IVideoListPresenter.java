package com.chailijun.mtime.mvp.interf;


public interface IVideoListPresenter extends IPresenter {
    void getVideoList(int movieId, int pageIndex);
}
