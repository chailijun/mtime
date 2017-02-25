package com.chailijun.mtime.data.cinema_movie;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * 电影放映场次：http://api.m.mtime.cn/Showtime/LocationMovieShowtimes.api?locationId=290&movieId=123883&needAllShowtimes=false&date=20170116
 */

public class MovieShowtimes {


    /**
     * address : 北京市丰台区丰科路6号万达广场6层
     * baiduLatitude : 39.813484
     * baiduLongitude : 116.30671
     * business : []
     * cid : 1039
     * cityId : 290
     * cn : 北京丰台万达广场店
     * couponActivityList : []
     * districtId : 1367
     * feature : {"has2D":0,"has3D":1,"hasFeature4K":0,"hasIMAX":0,"hasPark":1,"hasWifi":1,"isDMAX":0}
     * isCurrentDayIMAX : false
     * isTicket : true
     * latitude : 0
     * ln : 北京
     * longitude : 0
     * minPrice : 5300
     * rating : 0.0
     * recentDates : [1484581200,1484583000,1484584800,1484587200,1484588400]
     * sC : 17
     * subway : []
     */

    private List<CinemaItem> cs;

    public List<CinemaItem> getCs() {
        return cs;
    }

    public void setCs(List<CinemaItem> cs) {
        this.cs = cs;
    }

    public static class CinemaItem implements MultiItemEntity{

        public static final int TYPE_COMM = 0;
        public static final int TYPE_RECENTLY = 1;
        public static final int TYPE_LIKE = 2;
        /**
         * type:0 普通影院
         * type:1 离我最近
         * type:2 我常去的
         */
        private int type;

        @Override
        public int getItemType() {
            int itemType = -1;
            switch (type){
                case 0:
                    itemType = TYPE_COMM;
                    break;
                case 1:
                    itemType = TYPE_RECENTLY;
                    break;
                case 2:
                    itemType = TYPE_LIKE;
                    break;
                default:
                    break;
            }
            return itemType;
        }

        /**
         * 离我的距离(字符串)
         */
        private String distanceStr;

        /**
         * 离我的距离
         */
        private double distance;

        private String address;
        private double baiduLatitude;
        private double baiduLongitude;
        private int cid;
        private int cityId;
        private String cn;
        private int districtId;
        /**
         * has2D : 0
         * has3D : 1
         * hasFeature4K : 0
         * hasIMAX : 0
         * hasPark : 1
         * hasWifi : 1
         * isDMAX : 0
         */

        private FeatureBean feature;
        private boolean isCurrentDayIMAX;
        private boolean isTicket;

        private double latitude;
        private String ln;
        private double longitude;

        private int minPrice;
        private String rating;
        private int sC;
        private List<BusinessBean> business;

        /**
         * 优惠券
         */
        private List<CouponActivityListBean> couponActivityList;

        /**
         * 最近放映日期
         */
        private List<Long> recentDates;

        /**
         * 地铁
         */
        private List<SubwayBean> subway;


        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        public String getDistanceStr() {
            return distanceStr;
        }

        public void setDistanceStr(String distanceStr) {
            this.distanceStr = distanceStr;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public double getBaiduLatitude() {
            return baiduLatitude;
        }

        public void setBaiduLatitude(double baiduLatitude) {
            this.baiduLatitude = baiduLatitude;
        }

        public double getBaiduLongitude() {
            return baiduLongitude;
        }

        public void setBaiduLongitude(double baiduLongitude) {
            this.baiduLongitude = baiduLongitude;
        }

        public int getCid() {
            return cid;
        }

        public void setCid(int cid) {
            this.cid = cid;
        }

        public int getCityId() {
            return cityId;
        }

        public void setCityId(int cityId) {
            this.cityId = cityId;
        }

        public String getCn() {
            return cn;
        }

        public void setCn(String cn) {
            this.cn = cn;
        }

        public int getDistrictId() {
            return districtId;
        }

        public void setDistrictId(int districtId) {
            this.districtId = districtId;
        }

        public FeatureBean getFeature() {
            return feature;
        }

        public void setFeature(FeatureBean feature) {
            this.feature = feature;
        }

        public boolean isIsCurrentDayIMAX() {
            return isCurrentDayIMAX;
        }

        public void setIsCurrentDayIMAX(boolean isCurrentDayIMAX) {
            this.isCurrentDayIMAX = isCurrentDayIMAX;
        }

        public boolean isIsTicket() {
            return isTicket;
        }

        public void setIsTicket(boolean isTicket) {
            this.isTicket = isTicket;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public String getLn() {
            return ln;
        }

        public void setLn(String ln) {
            this.ln = ln;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public int getMinPrice() {
            return minPrice;
        }

        public void setMinPrice(int minPrice) {
            this.minPrice = minPrice;
        }

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public int getSC() {
            return sC;
        }

        public void setSC(int sC) {
            this.sC = sC;
        }

        public List<BusinessBean> getBusiness() {
            return business;
        }

        public void setBusiness(List<BusinessBean> business) {
            this.business = business;
        }

        public List<CouponActivityListBean> getCouponActivityList() {
            return couponActivityList;
        }

        public void setCouponActivityList(List<CouponActivityListBean> couponActivityList) {
            this.couponActivityList = couponActivityList;
        }

        public List<Long> getRecentDates() {
            return recentDates;
        }

        public void setRecentDates(List<Long> recentDates) {
            this.recentDates = recentDates;
        }

        public List<SubwayBean> getSubway() {
            return subway;
        }

        public void setSubway(List<SubwayBean> subway) {
            this.subway = subway;
        }


        public static class BusinessBean{
            private int bId;

            public int getbId() {
                return bId;
            }

            public void setbId(int bId) {
                this.bId = bId;
            }
        }

        public static class CouponActivityListBean{

            /**
             * desc : 全场50元购票（1月12日—2月12日，含LUXE2D、LUXE3D）
             * id : 406
             * isSelected : true
             * tag : 店庆特惠
             * url :
             */

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

        private static class SubwayBean{

            /**
             * sId : 56
             * station : [{"distanceStr":0.687,"stationId":1083}]
             */

            private int sId;
            /**
             * distanceStr : 0.687
             * stationId : 1083
             */

            private List<StationBean> station;

            public int getSId() {
                return sId;
            }

            public void setSId(int sId) {
                this.sId = sId;
            }

            public List<StationBean> getStation() {
                return station;
            }

            public void setStation(List<StationBean> station) {
                this.station = station;
            }

            public static class StationBean {
                private double distance;
                private int stationId;

                public double getDistance() {
                    return distance;
                }

                public void setDistance(double distance) {
                    this.distance = distance;
                }

                public int getStationId() {
                    return stationId;
                }

                public void setStationId(int stationId) {
                    this.stationId = stationId;
                }
            }
        }

        public static class FeatureBean {
            private int has2D;
            private int has3D;
            private int hasFeature4K;
            private int hasIMAX;
            private int hasPark;
            private int hasWifi;
            private int isDMAX;

            public int getHas2D() {
                return has2D;
            }

            public void setHas2D(int has2D) {
                this.has2D = has2D;
            }

            public int getHas3D() {
                return has3D;
            }

            public void setHas3D(int has3D) {
                this.has3D = has3D;
            }

            public int getHasFeature4K() {
                return hasFeature4K;
            }

            public void setHasFeature4K(int hasFeature4K) {
                this.hasFeature4K = hasFeature4K;
            }

            public int getHasIMAX() {
                return hasIMAX;
            }

            public void setHasIMAX(int hasIMAX) {
                this.hasIMAX = hasIMAX;
            }

            public int getHasPark() {
                return hasPark;
            }

            public void setHasPark(int hasPark) {
                this.hasPark = hasPark;
            }

            public int getHasWifi() {
                return hasWifi;
            }

            public void setHasWifi(int hasWifi) {
                this.hasWifi = hasWifi;
            }

            public int getIsDMAX() {
                return isDMAX;
            }

            public void setIsDMAX(int isDMAX) {
                this.isDMAX = isDMAX;
            }
        }
    }
}
