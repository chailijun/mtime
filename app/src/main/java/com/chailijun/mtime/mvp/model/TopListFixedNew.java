package com.chailijun.mtime.mvp.model;

import java.util.List;


public class TopListFixedNew extends BaseData{

    private List<TopListBean> topList;

    public List<TopListBean> getTopList() {
        return topList;
    }

    public void setTopList(List<TopListBean> topList) {
        this.topList = topList;
    }

    public static class TopListBean {
        private String title;
        private String titleSmall;
        private String pageSubAreaId;
        private List<subTopListBean> subTopList;

        public List<subTopListBean> getSubTopList() {
            return subTopList;
        }

        public void setSubTopList(List<subTopListBean> subTopList) {
            this.subTopList = subTopList;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitleSmall() {
            return titleSmall;
        }

        public void setTitleSmall(String titleSmall) {
            this.titleSmall = titleSmall;
        }

        public String getPageSubAreaId() {
            return pageSubAreaId;
        }

        public void setPageSubAreaId(String pageSubAreaId) {
            this.pageSubAreaId = pageSubAreaId;
        }

        public static class subTopListBean {

            private String title;
            private String titleSmall;
            private String pageSubAreaId;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getTitleSmall() {
                return titleSmall;
            }

            public void setTitleSmall(String titleSmall) {
                this.titleSmall = titleSmall;
            }

            public String getPageSubAreaId() {
                return pageSubAreaId;
            }

            public void setPageSubAreaId(String pageSubAreaId) {
                this.pageSubAreaId = pageSubAreaId;
            }
        }
    }
}
