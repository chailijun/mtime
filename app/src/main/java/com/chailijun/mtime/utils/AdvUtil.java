package com.chailijun.mtime.utils;

import com.chailijun.mtime.MtimeApp;
import com.chailijun.mtime.db.entity.AdvList;
import com.chailijun.mtime.db.gen.AdvListDao;
import com.chailijun.mtime.mvp.model.Advertisement;

import java.util.List;

/**
 * 将Advertisement对象-->AdvList
 */
public class AdvUtil {

    public static void saveAdvertisement(Advertisement adv){
        List<Advertisement.AdvListBean> advListBean = adv.getAdvList();
        if (advListBean != null && advListBean.size() > 0){
            AdvListDao advListDao = MtimeApp.getInstances().getmDaoSession().getAdvListDao();

            for (int i = 0; i < advListBean.size(); i++) {

                AdvList advList = new AdvList();
                Advertisement.AdvListBean bean = advListBean.get(i);
                advList.setType(bean.getType());
                advList.setTypeName(bean.getTypeName());
                advList.setAdvTag(bean.getAdvTag());
                advList.setIsHorizontalScreen(bean.isIsHorizontalScreen());
                advList.setStartDate(bean.getStartDate());
                advList.setEndDate(bean.getEndDate());
                advList.setUrl(bean.getUrl());
                advList.setImage(bean.getImage());
                advList.setTag(bean.getTag());
                advList.setIsOpenH5(bean.isIsOpenH5());
                advList.setIsLogo(bean.isIsLogo());
                advList.setEntryText(bean.getEntryText());
                advList.setGotoPage_gotoType(bean.getGotoPage().getGotoType());
                advList.setGotoPage_url(bean.getGotoPage().getUrl());
                advList.setGotoPage_param_movieId(bean.getGotoPage().getParameters().getMovieId());
                advList.setGotoPage_param1_movieId(bean.getGotoPage().getParameters1().getMovieId());
                advList.setGotoPage_isGoH5(bean.getGotoPage().isIsGoH5());
                advList.setGotoPage_relatedTypeUrl(bean.getGotoPage().getRelatedTypeUrl());

                advListDao.insert(advList);
            }
        }


    }
}
