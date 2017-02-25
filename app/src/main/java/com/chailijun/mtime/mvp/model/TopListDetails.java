package com.chailijun.mtime.mvp.model;

import java.util.List;


public class TopListDetails extends BaseData{

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
        private int id;
        private String name;
        private String summary;
        private int totalCount;

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
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

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
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
        private String releaseDate;
        private String releaseLocation;
        private String movieType;
        private String director;
        private String actor;
        private String actor2;
        private String remark;
        private String weekBoxOffice;
        private String totalBoxOffice;
        private boolean isTicket;
        private boolean isPresell;

        public String getTotalBoxOffice() {
            return totalBoxOffice;
        }

        public void setTotalBoxOffice(String totalBoxOffice) {
            this.totalBoxOffice = totalBoxOffice;
        }

        public String getWeekBoxOffice() {
            return weekBoxOffice;
        }

        public void setWeekBoxOffice(String weekBoxOffice) {
            this.weekBoxOffice = weekBoxOffice;
        }

        public boolean isIsPresell() {
            return isPresell;
        }

        public void setPresell(boolean presell) {
            isPresell = presell;
        }

        public boolean isIsTicket() {
            return isTicket;
        }

        public void setTicket(boolean ticket) {
            isTicket = ticket;
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

        public String getReleaseDate() {
            return releaseDate;
        }

        public void setReleaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
        }

        public String getReleaseLocation() {
            return releaseLocation;
        }

        public void setReleaseLocation(String releaseLocation) {
            this.releaseLocation = releaseLocation;
        }

        public String getMovieType() {
            return movieType;
        }

        public void setMovieType(String movieType) {
            this.movieType = movieType;
        }

        public String getDirector() {
            return director;
        }

        public void setDirector(String director) {
            this.director = director;
        }

        public String getActor() {
            return actor;
        }

        public void setActor(String actor) {
            this.actor = actor;
        }

        public String getActor2() {
            return actor2;
        }

        public void setActor2(String actor2) {
            this.actor2 = actor2;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}
