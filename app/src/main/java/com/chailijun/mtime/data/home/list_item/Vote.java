package com.chailijun.mtime.data.home.list_item;

import com.chailijun.mtime.data.home.HomeListSuper;

/**
 * type：44  "投票"
 */

public class Vote extends HomeListSuper{

    @Override
    public int getHomeItemType() {
        return getType();
    }

    @Override
    protected int getHomeDataType() {
        return -1;
    }

    /**
     * comSpecialObjId : 21681167
     * type : 44
     * tag : 投票
     * url : http://m.mtime.cn/#!/quotepage/vote/10409
     */

    private int comSpecialObjId;
    private int type;
    private String tag;
    private String url;

    public int getComSpecialObjId() {
        return comSpecialObjId;
    }

    public void setComSpecialObjId(int comSpecialObjId) {
        this.comSpecialObjId = comSpecialObjId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
