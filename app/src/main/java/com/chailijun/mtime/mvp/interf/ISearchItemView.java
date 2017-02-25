package com.chailijun.mtime.mvp.interf;


public interface ISearchItemView<T extends Data> {

    void addSearchItem(T data);

    void addHotKeyWords(T data);

    void loadFailed(String msg);
}
