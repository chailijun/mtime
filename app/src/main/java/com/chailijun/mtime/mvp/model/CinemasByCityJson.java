package com.chailijun.mtime.mvp.model;

import java.util.List;

/**
 *根据城市id获取的影院信息
 */
public class CinemasByCityJson extends BaseData{

    private String address;
    private float baiduLatitude;
    private float baiduLongitude;
    private String cinameName;
    private int cinemaId;
    private int districtID;

    private FeatureBean feature;
    private boolean isETicket;
    private boolean isTicket;
    private float latitude;
    private float longitude;
    private int minPrice;
    private int movieCount;
    private float ratingFinal;
    private int showtimeCount;

    private List<CouponActivityListBean> couponActivityList;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getBaiduLatitude() {
        return baiduLatitude;
    }

    public void setBaiduLatitude(float baiduLatitude) {
        this.baiduLatitude = baiduLatitude;
    }

    public float getBaiduLongitude() {
        return baiduLongitude;
    }

    public void setBaiduLongitude(float baiduLongitude) {
        this.baiduLongitude = baiduLongitude;
    }

    public String getCinameName() {
        return cinameName;
    }

    public void setCinameName(String cinameName) {
        this.cinameName = cinameName;
    }

    public int getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(int cinemaId) {
        this.cinemaId = cinemaId;
    }

    public int getDistrictID() {
        return districtID;
    }

    public void setDistrictID(int districtID) {
        this.districtID = districtID;
    }

    public FeatureBean getFeature() {
        return feature;
    }

    public void setFeature(FeatureBean feature) {
        this.feature = feature;
    }

    public boolean isIsETicket() {
        return isETicket;
    }

    public void setIsETicket(boolean isETicket) {
        this.isETicket = isETicket;
    }

    public boolean isIsTicket() {
        return isTicket;
    }

    public void setIsTicket(boolean isTicket) {
        this.isTicket = isTicket;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }

    public int getMovieCount() {
        return movieCount;
    }

    public void setMovieCount(int movieCount) {
        this.movieCount = movieCount;
    }

    public float getRatingFinal() {
        return ratingFinal;
    }

    public void setRatingFinal(float ratingFinal) {
        this.ratingFinal = ratingFinal;
    }

    public int getShowtimeCount() {
        return showtimeCount;
    }

    public void setShowtimeCount(int showtimeCount) {
        this.showtimeCount = showtimeCount;
    }

    public List<CouponActivityListBean> getCouponActivityList() {
        return couponActivityList;
    }

    public void setCouponActivityList(List<CouponActivityListBean> couponActivityList) {
        this.couponActivityList = couponActivityList;
    }

    public static class FeatureBean {
        private int has3D;
        private int hasFeature4D;
        private int hasFeature4K;
        private int hasFeatureDolby;
        private int hasFeatureHuge;
        private int hasIMAX;
        private int hasLoveseat;
        private int hasPark;
        private int hasServiceTicket;
        private int hasVIP;
        private int hasWifi;

        public int getHas3D() {
            return has3D;
        }

        public void setHas3D(int has3D) {
            this.has3D = has3D;
        }

        public int getHasFeature4D() {
            return hasFeature4D;
        }

        public void setHasFeature4D(int hasFeature4D) {
            this.hasFeature4D = hasFeature4D;
        }

        public int getHasFeature4K() {
            return hasFeature4K;
        }

        public void setHasFeature4K(int hasFeature4K) {
            this.hasFeature4K = hasFeature4K;
        }

        public int getHasFeatureDolby() {
            return hasFeatureDolby;
        }

        public void setHasFeatureDolby(int hasFeatureDolby) {
            this.hasFeatureDolby = hasFeatureDolby;
        }

        public int getHasFeatureHuge() {
            return hasFeatureHuge;
        }

        public void setHasFeatureHuge(int hasFeatureHuge) {
            this.hasFeatureHuge = hasFeatureHuge;
        }

        public int getHasIMAX() {
            return hasIMAX;
        }

        public void setHasIMAX(int hasIMAX) {
            this.hasIMAX = hasIMAX;
        }

        public int getHasLoveseat() {
            return hasLoveseat;
        }

        public void setHasLoveseat(int hasLoveseat) {
            this.hasLoveseat = hasLoveseat;
        }

        public int getHasPark() {
            return hasPark;
        }

        public void setHasPark(int hasPark) {
            this.hasPark = hasPark;
        }

        public int getHasServiceTicket() {
            return hasServiceTicket;
        }

        public void setHasServiceTicket(int hasServiceTicket) {
            this.hasServiceTicket = hasServiceTicket;
        }

        public int getHasVIP() {
            return hasVIP;
        }

        public void setHasVIP(int hasVIP) {
            this.hasVIP = hasVIP;
        }

        public int getHasWifi() {
            return hasWifi;
        }

        public void setHasWifi(int hasWifi) {
            this.hasWifi = hasWifi;
        }
    }

    public static class CouponActivityListBean {
        private String desc;
        private int id;
        private boolean isSelected;
        private String tag;
        private String url;

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isIsSelected() {
            return isSelected;
        }

        public void setIsSelected(boolean isSelected) {
            this.isSelected = isSelected;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
