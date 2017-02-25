package com.chailijun.mtime.mvp.interf;


public interface ILocationMoviesView<T extends Data> {

    void addLocationMovies(T data);
    void loadFailed(String msg);
}
