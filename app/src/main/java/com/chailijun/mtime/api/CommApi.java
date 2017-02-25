package com.chailijun.mtime.api;



import com.chailijun.mtime.mvp.model.HomeIndexJson;

import retrofit2.http.GET;
import rx.Observable;

public interface CommApi {

    @GET("/home/index.api")
    Observable<HomeIndexJson> getHomeIndex();
}
