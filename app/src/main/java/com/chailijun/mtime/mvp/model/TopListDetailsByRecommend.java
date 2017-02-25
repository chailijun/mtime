package com.chailijun.mtime.mvp.model;

import java.util.List;


public class TopListDetailsByRecommend extends BaseData{

    private TopListBean topList;

    private int totalCount;
    private int pageCount;

    private NextTopListBean nextTopList;

    private PreviousTopListBean previousTopList;

    private List<MoviesBean> movies;

    public TopListBean getTopList() {
        return topList;
    }

    public void setTopList(TopListBean topList) {
        this.topList = topList;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public NextTopListBean getNextTopList() {
        return nextTopList;
    }

    public void setNextTopList(NextTopListBean nextTopList) {
        this.nextTopList = nextTopList;
    }

    public PreviousTopListBean getPreviousTopList() {
        return previousTopList;
    }

    public void setPreviousTopList(PreviousTopListBean previousTopList) {
        this.previousTopList = previousTopList;
    }

    public List<MoviesBean> getMovies() {
        return movies;
    }

    public void setMovies(List<MoviesBean> movies) {
        this.movies = movies;
    }

    public static class TopListBean extends BaseData{
        private String summary;
        private int id;
        private String name;
        private int totalCount;

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }
    }

    public static class NextTopListBean {
        private int toplistId;
        private int toplistItemType;
        private int toplistType;

        public int getToplistId() {
            return toplistId;
        }

        public void setToplistId(int toplistId) {
            this.toplistId = toplistId;
        }

        public int getToplistItemType() {
            return toplistItemType;
        }

        public void setToplistItemType(int toplistItemType) {
            this.toplistItemType = toplistItemType;
        }

        public int getToplistType() {
            return toplistType;
        }

        public void setToplistType(int toplistType) {
            this.toplistType = toplistType;
        }
    }

    public static class PreviousTopListBean {
        private int toplistId;
        private int toplistItemType;
        private int toplistType;

        public int getToplistId() {
            return toplistId;
        }

        public void setToplistId(int toplistId) {
            this.toplistId = toplistId;
        }

        public int getToplistItemType() {
            return toplistItemType;
        }

        public void setToplistItemType(int toplistItemType) {
            this.toplistItemType = toplistItemType;
        }

        public int getToplistType() {
            return toplistType;
        }

        public void setToplistType(int toplistType) {
            this.toplistType = toplistType;
        }
    }

    public static class MoviesBean extends BaseData{

        private int id;
        private String name;
        private String nameEn;
        private int rankNum;
        private String posterUrl;
        private int decade;
        private float rating;
        private String director;
        private String remark;
        private String weekBoxOffice;
        private String totalBoxOffice;
        private boolean isTicket;
        private boolean isPresell;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNameEn() {
            return nameEn;
        }

        public void setNameEn(String nameEn) {
            this.nameEn = nameEn;
        }

        public int getRankNum() {
            return rankNum;
        }

        public void setRankNum(int rankNum) {
            this.rankNum = rankNum;
        }

        public String getPosterUrl() {
            return posterUrl;
        }

        public void setPosterUrl(String posterUrl) {
            this.posterUrl = posterUrl;
        }

        public int getDecade() {
            return decade;
        }

        public void setDecade(int decade) {
            this.decade = decade;
        }

        public float getRating() {
            return rating;
        }

        public void setRating(float rating) {
            this.rating = rating;
        }

        public String getDirector() {
            return director;
        }

        public void setDirector(String director) {
            this.director = director;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getWeekBoxOffice() {
            return weekBoxOffice;
        }

        public void setWeekBoxOffice(String weekBoxOffice) {
            this.weekBoxOffice = weekBoxOffice;
        }

        public String getTotalBoxOffice() {
            return totalBoxOffice;
        }

        public void setTotalBoxOffice(String totalBoxOffice) {
            this.totalBoxOffice = totalBoxOffice;
        }

        public boolean isIsTicket() {
            return isTicket;
        }

        public void setIsTicket(boolean isTicket) {
            this.isTicket = isTicket;
        }

        public boolean isIsPresell() {
            return isPresell;
        }

        public void setIsPresell(boolean isPresell) {
            this.isPresell = isPresell;
        }
    }
}
