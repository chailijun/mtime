package com.chailijun.mtime.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chailijun.mtime.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 演职员表
 */
public class MovieCreditsActivity extends BaseActivity {

    @BindView(R.id.head_title)
    TextView head_title;

    @Override
    protected int getLayoutId() {

        return R.layout.activity_movie_credits;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {

        getData();
        head_title.setText(getString(R.string.credits));
    }

    private void getData() {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        getData();
    }

    @OnClick({R.id.back})
    public void onClick(View view){
        finish();
    }
}
