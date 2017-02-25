package com.chailijun.baseadapter.interfaces;

/**
 * author： Chailijun
 * date  ： 2016/11/8 16:30
 * e-mail： 1499505466@qq.com
 */
public interface OnLoadMoreListener {

    /**
     * 加载更多的回调方法
     * @param isReload 是否是重新加载，只有加载失败后，点击重新加载时为true
     */
    void onLoadMore(boolean isReload);
}
