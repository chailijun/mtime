package com.chailijun.mtime.api;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface HomeStrApi {

    @GET("/PageSubArea/GetHomeFeed.api")
    Observable<String> getHomeList(@Query("pageIndex") int index);
}
