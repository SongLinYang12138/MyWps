package com.example.ysl.mywps.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.ysl.mywps.R;
import com.example.ysl.mywps.utils.NoDoubleClickListener;
import com.example.ysl.mywps.utils.SysytemSetting;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/1/14 0014.
 */

public class DocumentTurnActivity extends BaseActivity {

    @BindView(R.id.document_ll_inside)
    LinearLayout llInside;

    private MyClickListener click = new MyClickListener();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_document_turn_layout);
        ButterKnife.bind(this);
       llInside.setOnClickListener(click);
    }

    @Override
    public void initView() {

        showLeftButton(true, "", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setTitleText("公文流转");
    }

    @Override
    public void initData() {

    }

    private class MyClickListener extends NoDoubleClickListener{
        @Override
        public void click(View v) {

            switch (v.getId()){

                case R.id.document_ll_inside:

                    Intent intent = new Intent(DocumentTurnActivity.this,StayToDoActivity.class);
                    intent.putExtra(SysytemSetting.WPS_MODE,SysytemSetting.WPS_WRITE);
                    startActivity(intent);
                    break;


            }

        }
    }
}
