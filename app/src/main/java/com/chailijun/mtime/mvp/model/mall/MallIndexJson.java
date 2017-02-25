package com.chailijun.mtime.mvp.model.mall;

import com.chailijun.mtime.mvp.model.BaseData;

import java.io.Serializable;
import java.util.List;

public class MallIndexJson extends BaseData{

    private String code;

    private DataBean data;
    private String msg;
    private String showMsg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getShowMsg() {
        return showMsg;
    }

    public void setShowMsg(String showMsg) {
        this.showMsg = showMsg;
    }

    public static class DataBean {

        private MallBean mall;

        private List<SpecialTopicListBean> specialTopicList;

        public MallBean getMall() {
            return mall;
        }

        public void setMall(MallBean mall) {
            this.mall = mall;
        }

        public List<SpecialTopicListBean> getSpecialTopicList() {
            return specialTopicList;
        }

        public void setSpecialTopicList(List<SpecialTopicListBean> specialTopicList) {
            this.specialTopicList = specialTopicList;
        }

        public static class MallBean {

            private CellABean cellA;

            private CellBBean cellB;
            private CellCBean cellC;
            private String navBackgroundBigImg;
            private String searchBarDescribe;

            private List<CategoryBean> category;

            private List<NavigatorIconBean> navigatorIcon;

            private List<ScrollImgBean> scrollImg;

            private List<TopicBean> topic;

            public CellABean getCellA() {
                return cellA;
            }

            public void setCellA(CellABean cellA) {
                this.cellA = cellA;
            }

            public CellBBean getCellB() {
                return cellB;
            }

            public void setCellB(CellBBean cellB) {
                this.cellB = cellB;
            }

            public CellCBean getCellC() {
                return cellC;
            }

            public void setCellC(CellCBean cellC) {
                this.cellC = cellC;
            }

            public String getNavBackgroundBigImg() {
                return navBackgroundBigImg;
            }

            public void setNavBackgroundBigImg(String navBackgroundBigImg) {
                this.navBackgroundBigImg = navBackgroundBigImg;
            }

            public String getSearchBarDescribe() {
                return searchBarDescribe;
            }

            public void setSearchBarDescribe(String searchBarDescribe) {
                this.searchBarDescribe = searchBarDescribe;
            }

            public List<CategoryBean> getCategory() {
                return category;
            }

            public void setCategory(List<CategoryBean> category) {
                this.category = category;
            }

            public List<NavigatorIconBean> getNavigatorIcon() {
                return navigatorIcon;
            }

            public void setNavigatorIcon(List<NavigatorIconBean> navigatorIcon) {
                this.navigatorIcon = navigatorIcon;
            }

            public List<ScrollImgBean> getScrollImg() {
                return scrollImg;
            }

            public void setScrollImg(List<ScrollImgBean> scrollImg) {
                this.scrollImg = scrollImg;
            }

            public List<TopicBean> getTopic() {
                return topic;
            }

            public void setTopic(List<TopicBean> topic) {
                this.topic = topic;
            }

            public static class CellABean {
                private int goodsId;
                private String img;
                private long longTime;
                private long startTime;
                private String subTitle;
                private String title;
                private String titleColor;
                private String url;
                private int warmup;

                public int getGoodsId() {
                    return goodsId;
                }

                public void setGoodsId(int goodsId) {
                    this.goodsId = goodsId;
                }

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
                }

                public long getLongTime() {
                    return longTime;
                }

                public void setLongTime(long longTime) {
                    this.longTime = longTime;
                }

                public long getStartTime() {
                    return startTime;
                }

                public void setStartTime(long startTime) {
                    this.startTime = startTime;
                }

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

                public String getTitleColor() {
                    return titleColor;
                }

                public void setTitleColor(String titleColor) {
                    this.titleColor = titleColor;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public int getWarmup() {
                    return warmup;
                }

                public void setWarmup(int warmup) {
                    this.warmup = warmup;
                }
            }

            public static class CellBBean {
                private int goodsId;
                private String img;
                private long longTime;
                private long startTime;
                private String subTitle;
                private String title;
                private String titleColor;
                private String url;
                private int warmup;

                public int getGoodsId() {
                    return goodsId;
                }

                public void setGoodsId(int goodsId) {
                    this.goodsId = goodsId;
                }

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
                }

                public long getLongTime() {
                    return longTime;
                }

                public void setLongTime(long longTime) {
                    this.longTime = longTime;
                }

                public long getStartTime() {
                    return startTime;
                }

                public void setStartTime(long startTime) {
                    this.startTime = startTime;
                }

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

                public String getTitleColor() {
                    return titleColor;
                }

                public void setTitleColor(String titleColor) {
                    this.titleColor = titleColor;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public int getWarmup() {
                    return warmup;
                }

                public void setWarmup(int warmup) {
                    this.warmup = warmup;
                }
            }

            public static class CellCBean {

                private List<ListBean> list;

                public List<ListBean> getList() {
                    return list;
                }

                public void setList(List<ListBean> list) {
                    this.list = list;
                }

                public static class ListBean {
                    private String image;
                    private String subTitle;
                    private String title;
                    private String titleColor;
                    private String url;

                    public String getImage() {
                        return image;
                    }

                    public void setImage(String image) {
                        this.image = image;
                    }

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

                    public String getTitleColor() {
                        return titleColor;
                    }

                    public void setTitleColor(String titleColor) {
                        this.titleColor = titleColor;
                    }

                    public String getUrl() {
                        return url;
                    }

                    public void setUrl(String url) {
                        this.url = url;
                    }
                }
            }

            public static class CategoryBean {
                private String colorValue;
                private int goodsId;
                private String image;
                private String imageUrl;
                private String moreUrl;
                private String name;

                private List<SubListBean> subList;

                public String getColorValue() {
                    return colorValue;
                }

                public void setColorValue(String colorValue) {
                    this.colorValue = colorValue;
                }

                public int getGoodsId() {
                    return goodsId;
                }

                public void setGoodsId(int goodsId) {
                    this.goodsId = goodsId;
                }

                public String getImage() {
                    return image;
                }

                public void setImage(String image) {
                    this.image = image;
                }

                public String getImageUrl() {
                    return imageUrl;
                }

                public void setImageUrl(String imageUrl) {
                    this.imageUrl = imageUrl;
                }

                public String getMoreUrl() {
                    return moreUrl;
                }

                public void setMoreUrl(String moreUrl) {
                    this.moreUrl = moreUrl;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public List<SubListBean> getSubList() {
                    return subList;
                }

                public void setSubList(List<SubListBean> subList) {
                    this.subList = subList;
                }

                public static class SubListBean {
                    private int goodsId;
                    private String image;
                    private String title;
                    private String url;

                    public int getGoodsId() {
                        return goodsId;
                    }

                    public void setGoodsId(int goodsId) {
                        this.goodsId = goodsId;
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

                    public String getUrl() {
                        return url;
                    }

                    public void setUrl(String url) {
                        this.url = url;
                    }
                }
            }

            public static class NavigatorIconBean {
                private String iconTitle;
                private String image;
                private String url;

                public String getIconTitle() {
                    return iconTitle;
                }

                public void setIconTitle(String iconTitle) {
                    this.iconTitle = iconTitle;
                }

                public String getImage() {
                    return image;
                }

                public void setImage(String image) {
                    this.image = image;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }

            public static class ScrollImgBean {
                private String image;
                private String url;

                public String getImage() {
                    return image;
                }

                public void setImage(String image) {
                    this.image = image;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }

            public static class TopicBean implements Serializable{
                private String backgroupImage;
                private String checkedImage;
                private int goodsId;
                private String titleCn;
                private String titleEn;
                private String uncheckImage;
                private String url;

                private List<SubListBean> subList;

                public String getBackgroupImage() {
                    return backgroupImage;
                }

                public void setBackgroupImage(String backgroupImage) {
                    this.backgroupImage = backgroupImage;
                }

                public String getCheckedImage() {
                    return checkedImage;
                }

                public void setCheckedImage(String checkedImage) {
                    this.checkedImage = checkedImage;
                }

                public int getGoodsId() {
                    return goodsId;
                }

                public void setGoodsId(int goodsId) {
                    this.goodsId = goodsId;
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

                public String getUncheckImage() {
                    return uncheckImage;
                }

                public void setUncheckImage(String uncheckImage) {
                    this.uncheckImage = uncheckImage;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public List<SubListBean> getSubList() {
                    return subList;
                }

                public void setSubList(List<SubListBean> subList) {
                    this.subList = subList;
                }

                public static class SubListBean implements Serializable{
                    private int goodsId;
                    private String image;
                    private String title;
                    private String url;

                    public int getGoodsId() {
                        return goodsId;
                    }

                    public void setGoodsId(int goodsId) {
                        this.goodsId = goodsId;
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

                    public String getUrl() {
                        return url;
                    }

                    public void setUrl(String url) {
                        this.url = url;
                    }
                }
            }
        }

        public static class SpecialTopicListBean {
            private String moreLinks;
            private String relatedGoodsIdsStr;
            private String relatedUrl;
            private String specialTopicImg;
            private int specialTopicType;

            private List<RelatedGoodsListBean> relatedGoodsList;

            public String getMoreLinks() {
                return moreLinks;
            }

            public void setMoreLinks(String moreLinks) {
                this.moreLinks = moreLinks;
            }

            public String getRelatedGoodsIdsStr() {
                return relatedGoodsIdsStr;
            }

            public void setRelatedGoodsIdsStr(String relatedGoodsIdsStr) {
                this.relatedGoodsIdsStr = relatedGoodsIdsStr;
            }

            public String getRelatedUrl() {
                return relatedUrl;
            }

            public void setRelatedUrl(String relatedUrl) {
                this.relatedUrl = relatedUrl;
            }

            public String getSpecialTopicImg() {
                return specialTopicImg;
            }

            public void setSpecialTopicImg(String specialTopicImg) {
                this.specialTopicImg = specialTopicImg;
            }

            public int getSpecialTopicType() {
                return specialTopicType;
            }

            public void setSpecialTopicType(int specialTopicType) {
                this.specialTopicType = specialTopicType;
            }

            public List<RelatedGoodsListBean> getRelatedGoodsList() {
                return relatedGoodsList;
            }

            public void setRelatedGoodsList(List<RelatedGoodsListBean> relatedGoodsList) {
                this.relatedGoodsList = relatedGoodsList;
            }

            public static class RelatedGoodsListBean {
                private int goodsId;
                private String img;
                private String longName;
                private String name;
                private String price;
                private String url;

                public int getGoodsId() {
                    return goodsId;
                }

                public void setGoodsId(int goodsId) {
                    this.goodsId = goodsId;
                }

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
                }

                public String getLongName() {
                    return longName;
                }

                public void setLongName(String longName) {
                    this.longName = longName;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }
        }
    }
}
