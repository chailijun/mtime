package com.chailijun.mtime.mvp.model.movie;

import com.chailijun.mtime.mvp.model.BaseData;

import java.util.List;

@Deprecated
public class ExtendMovieDetailJson extends BaseData{

    private EventsBean events;

    private DataBankEntryBean dataBankEntry;

    private List<NewsBean> news;

    private List<?> relelatedMovielist;

    public EventsBean getEvents() {
        return events;
    }

    public void setEvents(EventsBean events) {
        this.events = events;
    }

    public DataBankEntryBean getDataBankEntry() {
        return dataBankEntry;
    }

    public void setDataBankEntry(DataBankEntryBean dataBankEntry) {
        this.dataBankEntry = dataBankEntry;
    }

    public List<NewsBean> getNews() {
        return news;
    }

    public void setNews(List<NewsBean> news) {
        this.news = news;
    }

    public List<?> getRelelatedMovielist() {
        return relelatedMovielist;
    }

    public void setRelelatedMovielist(List<?> relelatedMovielist) {
        this.relelatedMovielist = relelatedMovielist;
    }

    public static class EventsBean {
        private int eventCount;
        private String title;
        private List<String> list;

        public int getEventCount() {
            return eventCount;
        }

        public void setEventCount(int eventCount) {
            this.eventCount = eventCount;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<String> getList() {
            return list;
        }

        public void setList(List<String> list) {
            this.list = list;
        }
    }

    public static class DataBankEntryBean {
        private boolean isClassicLines;
        private int classicLinesCount;
        private boolean isBehind;
        private boolean isCompany;
        private int mediaReviewCount;
        private boolean isMediaReview;
        private int companyCount;
        private int soundCount;
        private boolean isSound;

        public boolean isIsClassicLines() {
            return isClassicLines;
        }

        public void setIsClassicLines(boolean isClassicLines) {
            this.isClassicLines = isClassicLines;
        }

        public int getClassicLinesCount() {
            return classicLinesCount;
        }

        public void setClassicLinesCount(int classicLinesCount) {
            this.classicLinesCount = classicLinesCount;
        }

        public boolean isIsBehind() {
            return isBehind;
        }

        public void setIsBehind(boolean isBehind) {
            this.isBehind = isBehind;
        }

        public boolean isIsCompany() {
            return isCompany;
        }

        public void setIsCompany(boolean isCompany) {
            this.isCompany = isCompany;
        }

        public int getMediaReviewCount() {
            return mediaReviewCount;
        }

        public void setMediaReviewCount(int mediaReviewCount) {
            this.mediaReviewCount = mediaReviewCount;
        }

        public boolean isIsMediaReview() {
            return isMediaReview;
        }

        public void setIsMediaReview(boolean isMediaReview) {
            this.isMediaReview = isMediaReview;
        }

        public int getCompanyCount() {
            return companyCount;
        }

        public void setCompanyCount(int companyCount) {
            this.companyCount = companyCount;
        }

        public int getSoundCount() {
            return soundCount;
        }

        public void setSoundCount(int soundCount) {
            this.soundCount = soundCount;
        }

        public boolean isIsSound() {
            return isSound;
        }

        public void setIsSound(boolean isSound) {
            this.isSound = isSound;
        }
    }

    public static class NewsBean {
        private int newCount;
        private int id;
        private int type;
        private String image;
        private String title;
        private String title2;
        private long publishTime;

        public int getNewCount() {
            return newCount;
        }

        public void setNewCount(int newCount) {
            this.newCount = newCount;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle2() {
            return title2;
        }

        public void setTitle2(String title2) {
            this.title2 = title2;
        }

        public long getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(long publishTime) {
            this.publishTime = publishTime;
        }
    }
}
