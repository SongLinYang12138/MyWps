package com.example.ysl.mywps.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.example.ysl.mywps.R;

/**
 * Created by ysl on 2018/1/15.
 */

public class CommitActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commit_layout);
        showLeftButton(true, "", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setTitleText("提交");
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }
}
