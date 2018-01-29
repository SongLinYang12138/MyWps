package com.example.ysl.mywps.ui.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;

import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ysl.mywps.R;
import com.example.ysl.mywps.ui.fragment.ContactFragment;
import com.example.ysl.mywps.ui.fragment.MessageFragment;
import com.example.ysl.mywps.ui.fragment.MineFragment;
import com.example.ysl.mywps.ui.fragment.WorkFragment;
import com.example.ysl.mywps.utils.CommonUtil;
import com.example.ysl.mywps.utils.NoDoubleClickListener;
import com.orhanobut.logger.Logger;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.JPushMessage;

public class MainActivity extends AppCompatActivity {

    //    TextView tvText;
    private static final int BACK_CODE = 1;
    private static final String TAG = "aaa";
    @BindView(R.id.main_tv_message)
    TextView tvMessage;
    @BindView(R.id.main_ib_message)
    ImageButton ibMessage;
    @BindView(R.id.main_ll_message)
    LinearLayout llMessage;

    @BindView(R.id.main_tv_work)
    TextView tvWork;
    @BindView(R.id.main_ib_work)
    ImageButton ibWork;
    @BindView(R.id.main_ll_work)
    LinearLayout llWork;

    @BindView(R.id.main_tv_contact)
    TextView tvConact;
    @BindView(R.id.main_ib_contact)
    ImageButton ibContact;
    @BindView(R.id.main_ll_contact)
    LinearLayout llContact;


    @BindView(R.id.main_tv_mine)
    TextView tvMine;
    @BindView(R.id.main_ib_mine)
    ImageButton ibMine;
    @BindView(R.id.main_ll_mine)
    LinearLayout llMine;

    @BindView(R.id.main_rl_container)
    RelativeLayout rlContainer;


    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private Fragment currentFrament, messageFragment, contactFragment, workFragment, mineFragment;
    private ColorStateList colorNomal, colorSelect;
    private MyclickListener myclickListener;
    private float x1, x2;
    private int currentIndex = 0;

    //    公文流转  内部公文 代办事项   流程 调专业版
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        myclickListener = new MyclickListener();

        llMessage.setOnClickListener(myclickListener);
        llWork.setOnClickListener(myclickListener);
        llContact.setOnClickListener(myclickListener);
        llMine.setOnClickListener(myclickListener);
        ibMessage.setOnClickListener(myclickListener);
        ibWork.setOnClickListener(myclickListener);
        ibContact.setOnClickListener(myclickListener);
        ibMine.setOnClickListener(myclickListener);

        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        colorNomal = getResources().getColorStateList(R.color.bottom_normal);
        colorSelect = getResources().getColorStateList(R.color.bottom_selected);
        showMessage(1);


    }


    private void setTextBack(int index) {

        switch (index) {
            case 1:
                currentIndex = 1;
                tvMessage.setTextColor(colorSelect);
                tvConact.setTextColor(colorNomal);
                tvWork.setTextColor(colorNomal);
                tvMine.setTextColor(colorNomal);

                ibMessage.setBackground(getResources().getDrawable(R.mipmap.icon_message_selected));
                ibWork.setBackground(getResources().getDrawable(R.mipmap.icon_work_normal));
                ibContact.setBackground(getResources().getDrawable(R.mipmap.icon_contact_normal));
                ibMine.setBackground(getResources().getDrawable(R.mipmap.icon_mine_normal));

                break;
            case 2:


                currentIndex = 2;
                tvMessage.setTextColor(colorNomal);
                tvConact.setTextColor(colorNomal);
                tvWork.setTextColor(colorSelect);
                tvMine.setTextColor(colorNomal);

                ibMessage.setBackground(getResources().getDrawable(R.mipmap.icon_message_normal));
                ibWork.setBackground(getResources().getDrawable(R.mipmap.icon_work_selected));
                ibContact.setBackground(getResources().getDrawable(R.mipmap.icon_contact_normal));
                ibMine.setBackground(getResources().getDrawable(R.mipmap.icon_mine_normal));

                break;
            case 3:

                currentIndex = 3;
                tvMessage.setTextColor(colorNomal);
                tvConact.setTextColor(colorSelect);
                tvWork.setTextColor(colorNomal);
                tvMine.setTextColor(colorNomal);


                ibMessage.setBackground(getResources().getDrawable(R.mipmap.icon_message_normal));
                ibWork.setBackground(getResources().getDrawable(R.mipmap.icon_contact_normal));
                ibContact.setBackground(getResources().getDrawable(R.mipmap.icon_contact_select));
                ibMine.setBackground(getResources().getDrawable(R.mipmap.icon_mine_normal));
                break;
            case 4:
                currentIndex = 4;
                tvMessage.setTextColor(colorNomal);
                tvConact.setTextColor(colorNomal);
                tvWork.setTextColor(colorNomal);
                tvMine.setTextColor(colorSelect);

                ibMessage.setBackground(getResources().getDrawable(R.mipmap.icon_message_normal));
                ibWork.setBackground(getResources().getDrawable(R.mipmap.icon_work_normal));
                ibContact.setBackground(getResources().getDrawable(R.mipmap.icon_contact_normal));
                ibMine.setBackground(getResources().getDrawable(R.mipmap.icon_mine_selected));
                break;


        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        if (MotionEvent.ACTION_DOWN == event.getAction()) {
            x1 = event.getX();
        }
        if (MotionEvent.ACTION_UP == event.getAction()) {
            x2 = event.getX();
            float value = x2 - x1;
            if (Math.abs(value) > 100) {

//                direction 0 right 1 left
                //菜单朝左滑动
                if (value < 0) {

                    if (currentIndex != 4) {
                        showMessage(currentIndex + 1);
                    }
                } else {
//                    菜单向右移
                    if (currentIndex != 1) {
                        showMessage(currentIndex - 1);
                    }
                }
            }
        }
        return false;

    }

    private void showMessage(int index) {
        transaction = fragmentManager.beginTransaction();
        if (currentFrament != null) transaction.hide(currentFrament);

        switch (index) {

            case 1:

                setTextBack(1);
                if (messageFragment == null) {
                    messageFragment = new MessageFragment();
                    transaction.add(R.id.main_rl_container, messageFragment);
                } else {
                    transaction.show(messageFragment);

                }
                currentFrament = messageFragment;
                break;
            case 2:
                setTextBack(2);

                if (workFragment == null) {
                    workFragment = new WorkFragment();
                    transaction.add(R.id.main_rl_container, workFragment);
                } else {

                    transaction.show(workFragment);

                }
                currentFrament = workFragment;

                break;

            case 3:
                setTextBack(3);
                if (contactFragment == null) {
                    contactFragment = new ContactFragment();
                    transaction.add(R.id.main_rl_container, contactFragment);
                } else {

                    transaction.show(contactFragment);

                }
                currentFrament = contactFragment;
                break;
            case 4:
                setTextBack(4);

                if (mineFragment == null) {
                    mineFragment = new MineFragment();
                    transaction.add(R.id.main_rl_container, mineFragment);
                } else {

                    transaction.show(mineFragment);

                }
                currentFrament = mineFragment;
                break;
        }

        transaction.commit();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private class MyclickListener extends NoDoubleClickListener {
        @Override
        public void click(View v) {


            int id = v.getId();

            if (id == R.id.main_ll_message || id == R.id.main_ib_message) {

                showMessage(1);
            } else if (id == R.id.main_ll_work || id == R.id.main_ib_work) {

                showMessage(2);
            } else if (id == R.id.main_ll_contact || id == R.id.main_ib_contact) {

                showMessage(3);
            } else if (id == R.id.main_ll_mine || id == R.id.main_ib_mine) {

                showMessage(4);
            }


        }
    }

}
