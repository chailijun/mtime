package com.chailijun.mtime.homepage;

import com.chailijun.mtime.base.BasePresenter;
import com.chailijun.mtime.base.BaseView;
import com.chailijun.mtime.data.home.HomeIndex;
import com.chailijun.mtime.data.home.HomeListSuper;
import com.chailijun.mtime.data.home.HotPlayMovies;

import java.util.List;



public interface HomeContract {

    interface View extends BaseView<Presenter>{

        /**
         * 加载中
         * @param active
         */
        void showLoading(boolean active);

        /**
         * (初始)加载错误
         * @param active
         */
        void showLoadingError(boolean active);

        //首页新闻列表
        void showHomeList(List<HomeListSuper> list);
        void showLoadingHomeListError(String msg);
        void showNoHomeList();

        //首页索引
        void showIndex(HomeIndex homeIndex);
        void showLoadingIndexError(String msg);

//        void showNoIndex();

        //首页热放电影
        void showHotPlayMovies(HotPlayMovies movies);
        void showLoadingHotPlayMoviesError(String msg);

//        void showNoMovie();
    }

    interface Presenter extends BasePresenter{

        void loadHomeIndex(int locationId);

        void loadHotPlayMovies(int locationId);

        void loadHomeList(int pageIndex);
    }

}
