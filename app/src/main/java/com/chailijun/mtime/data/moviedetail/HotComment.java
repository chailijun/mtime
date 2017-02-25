package com.chailijun.mtime.data.moviedetail;


import java.util.List;

/**
 * 电影详细--短评、影评
 */

public class HotComment {

    private MiniBean mini;
    private PlusBean plus;

    public MiniBean getMini() {
        return mini;
    }

    public void setMini(MiniBean mini) {
        this.mini = mini;
    }

    public PlusBean getPlus() {
        return plus;
    }

    public void setPlus(PlusBean plus) {
        this.plus = plus;
    }

    public static class MiniBean {
        private int total;
        private List<ListBean> list;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            private long commentDate;
            private int commentId;
            private String content;
            private String headImg;
            private String img;
            private boolean isHot;
            private boolean isPraise;
            private String locationName;
            private String nickname;
            private int praiseCount;
            private float rating;
            private int replyCount;

            public long getCommentDate() {
                return commentDate;
            }

            public void setCommentDate(long commentDate) {
                this.commentDate = commentDate;
            }

            public int getCommentId() {
                return commentId;
            }

            public void setCommentId(int commentId) {
                this.commentId = commentId;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getHeadImg() {
                return headImg;
            }

            public void setHeadImg(String headImg) {
                this.headImg = headImg;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public boolean isIsHot() {
                return isHot;
            }

            public void setIsHot(boolean isHot) {
                this.isHot = isHot;
            }

            public boolean isIsPraise() {
                return isPraise;
            }

            public void setIsPraise(boolean isPraise) {
                this.isPraise = isPraise;
            }

            public String getLocationName() {
                return locationName;
            }

            public void setLocationName(String locationName) {
                this.locationName = locationName;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public int getPraiseCount() {
                return praiseCount;
            }

            public void setPraiseCount(int praiseCount) {
                this.praiseCount = praiseCount;
            }

            public float getRating() {
                return rating;
            }

            public void setRating(float rating) {
                this.rating = rating;
            }

            public int getReplyCount() {
                return replyCount;
            }

            public void setReplyCount(int replyCount) {
                this.replyCount = replyCount;
            }
        }
    }

    public static class PlusBean {
        private int total;
        private List<ListBean> list;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            private long commentDate;
            private int commentId;
            private String content;
            private String headImg;
            private boolean isWantSee;
            private String locationName;
            private String nickname;
            private float rating;
            private int replyCount;
            private String title;

            public long getCommentDate() {
                return commentDate;
            }

            public void setCommentDate(long commentDate) {
                this.commentDate = commentDate;
            }

            public int getCommentId() {
                return commentId;
            }

            public void setCommentId(int commentId) {
                this.commentId = commentId;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getHeadImg() {
                return headImg;
            }

            public void setHeadImg(String headImg) {
                this.headImg = headImg;
            }

            public boolean isIsWantSee() {
                return isWantSee;
            }

            public void setIsWantSee(boolean isWantSee) {
                this.isWantSee = isWantSee;
            }

            public String getLocationName() {
                return locationName;
            }

            public void setLocationName(String locationName) {
                this.locationName = locationName;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public float getRating() {
                return rating;
            }

            public void setRating(float rating) {
                this.rating = rating;
            }

            public int getReplyCount() {
                return replyCount;
            }

            public void setReplyCount(int replyCount) {
                this.replyCount = replyCount;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}