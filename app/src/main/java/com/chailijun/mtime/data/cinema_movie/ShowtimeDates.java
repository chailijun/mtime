package com.chailijun.mtime.data.cinema_movie;

import java.util.List;

/**
 * 电影放映日期:http://api.m.mtime.cn/Showtime/LocationMovieShowtimeDates.api?locationId=290&movieId=123883
 */

public class ShowtimeDates {

    /**
     * movieId : 123883
     * nameCN : 太空旅客
     * nameEN : Passengers
     */

    private MovieBean movie;
    /**
     * dateValue : 2017-01-16
     * seconds : 1484524800
     */

    private List<ShowtimeDatesBean> showtimeDates;

    public MovieBean getMovie() {
        return movie;
    }

    public void setMovie(MovieBean movie) {
        this.movie = movie;
    }

    public List<ShowtimeDatesBean> getShowtimeDates() {
        return showtimeDates;
    }

    public void setShowtimeDates(List<ShowtimeDatesBean> showtimeDates) {
        this.showtimeDates = showtimeDates;
    }

    public static class MovieBean {
        private int movieId;
        private String nameCN;
        private String nameEN;

        public int getMovieId() {
            return movieId;
        }

        public void setMovieId(int movieId) {
            this.movieId = movieId;
        }

        public String getNameCN() {
            return nameCN;
        }

        public void setNameCN(String nameCN) {
            this.nameCN = nameCN;
        }

        public String getNameEN() {
            return nameEN;
        }

        public void setNameEN(String nameEN) {
            this.nameEN = nameEN;
        }
    }

    public static class ShowtimeDatesBean {
        private String dateValue;
        private long seconds;

        public String getDateValue() {
            return dateValue;
        }

        public void setDateValue(String dateValue) {
            this.dateValue = dateValue;
        }

        public long getSeconds() {
            return seconds;
        }

        public void setSeconds(long seconds) {
            this.seconds = seconds;
        }
    }
}
