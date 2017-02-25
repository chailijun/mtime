package com.chailijun.mtime.db.entity;

import com.chailijun.mtime.adapter.decoration.ISuspensionInterface;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * author： Chailijun
 * date  ： 2016/12/9 16:10
 * e-mail： 1499505466@qq.com
 */
@Entity
public class City implements ISuspensionInterface,Comparable<City>{
    @Id
    private Long id;
    private int count;
    /**
     * 城市名称
     */
    private String n;
    /**
     * 城市名称全拼
     */
    private String pinyinFull;
    /**
     * 城市名称简拼
     */
    private String pinyinShort;

    public String getPinyinShort() {
        return this.pinyinShort;
    }
    public void setPinyinShort(String pinyinShort) {
        this.pinyinShort = pinyinShort;
    }
    public String getPinyinFull() {
        return this.pinyinFull;
    }
    public void setPinyinFull(String pinyinFull) {
        this.pinyinFull = pinyinFull;
    }
    public String getN() {
        return this.n;
    }
    public void setN(String n) {
        this.n = n;
    }
    public int getCount() {
        return this.count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 507406530)
    public City(Long id, int count, String n, String pinyinFull, String pinyinShort) {
        this.id = id;
        this.count = count;
        this.n = n;
        this.pinyinFull = pinyinFull;
        this.pinyinShort = pinyinShort;
    }
    @Generated(hash = 750791287)
    public City() {
    }

    @Override
    public int compareTo(City city) {
        String s1 = getPinyinShort().substring(0, 1).toUpperCase();
        String s2 = city.getPinyinShort().substring(0, 1).toUpperCase();
        int i = s1.compareTo(s2);
        if (0 == i){
            return this.getPinyinShort().substring(1).toUpperCase()
                    .compareTo(city.getPinyinShort().substring(1).toUpperCase());
        }
        return i;
    }

    @Override
    public boolean isShowSuspension() {
        return true;
    }

    @Override
    public String getSuspensionTag() {
        //取城市名的第一个汉字的首字符作为索引tag
        return getPinyinShort().substring(0,1);
    }
}
