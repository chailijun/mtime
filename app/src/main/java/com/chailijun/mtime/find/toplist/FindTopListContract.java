package com.chailijun.mtime.find.toplist;

import com.chailijun.mtime.base.BasePresenter;
import com.chailijun.mtime.base.BaseView;
import com.chailijun.mtime.data.find.toplist.TopListItem;
import com.chailijun.mtime.data.find.trailer.TrailerItem;

import java.util.List;


public interface FindTopListContract {

    interface View extends BaseView<Presenter>{

        void showTopList(List<TopListItem> topLists);

        void showLoadingTopListError(String msg);

        void showNoTopList();
    }

    interface Presenter extends BasePresenter{

        void loadTopList(int pageIndex);
    }
}
