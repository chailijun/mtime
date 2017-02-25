package com.chailijun.mtime.mvp.model;

import java.util.List;

public class NewsDetailFristJson extends BaseData {

    private String code;
    private String showMsg;
    private String msg;
    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getShowMsg() {
        return showMsg;
    }

    public void setShowMsg(String showMsg) {
        this.showMsg = showMsg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private List<NewsDetailBean> newsDetail;

        public List<NewsDetailBean> getNewsDetail() {
            return newsDetail;
        }

        public void setNewsDetail(List<NewsDetailBean> newsDetail) {
            this.newsDetail = newsDetail;
        }

        public static class NewsDetailBean  extends BaseData {
            private int contentType;
            private int index;
            private AdvertisementBean advertisement;
            /**
             * content : 说漫威反派弱？灭霸会帮他们全部挽回面子的？
             * date : 2016-11-16 22:51:45
             * fromApp :
             * nickname : 匿名
             * userImage : http://img2.mtime.cn/images/default/head.gif
             * timestamp : 1479336706
             * id : 4385317
             * mVPType : 0
             * replies : []
             * replyCount : 0
             * totalCount : 40
             */

            private List<CommentsBean> comments;

            public int getContentType() {
                return contentType;
            }

            public void setContentType(int contentType) {
                this.contentType = contentType;
            }

            public int getIndex() {
                return index;
            }

            public void setIndex(int index) {
                this.index = index;
            }

            public AdvertisementBean getAdvertisement() {
                return advertisement;
            }

            public void setAdvertisement(AdvertisementBean advertisement) {
                this.advertisement = advertisement;
            }

            public List<CommentsBean> getComments() {
                return comments;
            }

            public void setComments(List<CommentsBean> comments) {
                this.comments = comments;
            }

            public static class AdvertisementBean {
                private String type;
                private String typeName;
                private boolean isHorizontalScreen;
                private long startDate;
                private long endDate;
                private String url;
                private String image;
                private String tag;
                private boolean isOpenH5;
                private String advTag;

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getTypeName() {
                    return typeName;
                }

                public void setTypeName(String typeName) {
                    this.typeName = typeName;
                }

                public boolean isIsHorizontalScreen() {
                    return isHorizontalScreen;
                }

                public void setIsHorizontalScreen(boolean isHorizontalScreen) {
                    this.isHorizontalScreen = isHorizontalScreen;
                }

                public long getStartDate() {
                    return startDate;
                }

                public void setStartDate(long startDate) {
                    this.startDate = startDate;
                }

                public long getEndDate() {
                    return endDate;
                }

                public void setEndDate(long endDate) {
                    this.endDate = endDate;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getImage() {
                    return image;
                }

                public void setImage(String image) {
                    this.image = image;
                }

                public String getTag() {
                    return tag;
                }

                public void setTag(String tag) {
                    this.tag = tag;
                }

                public boolean isIsOpenH5() {
                    return isOpenH5;
                }

                public void setIsOpenH5(boolean isOpenH5) {
                    this.isOpenH5 = isOpenH5;
                }

                public String getAdvTag() {
                    return advTag;
                }

                public void setAdvTag(String advTag) {
                    this.advTag = advTag;
                }
            }

            public static class CommentsBean {
                private String content;
                private String date;
                private String fromApp;
                private String nickname;
                private String userImage;
                private long timestamp;
                private int id;
                private int mVPType;
                private int replyCount;
                private int totalCount;
                private List<?> replies;

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public String getFromApp() {
                    return fromApp;
                }

                public void setFromApp(String fromApp) {
                    this.fromApp = fromApp;
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

                public long getTimestamp() {
                    return timestamp;
                }

                public void setTimestamp(long timestamp) {
                    this.timestamp = timestamp;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getMVPType() {
                    return mVPType;
                }

                public void setMVPType(int mVPType) {
                    this.mVPType = mVPType;
                }

                public int getReplyCount() {
                    return replyCount;
                }

                public void setReplyCount(int replyCount) {
                    this.replyCount = replyCount;
                }

                public int getTotalCount() {
                    return totalCount;
                }

                public void setTotalCount(int totalCount) {
                    this.totalCount = totalCount;
                }

                public List<?> getReplies() {
                    return replies;
                }

                public void setReplies(List<?> replies) {
                    this.replies = replies;
                }
            }
        }
    }
}
