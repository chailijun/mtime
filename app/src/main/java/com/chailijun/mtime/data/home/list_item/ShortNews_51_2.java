package com.chailijun.mtime.data.home.list_item;

import com.chailijun.mtime.data.home.HomeListSuper;
import com.chailijun.mtime.data.home.Relations1;
import com.chailijun.mtime.data.home.RelationsBean;

import java.util.List;

/**
 * type：51  dataType：2  "简讯"  带有视频标识
 */

public class ShortNews_51_2 extends HomeListSuper {

    @Override
    public int getHomeItemType() {
        return getType();
    }

    @Override
    protected int getHomeDataType() {
        return getDataType();
    }

    /**
     * title : 电影《欢乐喜剧人》曝人物版预告片
     * titlesmall : 《欢乐喜剧人》人物版预告片
     * publishTime : 1483776173
     * tag : 简讯
     * id : 1565186
     * status : 1
     * comSpecialObjId : 23393318
     * dataType : 2
     * relations : [{"type":1,"id":238020,"name":"欢乐喜剧人","image":"http://img5.mtime.cn/mt/2016/12/13/155832.74041160_1280X720X2.jpg","year":"2017","rating":"0.0","scoreCount":86,"releaseDate":"2017年1月28日","relaseLocation":"中国"},{"type":2,"id":900722,"name":"罗温·艾金森","nameEn":"Rowan Atkinson","image":"http://img31.mtime.cn/ph/2014/06/15/095337.58271863_1280X720X2.jpg","year":1955,"rating":9.4},{"type":2,"id":1249054,"name":"郭德纲","nameEn":"Degang Guo","image":"http://img31.mtime.cn/ph/2016/08/30/160847.94668956_1280X720X2.jpg","year":1973,"rating":7.5},{"type":2,"id":1858614,"name":"岳云鹏","nameEn":"Yunpeng Yue","image":"http://img5.mtime.cn/ph/2016/12/14/144629.28266265_1280X720X2.jpg","year":1985,"rating":7.1}]
     * isShow : 是
     * img1 : http://img5.mtime.cn/mg/2017/01/06/225858.35195922.jpg
     * img2 : http://img5.mtime.cn/mg/2017/01/06/225858.35195922.jpg
     * img3 :
     * type : 51
     * commentCount : 49
     * time : 2017-1-7 9:48:45
     * content : 郭德纲小岳岳本色演出 憨豆先生献终极秀
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
     * id : 238020
     * name : 欢乐喜剧人
     * image : http://img5.mtime.cn/mt/2016/12/13/155832.74041160_1280X720X2.jpg
     * year : 2017
     * rating : 0.0
     * scoreCount : 86
     * releaseDate : 2017年1月28日
     * relaseLocation : 中国
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
