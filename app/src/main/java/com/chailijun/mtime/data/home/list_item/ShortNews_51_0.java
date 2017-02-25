package com.chailijun.mtime.data.home.list_item;

import com.chailijun.mtime.data.home.HomeListSuper;
import com.chailijun.mtime.data.home.Relations1;
import com.chailijun.mtime.data.home.RelationsBean;

import java.util.List;

/**
 * type：51  dataType：0  "简讯"
 */

public class ShortNews_51_0 extends HomeListSuper {

    @Override
    public int getHomeItemType() {
        return getType();
    }

    @Override
    protected int getHomeDataType() {
        return getDataType();
    }

    /**
     * title : 克尔斯滕邓斯特将主演AMC新剧
     * titlesmall : 克尔斯滕邓斯特将主演AMC新剧
     * publishTime : 1483789924
     * tag : 简讯
     * id : 1565190
     * status : 1
     * comSpecialObjId : 23394075
     * dataType : 0
     * relations : [{"type":1,"id":239181,"name":"On Becoming A God","image":"http://img31.mtime.cn/mt/1181/239181/239181_1280X720X2.jpg","year":"2017","rating":"0.0","scoreCount":0,"releaseDate":"","relaseLocation":""},{"type":2,"id":929797,"name":"克尔斯滕·邓斯特","nameEn":"Kirsten Dunst","image":"http://img31.mtime.cn/ph/2014/03/14/152510.91171810_1280X720X2.jpg","year":1982,"rating":8.6},{"type":2,"id":1633046,"name":"欧格斯·兰斯莫斯","nameEn":"Yorgos Lanthimos","image":"http://img31.mtime.cn/ph/2015/05/06/091310.98348622_1280X720X2.jpg","year":1973,"rating":7.7}]
     * isShow : 是
     * img1 : http://img5.mtime.cn/mg/2017/01/07/115111.94339330.jpg
     * img2 : http://img5.mtime.cn/mg/2017/01/07/115111.94339330.jpg
     * img3 :
     * type : 51
     * commentCount : 12
     * time : 2017-1-7 12:55:04
     * content : "龙虾"导演欧格斯兰斯莫斯执导黑色喜剧
     */

    private String title;
    private String titlesmall;
    private long publishTime;
    private String tag;
    private int id;
    private int status;
    private int comSpecialObjId;
    private int dataType;
    private String isShow;
    private String img1;
    private String img2;
    private String img3;
    private int type;
    private int commentCount;
    private String time;
    private String content;
    /**
     * type : 1
     * id : 239181
     * name : On Becoming A God
     * image : http://img31.mtime.cn/mt/1181/239181/239181_1280X720X2.jpg
     * year : 2017
     * rating : 0.0
     * scoreCount : 0
     * releaseDate :
     * relaseLocation :
     */

    private List<RelationsBean> relations;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitlesmall() {
        return titlesmall;
    }

    public void setTitlesmall(String titlesmall) {
        this.titlesmall = titlesmall;
    }

    public long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getComSpecialObjId() {
        return comSpecialObjId;
    }

    public void setComSpecialObjId(int comSpecialObjId) {
        this.comSpecialObjId = comSpecialObjId;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getImg3() {
        return img3;
    }

    public void setImg3(String img3) {
        this.img3 = img3;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<RelationsBean> getRelations() {
        return relations;
    }

    public void setRelations(List<RelationsBean> relations) {
        this.relations = relations;
    }
}
