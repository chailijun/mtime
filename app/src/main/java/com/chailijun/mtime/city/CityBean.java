package com.chailijun.mtime.city;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chailijun.mtime.adapter.decoration.ISuspensionInterface;
import com.chailijun.mtime.db.entity.City;

import java.util.List;


public class CityBean implements ISuspensionInterface, MultiItemEntity {

    public static final int TYPE_GRID     = 0;//item为网格布局
    public static final int TYPE_LINE     = 1;//item为线性布局
    public static final int TYPE_LOCATION = 2;//定位城市

    private List<City> cityList;

    //悬停ItemDecoration显示的Tag
    private String suspensionTag;

    /**
     * type:0 item为网格布局
     * type:1 item为线性布局
     */
    private int type;

    public CityBean(List<City> cityList, String suspensionTag) {
        this.cityList = cityList;
        this.suspensionTag = suspensionTag;
    }

    public CityBean(List<City> cityList, String suspensionTag, int type) {
        this.cityList = cityList;
        this.suspensionTag = suspensionTag;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<City> getCityList() {
        return cityList;
    }

    public void setCityList(List<City> cityList) {
        this.cityList = cityList;
    }

    public void setSuspensionTag(String suspensionTag) {
        this.suspensionTag = suspensionTag;
    }

    @Override
    public boolean isShowSuspension() {
        //item显示头部tag
        return true;
    }

    @Override
    public String getSuspensionTag() {
        return suspensionTag;
    }

    @Override
    public int getItemType() {
        if (type == 0) {
            return TYPE_GRID;

        } else if (type == 1) {
            return TYPE_LINE;

        } else if(type == 2){
            return TYPE_LOCATION;
        }else {
            return -1;
        }
    }
}
