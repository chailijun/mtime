
package com.chailijun.mtime.mvp.interf;


public interface IHotPlayMoviesView<T extends Data> {

    void addHotPlayMovies(T data);
    void loadFailed(String msg);
}
