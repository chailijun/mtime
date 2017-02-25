package com.chailijun.mtime.api;

import com.chailijun.mtime.data.HttpResult;
import com.chailijun.mtime.data.home.HomeIndex;



import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;


public interface MyHostApi {

    @GET("/api/homeIndex.php")
    Observable<HttpResult<HomeIndex>> getHomeIndex(@Query("locationId") int locationId);
}
