package com.chailijun.mtime.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * author： Chailijun
 * date  ： 2016/12/26 11:04
 * e-mail： 1499505466@qq.com
 */
@Entity
public class AdvList {

    @Id
    private Long id;

    private String  type;
    private String  typeName;
    private String  advTag;
    private boolean isHorizontalScreen;
    private long    startDate;
    private long    endDate;
    private String  url;
    private String  image;
    private String  tag;
    private boolean isOpenH5;
    private boolean isLogo;
    private String  entryText;

    private String  gotoPage_gotoType;
    private String  gotoPage_url;
    private int     gotoPage_param_movieId;
    private String  gotoPage_param1_movieId;
    private boolean gotoPage_isGoH5;
    private String  gotoPage_relatedTypeUrl;


    public String getGotoPage_relatedTypeUrl() {
        return this.gotoPage_relatedTypeUrl;
    }
    public void setGotoPage_relatedTypeUrl(String gotoPage_relatedTypeUrl) {
        this.gotoPage_relatedTypeUrl = gotoPage_relatedTypeUrl;
    }
    public boolean getGotoPage_isGoH5() {
        return this.gotoPage_isGoH5;
    }
    public void setGotoPage_isGoH5(boolean gotoPage_isGoH5) {
        this.gotoPage_isGoH5 = gotoPage_isGoH5;
    }
    public String getGotoPage_param1_movieId() {
        return this.gotoPage_param1_movieId;
    }
    public void setGotoPage_param1_movieId(String gotoPage_param1_movieId) {
        this.gotoPage_param1_movieId = gotoPage_param1_movieId;
    }
    public int getGotoPage_param_movieId() {
        return this.gotoPage_param_movieId;
    }
    public void setGotoPage_param_movieId(int gotoPage_param_movieId) {
        this.gotoPage_param_movieId = gotoPage_param_movieId;
    }
    public String getGotoPage_url() {
        return this.gotoPage_url;
    }
    public void setGotoPage_url(String gotoPage_url) {
        this.gotoPage_url = gotoPage_url;
    }
    public String getGotoPage_gotoType() {
        return this.gotoPage_gotoType;
    }
    public void setGotoPage_gotoType(String gotoPage_gotoType) {
        this.gotoPage_gotoType = gotoPage_gotoType;
    }
    public String getEntryText() {
        return this.entryText;
    }
    public void setEntryText(String entryText) {
        this.entryText = entryText;
    }
    public boolean getIsLogo() {
        return this.isLogo;
    }
    public void setIsLogo(boolean isLogo) {
        this.isLogo = isLogo;
    }
    public boolean getIsOpenH5() {
        return this.isOpenH5;
    }
    public void setIsOpenH5(boolean isOpenH5) {
        this.isOpenH5 = isOpenH5;
    }
    public String getTag() {
        return this.tag;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }
    public String getImage() {
        return this.image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public long getEndDate() {
        return this.endDate;
    }
    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }
    public long getStartDate() {
        return this.startDate;
    }
    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }
    public boolean getIsHorizontalScreen() {
        return this.isHorizontalScreen;
    }
    public void setIsHorizontalScreen(boolean isHorizontalScreen) {
        this.isHorizontalScreen = isHorizontalScreen;
    }
    public String getAdvTag() {
        return this.advTag;
    }
    public void setAdvTag(String advTag) {
        this.advTag = advTag;
    }
    public String getTypeName() {
        return this.typeName;
    }
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 2087466077)
    public AdvList(Long id, String type, String typeName, String advTag,
            boolean isHorizontalScreen, long startDate, long endDate, String url,
            String image, String tag, boolean isOpenH5, boolean isLogo,
            String entryText, String gotoPage_gotoType, String gotoPage_url,
            int gotoPage_param_movieId, String gotoPage_param1_movieId,
            boolean gotoPage_isGoH5, String gotoPage_relatedTypeUrl) {
        this.id = id;
        this.type = type;
        this.typeName = typeName;
        this.advTag = advTag;
        this.isHorizontalScreen = isHorizontalScreen;
        this.startDate = startDate;
        this.endDate = endDate;
        this.url = url;
        this.image = image;
        this.tag = tag;
        this.isOpenH5 = isOpenH5;
        this.isLogo = isLogo;
        this.entryText = entryText;
        this.gotoPage_gotoType = gotoPage_gotoType;
        this.gotoPage_url = gotoPage_url;
        this.gotoPage_param_movieId = gotoPage_param_movieId;
        this.gotoPage_param1_movieId = gotoPage_param1_movieId;
        this.gotoPage_isGoH5 = gotoPage_isGoH5;
        this.gotoPage_relatedTypeUrl = gotoPage_relatedTypeUrl;
    }
    @Generated(hash = 1010624047)
    public AdvList() {
    }

}


