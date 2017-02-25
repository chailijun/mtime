package com.chailijun.mtime.mvp.interf;


public interface INewsListView <T extends Data> {

    void addNewsList(T data);
    void loadFailed(String msg);
}
