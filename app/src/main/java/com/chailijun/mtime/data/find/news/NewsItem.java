package com.chailijun.mtime.data.find.news;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * 发现--新闻（列表item）
 */

public class NewsItem implements MultiItemEntity{

    public static final int TYPE_0 = 1002;
    public static final int TYPE_1 = 1003;
    public static final int TYPE_2 = 1004;

    private int id;
    private int type;
    private String image;
    private String title;
    private String title2;
    private String summary;
    private String summaryInfo;
    private String tag;
    private long publishTime;
    private int commentCount;

    private List<ImagesBean> images;

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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSummaryInfo() {
        return summaryInfo;
    }

    public void setSummaryInfo(String summaryInfo) {
        this.summaryInfo = summaryInfo;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public List<ImagesBean> getImages() {
        return images;
    }

    public void setImages(List<ImagesBean> images) {
        this.images = images;
    }

    public static class ImagesBean {
        private int gId;
        private String title;
        private String desc;
        private String url1;
        private String url2;

        public int getGId() {
            return gId;
        }

        public void setGId(int gId) {
            this.gId = gId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getUrl1() {
            return url1;
        }

        public void setUrl1(String url1) {
            this.url1 = url1;
        }

        public String getUrl2() {
            return url2;
        }

        public void setUrl2(String url2) {
            this.url2 = url2;
        }
    }

    @Override
    public int getItemType() {
        int itemType = -1;
        switch (type){
            case 0:
                itemType = TYPE_0;
                break;
            case 1:
                itemType = TYPE_1;
                break;
            case 2:
                itemType = TYPE_2;
                break;
            default:
                break;
        }
        return itemType;
    }
}
