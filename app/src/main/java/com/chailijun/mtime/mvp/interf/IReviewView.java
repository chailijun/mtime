package com.chailijun.mtime.mvp.interf;

import com.chailijun.mtime.data.find.review.Review;

import java.util.List;


public interface IReviewView <T extends Data> {

    void addReview(List<Review> data);

    void loadFailed(String msg);
}
