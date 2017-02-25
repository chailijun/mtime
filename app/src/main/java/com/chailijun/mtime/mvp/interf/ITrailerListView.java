package com.chailijun.mtime.mvp.interf;


public interface ITrailerListView <T extends Data> {

    void addTrailerList(T data);

    void loadFailed(String msg);
}