package com.chailijun.mtime.mvp.model.movie;

import com.chailijun.mtime.mvp.interf.Data;
import com.chailijun.mtime.mvp.model.BaseData;
import com.chailijun.mtime.mvp.model.GoodsListBean;

import java.util.List;

@Deprecated
public class MovieDetailJson extends BaseData{

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
        private BasicBean basic;
        private BoxOfficeBean boxOffice;
        private LiveBean live;
        private RelatedBean related;

        public AdvertisementBean getAdvertisement() {
            return advertisement;
        }

        public void setAdvertisement(AdvertisementBean advertisement) {
            this.advertisement = advertisement;
        }

        public BasicBean getBasic() {
            return basic;
        }

        public void setBasic(BasicBean basic) {
            this.basic = basic;
        }

        public BoxOfficeBean getBoxOffice() {
            return boxOffice;
        }

        public void setBoxOffice(BoxOfficeBean boxOffice) {
            this.boxOffice = boxOffice;
        }

        public LiveBean getLive() {
            return live;
        }

        public void setLive(LiveBean live) {
            this.live = live;
        }

        public RelatedBean getRelated() {
            return related;
        }

        public void setRelated(RelatedBean related) {
            this.related = related;
        }

        public static class AdvertisementBean {
            private int count;
            private String error;
            private boolean success;

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

        public static class BasicBean {

            private AwardBean award;
            private String commentSpecial;
            private CommunityBean community;
            private DirectorBean director;
            private int hotRanking;
            private String img;
            private boolean is3D;
            private boolean isDMAX;
            private boolean isEggHunt;
            private boolean isFilter;
            private boolean isIMAX;
            private boolean isIMAX3D;
            private boolean isTicket;
            private String message;
            private String mins;
            private String name;
            private String nameEn;
            private double overallRating;
            private int personCount;
            private QuizGameBean quizGame;
            private String releaseArea;
            private String releaseDate;
            private int showCinemaCount;
            private int showDay;
            private int showtimeCount;
            private StageImgBean stageImg;
            private String story;
            private StyleBean style;
            private int totalNominateAward;
            private int totalWinAward;
            private String url;
            private VideoBean video;
            private List<ActorsBean> actors;
            private List<FestivalsBean> festivals;
            private List<String> type;

            public AwardBean getAward() {
                return award;
            }

            public void setAward(AwardBean award) {
                this.award = award;
            }

            public String getCommentSpecial() {
                return commentSpecial;
            }

            public void setCommentSpecial(String commentSpecial) {
                this.commentSpecial = commentSpecial;
            }

            public CommunityBean getCommunity() {
                return community;
            }

            public void setCommunity(CommunityBean community) {
                this.community = community;
            }

            public DirectorBean getDirector() {
                return director;
            }

            public void setDirector(DirectorBean director) {
                this.director = director;
            }

            public int getHotRanking() {
                return hotRanking;
            }

            public void setHotRanking(int hotRanking) {
                this.hotRanking = hotRanking;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public boolean isIs3D() {
                return is3D;
            }

            public void setIs3D(boolean is3D) {
                this.is3D = is3D;
            }

            public boolean isIsDMAX() {
                return isDMAX;
            }

            public void setIsDMAX(boolean isDMAX) {
                this.isDMAX = isDMAX;
            }

            public boolean isIsEggHunt() {
                return isEggHunt;
            }

            public void setIsEggHunt(boolean isEggHunt) {
                this.isEggHunt = isEggHunt;
            }

            public boolean isIsFilter() {
                return isFilter;
            }

            public void setIsFilter(boolean isFilter) {
                this.isFilter = isFilter;
            }

            public boolean isIsIMAX() {
                return isIMAX;
            }

            public void setIsIMAX(boolean isIMAX) {
                this.isIMAX = isIMAX;
            }

            public boolean isIsIMAX3D() {
                return isIMAX3D;
            }

            public void setIsIMAX3D(boolean isIMAX3D) {
                this.isIMAX3D = isIMAX3D;
            }

            public boolean isIsTicket() {
                return isTicket;
            }

            public void setIsTicket(boolean isTicket) {
                this.isTicket = isTicket;
            }

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }

            public String getMins() {
                return mins;
            }

            public void setMins(String mins) {
                this.mins = mins;
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

            public double getOverallRating() {
                return overallRating;
            }

            public void setOverallRating(double overallRating) {
                this.overallRating = overallRating;
            }

            public int getPersonCount() {
                return personCount;
            }

            public void setPersonCount(int personCount) {
                this.personCount = personCount;
            }

            public QuizGameBean getQuizGame() {
                return quizGame;
            }

            public void setQuizGame(QuizGameBean quizGame) {
                this.quizGame = quizGame;
            }

            public String getReleaseArea() {
                return releaseArea;
            }

            public void setReleaseArea(String releaseArea) {
                this.releaseArea = releaseArea;
            }

            public String getReleaseDate() {
                return releaseDate;
            }

            public void setReleaseDate(String releaseDate) {
                this.releaseDate = releaseDate;
            }

            public int getShowCinemaCount() {
                return showCinemaCount;
            }

            public void setShowCinemaCount(int showCinemaCount) {
                this.showCinemaCount = showCinemaCount;
            }

            public int getShowDay() {
                return showDay;
            }

            public void setShowDay(int showDay) {
                this.showDay = showDay;
            }

            public int getShowtimeCount() {
                return showtimeCount;
            }

            public void setShowtimeCount(int showtimeCount) {
                this.showtimeCount = showtimeCount;
            }

            public StageImgBean getStageImg() {
                return stageImg;
            }

            public void setStageImg(StageImgBean stageImg) {
                this.stageImg = stageImg;
            }

            public String getStory() {
                return story;
            }

            public void setStory(String story) {
                this.story = story;
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

            public VideoBean getVideo() {
                return video;
            }

            public void setVideo(VideoBean video) {
                this.video = video;
            }

            public List<ActorsBean> getActors() {
                return actors;
            }

            public void setActors(List<ActorsBean> actors) {
                this.actors = actors;
            }

            public List<FestivalsBean> getFestivals() {
                return festivals;
            }

            public void setFestivals(List<FestivalsBean> festivals) {
                this.festivals = festivals;
            }

            public List<String> getType() {
                return type;
            }

            public void setType(List<String> type) {
                this.type = type;
            }

            public static class AwardBean {
                private int totalNominateAward;
                private int totalWinAward;
                /**
                 * festivalId : 12
                 * nominateAwards : [{"awardName":"金马奖-最佳影片","festivalEventYear":"2016","persons":[],"sequenceNumber":53},{"awardName":"金马奖-最佳导演奖","festivalEventYear":"2016","persons":[{"nameCn":"冯小刚","nameEn":"Xiaogang Feng","personId":892845}],"sequenceNumber":53},{"awardName":"金马奖-最佳女主角奖","festivalEventYear":"2016","persons":[{"nameCn":"范冰冰","nameEn":"Bingbing Fan","personId":929639}],"sequenceNumber":53},{"awardName":"金马奖-最佳改编剧本","festivalEventYear":"2016","persons":[{"nameCn":"刘震云","nameEn":"Liu Zhenyun","personId":893104}],"sequenceNumber":53},{"awardName":"金马奖-最佳原创音乐","festivalEventYear":"2016","persons":[{"nameCn":"杜薇","nameEn":"Wei Du","personId":2231869}],"sequenceNumber":53}]
                 * nominateCount : 5
                 * winAwards : []
                 * winCount : 0
                 */

                private List<AwardListBean> awardList;

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

                public List<AwardListBean> getAwardList() {
                    return awardList;
                }

                public void setAwardList(List<AwardListBean> awardList) {
                    this.awardList = awardList;
                }

                public static class AwardListBean {
                    private int festivalId;
                    private int nominateCount;
                    private int winCount;
                    /**
                     * awardName : 金马奖-最佳影片
                     * festivalEventYear : 2016
                     * persons : []
                     * sequenceNumber : 53
                     */

                    private List<NominateAwardsBean> nominateAwards;
                    private List<?> winAwards;

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

                    public List<?> getWinAwards() {
                        return winAwards;
                    }

                    public void setWinAwards(List<?> winAwards) {
                        this.winAwards = winAwards;
                    }

                    public static class NominateAwardsBean {
                        private String awardName;
                        private String festivalEventYear;
                        private int sequenceNumber;
                        private List<?> persons;

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

                        public int getSequenceNumber() {
                            return sequenceNumber;
                        }

                        public void setSequenceNumber(int sequenceNumber) {
                            this.sequenceNumber = sequenceNumber;
                        }

                        public List<?> getPersons() {
                            return persons;
                        }

                        public void setPersons(List<?> persons) {
                            this.persons = persons;
                        }
                    }
                }
            }

            public static class CommunityBean {
            }

            public static class DirectorBean {
                private int directorId;
                private String img;
                private String name;
                private String nameEn;

                public int getDirectorId() {
                    return directorId;
                }

                public void setDirectorId(int directorId) {
                    this.directorId = directorId;
                }

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
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
            }

            public static class QuizGameBean {
            }

            public static class StageImgBean {
                private int count;
                /**
                 * imgId : 7255683
                 * imgUrl : http://img31.mtime.cn/pi/2016/07/05/093257.46923761_1280X720X2.jpg
                 */

                private List<ListBean> list;

                public int getCount() {
                    return count;
                }

                public void setCount(int count) {
                    this.count = count;
                }

                public List<ListBean> getList() {
                    return list;
                }

                public void setList(List<ListBean> list) {
                    this.list = list;
                }

                public static class ListBean {
                    private int imgId;
                    private String imgUrl;

                    public int getImgId() {
                        return imgId;
                    }

                    public void setImgId(int imgId) {
                        this.imgId = imgId;
                    }

                    public String getImgUrl() {
                        return imgUrl;
                    }

                    public void setImgUrl(String imgUrl) {
                        this.imgUrl = imgUrl;
                    }
                }
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

            public static class VideoBean {
                private int count;
                private String hightUrl;
                private String img;
                private String title;
                private String url;
                private int videoId;

                public int getCount() {
                    return count;
                }

                public void setCount(int count) {
                    this.count = count;
                }

                public String getHightUrl() {
                    return hightUrl;
                }

                public void setHightUrl(String hightUrl) {
                    this.hightUrl = hightUrl;
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

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public int getVideoId() {
                    return videoId;
                }

                public void setVideoId(int videoId) {
                    this.videoId = videoId;
                }
            }

            public static class ActorsBean {
                private int actorId;
                private String img;
                private String name;
                private String nameEn;
                private String roleImg;
                private String roleName;

                public int getActorId() {
                    return actorId;
                }

                public void setActorId(int actorId) {
                    this.actorId = actorId;
                }

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
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

                public String getRoleImg() {
                    return roleImg;
                }

                public void setRoleImg(String roleImg) {
                    this.roleImg = roleImg;
                }

                public String getRoleName() {
                    return roleName;
                }

                public void setRoleName(String roleName) {
                    this.roleName = roleName;
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
        }

        public static class BoxOfficeBean {
            private int movieId;
            private int ranking;
            private long todayBox;
            private String todayBoxDes;
            private String todayBoxDesUnit;
            private long totalBox;
            private String totalBoxDes;
            private String totalBoxUnit;

            public int getMovieId() {
                return movieId;
            }

            public void setMovieId(int movieId) {
                this.movieId = movieId;
            }

            public int getRanking() {
                return ranking;
            }

            public void setRanking(int ranking) {
                this.ranking = ranking;
            }

            public long getTodayBox() {
                return todayBox;
            }

            public void setTodayBox(long todayBox) {
                this.todayBox = todayBox;
            }

            public String getTodayBoxDes() {
                return todayBoxDes;
            }

            public void setTodayBoxDes(String todayBoxDes) {
                this.todayBoxDes = todayBoxDes;
            }

            public String getTodayBoxDesUnit() {
                return todayBoxDesUnit;
            }

            public void setTodayBoxDesUnit(String todayBoxDesUnit) {
                this.todayBoxDesUnit = todayBoxDesUnit;
            }

            public long getTotalBox() {
                return totalBox;
            }

            public void setTotalBox(long totalBox) {
                this.totalBox = totalBox;
            }

            public String getTotalBoxDes() {
                return totalBoxDes;
            }

            public void setTotalBoxDes(String totalBoxDes) {
                this.totalBoxDes = totalBoxDes;
            }

            public String getTotalBoxUnit() {
                return totalBoxUnit;
            }

            public void setTotalBoxUnit(String totalBoxUnit) {
                this.totalBoxUnit = totalBoxUnit;
            }
        }

        public static class LiveBean {
        }

        public static class RelatedBean {
            private int goodsCount;
            private int relateId;
            private String relatedUrl;
            private int type;
            private List<GoodsListBean> goodsList;

            public int getGoodsCount() {
                return goodsCount;
            }

            public void setGoodsCount(int goodsCount) {
                this.goodsCount = goodsCount;
            }

            public int getRelateId() {
                return relateId;
            }

            public void setRelateId(int relateId) {
                this.relateId = relateId;
            }

            public String getRelatedUrl() {
                return relatedUrl;
            }

            public void setRelatedUrl(String relatedUrl) {
                this.relatedUrl = relatedUrl;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public List<GoodsListBean> getGoodsList() {
                return goodsList;
            }

            public void setGoodsList(List<GoodsListBean> goodsList) {
                this.goodsList = goodsList;
            }
        }
    }
}
