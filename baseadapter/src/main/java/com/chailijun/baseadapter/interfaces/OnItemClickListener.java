package com.chailijun.baseadapter.interfaces;

import com.chailijun.baseadapter.ViewHolder;

/**
 * author： Chailijun
 * date  ： 2016/11/8 16:38
 * e-mail： 1499505466@qq.com
 */
public interface OnItemClickListener<T> {
    void onItemClick(ViewHolder viewHolder, T data, int position);
}
