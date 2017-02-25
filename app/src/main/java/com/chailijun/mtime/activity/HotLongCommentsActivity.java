package com.chailijun.mtime.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chailijun.mtime.R;
import com.chailijun.mtime.utils.Constants;

import butterknife.BindView;
import butterknife.OnClick;

public class HotLongCommentsActivity extends BaseActivity {

    @BindView(R.id.head_title)
    TextView head_title;
    private int movieId;

    @Override
    protected int getLayoutId() {

        return R.layout.activity_hot_long_comments;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        getData();
        head_title.setText(getString(R.string.hot_long_comment));
    }

    private void getData() {
        Intent intent = getIntent();
        movieId = intent.getIntExtra(Constants.MOVIE_ID,0);
    }

    @OnClick({R.id.back})
    public void onClick(View view){
        finish();
    }
}
