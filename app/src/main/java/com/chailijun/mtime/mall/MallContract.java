package com.chailijun.mtime.mall;

import com.chailijun.mtime.base.BasePresenter;
import com.chailijun.mtime.base.BaseView;
import com.chailijun.mtime.data.home.HomeIndex;
import com.chailijun.mtime.data.home.HomeListSuper;
import com.chailijun.mtime.data.mall.MallIndex;
import com.chailijun.mtime.data.mall.RecommendProducts;

import java.util.List;


public interface MallContract {

    interface View extends BaseView<Presenter> {

        void showLoading(boolean isActive);

        void showMallIndex(MallIndex index);

        void showLoadingMallIndexError(String msg);

        void showNoIndex();

        //推荐商品
        void showRecomProducts(List<RecommendProducts.GoodsListBean> goodsList);

        void showLoadingRecomProductsError(String msg);

        void showNoRecomProducts();

    }

    interface Presenter extends BasePresenter{

        void loadMallIndex();

        void loadRecommendProducts(String goodsIds, int pageIndex);
    }
}
