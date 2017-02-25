package com.chailijun.mtime.find;

import com.chailijun.mtime.base.BasePresenter;
import com.chailijun.mtime.base.BaseView;
import com.chailijun.mtime.data.find.Index;


public interface FindContract {

    interface View extends BaseView<Presenter>{

        void showLoading(boolean active);

        void showIndex(Index index);

        void showLoadingIndexError(String msg);
    }

    interface Presenter extends BasePresenter{

        void loadIndex();
    }
}
