package com.chailijun.mtime.mvp.interf;

import com.chailijun.mtime.mvp.model.CinemasByCityJson;

import java.util.List;

public interface ICinemasByCityView<T extends Data> {

    void addCinemasByCity(List<CinemasByCityJson> data);
    void loadFailed(String msg);
}
