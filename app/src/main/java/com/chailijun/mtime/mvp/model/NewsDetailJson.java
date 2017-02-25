package com.chailijun.mtime.mvp.model;


import java.util.List;

public class NewsDetailJson extends BaseData {

    private int type;
    private int id;
    private String title;
    private String title2;
    private String content;
    private String time;
    private String source;
    private String author;
    private String editor;
    private String url;
    private String wapUrl;
    private int previousNewsID;
    private int nextNewsID;
    private int commentCount;

    private List<RelationsBean> relations;

    private List<ImagesBean> images;

    private List<VideoListBean> videoList;

    public List<ImagesBean> getImages() {
        return images;
    }

    public void setImages(List<ImagesBean> images) {
        this.images = images;
    }

    public List<VideoListBean> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<VideoListBean> videoList) {
        this.videoList = videoList;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle2() {
        return title2;
    }

    public void setTitle2(String title2) {
        this.title2 = title2;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWapUrl() {
        return wapUrl;
    }

    public void setWapUrl(String wapUrl) {
        this.wapUrl = wapUrl;
    }

    public int getPreviousNewsID() {
        return previousNewsID;
    }

    public void setPreviousNewsID(int previousNewsID) {
        this.previousNewsID = previousNewsID;
    }

    public int getNextNewsID() {
        return nextNewsID;
    }

    public void setNextNewsID(int nextNewsID) {
        this.nextNewsID = nextNewsID;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public List<RelationsBean> getRelations() {
        return relations;
    }

    public void setRelations(List<RelationsBean> relations) {
        this.relations = relations;
    }

    public static class ImagesBean extends BaseData {
        private int gId;
        private String title;
        private String desc;
        private String url1;
        private String url2;

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getgId() {
            return gId;
        }

        public void setgId(int gId) {
            this.gId = gId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl1() {
            return url1;
        }

        public void setUrl1(String url1) {
            this.url1 = url1;
        }

        public String getUrl2() {
            return url2;
        }

        public void setUrl2(String url2) {
            this.url2 = url2;
        }
    }

    public static class VideoListBean {


        /**
         * Img : http://img5.mtime.cn/mg/2016/11/17/140100.77619056.jpg
         * VideoPath : http://vfx.mtime.cn/Video/2016/11/17/mp4/161117135954126948_480.mp4
         * VideoId  : 63383
         */

        private String Img;
        private String VideoPath;
        private int VideoId;

        public String getImg() {
            return Img;
        }

        public void setImg(String Img) {
            this.Img = Img;
        }

        public String getVideoPath() {
            return VideoPath;
        }

        public void setVideoPath(String VideoPath) {
            this.VideoPath = VideoPath;
        }

        public int getVideoId() {
            return VideoId;
        }

        public void setVideoId(int VideoId) {
            this.VideoId = VideoId;
        }
    }

    public static class RelationsBean {
        private int type;
        private int id;
        private String name;
        private String nameEn;
        private String image;
        private int year;
        private float rating;

        private int scoreCount;
        private String releaseDate;
        private String relaseLocation;

        public String getRelaseLocation() {
            return relaseLocation;
        }

        public void setRelaseLocation(String relaseLocation) {
            this.relaseLocation = relaseLocation;
        }

        public String getReleaseDate() {
            return releaseDate;
        }

        public void setReleaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
        }

        public int getScoreCount() {
            return scoreCount;
        }

        public void setScoreCount(int scoreCount) {
            this.scoreCount = scoreCount;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNameEn() {
            return nameEn;
        }

        public void setNameEn(String nameEn) {
            this.nameEn = nameEn;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public float getRating() {
            return rating;
        }

        public void setRating(float rating) {
            this.rating = rating;
        }
    }
}
