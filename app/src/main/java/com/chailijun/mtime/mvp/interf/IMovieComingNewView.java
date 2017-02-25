package com.chailijun.mtime.mvp.interf;


public interface IMovieComingNewView<T extends Data> {

    void addMovieComingNew(T data);
    void loadFailed(String msg);
}