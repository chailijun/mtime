package com.chailijun.mtime.mvp.interf;


public interface IMallView<T extends Data> {

    void addMallIndex(T data);
    void addRecommendProducts(T data);
    void loadFailed(String msg);
}
