package com.chailijun.mtime.payticket.movie;


import com.chailijun.mtime.base.BasePresenter;
import com.chailijun.mtime.base.BaseView;
import com.chailijun.mtime.data.payticket.LocationMoviesItem;

import java.util.List;

/**
 * author： Chailijun
 * date  ： 2017/1/6 09:43
 * e-mail： 1499505466@qq.com
 *
 * 热放电影的契约类
 */

public interface HotPlayContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showMovies(List<LocationMoviesItem> movies);

        void showLoadingMoviesError(String msg);

        void showNoMovies();

    }

    interface Presenter extends BasePresenter{

        void loadMovies(int locationId);
    }
}
