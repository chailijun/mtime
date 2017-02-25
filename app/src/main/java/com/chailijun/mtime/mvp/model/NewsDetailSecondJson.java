package com.chailijun.mtime.mvp.model;

import java.util.List;

public class NewsDetailSecondJson extends BaseData {


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
        /**
         * contentType : 4
         * index : 4
         * reads : [{"subTitle":"观影人次逼近500万 本土片\"绝地保龄\"首周居亚","title":"《奇异博士》韩国票房三连冠","newsId":1563095,"type":0,"imgUrl":""},{"subTitle":"《奇异博士》周票房连冠 《比利·林恩》首周8000万","title":"2016内地票房突破400亿","newsId":1563012,"type":0,"imgUrl":""},{"subTitle":"《降临》开画居季 《比利林恩》低于预期","title":"《奇异博士》全球票房三连冠","newsId":1563028,"type":0,"imgUrl":""}]
         * relatedGoods : {}
         */

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
            private RelatedGoodsBean relatedGoods;
            /**
             * subTitle : 观影人次逼近500万 本土片"绝地保龄"首周居亚
             * title : 《奇异博士》韩国票房三连冠
             * newsId : 1563095
             * type : 0
             * imgUrl :
             */

            private List<ReadsBean> reads;

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

            public RelatedGoodsBean getRelatedGoods() {
                return relatedGoods;
            }

            public void setRelatedGoods(RelatedGoodsBean relatedGoods) {
                this.relatedGoods = relatedGoods;
            }

            public List<ReadsBean> getReads() {
                return reads;
            }

            public void setReads(List<ReadsBean> reads) {
                this.reads = reads;
            }

            public static class RelatedGoodsBean{
                private String relatedUrl;

                private List<GoodsListBean> goodsList;

                public String getRelatedUrl() {
                    return relatedUrl;
                }

                public void setRelatedUrl(String relatedUrl) {
                    this.relatedUrl = relatedUrl;
                }

                public List<GoodsListBean> getGoodsList() {
                    return goodsList;
                }

                public void setGoodsList(List<GoodsListBean> goodsList) {
                    this.goodsList = goodsList;
                }


            }

            public static class ReadsBean {
                private String subTitle;
                private String title;
                private int newsId;
                private int type;
                private String imgUrl;

                public String getSubTitle() {
                    return subTitle;
                }

                public void setSubTitle(String subTitle) {
                    this.subTitle = subTitle;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public int getNewsId() {
                    return newsId;
                }

                public void setNewsId(int newsId) {
                    this.newsId = newsId;
                }

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public String getImgUrl() {
                    return imgUrl;
                }

                public void setImgUrl(String imgUrl) {
                    this.imgUrl = imgUrl;
                }
            }
        }
    }
}
