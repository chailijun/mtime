package com.chailijun.mtime.payticket.movie;

import com.chailijun.mtime.base.BasePresenter;
import com.chailijun.mtime.base.BaseView;
import com.chailijun.mtime.data.payticket.LocationMoviesItem;
import com.chailijun.mtime.data.payticket.MovieComingNew;
import com.chailijun.mtime.mvp.model.movie.MovieComingNewJson;

import java.util.List;

/**
 * author： Chailijun
 * date  ： 2017/1/10 10:26
 * e-mail： 1499505466@qq.com
 */

public interface ComingMovieContract {

    interface View extends BaseView<Presenter> {

        void showMovies(MovieComingNew movies);

        void showLoadingMoviesError(String msg);

        void showNoMovies();

    }

    interface Presenter extends BasePresenter {

        void loadMovies(int locationId);
    }
}
