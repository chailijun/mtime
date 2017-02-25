package com.chailijun.mtime.base;

import com.chailijun.mtime.R;

/**
 * 带头部的Activity
 */

public abstract class HeadActivity extends BaseActivity{

    @Override
    public int bindLayout() {
        return R.layout.activity_head;
    }
}
