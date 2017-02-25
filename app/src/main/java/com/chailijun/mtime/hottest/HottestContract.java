package com.chailijun.mtime.hottest;

import com.chailijun.mtime.base.BasePresenter;
import com.chailijun.mtime.base.BaseView;
import com.chailijun.mtime.data.hottest.Hottest;


public interface HottestContract {

    interface View extends BaseView<Presenter>{

        void showHotest(Hottest hottest);

        void showLoadingHotestError(String msg);

        void showNoData();

    }

    interface Presenter extends BasePresenter{

        void loadHotest(int locationId,int pageIndex);
    }
}
