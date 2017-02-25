package com.chailijun.mtime.data.find.toplist;


public class TopListItem {

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
