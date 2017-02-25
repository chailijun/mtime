package com.chailijun.mtime.mvp.model;

import java.util.List;

public class TopListOfAll extends BaseData{

    private int totalCount;
    private int pageCount;

    private List<TopListsBean> topLists;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<TopListsBean> getTopLists() {
        return topLists;
    }

    public void setTopLists(List<TopListsBean> topLists) {
        this.topLists = topLists;
    }

    public static class TopListsBean extends BaseData{
        private int id;
        private String topListNameCn;
        private String topListNameEn;
        private int type;
        private String summary;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTopListNameCn() {
            return topListNameCn;
        }

        public void setTopListNameCn(String topListNameCn) {
            this.topListNameCn = topListNameCn;
        }

        public String getTopListNameEn() {
            return topListNameEn;
        }

        public void setTopListNameEn(String topListNameEn) {
            this.topListNameEn = topListNameEn;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }
    }
}
