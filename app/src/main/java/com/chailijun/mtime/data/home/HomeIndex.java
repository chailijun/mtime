package com.chailijun.mtime.data.home;

import java.util.List;

public class HomeIndex {

    private String searchBarDescribe;

    private List<AdvListBean> advList;

    private List<MallRecommendsBean> mallRecommends;

    private List<TopPostersBean> topPosters;

    public String getSearchBarDescribe() {
        return searchBarDescribe;
    }

    public void setSearchBarDescribe(String searchBarDescribe) {
        this.searchBarDescribe = searchBarDescribe;
    }

    public List<AdvListBean> getAdvList() {
        return advList;
    }

    public void setAdvList(List<AdvListBean> advList) {
        this.advList = advList;
    }

    public List<MallRecommendsBean> getMallRecommends() {
        return mallRecommends;
    }

    public void setMallRecommends(List<MallRecommendsBean> mallRecommends) {
        this.mallRecommends = mallRecommends;
    }

    public List<TopPostersBean> getTopPosters() {
        return topPosters;
    }

    public void setTopPosters(List<TopPostersBean> topPosters) {
        this.topPosters = topPosters;
    }

    public static class AdvListBean {
        private String advTag;
        private String img;
        private GotoPageBean gotoPage;

        public String getAdvTag() {
            return advTag;
        }

        public void setAdvTag(String advTag) {
            this.advTag = advTag;
        }

        public GotoPageBean getGotoPage() {
            return gotoPage;
        }

        public void setGotoPage(GotoPageBean gotoPage) {
            this.gotoPage = gotoPage;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }

    public static class MallRecommendsBean {
        private int goodsId;
        private String gotoType;
        private String image;
        private String keyWord;
        private int position;
        private String title;
        private String titleColor;
        private String titleSmall;
        private int type;
        private String url;

        public int getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(int goodsId) {
            this.goodsId = goodsId;
        }

        public String getGotoType() {
            return gotoType;
        }

        public void setGotoType(String gotoType) {
            this.gotoType = gotoType;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getKeyWord() {
            return keyWord;
        }

        public void setKeyWord(String keyWord) {
            this.keyWord = keyWord;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitleColor() {
            return titleColor;
        }

        public void setTitleColor(String titleColor) {
            this.titleColor = titleColor;
        }

        public String getTitleSmall() {
            return titleSmall;
        }

        public void setTitleSmall(String titleSmall) {
            this.titleSmall = titleSmall;
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
    }

    public static class TopPostersBean {

        /**
         * advTag :
         * img : http://chailijun.oss-cn-shanghai.aliyuncs.com/mg/2017/01/03/084455.59735412.jpg
         * gotoPage : {"gotoType":"gotonews","isGoH5":false,"parameters":{"newId":1564984},"parameters1":{"newId":"1564984"},"url":"http://api.m.mtime.cn/News/Detail.api?newsId=1564984"}
         */

        private String advTag;
        private String img;
        /**
         * gotoType : gotonews
         * isGoH5 : false
         * parameters : {"newId":1564984}
         * parameters1 : {"newId":"1564984"}
         * url : http://api.m.mtime.cn/News/Detail.api?newsId=1564984
         */

        private GotoPageBean gotoPage;

        public String getAdvTag() {
            return advTag;
        }

        public void setAdvTag(String advTag) {
            this.advTag = advTag;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public GotoPageBean getGotoPage() {
            return gotoPage;
        }

        public void setGotoPage(GotoPageBean gotoPage) {
            this.gotoPage = gotoPage;
        }
    }
}
