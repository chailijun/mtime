package com.chailijun.mtime.mvp.model.movie;

import com.chailijun.mtime.mvp.model.BaseData;

import java.util.List;

public class SearchMovie extends BaseData{

    private int pageNum;
    private int totalCount;

    private List<MovieModelListBean> movieModelList;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<MovieModelListBean> getMovieModelList() {
        return movieModelList;
    }

    public void setMovieModelList(List<MovieModelListBean> movieModelList) {
        this.movieModelList = movieModelList;
    }

    public static class MovieModelListBean extends BaseData{
        private String img;
        private int length;
        private int movieId;
        private int rYear;
        private float ratingFinal;
        private String titleCn;
        private String titleEn;
        private String type;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public int getMovieId() {
            return movieId;
        }

        public void setMovieId(int movieId) {
            this.movieId = movieId;
        }

        public int getRYear() {
            return rYear;
        }

        public void setRYear(int rYear) {
            this.rYear = rYear;
        }

        public float getRatingFinal() {
            return ratingFinal;
        }

        public void setRatingFinal(float ratingFinal) {
            this.ratingFinal = ratingFinal;
        }

        public String getTitleCn() {
            return titleCn;
        }

        public void setTitleCn(String titleCn) {
            this.titleCn = titleCn;
        }

        public String getTitleEn() {
            return titleEn;
        }

        public void setTitleEn(String titleEn) {
            this.titleEn = titleEn;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
