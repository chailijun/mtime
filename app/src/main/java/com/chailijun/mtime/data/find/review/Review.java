package com.chailijun.mtime.data.find.review;

import com.chailijun.mtime.mvp.model.BaseData;

public class Review extends BaseData {

    private int id;
    private String nickname;
    private String userImage;
    private String rating;
    private String title;
    private String summary;

    private RelatedObjBean relatedObj;

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

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public RelatedObjBean getRelatedObj() {
        return relatedObj;
    }

    public void setRelatedObj(RelatedObjBean relatedObj) {
        this.relatedObj = relatedObj;
    }

    public static class RelatedObjBean {
        private int type;
        private int id;
        private String title;
        private String rating;
        private String image;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

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

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
