package com.chailijun.mtime.cinema_movie;

import com.chailijun.mtime.base.BasePresenter;
import com.chailijun.mtime.base.BaseView;
import com.chailijun.mtime.data.cinema_movie.MovieShowtimes;
import com.chailijun.mtime.data.cinema_movie.ShowtimeDates;

import java.util.List;


public interface PayticketContract {

    interface View extends BaseView<Presenter>{

        void showLoading(boolean active);

        void showtimeDates(ShowtimeDates dates);

        void showLoadShowTimeDatesError(String msg);

        /**
         * 显示 城市--电影--影院
         * @param cinemaItems
         */
        void showMovieShowtimes(List<MovieShowtimes.CinemaItem> cinemaItems);

        void showLoadMovieShowtimesError(String msg);

        void showAllCinema(List<MovieShowtimes.CinemaItem> cinemaItems);

        void showOrderByPrice(List<MovieShowtimes.CinemaItem> cinemaItems);

        void showOrderByDistance(List<MovieShowtimes.CinemaItem> cinemaItems);

    }

    interface Presenter extends BasePresenter{

        /**
         * 加载电影放映日期
         * @param locationId
         * @param movieId
         */
        void loadShowtimeDates(int locationId,int movieId);

        /**
         * 加载 城市--电影--影院
         * @param locationId
         * @param movieId
         * @param needAllShowtimes 显示全部场次
         * @param date
         */
        void loadAllCinemas(int locationId, int movieId, boolean needAllShowtimes, String date);

        /**
         * 将最近的影院放在列表的第一个位置
         * @param cinemaItems
         * @return
         */
        List<MovieShowtimes.CinemaItem> getRecentlyCinema(List<MovieShowtimes.CinemaItem> cinemaItems);

        /**
         *获取全部影院
         */
        void getAllCinema(List<MovieShowtimes.CinemaItem> cinemaItems);

        /**
         * 将影院按照票价升序排序
         * @param cinemaItems
         * @return
         */
        void getOrderByPrice(List<MovieShowtimes.CinemaItem> cinemaItems);

        /**
         * 将影院按照离定位地点的距离的升序排序
         * @param cinemaItems
         * @return
         */
        void getOrderByDistance(List<MovieShowtimes.CinemaItem> cinemaItems);
    }
}
