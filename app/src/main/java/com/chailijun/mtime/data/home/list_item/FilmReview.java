package com.chailijun.mtime.data.home.list_item;

import com.chailijun.mtime.data.home.HomeListSuper;

import java.util.List;

/**
 * type：336   "影评"
 */

public class FilmReview extends HomeListSuper{

    @Override
    public int getHomeItemType() {
        return getType();
    }

    @Override
    protected int getHomeDataType() {
        return -1;
    }

    /**
     * id : 7991550
     * nickname : Cydeny
     * comSpecialObjId : 23351875
     * userImage : http://img32.mtime.cn/up/2013/01/08/081313.64016596_128X128.jpg
     * title : 太空里的“上错飞船嫁错郎”
     * summaryInfo : 当“作家”大表姐出现后，电影就变成了一个彻头彻尾的浪漫故事，充满着过剩的荷尔蒙。
     * tag : 影评
     * type : 336
     * rating : 4.0
     * relatedObj : {"type":["冒险","剧情","爱情","科幻"],"id":123883,"title":"太空旅客","name":"太空旅客","titleCn":"太空旅客","titleEn":"Passengers","runtime":"116分钟","url":"http://movie.mtime.com/123883/","wapUrl":"http://movie.wap.mtime.com/123883/","rating":7.5,"image":"http://img5.mtime.cn/mg/2017/01/02/102541.69140989.jpg","releaseLocation":"中国"}
     */

    private String id;
    private String nickname;
    private int comSpecialObjId;
    private String userImage;
    private String title;
    private String summaryInfo;
    private String tag;
    private int type;
    private String rating;
    /**
     * type : ["冒险","剧情","爱情","科幻"]
     * id : 123883
     * title : 太空旅客
     * name : 太空旅客
     * titleCn : 太空旅客
     * titleEn : Passengers
     * runtime : 116分钟
     * url : http://movie.mtime.com/123883/
     * wapUrl : http://movie.wap.mtime.com/123883/
     * rating : 7.5
     * image : http://img5.mtime.cn/mg/2017/01/02/102541.69140989.jpg
     * releaseLocation : 中国
     */

    private RelatedObjBean relatedObj;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getComSpecialObjId() {
        return comSpecialObjId;
    }

    public void setComSpecialObjId(int comSpecialObjId) {
        this.comSpecialObjId = comSpecialObjId;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public static class RelatedObjBean {
        private int id;
        private String title;
        private String name;
        private String titleCn;
        private String titleEn;
        private String runtime;
        private String url;
        private String wapUrl;
        private double rating;
        private String image;
        private String releaseLocation;
        private List<String> type;

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public String getRuntime() {
            return runtime;
        }

        public void setRuntime(String runtime) {
            this.runtime = runtime;
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

        public double getRating() {
            return rating;
        }

        public void setRating(double rating) {
            this.rating = rating;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getReleaseLocation() {
            return releaseLocation;
        }

        public void setReleaseLocation(String releaseLocation) {
            this.releaseLocation = releaseLocation;
        }

        public List<String> getType() {
            return type;
        }

        public void setType(List<String> type) {
            this.type = type;
        }
    }
}
