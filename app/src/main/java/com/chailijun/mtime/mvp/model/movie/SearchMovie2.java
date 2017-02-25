package com.chailijun.mtime.mvp.model.movie;

import com.chailijun.mtime.mvp.model.BaseData;

import java.util.List;

@Deprecated
public class SearchMovie2 extends BaseData{

    private String code;

    private DataBean data;
    private String msg;
    private String showMsg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getShowMsg() {
        return showMsg;
    }

    public void setShowMsg(String showMsg) {
        this.showMsg = showMsg;
    }

    public static class DataBean {
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

        public static class MovieModelListBean {
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
}
