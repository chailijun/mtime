package com.chailijun.mtime.data.find;

import java.io.Serializable;

/**
 * "发现"的索引
 */

public class Index {


    /**
     * newsID : 1565492
     * title : 黑泽明《蜘蛛巢城》上映60周年
     * type : 0
     * imageUrl : http://img5.mtime.cn/mg/2017/01/15/111715.97844451.jpg
     */

    private NewsBean news;
    /**
     * trailerID : 64200
     * title : 《赛车总动员3》中文预告
     * imageUrl : http://img5.mtime.cn/mg/2017/01/10/192328.93605490.jpg
     * mp4Url : http://vfx.mtime.cn/Video/2017/01/10/mp4/170110151912397035_480.mp4
     * hightUrl : http://vfx.mtime.cn/Video/2017/01/10/mp4/170110151912397035.mp4
     * movieId : 228907
     */

    private TrailerBean trailer;
    /**
     * reviewID : 7992933
     * title : 90年的太空生活该如何度过？
     * posterUrl : http://img5.mtime.cn/mt/2016/12/26/113421.22552790_1280X720X2.jpg
     * movieName : 太空旅客
     * imageUrl : http://img5.mtime.cn/mg/2017/01/15/091114.98552288.jpg
     */

    private ReviewBean review;
    /**
     * id : 10792
     * imageUrl : http://img31.mtime.cn/mg/2015/03/31/100230.43767720.jpg
     */

    private ViewingGuideBean viewingGuide;
    /**
     * id : 1470
     * title : 《好莱坞报道者》评2016年15部佳剧
     * imageUrl : http://img5.mtime.cn/mg/2016/12/28/105521.78553814.jpg
     * type : 0
     */

    private TopListBean topList;

    public NewsBean getNews() {
        return news;
    }

    public void setNews(NewsBean news) {
        this.news = news;
    }

    public TrailerBean getTrailer() {
        return trailer;
    }

    public void setTrailer(TrailerBean trailer) {
        this.trailer = trailer;
    }

    public ReviewBean getReview() {
        return review;
    }

    public void setReview(ReviewBean review) {
        this.review = review;
    }

    public ViewingGuideBean getViewingGuide() {
        return viewingGuide;
    }

    public void setViewingGuide(ViewingGuideBean viewingGuide) {
        this.viewingGuide = viewingGuide;
    }

    public TopListBean getTopList() {
        return topList;
    }

    public void setTopList(TopListBean topList) {
        this.topList = topList;
    }

    public static class NewsBean implements Serializable{
        private int newsID;
        private String title;
        private int type;
        private String imageUrl;

        public int getNewsID() {
            return newsID;
        }

        public void setNewsID(int newsID) {
            this.newsID = newsID;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }

    public static class TrailerBean implements Serializable{
        private int trailerID;
        private String title;
        private String imageUrl;
        private String mp4Url;
        private String hightUrl;
        private int movieId;

        public int getTrailerID() {
            return trailerID;
        }

        public void setTrailerID(int trailerID) {
            this.trailerID = trailerID;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getMp4Url() {
            return mp4Url;
        }

        public void setMp4Url(String mp4Url) {
            this.mp4Url = mp4Url;
        }

        public String getHightUrl() {
            return hightUrl;
        }

        public void setHightUrl(String hightUrl) {
            this.hightUrl = hightUrl;
        }

        public int getMovieId() {
            return movieId;
        }

        public void setMovieId(int movieId) {
            this.movieId = movieId;
        }
    }

    public static class ReviewBean implements Serializable{
        private int reviewID;
        private String title;
        private String posterUrl;
        private String movieName;
        private String imageUrl;

        public int getReviewID() {
            return reviewID;
        }

        public void setReviewID(int reviewID) {
            this.reviewID = reviewID;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPosterUrl() {
            return posterUrl;
        }

        public void setPosterUrl(String posterUrl) {
            this.posterUrl = posterUrl;
        }

        public String getMovieName() {
            return movieName;
        }

        public void setMovieName(String movieName) {
            this.movieName = movieName;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }

    public static class ViewingGuideBean {
        private String id;
        private String imageUrl;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }

    public static class TopListBean implements Serializable{
        private int id;
        private String title;
        private String imageUrl;
        private int type;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
