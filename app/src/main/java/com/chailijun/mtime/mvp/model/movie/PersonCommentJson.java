package com.chailijun.mtime.mvp.model.movie;

import com.chailijun.mtime.mvp.model.BaseData;

import java.util.List;

/**
 * 对演员的评论
 */
public class PersonCommentJson extends BaseData{

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

    public static class ListBean extends BaseData{
        private int count;
        private int tweetId;
        private String userImage;
        private int userId;
        private String nickname;
        private String address;
        private int rating;
        private String content;
        private long stampTime;
        private int commentCount;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getTweetId() {
            return tweetId;
        }

        public void setTweetId(int tweetId) {
            this.tweetId = tweetId;
        }

        public String getUserImage() {
            return userImage;
        }

        public void setUserImage(String userImage) {
            this.userImage = userImage;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getRating() {
            return rating;
        }

        public void setRating(int rating) {
            this.rating = rating;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public long getStampTime() {
            return stampTime;
        }

        public void setStampTime(long stampTime) {
            this.stampTime = stampTime;
        }

        public int getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }
    }
}
