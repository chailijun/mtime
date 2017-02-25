package com.chailijun.mtime.mvp.model;

import java.io.Serializable;
import java.util.List;

public class HomeIndexJson extends HomeBaseData {

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

        private LiveBean live;
        private String searchBarDescribe;

        private List<AdvListBean> advList;
        private List<?> mallEntrys;

        private List<MallRecommendsBean> mallRecommends;
        private List<?> specialTopicList;

        private List<TopPostersBean> topPosters;


        public LiveBean getLive() {
            return live;
        }

        public void setLive(LiveBean live) {
            this.live = live;
        }

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

        public List<?> getMallEntrys() {
            return mallEntrys;
        }

        public void setMallEntrys(List<?> mallEntrys) {
            this.mallEntrys = mallEntrys;
        }

        public List<MallRecommendsBean> getMallRecommends() {
            return mallRecommends;
        }

        public void setMallRecommends(List<MallRecommendsBean> mallRecommends) {
            this.mallRecommends = mallRecommends;
        }

        public List<?> getSpecialTopicList() {
            return specialTopicList;
        }

        public void setSpecialTopicList(List<?> specialTopicList) {
            this.specialTopicList = specialTopicList;
        }

        public List<TopPostersBean> getTopPosters() {
            return topPosters;
        }

        public void setTopPosters(List<TopPostersBean> topPosters) {
            this.topPosters = topPosters;
        }

        public static class LiveBean {
            private String img;
            private int liveId;
            private String playNumTag;
            private String playTag;
            private int status;
            private String title;
            private String url;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public int getLiveId() {
                return liveId;
            }

            public void setLiveId(int liveId) {
                this.liveId = liveId;
            }

            public String getPlayNumTag() {
                return playNumTag;
            }

            public void setPlayNumTag(String playNumTag) {
                this.playNumTag = playNumTag;
            }

            public String getPlayTag() {
                return playTag;
            }

            public void setPlayTag(String playTag) {
                this.playTag = playTag;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
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

        public static class AdvListBean {
            private String advTag;
            /**
             * gotoType : gotourl
             * isGoH5 : false
             * parameters : {}
             * parameters1 : {}
             * relatedId : 0
             * relatedTypeUrl :
             * url : http://m.mtime.cn/#!/download/
             */

            private GotoPageBean gotoPage;
            private String img;
            private String url;

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

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public static class GotoPageBean {
                private String gotoType;
                private boolean isGoH5;
                private ParametersBean parameters;
                private Parameters1Bean parameters1;
                private int relatedId;
                private String relatedTypeUrl;
                private String url;

                public String getGotoType() {
                    return gotoType;
                }

                public void setGotoType(String gotoType) {
                    this.gotoType = gotoType;
                }

                public boolean isIsGoH5() {
                    return isGoH5;
                }

                public void setIsGoH5(boolean isGoH5) {
                    this.isGoH5 = isGoH5;
                }

                public ParametersBean getParameters() {
                    return parameters;
                }

                public void setParameters(ParametersBean parameters) {
                    this.parameters = parameters;
                }

                public Parameters1Bean getParameters1() {
                    return parameters1;
                }

                public void setParameters1(Parameters1Bean parameters1) {
                    this.parameters1 = parameters1;
                }

                public int getRelatedId() {
                    return relatedId;
                }

                public void setRelatedId(int relatedId) {
                    this.relatedId = relatedId;
                }

                public String getRelatedTypeUrl() {
                    return relatedTypeUrl;
                }

                public void setRelatedTypeUrl(String relatedTypeUrl) {
                    this.relatedTypeUrl = relatedTypeUrl;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public static class ParametersBean {
                }

                public static class Parameters1Bean {
                }
            }
        }

        public static class MallRecommendsBean {
            private int contentId;
            private int endTime;
            private int goodsId;
            /**
             * gotoType : gotogoodslist
             * isGoH5 : false
             * parameters : {"keyword":0}
             * parameters1 : {"keyword":"?salefid=1"}
             * relatedId : 0
             * relatedTypeUrl :
             * url : http://mall.wv.mtime.cn/?salefid=1#!/commerce/list/
             */

            private GotoPageBean gotoPage;
            private String image;
            private int position;
            private int startTime;
            private String title;
            private String titleColor;
            private String titleSmall;
            private int warmup;

            public int getContentId() {
                return contentId;
            }

            public void setContentId(int contentId) {
                this.contentId = contentId;
            }

            public int getEndTime() {
                return endTime;
            }

            public void setEndTime(int endTime) {
                this.endTime = endTime;
            }

            public int getGoodsId() {
                return goodsId;
            }

            public void setGoodsId(int goodsId) {
                this.goodsId = goodsId;
            }

            public GotoPageBean getGotoPage() {
                return gotoPage;
            }

            public void setGotoPage(GotoPageBean gotoPage) {
                this.gotoPage = gotoPage;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public int getPosition() {
                return position;
            }

            public void setPosition(int position) {
                this.position = position;
            }

            public int getStartTime() {
                return startTime;
            }

            public void setStartTime(int startTime) {
                this.startTime = startTime;
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

            public int getWarmup() {
                return warmup;
            }

            public void setWarmup(int warmup) {
                this.warmup = warmup;
            }

            public static class GotoPageBean {
                private String gotoType;
                private boolean isGoH5;
                /**
                 * keyword : 0
                 */

                private ParametersBean parameters;
                /**
                 * keyword : ?salefid=1
                 */

                private Parameters1Bean parameters1;
                private int relatedId;
                private String relatedTypeUrl;
                private String url;

                public String getGotoType() {
                    return gotoType;
                }

                public void setGotoType(String gotoType) {
                    this.gotoType = gotoType;
                }

                public boolean isIsGoH5() {
                    return isGoH5;
                }

                public void setIsGoH5(boolean isGoH5) {
                    this.isGoH5 = isGoH5;
                }

                public ParametersBean getParameters() {
                    return parameters;
                }

                public void setParameters(ParametersBean parameters) {
                    this.parameters = parameters;
                }

                public Parameters1Bean getParameters1() {
                    return parameters1;
                }

                public void setParameters1(Parameters1Bean parameters1) {
                    this.parameters1 = parameters1;
                }

                public int getRelatedId() {
                    return relatedId;
                }

                public void setRelatedId(int relatedId) {
                    this.relatedId = relatedId;
                }

                public String getRelatedTypeUrl() {
                    return relatedTypeUrl;
                }

                public void setRelatedTypeUrl(String relatedTypeUrl) {
                    this.relatedTypeUrl = relatedTypeUrl;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public static class ParametersBean {
                    private int keyword;
                    private int goodsId;

                    public int getGoodsId() {
                        return goodsId;
                    }

                    public void setGoodsId(int goodsId) {
                        this.goodsId = goodsId;
                    }

                    public int getKeyword() {
                        return keyword;
                    }

                    public void setKeyword(int keyword) {
                        this.keyword = keyword;
                    }
                }

                public static class Parameters1Bean {
                    private String keyword;
                    private String goodsId;

                    public String getGoodsId() {
                        return goodsId;
                    }

                    public void setGoodsId(String goodsId) {
                        this.goodsId = goodsId;
                    }

                    public String getKeyword() {
                        return keyword;
                    }

                    public void setKeyword(String keyword) {
                        this.keyword = keyword;
                    }
                }
            }
        }

        public static class TopPostersBean {
            private String advTag;
            /**
             * gotoType : gotonews
             * isGoH5 : false
             * parameters : {"newId":1562738}
             * parameters1 : {"newId":"1562738"}
             * relatedId : 1562738
             * relatedTypeUrl :
             * url : http://m.mtime.cn/#!/news/movie/1562738/
             */

            private GotoPageBean gotoPage;
            private String img;

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

            public static class GotoPageBean implements Serializable{
                private String gotoType;
                private boolean isGoH5;
                /**
                 * newId : 1562738
                 */

                private ParametersBean parameters;
                /**
                 * newId : 1562738
                 */

                private Parameters1Bean parameters1;
                private int relatedId;
                private String relatedTypeUrl;
                private String url;

                public String getGotoType() {
                    return gotoType;
                }

                public void setGotoType(String gotoType) {
                    this.gotoType = gotoType;
                }

                public boolean isIsGoH5() {
                    return isGoH5;
                }

                public void setIsGoH5(boolean isGoH5) {
                    this.isGoH5 = isGoH5;
                }

                public ParametersBean getParameters() {
                    return parameters;
                }

                public void setParameters(ParametersBean parameters) {
                    this.parameters = parameters;
                }

                public Parameters1Bean getParameters1() {
                    return parameters1;
                }

                public void setParameters1(Parameters1Bean parameters1) {
                    this.parameters1 = parameters1;
                }

                public int getRelatedId() {
                    return relatedId;
                }

                public void setRelatedId(int relatedId) {
                    this.relatedId = relatedId;
                }

                public String getRelatedTypeUrl() {
                    return relatedTypeUrl;
                }

                public void setRelatedTypeUrl(String relatedTypeUrl) {
                    this.relatedTypeUrl = relatedTypeUrl;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public static class ParametersBean implements Serializable{
                    private int newId;

                    public int getNewId() {
                        return newId;
                    }

                    public void setNewId(int newId) {
                        this.newId = newId;
                    }
                }

                public static class Parameters1Bean implements Serializable{
                    private String newId;

                    public String getNewId() {
                        return newId;
                    }

                    public void setNewId(String newId) {
                        this.newId = newId;
                    }
                }
            }
        }
    }
}
