package com.chailijun.mtime.api;


public class ApiConstants {

    public static final String HOST = "http://m.mtime.cn/";
    public static final String MYHOST = "http://www.chailijun.online";
    public static final String API = "http://api.m.mtime.cn";
    public static final String COMM_API = "http://comm-api.m.mtime.cn";
    public static final String TICKET_API = "http://ticket-api.m.mtime.cn";
    public static final String Mall_API = "http://mall-api.m.mtime.cn";
    /**广告*/
    public static final String Adv_URL = "http://api.m.mtime.cn/Advertisement/MobileAdvertisementInfo.api?locationId=";

    /**票房榜Api中区域参数（内地）（ID值由服务端决定）*/
    public static final int AreaID_MAINLAND      = 48266;
    /**票房榜Api中区域参数（北美）*/
    public static final int AreaID_NORTH_AMERICA = 48267;
    /**票房榜Api中区域参数（全球）*/
    public static final int AreaID_GLOBAL        = 48268;


    public static final String HOME_INDEX = "http://www.chailijun.online/api/homeIndex.php";

    //时光热度榜
    public static final String hotest = "http://api.m.mtime.cn/Movie/hotest.api?locationId=290&pageIndex=1";
    //直播列表
    public static final String live = "http://feature.mtime.cn/video/mobile/2016/live/list/";

    public static final String HOME = "http://comm-api.m.mtime.cn/home/index.api";
    public static final String TICKET = "http://ticket-api.m.mtime.cn/home/userRelatedInfo.api?movieId=";
    public static final String HotPlayMovies = "http://api.m.mtime.cn/PageSubArea/HotPlayMovies.api?locationId=292";//assets中有cityJson
    public static final String GetHomeFeed = "http://api.m.mtime.cn/PageSubArea/GetHomeFeed.api?pageIndex=1";
    public static final String GetCityByLongitudelatitude = "http://api.m.mtime.cn/GetCityByLongitudelatitude.api?longitude=121.553682&cityName=%E4%B8%8A%E6%B5%B7%E5%B8%82&latitude=31.283119";

    //新闻详细
    public static final String News_Detail = "http://api.m.mtime.cn/News/Detail.api?newsId=1562988";

    //电影
    //电影详细
    public static final String MOVIE_Detail = "http://ticket-api.m.mtime.cn/movie/detail.api?locationId=292&movieId=229639";
    public static final String MOVIE_ExtendMovieDetail = "http://api.m.mtime.cn/Movie/extendMovieDetail.api?MovieId=229639";
    //短评
    public static final String MOVIE_HotComment = "http://ticket-api.m.mtime.cn/movie/hotComment.api?movieId=229639";
    public static final String MOVIE_GetRelatedObjStatus = "http://api.m.mtime.cn/Showtime/GetRelatedObjStatus.api?relateId=229639&relateType=1";
    public static final String MOVIE_VoteUrl = "http://api.m.mtime.cn/Movie/VoteUrl.api?movieId=229639";
    public static final String MOVIE_Video = "http://api.m.mtime.cn/Movie/Video.api?movieId=229639&pageIndex=1";
    public static final String MOVIE_ImageAll = "http://api.m.mtime.cn/Movie/ImageAll.api?movieId=229639";
    public static final String MOVIE_MediaComments = "http://api.m.mtime.cn/Movie/MediaComments.api?MovieId=229639";
    public static final String MOVIE_CompanyList = "http://api.m.mtime.cn/Movie/CompanyList.api?MovieId=217851";
    //演员列表以及职务
    public static final String MovieCreditsWithTypes = "http://api.m.mtime.cn/Movie/MovieCreditsWithTypes.api?movieId=229639";
    //电影详细--短评--全部
    public static final String HotMovieComments = "http://api.m.mtime.cn/Showtime/HotMovieComments.api?movieId=229639&pageIndex=1";
    //电影详细--影评--全部
    public static final String HotLongComments = "http://api.m.mtime.cn/Movie/HotLongComments.api?movieId=229639&pageIndex=1";
    public static final String Review_Detail = "http://api.m.mtime.cn/Review/Detail.api?reviewId=7986974";


    //广告
    public static final String Advertisement = "http://api.m.mtime.cn/Advertisement/MobileAdvertisementInfo.api?locationId=292";
    //主页弹出式广告
    public static final String Advertisement_MainPagePopup = "http://api.m.mtime.cn/Advertisement/MainPagePopup.api?thirdOperate=0&thirdTime=0&ownTime=0&lastTime=0&locationId=290&ownAdvId=0&thirdShowCount=0&thirdAdvId=0&ownShowCount=0&ownOperate=0";


    //人物
    public static final String PERSON_Detail = "http://ticket-api.m.mtime.cn/person/detail.api?personId=929639&cityId=292";
    public static final String PERSON_Movie = "http://api.m.mtime.cn/Person/Movie.api?personId=929639&orderId=0&pageIndex=1";
    public static final String PERSON_Comment = "http://api.m.mtime.cn/Person/Comment.api?personId=929639&pageIndex=1";
    //@POST
    public static final String REVIEW = "http://api.m.mtime.cn/Review/PariseInfosByRelatedIds.api";

    //新闻详细下方的评论、相关阅读、相关商品
    public static final String NewsDetail_Comment = "http://api.m.mtime.cn/PageSubArea/NewsDetailFrist.api?locationId=292&newsId=1562988";
    public static final String NewsDetail_ReadsAndGoods = "http://api.m.mtime.cn/PageSubArea/NewsDetailSecond.api?locationId=292&newsId=1562988";


    //商场
    public static final String MALL = "http://mall-api.m.mtime.cn/mall/index.api";
    public static final String GoodsPrices = "http://api.m.mtime.cn/ECommerce/GoodsPrices.api?goodsIds=102612";//可变参数102422,102423,102670
    //    public static final String RecommendProducts = "http://api.m.mtime.cn/ECommerce/RecommendProducts.api?pageIndex=2";
    public static final String RecommendProducts_0 = "http://api.m.mtime.cn/ECommerce/RecommendProducts.api?goodsIds=&pageIndex=4";
    public static final String RecommendProducts_1 = "http://api.m.mtime.cn/ECommerce/RecommendProducts.api?goodsIds=102612&pageIndex=1";
    public static final String RecommendProducts_3 = "http://api.m.mtime.cn/ECommerce/RecommendProducts.api?goodsIds=101608%2C104384%2C104651&pageIndex=1";//goodsIds为最近3次浏览商品的id
    //优惠券
    public static final String GoodsCouponTip_0 = "http://api.m.mtime.cn/Account/GoodsCoupons.api?showStatus=1";
    public static final String GoodsCouponTip = "http://api.m.mtime.cn/ECommerce/GoodsCouponTip.api?lastTime=1479135054";

    public static final String CartCount = "http://api.m.mtime.cn/ECommerce/CartCount.api";
    public static final String AvaliableETicketAndTicket = "http://api.m.mtime.cn/Ticket/AvaliableETicketAndTicket.api";
    //商品详细
    public static final String product_detail = "http://mall.wv.mtime.cn/Service/callback-mall.mi/product/detail.api?goodsId=104706&locationId=290&t=20171814382897882";
    public static final String MiniGoodsInfo = "http://mall.wv.mtime.cn/Service/callback.mi/ECommerce/MiniGoodsInfo.api?goodsId=104706&t=20171814491761206";
    //商品详细--图文详情
    public static final String GoodsImageTextInfo = "http://mall.wv.mtime.cn/Service/callback.mi/ECommerce/GoodsImageTextInfo.api?t=20171814382914470&goodsId=104706&pageIndex=1";
    //商品详细--可能感兴趣的商品
    public static final String RecommendProducts = "http://mall.wv.mtime.cn/Service/callback.mi/ECommerce/RecommendProducts.api?t=20171814382981629&goodsIds=104706&pageIndex=1";
    //商品搜索
    public static final String SearchGoods = "http://mall.wv.mtime.cn/Service/callback.mi/ECommerce/SearchGoods.api?keyword=%E6%98%9F%E7%90%83%E5%A4%A7%E6%88%98&pageIndex=1&sf=0&sm=2&topicId=0&movieId=0&roleId=0&categoryId1=42&categoryId2=0&brandId=0&fmin=0&fmax=0&salefid=128&cd=0&t=2017181574134327";
    //商品---好物推荐
    public static final String SearchGoods2 = "http://mall.wv.mtime.cn/Service/callback.mi/ECommerce/SearchGoods.api?keyword=&pageIndex=1&sf=0&sm=2&topicId=0&movieId=0&roleId=0&categoryId1=0&categoryId2=0&brandId=13&fmin=0&fmax=0&salefid=0&cd=0&t=20171815151362992";

    //购票
    //电影-->正在热映
    public static final String LocationMovies = "http://api.m.mtime.cn/Showtime/LocationMovies.api?locationId=292";
    //电影-->即将上映
    public static final String MovieComingNew = "http://api.m.mtime.cn/Movie/MovieComingNew.api?locationId=292";

    //影院
    public static final String BaseCityData = "http://api.m.mtime.cn/Showtime/BaseCityData.api?locationid=292";
    //影院--全部
    public static final String OnlineCinemasByCity = "http://api.m.mtime.cn/OnlineLocationCinema/OnlineCinemasByCity.api?locationId=292";
    public static final String LocationMovieShowtimes = "http://api.m.mtime.cn/Showtime/LocationMovieShowtimes.api?locationId=792&movieId=232556&needAllShowtimes=true&date=20161205";

    //@POST
    public static final String Push_GetRemindMovies = "http://api.m.mtime.cn/Push/GetRemindMovies.api";
    public static final String User_FavoriteCinema = "http://api.m.mtime.cn/User/FavoriteCinemaListByCityID.api";


    public static final String startup = "http://comm-api.m.mtime.cn/startup/load.api?isUpgrade=true";
    //启动时根据经纬度定位
    public static final String GetCityByLongitudelatitudertup = "http://api.m.mtime.cn/GetCityByLongitudelatitude.api?longitude=121.5536&cityName=%E4%B8%8A%E6%B5%B7%E5%B8%82&latitude=31.283095";
    public static final String MainPagePopup = "http://api.m.mtime.cn/Advertisement/MainPagePopup.api";
    public static final String userRelatedInfo = "http://ticket-api.m.mtime.cn/home/userRelatedInfo.api?movieId=";

    public static final String Version = "http://api.m.mtime.cn/Mobile/Version.api";
    public static final String GetUnreadMessage = "http://api.m.mtime.cn/Push/GetUnreadMessage.api?lastTimeCommentary=1481787142075&DeviceToken=3589338425749976064.796977711398564727&lastTimeBroadcast=1481787142075&jPushRegID=190e35f7e04c7e9095c&lastTimeNotification=1481787142075";


    //发现
    public static final String GetRecommendationIndexInfo = "http://api.m.mtime.cn/PageSubArea/GetRecommendationIndexInfo.api";
    public static final String NewsList = "http://api.m.mtime.cn/News/NewsList.api?pageIndex=1";
    public static final String TrailerList = "http://api.m.mtime.cn/PageSubArea/TrailerList.api";
    public static final String TopListOfAll = "http://api.m.mtime.cn/TopList/TopListOfAll.api?pageIndex=1";
    public static final String Review = "http://api.m.mtime.cn/MobileMovie/Review.api?needTop=false";

    //票房榜
    //内地票房榜
    public static final String TopList_mainland = "http://api.m.mtime.cn/TopList/TopListDetailsByRecommend.api?locationId=292&pageSubAreaID=48266&pageIndex=1";
    //北美票房榜
    public static final String TopList_northAmerica = "http://api.m.mtime.cn/TopList/TopListDetailsByRecommend.api?locationId=292&pageSubAreaID=48267&pageIndex=1";
    //全球票房榜
    public static final String TopList_global = "http://api.m.mtime.cn/TopList/TopListDetailsByRecommend.api?locationId=292&pageSubAreaID=48268&pageIndex=1";

    //发现--排行榜（获取时光Top100的pageSubAreaId）
    public static final String TopListFixedNew = "http://api.m.mtime.cn/TopList/TopListFixedNew.api";

    //搜索
    public static final String Movie_Search = "http://api.m.mtime.cn/Movie/GetSearchItem.api";
    public static final String HotKeyWords = "http://api.m.mtime.cn/Search/HotKeyWords.api";
    //影片分类筛选
    public static final String SearchMovie = "http://api.m.mtime.cn/Movie/SearchMovie.api?sortType=0&areas=-1&genreTypes=-1&sortMethod=1&years=-1&pageIndex=1";


    //购票--具体电影对应的影院
    //对应城市，电影排片日期
    public static final String LocationMovieShowtimeDates = "http://api.m.mtime.cn/Showtime/LocationMovieShowtimeDates.api?locationId=290&movieId=123883";

    //影院上映电影以及场次
    public static final String cinema_showtime = "http://ticket-api.m.mtime.cn/cinema/showtime.api?cinemaId=3173";


}
