package com.chailijun.mtime.mvp.interf;

import com.chailijun.mtime.mvp.model.CinemasByCityJson;

import java.util.List;


public interface IFindIndexInfoView<T extends Data> {

    void addIndexInfo(T data);

    void loadFailed(String msg);
}
