package com.chailijun.mtime.data.find.toplist;

import java.util.List;


public class TopList {


    /**
     * topLists : [{"id":1469,"topListNameCn":"《每日银幕》2016年度十佳影片","topListNameEn":"","type":0,"summary":"德国电影《托尼·厄德曼》登顶"},{"id":1467,"topListNameCn":"好莱坞票房回报率最高的十大演员","topListNameEn":"","type":2,"summary":"\u201c美队\u201d克里斯·埃文斯蝉位列\u201c2016好莱坞票房回报率最高演员\u201d排行榜NO.1，克里斯·帕拉特、斯嘉丽·约翰逊紧随其后，分列第二第三。"},{"id":1466,"topListNameCn":"《时代》杂志评2016年度十佳影片","topListNameEn":"","type":0,"summary":"讲述黑人同志的《月光男孩》夺冠"},{"id":1446,"topListNameCn":"不可错过的20部美丽生灵","topListNameEn":"","type":0,"summary":"森林之王分分钟反差萌，天空中自由翱翔的翅膀，看似蠢蠢的高智商熊星人 ，海洋杀手的温暖瞬间，还有蜜汁不懂的骆驼之唇，无法抗拒这些看不够的美丽生灵。"},{"id":1435,"topListNameCn":"30岁的时候应该看的55部电影","topListNameEn":"","type":0,"summary":"30岁的时候一定要看的55部电影"},{"id":1427,"topListNameCn":"时光评分最高的20部伊朗电影","topListNameEn":"","type":0,"summary":"高分片单哪国强?伊朗电影有话讲！谈及儿童片的佳作非伊朗电影莫属,论及宗教与人生之反思,伊朗电影更加切身与写实,有笑也有泪。在宗教、民族与政治的夹缝中,伊朗电影生存艰难却力道十足。"},{"id":1404,"topListNameCn":"如何成为一个父亲","topListNameEn":"","type":0,"summary":"不是每一个人都是天生的父母，一名有担当和有责任感的父亲往往是与孩子一同成长的，甚至毫无血缘关系的纠葛，情感的根源也来自于想要成为父亲的愿望。"},{"id":1398,"topListNameCn":"暗黑的行业天才","topListNameEn":"","type":0,"summary":"俗话说天才与疯子只有一线之隔，完美的自虐与虐人是行业天才的最高美德，颤抖吧！围观君~"},{"id":1392,"topListNameCn":"《帝国》评80部80年代最佳影片","topListNameEn":"","type":0,"summary":"\u201c80年代\u201d是一个迷人的年代，很多被大众熟知的经典影片诞生于这个时期。"},{"id":1370,"topListNameCn":"最赚人眼泪的50部电影","topListNameEn":"","type":0,"summary":"即便是\u201c铁汉\u201d也忍不住飙泪。"},{"id":1366,"topListNameCn":"2016年上半年最受欢迎的10部冷门佳作","topListNameEn":"","type":0,"summary":"《蛇之拥抱》、《公羊》\u2026\u2026"},{"id":1368,"topListNameCn":"《Indiewire》评20年来20部动画佳作","topListNameEn":"","type":0,"summary":"《机器人总动员》未上榜"},{"id":1363,"topListNameCn":"电影里的\"守\"艺人与工匠精神","topListNameEn":"","type":0,"summary":"一件事做到天荒地老，他们所追求的并非世俗功利，而只是简简单单的不忘初心。"},{"id":1349,"topListNameCn":"英国电影学会影史LGBT电影30佳","topListNameEn":"","type":0,"summary":"《卡罗尔》荣登榜首"},{"id":1342,"topListNameCn":"2015年日本《电影旬报》海外十佳影片","topListNameEn":"","type":0,"summary":"2015日本旬报海外佳片榜：《疯狂的麦克斯4》夺魁，《刺客聂隐娘》位列第五。"},{"id":1340,"topListNameCn":"2015年日本《电影旬报》年度十佳影片","topListNameEn":"","type":0,"summary":"2015日本旬报大奖揭晓：《恋人们》夺魁，《海街日记》位列第四。"},{"id":1337,"topListNameCn":"2015独立电影北美票房榜Top20","topListNameEn":"","type":0,"summary":"国外著名电影媒体indieWIRE网站29日评选出2015年北美票房最高的20部独立电影，票房统计截止至2015年12月29日。海伦·米伦主演的《金衣女人》以3000多万美元票房成为吸金之王。《机械姬》和《聚焦》分别位列二三位。"},{"id":1334,"topListNameCn":"《综艺》2015年度十佳电影","topListNameEn":"","type":0,"summary":"《刺客聂隐娘》入围美国Variety网站的年度十佳电影。"},{"id":1333,"topListNameCn":"《卫报》2015年电影50佳","topListNameEn":"","type":0,"summary":"奥斯卡外语片大热门《索尔之子》名列榜首。"},{"id":1332,"topListNameCn":"美国电影学会（AFI）2015年度电影十佳","topListNameEn":"","type":0,"summary":"《大空头》、《间谍之桥》、《卡萝尔》位列前三位。"}]
     * totalCount : 160
     * pageCount : 8
     */

    private int totalCount;
    private int pageCount;
    /**
     * id : 1469
     * topListNameCn : 《每日银幕》2016年度十佳影片
     * topListNameEn :
     * type : 0
     * summary : 德国电影《托尼·厄德曼》登顶
     */

    private List<TopListItem> topLists;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<TopListItem> getTopLists() {
        return topLists;
    }

    public void setTopLists(List<TopListItem> topLists) {
        this.topLists = topLists;
    }
}
