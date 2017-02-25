package com.chailijun.mtime.mvp.model;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * 存放媒体文件的基本信息
*/
public class MediaItem implements Serializable {

    private int id;//视频id
    private String name;// 视频的名称
    private String url;// 视频在sdcard下的绝对路径
    private String hightUrl;//高清地址
    private String imageUrl;//封面图片地址
    private long duration;// 视频的时长:单位，毫秒
    private long size;// 视频的大小
    private String artist;//歌曲的演唱者,艺术家
    private String desc;//
    private Bitmap bitmap;//视频文件的缩略图

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getHightUrl() {
        return hightUrl;
    }

    public void setHightUrl(String hightUrl) {
        this.hightUrl = hightUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
