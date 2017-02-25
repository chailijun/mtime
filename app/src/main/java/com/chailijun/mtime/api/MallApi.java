package com.chailijun.mtime.api;

import com.chailijun.mtime.data.HttpResult;
import com.chailijun.mtime.data.mall.MallIndex;
import com.chailijun.mtime.mvp.model.mall.MallIndexJson;

import retrofit2.http.GET;
import rx.Observable;

public interface MallApi {

    @Deprecated
    @GET("/mall/index.api")
    Observable<MallIndexJson> getMallIndex1();

    @GET("/mall/index.api")
    Observable<HttpResult<MallIndex>> getMallIndex();
}
