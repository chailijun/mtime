package com.chailijun.mtime.api;


import com.chailijun.mtime.data.find.Index;
import com.chailijun.mtime.data.find.toplist.TopList;
import com.chailijun.mtime.data.hottest.Hottest;
import com.chailijun.mtime.data.moviedetail.ExtendMovieDetail;
import com.chailijun.mtime.data.home.HotPlayMovies;
import com.chailijun.mtime.data.mall.RecommendProducts;
import com.chailijun.mtime.data.payticket.LocationMovies;
import com.chailijun.mtime.data.payticket.MovieComingNew;
import com.chailijun.mtime.data.cinema_movie.MovieShowtimes;
import com.chailijun.mtime.data.cinema_movie.ShowtimeDates;
import com.chailijun.mtime.mvp.model.Advertisement;
import com.chailijun.mtime.mvp.model.CinemasByCityJson;
import com.chailijun.mtime.mvp.model.GetSearchItem;
import com.chailijun.mtime.mvp.model.HomeListJson;
import com.chailijun.mtime.mvp.model.HotKeyWords;
import com.chailijun.mtime.mvp.model.HotPlayMoviesJson;
import com.chailijun.mtime.mvp.model.HttpResult;
import com.chailijun.mtime.mvp.model.NewsDetailFristJson;
import com.chailijun.mtime.mvp.model.NewsDetailJson;
import com.chailijun.mtime.mvp.model.NewsDetailSecondJson;
import com.chailijun.mtime.data.find.review.Review;
import com.chailijun.mtime.mvp.model.ReviewDetailJson;
import com.chailijun.mtime.mvp.model.TopListDetails;
import com.chailijun.mtime.mvp.model.TopListFixedNew;
import com.chailijun.mtime.mvp.model.TopListOfAll;
import com.chailijun.mtime.mvp.model.mall.RecommendProductsJson;
import com.chailijun.mtime.mvp.model.movie.ExtendMovieDetailJson;
import com.chailijun.mtime.mvp.model.movie.LocationMoviesJson;
import com.chailijun.mtime.mvp.model.movie.MovieComingNewJson;
import com.chailijun.mtime.mvp.model.movie.PersonCommentJson;
import com.chailijun.mtime.mvp.model.movie.PersonMovieJson;
import com.chailijun.mtime.mvp.model.movie.SearchMovie;
import com.chailijun.mtime.mvp.model.movie.TrailerList;
import com.chailijun.mtime.mvp.model.movie.VideoListJson;
import com.chailijun.mtime.mvp.model.news.IndexInfo;
import com.chailijun.mtime.mvp.model.news.NewsList;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface HomeApi {

    @Deprecated
    @GET("/PageSubArea/GetHomeFeed.api")
    Observable<HomeListJson> getHomeList1(@Query("pageIndex") int index);
    @GET("/PageSubArea/GetHomeFeed.api")
    Observable<String> getHomeList(@Query("pageIndex") int index);

    @Deprecated
    @GET("/PageSubArea/HotPlayMovies.api")
    Observable<HotPlayMoviesJson> getHotPlayMovies1(@Query("locationId") int locationId);

    @GET("/PageSubArea/HotPlayMovies.api")
    Observable<HotPlayMovies> getHotPlayMovies(@Query("locationId") int locationId);


    @GET("/News/Detail.api")
    Observable<NewsDetailJson> getNewsDetail(@Query("newsId") int newsId);

    @GET("/PageSubArea/NewsDetailFrist.api")
    Observable<NewsDetailFristJson> getNewsDetailFrist(@Query("locationId") int locationId,
                                                       @Query("newsId") int newsId);

    @GET("/PageSubArea/NewsDetailSecond.api")
    Observable<NewsDetailSecondJson> getNewsDetailSecond(@Query("locationId") int locationId,
                                                         @Query("newsId") int newsId);
    @Deprecated
    @GET("/Movie/extendMovieDetail.api")
    Observable<ExtendMovieDetailJson> getExtendMovieDetail1(@Query("movieId") int movieId);
    @GET("/Movie/extendMovieDetail.api")
    Observable<ExtendMovieDetail> getExtendMovieDetail(@Query("movieId") int movieId);

    @GET("/Movie/Video.api")
    Observable<VideoListJson> getVideoList(@Query("movieId") int movieId,
                                           @Query("pageIndex") int pageIndex);

    @GET("/Person/Comment.api")
    Observable<PersonCommentJson> getPersonComment(@Query("personId") int personId,
                                                   @Query("pageIndex") int pageIndex);

    @GET("/Person/Movie.api")
    Observable<List<PersonMovieJson>> getPersonMovie(@Query("personId") int personId,
                                                     @Query("orderId") int orderId,
                                                     @Query("pageIndex") int pageIndex);

    @GET("/Review/Detail.api")
    Observable<ReviewDetailJson> getReviewDetail(@Query("reviewId") int reviewId);

    @Deprecated
    @GET("/Showtime/LocationMovies.api")
    Observable<LocationMoviesJson> getLocationMovies1(@Query("locationId") int locationId);

    @GET("/Showtime/LocationMovies.api")
    Observable<LocationMovies> getLocationMovies(@Query("locationId") int locationId);

    @Deprecated
    @GET("/Movie/MovieComingNew.api")
    Observable<MovieComingNewJson> getMovieComingNew1(@Query("locationId") int locationId);
    @GET("/Movie/MovieComingNew.api")
    Observable<MovieComingNew> getMovieComingNew(@Query("locationId") int locationId);

    @GET("/OnlineLocationCinema/OnlineCinemasByCity.api")
    Observable<List<CinemasByCityJson>> getCinemasByCity(@Query("locationId") int locationId);

    @Deprecated
    @GET("/ECommerce/RecommendProducts.api")
    Observable<RecommendProductsJson> getRecommendProducts1(@Query("goodsIds") String goodsIds,
                                                            @Query("pageIndex") int pageIndex);

    @GET("/ECommerce/RecommendProducts.api")
    Observable<RecommendProducts> getRecommendProducts(@Query("goodsIds") String goodsIds,
                                                       @Query("pageIndex") int pageIndex);

    @Deprecated
    @GET("/PageSubArea/GetRecommendationIndexInfo.api")
    Observable<IndexInfo> getRecommendationIndexInfo();

    @GET("/PageSubArea/GetRecommendationIndexInfo.api")
    Observable<Index> getFindIndex();

    @Deprecated
    @GET("/News/NewsList.api")
    Observable<NewsList> getNewsList1(@Query("pageIndex") int pageIndex);

    @GET("/News/NewsList.api")
    Observable<com.chailijun.mtime.data.find.news.NewsList> getNewsList(@Query("pageIndex") int pageIndex);

    @Deprecated
    @GET("/PageSubArea/TrailerList.api")
    Observable<TrailerList> getTrailerList1();

    @GET("/PageSubArea/TrailerList.api")
    Observable<com.chailijun.mtime.data.find.trailer.TrailerList> getTrailerList();

    @Deprecated
    @GET("/TopList/TopListOfAll.api")
    Observable<TopListOfAll> getTopListOfAll(@Query("pageIndex") int pageIndex);

    @GET("/TopList/TopListOfAll.api")
    Observable<TopList> getTopList(@Query("pageIndex") int pageIndex);

    @GET("/TopList/TopListFixedNew.api")
    Observable<TopListFixedNew> getTopListFixedNew();

    @GET("/MobileMovie/Review.api")
    Observable<List<Review>> getReview(@Query("needTop") boolean needTop);

    @GET("/TopList/TopListDetailsByRecommend.api")
    Observable<TopListDetails> getTopListDetailsByRecommend(
            @Query("locationId") int locationId,
            @Query("pageSubAreaID") int pageSubAreaID,
            @Query("pageIndex") int pageIndex);

    @GET("/TopList/TopListDetails.api")
    Observable<TopListDetails> getTopListDetails(@Query("topListId") int topListId,
                                                 @Query("pageIndex") int pageIndex);
    @GET("/Movie/GetSearchItem.api")
    Observable<GetSearchItem> getSearchItem();

    @GET("/Search/HotKeyWords.api")
    Observable<HotKeyWords> getHotKeyWords();

    @GET("/Movie/SearchMovie.api")
    Observable<HttpResult<SearchMovie>> getSearchMovie(@Query("sortType")   int sortType,
                                                       @Query("areas")      String areas,
                                                       @Query("genreTypes") String genreTypes,
                                                       @Query("sortMethod") int sortMethod,
                                                       @Query("years")      String years,
                                                       @Query("pageIndex")  int pageIndex);

    @GET("/Advertisement/MobileAdvertisementInfo.api")
    Observable<Advertisement> getAdvertisement(@Query("locationId") int locationId);

    //时光热度榜
    @GET("/Movie/hotest.api")
    Observable<Hottest> getHottest(@Query("locationId") int locationId,@Query("pageIndex") int pageIndex);

    //电影放映日期
    @GET("/Showtime/LocationMovieShowtimeDates.api")
    Observable<ShowtimeDates> getLocationMovieShowtimeDates(@Query("locationId") int locationId,@Query("movieId") int movieId);

    //城市--电影--影院
    @GET("/Showtime/LocationMovieShowtimes.api")
    Observable<MovieShowtimes> getLocationMovieShowtimes(@Query("locationId")       int locationId,
                                                         @Query("movieId")          int movieId,
                                                         @Query("needAllShowtimes") boolean needAllShowtimes,
                                                         @Query("date")             String date);
}
