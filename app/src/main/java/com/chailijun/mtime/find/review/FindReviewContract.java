package com.chailijun.mtime.find.review;

import com.chailijun.mtime.base.BasePresenter;
import com.chailijun.mtime.base.BaseView;
import com.chailijun.mtime.data.find.review.Review;
import com.chailijun.mtime.data.find.trailer.TrailerItem;

import java.util.List;

public interface FindReviewContract {

    interface View extends BaseView<Presenter>{

        void showReview(List<Review> reviews);

        void showLoadingReviewError(String msg);
    }

    interface Presenter extends BasePresenter{

        void loadReview(boolean needTop);
    }
}
