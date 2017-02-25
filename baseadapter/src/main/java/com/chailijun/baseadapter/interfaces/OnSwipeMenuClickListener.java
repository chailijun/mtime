package com.chailijun.baseadapter.interfaces;

import com.chailijun.baseadapter.ViewHolder;

/**
 * author： Chailijun
 * date  ： 2016/11/8 16:39
 * e-mail： 1499505466@qq.com
 */
public interface OnSwipeMenuClickListener<T> {
    void onSwipMenuClick(ViewHolder viewHolder, T data, int position);
}
