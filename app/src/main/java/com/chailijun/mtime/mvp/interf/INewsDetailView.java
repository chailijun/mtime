package com.chailijun.mtime.mvp.interf;


public interface INewsDetailView<T extends Data> {

    void addNewsDetail(T data);
    void addNewsDetailFrist(T data);
    void addNewsDetailSecond(T data);
    void loadFailed(String msg);
}
