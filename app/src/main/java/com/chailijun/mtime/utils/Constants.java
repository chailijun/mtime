package com.chailijun.mtime.utils;


public class Constants {

    /**底部导航索引值--首页*/
    public static final int NAV_HOME = 0;
    /**底部导航索引值--购票*/
    public static final int NAV_PAYTICKET = 1;
    /**底部导航索引值--商城*/
    public static final int NAV_MALL = 2;
    /**底部导航索引值--发现*/
    public static final int NAV_FIND = 3;
    /**底部导航索引值--我的*/
    public static final int NAV_USER = 4;

    /**
     * （GET方式）网络连接失败重新连接持续的时间（单位毫秒）
     */
    public static final int GET_DURATION = 2000;

    /******Home页面中RecyclerView item的类型*****/

    /**
     * Home页面的头部类型
     */
    public static final int TYPE_HEAD = 1000;
    /**
     * type:51 dateType:1 图集
     */
    public static final int TYPE_LIST_Images = 1001;
    /**
     * type:336 影评
     */
    public static final int TYPE_LIST_FilmReview = 1002;
    /**
     * type:51 dateType:0/2 简讯
     */
    public static final int TYPE_LIST_NewsWithVideo = 1003;
    /**
     * type:64 猜电影、免费观影
     */
    public static final int TYPE_LIST_URL = 1004;
    /**
     * type:-1 欧美新片、华语新片
     */
    public static final int TYPE_LIST_NewMovie = 1005;
    /**
     * type:90 榜单
     */
    public static final int TYPE_LIST_Top = 1006;
    /**
     * type:44 投票（结尾）
     */
    public static final int TYPE_LIST_Vote = 1007;

    /**************************************************/


    /******新闻详细页面中RecyclerView item的类型*****/

    /**
     * 新闻详细顶部的画廊
     */
    public static final int TYPE_NEWS_GALLERY = 2001;
    /**
     * 新闻详细
     */
    public static final int TYPE_NEWS_DETAIL = 2002;
    /**
     * 新闻分享
     */
    public static final int TYPE_NEWS_SHARE = 2003;
    /**
     * 相关资料
     */
    public static final int TYPE_NEWS_RELATIONS_INFO = 2004;
    /**
     * 相关商品
     */
    public static final int TYPE_NEWS_RELATIONS_GOODS = 2005;
    /**
     * 相关阅读
     */
    public static final int TYPE_NEWS_RELATIONS_READS = 2006;
    /**
     * 精彩评论
     */
    public static final int TYPE_NEWS_COMMENT = 2007;
    /**
     * 新闻详细底部的广告条
     */
    public static final int TYPE_NEWS_ADV_BANNER = 2008;

    /**************************************************/


    public static final String IS_FIRST = "is_first_enter_app";//是否是第一次进入app

    public static final String NEWS_ID = "newsId";      //新闻id
    public static final String MOVIE_ID = "movie_id";    //电影id
    public static final String PERSON_ID = "person_id";   //人物id
    public static final String LOCATION_ID = "locationId";  //城市id
    public static final String REVIEW_ID = "review_id";   //影评id
    public static final String MOVIE_NAME = "movie_name";  //电影名字
    public static final String TOPLIST_ID = "toplist_id";  //排行榜id

    public static final String COMMENT_TOTAL = "comment_total";//评论总数
    public static final String GOTO_PAGE = "GOTO_PAGE";
    public static final String GOTO_URL = "goto_url";
    public static final String HOME_INDEX = "home_index";
    public static final String HOME_LIST = "home_list";
    public static final String NEWS_DETAIL = "news_detail";
    public static final String NEWS_DETAIL_FRIST = "news_detail_frist";

    public static final String NEWS_DETAIL_SECOND = "news_detail_second";
    public static final String MOVIE_DETAIL = "movie_detail";
    public static final String MOVIE_HOT_COMMENT = "movie_hot_comment";
    public static final String EXTEND_MOVIE_DETAIL = "extend_movie_detail";

    //EventBus发送的消息中自定义的标识
    public static final int MSG_WHAT = 1;
    public static final int UPDATE_DATA = 2;
    /**
     * 主题商品
     */
    public static final String TOPIC_GOODS = "topic_goods";
    /**
     * 商场头部索引
     */
    public static final String MALL_INDEX = "mall_index";
    /**
     * 推荐商品
     */
    public static final String RECOMMEND_PRODUCTS = "recommend_products";
    /**
     * 商品列表的url
     */
    public static final String GOODS_URL = "goods_url";

    /**
     * 地区
     */
    public static final String AREA = "area";
    /**
     * 类型
     */
    public static final String GENRE_TYPES = "genreTypes";
    /**
     * 年代
     */
    public static final String YEARS = "years";
    /**
     * 内地
     */
    public static final int MAINLAND = 1;
    /**
     * 北美
     */
    public static final int NORTH_AMERICA = 2;
    /**
     * 全球
     */
    public static final int GLOBAL = 3;
    /**
     * 时光Top100的pageSubAreaId
     */
    public static final String MTIME_TOP100_AreaId = "mtime_top100_AreaId";
    /**
     * 华语Top100的pageSubAreaId
     */
    public static final String Chinese_Language_TOP100_AreaId = "Chinese_Language_TOP100_AreaId";
    public static final String PAGESUB_AREA_ID = "pageSubAreaID";
    public static final String SEARCH_HISTORY = "activity_search_movie_history";
    /**电影分类标签（地区、类型、年代）*/
    public static final String SEARCH_ITEM = "search_item";
    /**所有的广告*/
    public static final String ADV_ALL = "adv_all";
    /**电影搜索栏中hint内容*/
    public static final String MOVIE_EDIT_HINT = "movie_edit_hint";

    /**
     * 开启页广告图片的点击事件类型（gotourl）
     */
    public static final String GOTO_TYPE_URL = "gotourl";
    /**
     * 开启页广告图片的点击事件类型（gotomovie）
     */
    public static final String GOTO_TYPE_MOVIE = "gotomovie";
    /**
     * "发现"中tab的索引值
     */
    public static final String FIND_TAB_INDEX = "find_tab_index";

    /**
     * 影院id
     */
    public static final String CINEMA_ID = "cinema_id";

    /**
     * 百度地图维度
     */
    public static final String LATITUDE = "baidu_Latitude";
    /**
     * 百度地图经度
     */
    public static final String LONGITUDE = "baidu_Longitude";
}
