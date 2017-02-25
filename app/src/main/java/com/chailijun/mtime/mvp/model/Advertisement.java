package com.chailijun.mtime.mvp.model;


import java.util.List;

/**

 * 广告
 */
public class Advertisement {

    private boolean success;
    private int count;
    private String error;

    private List<AdvListBean> advList;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<AdvListBean> getAdvList() {
        return advList;
    }

    public void setAdvList(List<AdvListBean> advList) {
        this.advList = advList;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public static class AdvListBean {
        private String type;
        private String typeName;
        private String advTag;
        private boolean isHorizontalScreen;
        private long startDate;
        private long endDate;
        private String url;
        private String image;
        private String tag;
        private boolean isOpenH5;
        private boolean isLogo;
        private String entryText;

        private GotoPageBean gotoPage;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getAdvTag() {
            return advTag;
        }

        public void setAdvTag(String advTag) {
            this.advTag = advTag;
        }

        public boolean isIsHorizontalScreen() {
            return isHorizontalScreen;
        }

        public void setIsHorizontalScreen(boolean isHorizontalScreen) {
            this.isHorizontalScreen = isHorizontalScreen;
        }

        public long getStartDate() {
            return startDate;
        }

        public void setStartDate(long startDate) {
            this.startDate = startDate;
        }

        public long getEndDate() {
            return endDate;
        }

        public void setEndDate(long endDate) {
            this.endDate = endDate;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public boolean isIsOpenH5() {
            return isOpenH5;
        }

        public void setIsOpenH5(boolean isOpenH5) {
            this.isOpenH5 = isOpenH5;
        }

        public boolean isIsLogo() {
            return isLogo;
        }

        public void setIsLogo(boolean isLogo) {
            this.isLogo = isLogo;
        }

        public String getEntryText() {
            return entryText;
        }

        public void setEntryText(String entryText) {
            this.entryText = entryText;
        }

        public GotoPageBean getGotoPage() {
            return gotoPage;
        }

        public void setGotoPage(GotoPageBean gotoPage) {
            this.gotoPage = gotoPage;
        }

        public static class GotoPageBean {

            private String gotoType;
            private String url;
            private ParametersBean parameters;
            private Parameters1Bean parameters1;
            private boolean isGoH5;
            private String relatedTypeUrl;

            public String getGotoType() {
                return gotoType;
            }

            public void setGotoType(String gotoType) {
                this.gotoType = gotoType;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
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

            public boolean isIsGoH5() {
                return isGoH5;
            }

            public void setIsGoH5(boolean isGoH5) {
                this.isGoH5 = isGoH5;
            }

            public String getRelatedTypeUrl() {
                return relatedTypeUrl;
            }

            public void setRelatedTypeUrl(String relatedTypeUrl) {
                this.relatedTypeUrl = relatedTypeUrl;
            }

            public static class ParametersBean {
                private int movieId;

                public int getMovieId() {
                    return movieId;
                }

                public void setMovieId(int movieId) {
                    this.movieId = movieId;
                }
            }

            public static class Parameters1Bean {
                private String movieId;

                public String getMovieId() {
                    return movieId;
                }

                public void setMovieId(String movieId) {
                    this.movieId = movieId;
                }
            }
        }
    }
}
