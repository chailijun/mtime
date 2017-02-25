package com.chailijun.mtime.mvp.model.news;

import com.chailijun.mtime.mvp.model.BaseData;

import java.io.Serializable;

/**
 * 发现的索引
 */
@Deprecated
public class IndexInfo extends BaseData{

    private NewsBean news;

    private TrailerBean trailer;

    private ReviewBean review;

    private ViewingGuideBean viewingGuide;

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

    public static class NewsBean extends BaseData implements Serializable{
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

    public static class TrailerBean extends BaseData implements Serializable{
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

    public static class ReviewBean extends BaseData implements Serializable{
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

    public static class TopListBean extends BaseData implements Serializable{
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
