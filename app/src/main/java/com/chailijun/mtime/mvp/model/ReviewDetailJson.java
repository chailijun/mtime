package com.chailijun.mtime.mvp.model;

import java.util.List;

/**
 * 影评详细
 */
public class ReviewDetailJson extends BaseData{
    private int commentCount;
    private String content;

    private int id;
    private String nickname;
    private String rating;
    private String summaryInfo;
    private String time;
    private String title;
    private String topImgUrl;
    private String url;
    private String userImage;

    private RelatedObjBean relatedObj;
    List<String> type;

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public RelatedObjBean getRelatedObj() {
        return relatedObj;
    }

    public void setRelatedObj(RelatedObjBean relatedObj) {
        this.relatedObj = relatedObj;
    }

    public String getSummaryInfo() {
        return summaryInfo;
    }

    public void setSummaryInfo(String summaryInfo) {
        this.summaryInfo = summaryInfo;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTopImgUrl() {
        return topImgUrl;
    }

    public void setTopImgUrl(String topImgUrl) {
        this.topImgUrl = topImgUrl;
    }

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public static class RelatedObjBean{

        private int id;
        private String image;
        private String name;
        private float rating;
        private String releaseDate;
        private String releaseLocation;
        private String runtime;
        private String title;
        private String titleCn;
        private String titleEn;
        private int type;
        private String url;
        private String wapUrl;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public String getRuntime() {
            return runtime;
        }

        public void setRuntime(String runtime) {
            this.runtime = runtime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
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

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getWapUrl() {
            return wapUrl;
        }

        public void setWapUrl(String wapUrl) {
            this.wapUrl = wapUrl;
        }
    }

}
