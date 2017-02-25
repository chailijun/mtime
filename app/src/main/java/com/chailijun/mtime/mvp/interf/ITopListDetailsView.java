package com.chailijun.mtime.mvp.interf;


public interface ITopListDetailsView <T extends Data> {

    void addTopListDetails(T data);

    void loadFailed(String msg);
}
