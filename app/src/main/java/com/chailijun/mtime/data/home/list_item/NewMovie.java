package com.chailijun.mtime.data.home.list_item;

import com.chailijun.mtime.data.home.HomeListSuper;

/**
 * type：-1  "日韩新片""欧美新片"
 */

public class NewMovie extends HomeListSuper {

    @Override
    public int getHomeItemType() {
        return getType();
    }

    @Override
    protected int getHomeDataType() {
        return -1;
    }

    /**
     * titleCn : 《你会在那里吗？》2016
     * titleEn : 法国小说改编韩国奇幻电影
     * rating : 6.7
     * tag : 日韩新片
     * status : 2
     * content : 金允石穿越时空三十年挽救真爱
     * id : 231218
     * isShow : 是
     * comSpecialObjId : 23395116
     * image : http://img5.mtime.cn/mt/2016/11/17/160910.59564866_1280X720X2.jpg
     * type : -1
     */

    private String titleCn;
    private String titleEn;
    private String rating;
    private String tag;
    private int status;
    private String content;
    private String id;
    private String isShow;
    private int comSpecialObjId;
    private String image;
    private int type;

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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public int getComSpecialObjId() {
        return comSpecialObjId;
    }

    public void setComSpecialObjId(int comSpecialObjId) {
        this.comSpecialObjId = comSpecialObjId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
