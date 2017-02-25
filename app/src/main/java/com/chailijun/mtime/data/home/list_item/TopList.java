package com.chailijun.mtime.data.home.list_item;

import com.chailijun.mtime.data.home.HomeListSuper;

import java.util.List;

/**
 * type：90  "榜单"    进入相应类型电影榜单
 */

public class TopList extends HomeListSuper {

    @Override
    public int getHomeItemType() {
        return getType();
    }

    @Override
    protected int getHomeDataType() {
        return -1;
    }

    /**
     * comSpecialObjId : 22642507
     * title : 10部脑洞大开的影片
     * Memo : 《瑞士军刀男》看不过瘾？我们挑选了10部让你意想不到的脑洞片，让你一次看个够。
     * tag : 榜单
     * id : 1457
     * isShow : 是
     * type : 90
     * Time : 2016-10-5 16:37:46
     * movies : [{"id":39482,"name":"心灵游戏","nameEn":"Mind Game","posterUrl":"http://img31.mtime.cn/mt/2014/02/23/031815.88387697_1280X720X2.jpg","decade":2004,"rating":8.4,"releaseLocation":"日本","director":"汤浅政明","actor":"今田耕司","actor2":"Sayaka Maeda"},{"id":13137,"name":"上帝也疯狂","nameEn":"The Gods Must Be Crazy","posterUrl":"http://img31.mtime.cn/mt/2014/02/22/231703.32704004_1280X720X2.jpg","decade":1980,"rating":8.2,"releaseLocation":"南非","director":"Jamie Uys","actor":"Marius Weyers","actor2":"Sandra Prinsloo"},{"id":27234,"name":"圣山","nameEn":"The Holy Mountain","posterUrl":"http://img31.mtime.cn/mt/2014/02/23/020200.54592312_1280X720X2.jpg","decade":1973,"rating":8.2,"releaseLocation":"法国","director":"亚历桑德罗·佐杜洛夫斯基","actor":"亚历桑德罗·佐杜洛夫斯基","actor2":"霍拉西欧·萨利纳斯"},{"id":70748,"name":"我嫁了一个怪物!","nameEn":"I Married a Strange Person!","posterUrl":"http://img31.mtime.cn/mt/2014/02/23/052012.11444549_1280X720X2.jpg","decade":1997,"rating":7.9,"releaseLocation":"加拿大","director":"比尔·普莱姆顿","actor":"Charis Michaelson","actor2":"Tom Larson"},{"id":14057,"name":"蒙迪佩登与圣杯","nameEn":"Monty Python and the Holy Grail","posterUrl":"http://img31.mtime.cn/mt/2014/02/22/232812.81848621_1280X720X2.jpg","decade":1975,"rating":7.7,"releaseLocation":"英国","director":"特瑞·吉列姆","actor":"约翰·克立斯","actor2":"艾瑞克·爱都"},{"id":226206,"name":"瑞士军刀男","nameEn":"Swiss Army Man","posterUrl":"http://img31.mtime.cn/mt/2016/04/05/090409.28775454_1280X720X2.jpg","decade":2016,"rating":7.6,"releaseLocation":"美国","director":"丹·关","actor":"保罗·达诺","actor2":"丹尼尔·雷德克里夫"},{"id":190901,"name":"冲出女儿国","nameEn":"Jacky in Women's Kingdom","posterUrl":"http://img31.mtime.cn/mt/2013/12/18/195841.82677512_1280X720X2.jpg","decade":2014,"rating":7.5,"releaseLocation":"法国","director":"Riad Sattouf","actor":"文森·拉寇斯","actor2":"夏洛特·甘斯布"},{"id":108534,"name":"符号","nameEn":"Symbol","posterUrl":"http://img31.mtime.cn/mt/2014/02/23/074154.39104506_1280X720X2.jpg","decade":2009,"rating":8.1,"releaseLocation":"日本","director":"松本人志","actor":"松本人志","actor2":"Adriana Fricke"},{"id":105833,"name":"钢铁苍穹","nameEn":"Iron Sky","posterUrl":"http://img21.mtime.cn/mt/2012/02/04/101157.96140742_1280X720X2.jpg","decade":2012,"rating":7.2,"releaseLocation":"德国","director":"季莫·沃伦索拉","actor":"茱莉亚·迭泽","actor2":"克里斯托弗·卡比"},{"id":129653,"name":"橡皮轮胎","nameEn":"Rubber","posterUrl":"http://img21.mtime.cn/mt/2011/03/01/110655.83471623_1280X720X2.jpg","decade":2010,"rating":6.9,"releaseLocation":"法国","director":"昆汀·杜飞","actor":"哈莉·拉姆","actor2":"罗珊娜·马奎达"}]
     */

    private int comSpecialObjId;
    private String title;
    private String Memo;
    private String tag;
    private String id;
    private String isShow;
    private int type;
    private String Time;
    /**
     * id : 39482
     * name : 心灵游戏
     * nameEn : Mind Game
     * posterUrl : http://img31.mtime.cn/mt/2014/02/23/031815.88387697_1280X720X2.jpg
     * decade : 2004
     * rating : 8.4
     * releaseLocation : 日本
     * director : 汤浅政明
     * actor : 今田耕司
     * actor2 : Sayaka Maeda
     */

    private List<MoviesBean> movies;

    public int getComSpecialObjId() {
        return comSpecialObjId;
    }

    public void setComSpecialObjId(int comSpecialObjId) {
        this.comSpecialObjId = comSpecialObjId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMemo() {
        return Memo;
    }

    public void setMemo(String Memo) {
        this.Memo = Memo;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String Time) {
        this.Time = Time;
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

        public int getDecade() {
            return decade;
        }

        public void setDecade(int decade) {
            this.decade = decade;
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

        public String getDirector() {
            return director;
        }

        public void setDirector(String director) {
            this.director = director;
        }

        public String getActor() {
            return actor;
        }

        public void setActor(String actor) {
            this.actor = actor;
        }

        public String getActor2() {
            return actor2;
        }

        public void setActor2(String actor2) {
            this.actor2 = actor2;
        }
    }
}
