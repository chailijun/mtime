package com.chailijun.mtime.data.home.list_item;

import com.chailijun.mtime.data.home.HomeListSuper;

/**
 * type：64   "免费观影" "猜电影"  点击进入web
 */

public class GuessMovie extends HomeListSuper {

    @Override
    public int getHomeItemType() {
        return getType();
    }

    @Override
    protected int getHomeDataType() {
        return -1;
    }

    /**
     * comSpecialObjId : 23253680
     * title : 只有高逼格的人才答得上来
     * memo : 导演没打算让你看懂的“罗曼蒂克”，你看懂了么？
     * tag : 猜电影
     * isShow : 是
     * pic1Url : http://img5.mtime.cn/mg/2016/12/21/142402.63001341.jpg
     * type : 64
     * url : http://feature.mtime.cn/puzzle/answer.html?gameClassId=1666&amp;gameTypeId=1&amp;gameId=0
     * time : 2016-12-21 14:22:51
     */

    private int comSpecialObjId;
    private String title;
    private String memo;
    private String tag;
    private String isShow;
    private String pic1Url;
    private int type;
    private String url;
    private String time;

    public int getComSpecialObjId() {
        return comSpecialObjId;
    }

    public void setComSpecialObjId(int comSpecialObjId) {
        this.comSpecialObjId = comSpecialObjId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public String getPic1Url() {
        return pic1Url;
    }

    public void setPic1Url(String pic1Url) {
        this.pic1Url = pic1Url;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
