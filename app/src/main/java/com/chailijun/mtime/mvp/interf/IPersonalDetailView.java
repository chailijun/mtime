package com.chailijun.mtime.mvp.interf;

import com.chailijun.mtime.mvp.model.BaseData;
import com.chailijun.mtime.mvp.model.movie.PersonMovieJson;

import java.util.List;


public interface IPersonalDetailView<T extends Data> {
    void addPersonDetail(T data);
    void addPersonMovie(List<PersonMovieJson> datas);
    void addPersonComment(T data);
    void loadFailed(String msg);
}
