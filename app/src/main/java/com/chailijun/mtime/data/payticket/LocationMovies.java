package com.chailijun.mtime.data.payticket;

import java.util.List;

public class LocationMovies {

    private String bImg;
    private String date;
    private int lid;
    private int newActivitiesTime;
    private int totalComingMovie;
    private String voucherMsg;

    private List<LocationMoviesItem> ms;

    public String getBImg() {
        return bImg;
    }

    public void setBImg(String bImg) {
        this.bImg = bImg;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getLid() {
        return lid;
    }

    public void setLid(int lid) {
        this.lid = lid;
    }

    public int getNewActivitiesTime() {
        return newActivitiesTime;
    }

    public void setNewActivitiesTime(int newActivitiesTime) {
        this.newActivitiesTime = newActivitiesTime;
    }

    public int getTotalComingMovie() {
        return totalComingMovie;
    }

    public void setTotalComingMovie(int totalComingMovie) {
        this.totalComingMovie = totalComingMovie;
    }

    public String getVoucherMsg() {
        return voucherMsg;
    }

    public void setVoucherMsg(String voucherMsg) {
        this.voucherMsg = voucherMsg;
    }

    public List<LocationMoviesItem> getMs() {
        return ms;
    }

    public void setMs(List<LocationMoviesItem> ms) {
        this.ms = ms;
    }
}
