package com.chailijun.mtime.mvp.interf;


public interface IReviewDetailView<T extends Data> {
    void addReviewDetail(T data);
    void loadFailed(String msg);
}

