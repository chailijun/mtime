package com.chailijun.mtime.mvp.interf;


public interface IMoviePresenter extends IPresenter {

    void getMovieDetail(int locationId,int movieId);
    void getExtendMovieDetail(int movieId);
    void getHotComment(int movieId);
}
