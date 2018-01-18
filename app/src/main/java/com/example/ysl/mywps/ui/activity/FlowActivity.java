package com.example.ysl.mywps.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.ysl.mywps.R;
import com.example.ysl.mywps.ui.adapter.FlowAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ysl on 2018/1/15.
 */

public class FlowActivity extends BaseActivity {

    @BindView(R.id.flow_listview)
    ListView listView;

    FlowAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_flow_layout);

        ArrayList<String> list = new ArrayList<>();

        for (int i = 0; i < 5; ++i) {
            list.add(" " + i);
        }
        ButterKnife.bind(this);
        showLeftButton(true, "", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setTitleText("流程进度");

        adapter = new FlowAdapter(this, list);
        listView.setAdapter(adapter);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }
}
