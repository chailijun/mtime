package com.chailijun.mtime.find.trailer;

import com.chailijun.mtime.base.BasePresenter;
import com.chailijun.mtime.base.BaseView;
import com.chailijun.mtime.data.find.news.NewsItem;
import com.chailijun.mtime.data.find.trailer.TrailerItem;

import java.util.List;

/**
 * author： Chailijun
 * date  ： 2017/1/15 14:08
 * e-mail： 1499505466@qq.com
 */

public interface FindTrailerContract {

    interface View extends BaseView<Presenter>{

        void showTrailer(List<TrailerItem> trailers);

        void showLoadingTrailerError(String msg);
    }

    interface Presenter extends BasePresenter{

        void loadTrailer();
    }
}
