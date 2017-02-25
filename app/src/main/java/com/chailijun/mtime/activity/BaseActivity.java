package com.chailijun.mtime.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import com.chailijun.mtime.MtimeApp;

import butterknife.ButterKnife;
import butterknife.Unbinder;


@Deprecated
public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutId());
        unbinder = ButterKnife.bind(this);
        initViews(savedInstanceState);
    }

    protected abstract int getLayoutId();

    protected abstract void initViews(@Nullable Bundle savedInstanceState);

    @Override
    protected void onResume() {
        super.onResume();
//        Bugtags.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        Bugtags.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null){
            unbinder.unbind();
        }
//        // 测试有无内存泄漏
//        RefWatcher refWatcher = MtimeApp.getRefWatcher(this);
//        refWatcher.watch(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        Bugtags.onDispatchTouchEvent(this, ev);
        return super.dispatchTouchEvent(ev);
    }
}
