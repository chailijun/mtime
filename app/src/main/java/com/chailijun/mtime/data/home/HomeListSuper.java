package com.chailijun.mtime.data.home;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chailijun.mtime.utils.Logger;

/**
 * HomeList中有多种类型，均继承该类
 */

public abstract class HomeListSuper implements MultiItemEntity{

    /**
     * type:51 dateType:1 图集
     */
    public static final int TYPE_LIST_IMAGES = 1001;
    /**
     * type:336 影评
     */
    public static final int TYPE_FILM_REVIEW = 1002;
    /**
     * type:51 dateType:0 简讯
     */
    public static final int TYPE_SHORT_NEWS0 = 1003;
    /**
     * type:51 dateType:2 简讯
     */
    public static final int TYPE_SHORT_NEWS2 = 1004;
    /**
     * type:64 猜电影、免费观影
     */
    public static final int TYPE_LIST_URL = 1005;
    /**
     * type:-1 欧美新片、华语新片
     */
    public static final int TYPE_NEW_MOVIE = 1006;
    /**
     * type:90 榜单
     */
    public static final int TYPE_TOP_LIST = 1007;
    /**
     * type:44 投票（结尾）
     */
    public static final int TYPE_LIST_VOTE = 1008;
    public static final int TYPE_UNKNOW = 1009;

    @Override
    public int getItemType() {
        int itemType = -1;
        switch (getHomeItemType()) {
            case -1:
                itemType = TYPE_NEW_MOVIE;//欧美新片
                break;
            case 51:
                if (getHomeDataType() == 0) {
                    itemType = TYPE_SHORT_NEWS0;//简讯:0

                } else if(getHomeDataType() == 1){
                    itemType = TYPE_LIST_IMAGES;//图集

                }else if(getHomeDataType() == 2){
                    itemType = TYPE_SHORT_NEWS2;//简讯:2

                }else {
                    Logger.e(HomeListSuper.class.getSimpleName(),"type51中dataType > 2");
                }
                break;
            case 64:
                itemType = TYPE_LIST_URL;//猜电影、免费观影
                break;
            case 336:
                itemType = TYPE_FILM_REVIEW;//影评
                break;
            case 90:
                itemType = TYPE_TOP_LIST;//榜单
                break;
            /*case 44:
                itemType = TYPE_LIST_VOTE;//投票
                break;*/
            default:
                itemType = TYPE_UNKNOW;
                Logger.e(HomeListSuper.class.getSimpleName(),"新闻有新类型了");
                break;
        }
        return itemType;
    }

    protected abstract int getHomeItemType();

    protected abstract int getHomeDataType();
}
