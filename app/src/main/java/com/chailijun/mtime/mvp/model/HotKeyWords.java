package com.chailijun.mtime.mvp.model;

import java.util.List;


public class HotKeyWords extends BaseData{

    private String hotContent;
    private List<String> keywords;

    public String getHotContent() {
        return hotContent;
    }

    public void setHotContent(String hotContent) {
        this.hotContent = hotContent;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }
}
