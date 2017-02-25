package com.chailijun.mtime.find.news;

import com.chailijun.mtime.base.BasePresenter;
import com.chailijun.mtime.base.BaseView;
import com.chailijun.mtime.data.find.news.NewsItem;
import com.chailijun.mtime.data.find.trailer.TrailerItem;

import java.util.List;

public interface FindNewsContract {

    interface View extends BaseView<Presenter>{

        void showNews(List<NewsItem> newsItems);

        void showLoadingNewsError(String msg);

        void showNoNews();
    }

    interface Presenter extends BasePresenter{

        void loadNews(int pageIndex);
    }
}
