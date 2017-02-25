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

public class MovieImageAllActivity extends BaseActivity {

    @BindView(R.id.head_title)
    TextView head_title;

    String movieName;

    @Override
    protected int getLayoutId() {

        return R.layout.activity_movie_image_all;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        getData();

        head_title.setText(movieName);

    }

    private void getData() {
        Intent intent = getIntent();
        movieName = intent.getStringExtra(Constants.MOVIE_NAME);
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
