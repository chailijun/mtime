package com.chailijun.mtime.mvp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeListJson extends HomeBaseData {

    private int count;

    private List<DataBean> data;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean extends HomeBaseData {
        //tag : 简讯
        private String title;
        private String titlesmall;
        private int publishTime;
        private String tag;

//        @SerializedName("id")
//        private String movieId;

        private int id;
        private int status;
        private int comSpecialObjId;
        private int dataType;
        private String isShow;
        private String img1;
        private String img2;
        private String img3;
        private int type;
        private int commentCount;
        private String time;
        private String content;
        private List<RelationsBean> relations;

        //tag : 猜电影
        private String memo;
        private String pic1Url;
        private String url;

        //tag : 图集
        private List<ImagesBean> images;

        //tag : 欧美新片
        private String titleCn;
        private String titleEn;
        private String image;
        private Object rating;

        //* tag : 影评
        private String nickname;
        private String userImage;
        private String summaryInfo;
        private RelatedObjBean relatedObj;

        //榜单
        @SerializedName("Memo")
        private String memo_movie;
        @SerializedName("Time")
        private String time_movie;
        private List<MoviesBean> movies;

//        public String getMovieId() {
//            return movieId;
//        }
//
//        public void setMovieId(String movieId) {
//            this.movieId = movieId;
//        }

        public String getMemo_movie() {
            return memo_movie;
        }

        public void setMemo_movie(String memo_movie) {
            this.memo_movie = memo_movie;
        }

        public String getTime_movie() {
            return time_movie;
        }

        public void setTime_movie(String time_movie) {
            this.time_movie = time_movie;
        }

        public List<MoviesBean> getMovies() {
            return movies;
        }

        public void setMovies(List<MoviesBean> movies) {
            this.movies = movies;
        }

        public static class MoviesBean {
            private int id;
            private String name;
            private String nameEn;
            private String posterUrl;
            private int decade;
            private float rating;
            private String releaseLocation;
            private String director;
            private String actor;
            private String actor2;

            public String getActor2() {
                return actor2;
            }

            public void setActor2(String actor2) {
                this.actor2 = actor2;
            }

            public String getActor() {
                return actor;
            }

            public void setActor(String actor) {
                this.actor = actor;
            }

            public int getDecade() {
                return decade;
            }

            public void setDecade(int decade) {
                this.decade = decade;
            }

            public String getDirector() {
                return director;
            }

            public void setDirector(String director) {
                this.director = director;
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

            public String getPosterUrl() {
                return posterUrl;
            }

            public void setPosterUrl(String posterUrl) {
                this.posterUrl = posterUrl;
            }

            public float getRating() {
                return rating;
            }

            public void setRating(float rating) {
                this.rating = rating;
            }

            public String getReleaseLocation() {
                return releaseLocation;
            }

            public void setReleaseLocation(String releaseLocation) {
                this.releaseLocation = releaseLocation;
            }
        }

        public int getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }

        public int getComSpecialObjId() {
            return comSpecialObjId;
        }

        public void setComSpecialObjId(int comSpecialObjId) {
            this.comSpecialObjId = comSpecialObjId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getDataType() {
            return dataType;
        }

        public void setDataType(int dataType) {
            this.dataType = dataType;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public List<ImagesBean> getImages() {
            return images;
        }

        public void setImages(List<ImagesBean> images) {
            this.images = images;
        }

        public String getImg1() {
            return img1;
        }

        public void setImg1(String img1) {
            this.img1 = img1;
        }

        public String getImg2() {
            return img2;
        }

        public void setImg2(String img2) {
            this.img2 = img2;
        }

        public String getImg3() {
            return img3;
        }

        public void setImg3(String img3) {
            this.img3 = img3;
        }

        public String getIsShow() {
            return isShow;
        }

        public void setIsShow(String isShow) {
            this.isShow = isShow;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getPic1Url() {
            return pic1Url;
        }

        public void setPic1Url(String pic1Url) {
            this.pic1Url = pic1Url;
        }

        public int getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(int publishTime) {
            this.publishTime = publishTime;
        }

        public Object getRating() {
            return rating;
        }

        public void setRating(Object rating) {
            this.rating = rating;
        }

        public RelatedObjBean getRelatedObj() {
            return relatedObj;
        }

        public void setRelatedObj(RelatedObjBean relatedObj) {
            this.relatedObj = relatedObj;
        }

        public List<RelationsBean> getRelations() {
            return relations;
        }

        public void setRelations(List<RelationsBean> relations) {
            this.relations = relations;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getSummaryInfo() {
            return summaryInfo;
        }

        public void setSummaryInfo(String summaryInfo) {
            this.summaryInfo = summaryInfo;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitleCn() {
            return titleCn;
        }

        public void setTitleCn(String titleCn) {
            this.titleCn = titleCn;
        }

        public String getTitleEn() {
            return titleEn;
        }

        public void setTitleEn(String titleEn) {
            this.titleEn = titleEn;
        }

        public String getTitlesmall() {
            return titlesmall;
        }

        public void setTitlesmall(String titlesmall) {
            this.titlesmall = titlesmall;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUserImage() {
            return userImage;
        }

        public void setUserImage(String userImage) {
            this.userImage = userImage;
        }

        public static class ImagesBean {
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

        public static class RelationsBean {
            private int type;
            private int id;
            private String name;
            private String nameEn;
            private String image;
            private Object year;
            private Object rating;
            private int scoreCount;
            private String releaseDate;
            private String relaseLocation;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
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

            public Object getRating() {
                return rating;
            }

            public void setRating(Object rating) {
                this.rating = rating;
            }

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

            public Object getYear() {
                return year;
            }

            public void setYear(Object year) {
                this.year = year;
            }
        }

        public static class RelatedObjBean {
            private int id;
            private String title;
            private String name;
            private String titleCn;
            private String titleEn;
            private String runtime;
            private String url;
            private String wapUrl;
            private float rating;
            private String image;
            private String releaseLocation;
            private List<String> type;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public float getRating() {
                return rating;
            }

            public void setRating(float rating) {
                this.rating = rating;
            }

            public String getReleaseLocation() {
                return releaseLocation;
            }

            public void setReleaseLocation(String releaseLocation) {
                this.releaseLocation = releaseLocation;
            }

            public String getRuntime() {
                return runtime;
            }

            public void setRuntime(String runtime) {
                this.runtime = runtime;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getTitleCn() {
                return titleCn;
            }

            public void setTitleCn(String titleCn) {
                this.titleCn = titleCn;
            }

            public String getTitleEn() {
                return titleEn;
            }

            public void setTitleEn(String titleEn) {
                this.titleEn = titleEn;
            }

            public List<String> getType() {
                return type;
            }

            public void setType(List<String> type) {
                this.type = type;
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
        }
    }
}
