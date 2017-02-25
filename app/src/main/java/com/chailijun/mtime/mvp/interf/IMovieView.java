package com.chailijun.mtime.mvp.interf;


public interface IMovieView<T extends Data> {

    void addMovieDetail(T data);
    void addExtendMovieDetail(T data);
    void addHotComment(T data);
    void loadFailed(String msg);
}
