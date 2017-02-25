package com.chailijun.mtime.mvp.model;


import com.chailijun.mtime.mvp.interf.Data;

public class BaseData implements Data {

    /**新闻详细中用此变量来判断item的类型*/
    protected int itemType = 0;

    /**
     * RecyclerView中使用该字段判断是否被选中
     */
    private boolean isSelected;

    protected Object obj;

    protected Object obj1;

    public Object getObj1() {
        return obj1;
    }

    public void setObj1(Object obj1) {
        this.obj1 = obj1;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
