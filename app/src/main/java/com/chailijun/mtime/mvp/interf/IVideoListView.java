package com.chailijun.mtime.mvp.interf;


public interface IVideoListView<T extends Data> {
    void addVideoList(T data);
    void loadFailed(String msg);
}

