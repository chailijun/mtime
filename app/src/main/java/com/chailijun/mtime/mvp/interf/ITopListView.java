package com.chailijun.mtime.mvp.interf;


public interface ITopListView <T extends Data> {

    void addTopList(T data);

    void addTopListFixedNew(T data);

    void loadFailed(String msg);
}