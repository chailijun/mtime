package com.chailijun.mtime.api;

import com.chailijun.mtime.data.HttpResult;
import com.chailijun.mtime.data.moviedetail.HotComment;
import com.chailijun.mtime.data.moviedetail.MovieDetail;
import com.chailijun.mtime.mvp.model.movie.HotCommentJson;
import com.chailijun.mtime.mvp.model.movie.MovieDetailJson;
import com.chailijun.mtime.mvp.model.movie.PersonDetailJson;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface TicketApi {

    @Deprecated
    @GET("/movie/detail.api")
    Observable<MovieDetailJson> getMovieDetail1(@Query("locationId") int locationId, @Query("movieId") int movieId);

    @GET("/movie/detail.api")
    Observable<HttpResult<MovieDetail>> getMovieDetail(@Query("locationId") int locationId, @Query("movieId") int movieId);

    @Deprecated
    @GET("/movie/hotComment.api")
    Observable<HotCommentJson> getHotComment1(@Query("movieId") int movieId);

    @GET("/movie/hotComment.api")
    Observable<HttpResult<HotComment>> getHotComment(@Query("movieId") int movieId);

    @GET("/person/detail.api")
    Observable<PersonDetailJson> getPersonDetail(@Query("personId") int personId, @Query("cityId") int cityId);
}
