package com.chailijun.mtime.mvp.interf;


public interface IHomeView<T extends Data> {

    void addHomeIndex(T data);
    void addHomeList(T data);
    void loadFailed(String msg);
}
