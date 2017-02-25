package com.chailijun.mtime.mvp.interf;


public interface ISearchMovieView <T extends Data> {

    void addSearchMovie(T data);

    void loadFailed(String msg);
}
