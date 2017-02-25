package com.chailijun.mtime.mvp.model.movie;

import com.chailijun.mtime.mvp.model.BaseData;

import java.util.List;

/**
 * 演员背景资料
 */
public class PersonDetailJson extends BaseData {

    private String code;
    private DataBean data;
    private String msg;
    private String showMsg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getShowMsg() {
        return showMsg;
    }

    public void setShowMsg(String showMsg) {
        this.showMsg = showMsg;
    }

    public static class DataBean {

        private AdvertisementBean advertisement;
        private BackgroundBean background;

        public AdvertisementBean getAdvertisement() {
            return advertisement;
        }

        public void setAdvertisement(AdvertisementBean advertisement) {
            this.advertisement = advertisement;
        }

        public BackgroundBean getBackground() {
            return background;
        }

        public void setBackground(BackgroundBean background) {
            this.background = background;
        }

        public static class AdvertisementBean {
            private int count;
            private String error;
            private boolean success;
            /**
             * advTag :
             * endDate : 1480607999
             * isHorizontalScreen : false
             * isOpenH5 : false
             * startDate : 1452355200
             * tag : 浦发银行
             * type : 205
             * typeName : 影人详情页banner2
             * url : http://static4da.mtime.cn/feature/mobile/banner/2016/1114/750210.html
             */

            private List<AdvListBean> advList;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public String getError() {
                return error;
            }

            public void setError(String error) {
                this.error = error;
            }

            public boolean isSuccess() {
                return success;
            }

            public void setSuccess(boolean success) {
                this.success = success;
            }

            public List<AdvListBean> getAdvList() {
                return advList;
            }

            public void setAdvList(List<AdvListBean> advList) {
                this.advList = advList;
            }

            public static class AdvListBean {
                private String advTag;
                private long endDate;
                private boolean isHorizontalScreen;
                private boolean isOpenH5;
                private long startDate;
                private String tag;
                private String type;
                private String typeName;
                private String url;

                public String getAdvTag() {
                    return advTag;
                }

                public void setAdvTag(String advTag) {
                    this.advTag = advTag;
                }

                public long getEndDate() {
                    return endDate;
                }

                public void setEndDate(long endDate) {
                    this.endDate = endDate;
                }

                public boolean isIsHorizontalScreen() {
                    return isHorizontalScreen;
                }

                public void setIsHorizontalScreen(boolean isHorizontalScreen) {
                    this.isHorizontalScreen = isHorizontalScreen;
                }

                public boolean isIsOpenH5() {
                    return isOpenH5;
                }

                public void setIsOpenH5(boolean isOpenH5) {
                    this.isOpenH5 = isOpenH5;
                }

                public long getStartDate() {
                    return startDate;
                }

                public void setStartDate(long startDate) {
                    this.startDate = startDate;
                }

                public String getTag() {
                    return tag;
                }

                public void setTag(String tag) {
                    this.tag = tag;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getTypeName() {
                    return typeName;
                }

                public void setTypeName(String typeName) {
                    this.typeName = typeName;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }
        }

        public static class BackgroundBean {
            private String address;
            private int birthDay;
            private int birthMonth;
            private int birthYear;
            private CommunityBean community;
            private String content;

            boolean isCollapsed=true;//文字是否折叠，默认折叠

            private HotMovieBean hotMovie;
            private String image;
            private int movieCount;
            private String nameCn;
            private String nameEn;
            private String profession;
            private QuizGameBean quizGame;
            private String rating;
            private String ratingFinal;

            private StyleBean style;
            private int totalNominateAward;
            private int totalWinAward;
            private String url;

            public boolean isCollapsed() {
                return isCollapsed;
            }

            public void setCollapsed(boolean collapsed) {
                isCollapsed = collapsed;
            }

            private List<AwardsBean> awards;

            private List<ExpriencesBean> expriences;
            /**
             * festivalId : 17
             * img : http://img31.mtime.cn/mg/2014/02/24/145913.30887805.jpg
             * nameCn : 香港电影金像奖
             * nameEn : Hong Kong Film Awards
             * shortName : 香港金像奖
             */

            private List<FestivalsBean> festivals;
            /**
             * image : http://img31.mtime.cn/pi/2015/12/31/111301.71351541_1000X1000.jpg
             * imageId : 7151072
             * type : 7002
             */

            private List<ImagesBean> images;
            /**
             * honor : 2013福布斯中国名人榜
             */

            private List<OtherHonorBean> otherHonor;
            private List<?> relationPersons;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public int getBirthDay() {
                return birthDay;
            }

            public void setBirthDay(int birthDay) {
                this.birthDay = birthDay;
            }

            public int getBirthMonth() {
                return birthMonth;
            }

            public void setBirthMonth(int birthMonth) {
                this.birthMonth = birthMonth;
            }

            public int getBirthYear() {
                return birthYear;
            }

            public void setBirthYear(int birthYear) {
                this.birthYear = birthYear;
            }

            public CommunityBean getCommunity() {
                return community;
            }

            public void setCommunity(CommunityBean community) {
                this.community = community;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public HotMovieBean getHotMovie() {
                return hotMovie;
            }

            public void setHotMovie(HotMovieBean hotMovie) {
                this.hotMovie = hotMovie;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public int getMovieCount() {
                return movieCount;
            }

            public void setMovieCount(int movieCount) {
                this.movieCount = movieCount;
            }

            public String getNameCn() {
                return nameCn;
            }

            public void setNameCn(String nameCn) {
                this.nameCn = nameCn;
            }

            public String getNameEn() {
                return nameEn;
            }

            public void setNameEn(String nameEn) {
                this.nameEn = nameEn;
            }

            public String getProfession() {
                return profession;
            }

            public void setProfession(String profession) {
                this.profession = profession;
            }

            public QuizGameBean getQuizGame() {
                return quizGame;
            }

            public void setQuizGame(QuizGameBean quizGame) {
                this.quizGame = quizGame;
            }

            public String getRating() {
                return rating;
            }

            public void setRating(String rating) {
                this.rating = rating;
            }

            public String getRatingFinal() {
                return ratingFinal;
            }

            public void setRatingFinal(String ratingFinal) {
                this.ratingFinal = ratingFinal;
            }

            public StyleBean getStyle() {
                return style;
            }

            public void setStyle(StyleBean style) {
                this.style = style;
            }

            public int getTotalNominateAward() {
                return totalNominateAward;
            }

            public void setTotalNominateAward(int totalNominateAward) {
                this.totalNominateAward = totalNominateAward;
            }

            public int getTotalWinAward() {
                return totalWinAward;
            }

            public void setTotalWinAward(int totalWinAward) {
                this.totalWinAward = totalWinAward;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public List<AwardsBean> getAwards() {
                return awards;
            }

            public void setAwards(List<AwardsBean> awards) {
                this.awards = awards;
            }

            public List<ExpriencesBean> getExpriences() {
                return expriences;
            }

            public void setExpriences(List<ExpriencesBean> expriences) {
                this.expriences = expriences;
            }

            public List<FestivalsBean> getFestivals() {
                return festivals;
            }

            public void setFestivals(List<FestivalsBean> festivals) {
                this.festivals = festivals;
            }

            public List<ImagesBean> getImages() {
                return images;
            }

            public void setImages(List<ImagesBean> images) {
                this.images = images;
            }

            public List<OtherHonorBean> getOtherHonor() {
                return otherHonor;
            }

            public void setOtherHonor(List<OtherHonorBean> otherHonor) {
                this.otherHonor = otherHonor;
            }

            public List<?> getRelationPersons() {
                return relationPersons;
            }

            public void setRelationPersons(List<?> relationPersons) {
                this.relationPersons = relationPersons;
            }

            public static class CommunityBean {
            }

            public static class HotMovieBean {
                private boolean isTiket;
                private String movieCover;
                private int movieId;
                private String movieTitleCn;
                private String movieTitleEn;
                private float ratingFinal;
                private String roleName;
                private int ticketPrice;
                private String type;

                public boolean isIsTiket() {
                    return isTiket;
                }

                public void setIsTiket(boolean isTiket) {
                    this.isTiket = isTiket;
                }

                public String getMovieCover() {
                    return movieCover;
                }

                public void setMovieCover(String movieCover) {
                    this.movieCover = movieCover;
                }

                public int getMovieId() {
                    return movieId;
                }

                public void setMovieId(int movieId) {
                    this.movieId = movieId;
                }

                public String getMovieTitleCn() {
                    return movieTitleCn;
                }

                public void setMovieTitleCn(String movieTitleCn) {
                    this.movieTitleCn = movieTitleCn;
                }

                public String getMovieTitleEn() {
                    return movieTitleEn;
                }

                public void setMovieTitleEn(String movieTitleEn) {
                    this.movieTitleEn = movieTitleEn;
                }

                public float getRatingFinal() {
                    return ratingFinal;
                }

                public void setRatingFinal(float ratingFinal) {
                    this.ratingFinal = ratingFinal;
                }

                public String getRoleName() {
                    return roleName;
                }

                public void setRoleName(String roleName) {
                    this.roleName = roleName;
                }

                public int getTicketPrice() {
                    return ticketPrice;
                }

                public void setTicketPrice(int ticketPrice) {
                    this.ticketPrice = ticketPrice;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }
            }

            public static class QuizGameBean {
            }

            public static class StyleBean {
                private int isLeadPage;
                private String leadImg;
                private String leadUrl;

                public int getIsLeadPage() {
                    return isLeadPage;
                }

                public void setIsLeadPage(int isLeadPage) {
                    this.isLeadPage = isLeadPage;
                }

                public String getLeadImg() {
                    return leadImg;
                }

                public void setLeadImg(String leadImg) {
                    this.leadImg = leadImg;
                }

                public String getLeadUrl() {
                    return leadUrl;
                }

                public void setLeadUrl(String leadUrl) {
                    this.leadUrl = leadUrl;
                }
            }

            public static class AwardsBean {
                private int festivalId;
                private int nominateCount;
                private int winCount;

                private List<NominateAwardsBean> nominateAwards;
                private List<WinAwardsBean> winAwards;

                public int getFestivalId() {
                    return festivalId;
                }

                public void setFestivalId(int festivalId) {
                    this.festivalId = festivalId;
                }

                public int getNominateCount() {
                    return nominateCount;
                }

                public void setNominateCount(int nominateCount) {
                    this.nominateCount = nominateCount;
                }

                public int getWinCount() {
                    return winCount;
                }

                public void setWinCount(int winCount) {
                    this.winCount = winCount;
                }

                public List<NominateAwardsBean> getNominateAwards() {
                    return nominateAwards;
                }

                public void setNominateAwards(List<NominateAwardsBean> nominateAwards) {
                    this.nominateAwards = nominateAwards;
                }

                public List<WinAwardsBean> getWinAwards() {
                    return winAwards;
                }

                public void setWinAwards(List<WinAwardsBean> winAwards) {
                    this.winAwards = winAwards;
                }

                public static class WinAwardsBean {

                    private String awardName;
                    private String festivalEventYear;
                    private String image;
                    private int movieId;
                    private String movieTitle;
                    private String movieTitleEn;
                    private String movieYear;
                    private String roleName;
                    private int sequenceNumber;

                    public String getAwardName() {
                        return awardName;
                    }

                    public void setAwardName(String awardName) {
                        this.awardName = awardName;
                    }

                    public String getFestivalEventYear() {
                        return festivalEventYear;
                    }

                    public void setFestivalEventYear(String festivalEventYear) {
                        this.festivalEventYear = festivalEventYear;
                    }

                    public String getImage() {
                        return image;
                    }

                    public void setImage(String image) {
                        this.image = image;
                    }

                    public int getMovieId() {
                        return movieId;
                    }

                    public void setMovieId(int movieId) {
                        this.movieId = movieId;
                    }

                    public String getMovieTitle() {
                        return movieTitle;
                    }

                    public void setMovieTitle(String movieTitle) {
                        this.movieTitle = movieTitle;
                    }

                    public String getMovieTitleEn() {
                        return movieTitleEn;
                    }

                    public void setMovieTitleEn(String movieTitleEn) {
                        this.movieTitleEn = movieTitleEn;
                    }

                    public String getMovieYear() {
                        return movieYear;
                    }

                    public void setMovieYear(String movieYear) {
                        this.movieYear = movieYear;
                    }

                    public String getRoleName() {
                        return roleName;
                    }

                    public void setRoleName(String roleName) {
                        this.roleName = roleName;
                    }

                    public int getSequenceNumber() {
                        return sequenceNumber;
                    }

                    public void setSequenceNumber(int sequenceNumber) {
                        this.sequenceNumber = sequenceNumber;
                    }
                }

                public static class NominateAwardsBean {
                    private String awardName;
                    private String festivalEventYear;
                    private String image;
                    private int movieId;
                    private String movieTitle;
                    private String movieTitleEn;
                    private String movieYear;
                    private String roleName;
                    private int sequenceNumber;

                    public String getAwardName() {
                        return awardName;
                    }

                    public void setAwardName(String awardName) {
                        this.awardName = awardName;
                    }

                    public String getFestivalEventYear() {
                        return festivalEventYear;
                    }

                    public void setFestivalEventYear(String festivalEventYear) {
                        this.festivalEventYear = festivalEventYear;
                    }

                    public String getImage() {
                        return image;
                    }

                    public void setImage(String image) {
                        this.image = image;
                    }

                    public int getMovieId() {
                        return movieId;
                    }

                    public void setMovieId(int movieId) {
                        this.movieId = movieId;
                    }

                    public String getMovieTitle() {
                        return movieTitle;
                    }

                    public void setMovieTitle(String movieTitle) {
                        this.movieTitle = movieTitle;
                    }

                    public String getMovieTitleEn() {
                        return movieTitleEn;
                    }

                    public void setMovieTitleEn(String movieTitleEn) {
                        this.movieTitleEn = movieTitleEn;
                    }

                    public String getMovieYear() {
                        return movieYear;
                    }

                    public void setMovieYear(String movieYear) {
                        this.movieYear = movieYear;
                    }

                    public String getRoleName() {
                        return roleName;
                    }

                    public void setRoleName(String roleName) {
                        this.roleName = roleName;
                    }

                    public int getSequenceNumber() {
                        return sequenceNumber;
                    }

                    public void setSequenceNumber(int sequenceNumber) {
                        this.sequenceNumber = sequenceNumber;
                    }
                }
            }

            public static class ExpriencesBean {
                private String content;
                private String img;
                private String title;
                private int year;

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public int getYear() {
                    return year;
                }

                public void setYear(int year) {
                    this.year = year;
                }
            }

            public static class FestivalsBean {
                private int festivalId;
                private String img;
                private String nameCn;
                private String nameEn;
                private String shortName;

                public int getFestivalId() {
                    return festivalId;
                }

                public void setFestivalId(int festivalId) {
                    this.festivalId = festivalId;
                }

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
                }

                public String getNameCn() {
                    return nameCn;
                }

                public void setNameCn(String nameCn) {
                    this.nameCn = nameCn;
                }

                public String getNameEn() {
                    return nameEn;
                }

                public void setNameEn(String nameEn) {
                    this.nameEn = nameEn;
                }

                public String getShortName() {
                    return shortName;
                }

                public void setShortName(String shortName) {
                    this.shortName = shortName;
                }
            }

            public static class ImagesBean {
                private String image;
                private int imageId;
                private int type;

                public String getImage() {
                    return image;
                }

                public void setImage(String image) {
                    this.image = image;
                }

                public int getImageId() {
                    return imageId;
                }

                public void setImageId(int imageId) {
                    this.imageId = imageId;
                }

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }
            }

            public static class OtherHonorBean {
                private String honor;

                public String getHonor() {
                    return honor;
                }

                public void setHonor(String honor) {
                    this.honor = honor;
                }
            }
        }
    }
}
