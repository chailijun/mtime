package com.chailijun.mtime.cinema_movie;

import android.support.annotation.NonNull;

import com.chailijun.mtime.api.ApiManage;
import com.chailijun.mtime.data.cinema_movie.MovieShowtimes;
import com.chailijun.mtime.data.cinema_movie.ShowtimeDates;
import com.chailijun.mtime.utils.BaiDuMapUtils;
import com.chailijun.mtime.utils.Constants;
import com.chailijun.mtime.utils.SPUtil;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class PayticketPresenter implements PayticketContract.Presenter{

    /**
     * 定位地点的经度
     */
    private double longitude;
    /**
     * 定位地点的纬度
     */
    private double latitude;

    @NonNull
    private PayticketContract.View mPayticketView;

    @NonNull
    private CompositeSubscription mSubscriptions;

    public PayticketPresenter(@NonNull PayticketContract.View payticketView) {
        mPayticketView = payticketView;

        mSubscriptions = new CompositeSubscription();
        mPayticketView.setPresenter(this);

        String longitude = SPUtil.get(Constants.LONGITUDE, null);
        String latitude = SPUtil.get(Constants.LATITUDE, null);

        this.longitude = Double.parseDouble(longitude);
        this.latitude = Double.parseDouble(latitude);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }

    @Override
    public void loadShowtimeDates(int locationId, int movieId) {

        mPayticketView.showLoading(true);

        Subscription subscription = ApiManage.getInstence().getHomeApi().getLocationMovieShowtimeDates(locationId, movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ShowtimeDates>() {
                    @Override
                    public void onCompleted() {
                        mPayticketView.showLoading(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mPayticketView.showLoading(false);
                        mPayticketView.showLoadShowTimeDatesError(e.getMessage());
                    }

                    @Override
                    public void onNext(ShowtimeDates dates) {
                        mPayticketView.showtimeDates(dates);
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void loadAllCinemas(int locationId, int movieId, boolean needAllShowtimes, String date) {

        mPayticketView.showLoading(true);

        Subscription subscription = ApiManage.getInstence().getHomeApi()
                .getLocationMovieShowtimes(locationId, movieId, needAllShowtimes, date)
                .map(movieShowtimes -> {
                    if (movieShowtimes.getCs() != null && movieShowtimes.getCs().size() > 0) {
                        return movieShowtimes.getCs();
                    }
                    throw new RuntimeException("加载 影院 数据为空！");
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<MovieShowtimes.CinemaItem>>() {
                    @Override
                    public void onCompleted() {
                        mPayticketView.showLoading(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mPayticketView.showLoading(false);
                        mPayticketView.showLoadMovieShowtimesError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<MovieShowtimes.CinemaItem> cinemaItems) {
                        mPayticketView.showMovieShowtimes(cinemaItems);
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public List<MovieShowtimes.CinemaItem> getRecentlyCinema(List<MovieShowtimes.CinemaItem> cinemaItems) {

        MovieShowtimes.CinemaItem recently = cinemaItems.get(0);
        //最近影院在list中的位置
        int  position = -1;
        for (int i = 0; i < cinemaItems.size(); i++) {
            MovieShowtimes.CinemaItem item = cinemaItems.get(i);

            //影院离定位地点的距离
            double distance = BaiDuMapUtils.GetShortDistance(
                    longitude, latitude, item.getBaiduLongitude(), item.getBaiduLatitude());

            //将单位换算为km
            double dis = distance / 1000.0;

            DecimalFormat formater = new DecimalFormat();
            //保留1位小数
            formater.setMaximumFractionDigits(1);
            //模式  四舍五入
            formater.setRoundingMode(RoundingMode.UP);

            if (dis < 20){
                item.setDistanceStr(formater.format(dis)+"km");
                item.setDistance(dis);
            }else if (dis > 20 && dis < 50){
                item.setDistanceStr(">20km");
                item.setDistance(dis);
            }else{
                item.setDistanceStr(">20km");

                //超过50km
                distance = BaiDuMapUtils.GetLongDistance(
                        longitude, latitude, item.getBaiduLongitude(), item.getBaiduLatitude());
                distance = distance / 1000.0;
                item.setDistance(distance);
            }

            //离我最近的影院
            if (cinemaItems.get(i).getDistance() < recently.getDistance()){
                recently = cinemaItems.get(i);
                position = i;
            }
        }

        //将最近的影院移到第一个位置
        if (position != -1){
            recently.setType(MovieShowtimes.CinemaItem.TYPE_RECENTLY);

            cinemaItems.remove(position);
            cinemaItems.add(0,recently);
        }
        return cinemaItems;
    }

    @Override
    public void getAllCinema(List<MovieShowtimes.CinemaItem> cinemaItems) {
        mPayticketView.showAllCinema(cinemaItems);
    }

    @Override
    public void getOrderByPrice(List<MovieShowtimes.CinemaItem> cinemaItems) {

        List<MovieShowtimes.CinemaItem> cinemas = new ArrayList<>();
        cinemas.addAll(cinemaItems);

        Collections.sort(cinemas, (o1, o2) -> {
            if (o1.isIsTicket() && o2.isIsTicket()){        //都有票按票价排序(相等则按距离排)
                if (o1.getMinPrice() > o2.getMinPrice()){
                    return 1;
                }else if(o1.getMinPrice() == o2.getMinPrice()){
                    return (int) (o1.getDistance() - o2.getDistance());
                }
                return -1;
            }else if (o1.isIsTicket() && !o2.isIsTicket()){ //有票排在前面
                return -1;
            }else if(!o1.isIsTicket() && o2.isIsTicket()){ //没票排在后面
                return 1;
            }else {                                        //都没票则位置不动
                return 0;
            }
        });
        mPayticketView.showOrderByPrice(cinemas);
    }

    @Override
    public void getOrderByDistance(List<MovieShowtimes.CinemaItem> cinemaItems) {
        List<MovieShowtimes.CinemaItem> cinemas = new ArrayList<>();
        cinemas.addAll(cinemaItems);

        Collections.sort(cinemas, (o1, o2) -> {
            if (o1.getDistance() > o2.getDistance()){
                return 1;
            }else if(o1.getDistance() == o2.getDistance()){
                return 0;
            }else {
                return -1;
            }
        });
        mPayticketView.showOrderByDistance(cinemas);
    }
}
