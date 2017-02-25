package com.chailijun.mtime.data.hottest;

import java.util.List;

/**
 * 时光热度榜
 */

public class Hottest {

    private int count;

    private List<ListBean> list;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        private int id;
        private String name;
        private String posterUrl;
        private String nameEn;
        private String rating;
        private String releaseArea;
        private String wantCount;
        private boolean isTicket;
        private boolean isPresell;
        private int directorId;
        private String director;
        private int directorId2;
        private String director2;
        private int actor1Id;
        private String actor1;
        private int actor2Id;
        private String actor2;
        private String releaseDate;

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

        public String getPosterUrl() {
            return posterUrl;
        }

        public void setPosterUrl(String posterUrl) {
            this.posterUrl = posterUrl;
        }

        public String getNameEn() {
            return nameEn;
        }

        public void setNameEn(String nameEn) {
            this.nameEn = nameEn;
        }

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public String getReleaseArea() {
            return releaseArea;
        }

        public void setReleaseArea(String releaseArea) {
            this.releaseArea = releaseArea;
        }

        public String getWantCount() {
            return wantCount;
        }

        public void setWantCount(String wantCount) {
            this.wantCount = wantCount;
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

        public int getDirectorId() {
            return directorId;
        }

        public void setDirectorId(int directorId) {
            this.directorId = directorId;
        }

        public String getDirector() {
            return director;
        }

        public void setDirector(String director) {
            this.director = director;
        }

        public int getDirectorId2() {
            return directorId2;
        }

        public void setDirectorId2(int directorId2) {
            this.directorId2 = directorId2;
        }

        public String getDirector2() {
            return director2;
        }

        public void setDirector2(String director2) {
            this.director2 = director2;
        }

        public int getActor1Id() {
            return actor1Id;
        }

        public void setActor1Id(int actor1Id) {
            this.actor1Id = actor1Id;
        }

        public String getActor1() {
            return actor1;
        }

        public void setActor1(String actor1) {
            this.actor1 = actor1;
        }

        public int getActor2Id() {
            return actor2Id;
        }

        public void setActor2Id(int actor2Id) {
            this.actor2Id = actor2Id;
        }

        public String getActor2() {
            return actor2;
        }

        public void setActor2(String actor2) {
            this.actor2 = actor2;
        }

        public String getReleaseDate() {
            return releaseDate;
        }

        public void setReleaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
        }
    }
}
