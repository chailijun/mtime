package com.chailijun.mtime.moviedetail;

import com.chailijun.mtime.base.BasePresenter;
import com.chailijun.mtime.base.BaseView;
import com.chailijun.mtime.data.moviedetail.ExtendMovieDetail;
import com.chailijun.mtime.data.moviedetail.HotComment;
import com.chailijun.mtime.data.moviedetail.MovieDetail;


public interface MovieDetailContract {

    interface View extends BaseView<Presenter>{

        /**
         * 加载中
         * @param active
         */
        void showLoading(boolean active);

        /**
         * 加载错误
         * @param active
         */
        void showLoadingError(boolean active);

        void showMovieDetail(MovieDetail movieDetail);
        void showLoadingMovieDetailError(String msg);

        void showExtendMovieDetail(ExtendMovieDetail extendMovieDetail);
        void showLoadingExtendMovieDetailError(String msg);

        void showHotComment(HotComment hotComment);
        void showLoadingHotCommentError(String msg);

    }

    interface Presenter extends BasePresenter{

        /**
         * 加载电影详细
         * @param locationId
         * @param movieId
         */
        void  loadMovieDetail(int locationId,int movieId);

        /**
         * 加载短评和影评
         * @param movieId
         */
        void  loadHotComment(int movieId);

        /**
         * 加载专业解读
         * @param movieId
         */
        void loadExtendMovieDetail(int movieId);
    }
}
