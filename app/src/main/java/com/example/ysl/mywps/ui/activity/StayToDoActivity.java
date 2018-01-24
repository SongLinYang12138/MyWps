package com.example.ysl.mywps.ui.activity;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ysl.mywps.R;
import com.example.ysl.mywps.ui.fragment.HasDoFragment;
import com.example.ysl.mywps.ui.fragment.StayDoFragment;
import com.example.ysl.mywps.utils.NoDoubleClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/1/14 0014.
 */

public class StayToDoActivity extends AppCompatActivity {

    @BindView(R.id.stay_rl_container)
    RelativeLayout rlContainer;
    @BindView(R.id.stay_tv_stay_do)
    TextView tvStay;
    @BindView(R.id.stay_tv_have_do)
    TextView tvHave;
    @BindView(R.id.stay_ll_back)
    LinearLayout llBack;


    private FragmentManager fragmentManager;
    private FragmentTransaction beginTransaction;
    private StayDoFragment stayFragment;
    private HasDoFragment doFragment;
    private Fragment currentFragment;
    private MyclickListener click = new MyclickListener();
    private ColorStateList normal,selected;
    private Drawable selctedBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_stay_to_do_layout);
        ButterKnife.bind(this);


        initData();
        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initData() {

        normal = getResources().getColorStateList(R.color.white_back);
        selected = getResources().getColorStateList(R.color.text_black);
        selctedBack = getResources().getDrawable(R.drawable.rect_white);


        fragmentManager = getSupportFragmentManager();
        tvStay.setOnClickListener(click);
        tvHave.setOnClickListener(click);
        showFragment(1);
    }

    private void showFragment(int position) {
        beginTransaction = fragmentManager.beginTransaction();
        if (currentFragment != null) beginTransaction.hide(currentFragment);
        switch (position) {

            case 1:

                tvStay.setTextColor(selected);
                tvHave.setTextColor(normal);
                tvStay.setBackground(selctedBack);
                tvHave.setBackgroundResource(R.color.tool_bar);

                if (stayFragment == null) {

                    stayFragment = new StayDoFragment();
                    beginTransaction.add(R.id.stay_rl_container, stayFragment);
                } else {

                    if (stayFragment.isHidden()) beginTransaction.show(stayFragment);

                }
                currentFragment = stayFragment;
                break;
            case 2:

                tvStay.setTextColor(normal);
                tvHave.setTextColor(selected);
                tvStay.setBackgroundResource(R.color.tool_bar);
                tvHave.setBackground(selctedBack);


                if (doFragment == null) {

                    doFragment = new HasDoFragment();
                    beginTransaction.add(R.id.stay_rl_container, doFragment);
                } else {

                    if (doFragment.isHidden()) beginTransaction.show(doFragment);

                }
                break;
        }

        beginTransaction.commit();

    }


    private class MyclickListener extends NoDoubleClickListener {
        @Override
        public void click(View v) {

            switch (v.getId()) {

                case R.id.stay_tv_stay_do:

                    showFragment(1);
                    break;
                case R.id.stay_tv_have_do:
                    showFragment(2);
                    break;


            }

        }
    }


}
