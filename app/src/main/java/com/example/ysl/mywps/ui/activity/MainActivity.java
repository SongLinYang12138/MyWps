package com.example.ysl.mywps.ui.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Typeface;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringDef;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
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
import com.example.ysl.mywps.bean.FileType;
import com.example.ysl.mywps.bean.TransportBean;
import com.example.ysl.mywps.net.HttpUtl;
import com.example.ysl.mywps.provider.UploadProvider;
import com.example.ysl.mywps.ui.adapter.PagerAdapter;
import com.example.ysl.mywps.ui.fragment.ContactFragment;
import com.example.ysl.mywps.ui.fragment.MessageFragment;
import com.example.ysl.mywps.ui.fragment.MineFragment;
import com.example.ysl.mywps.ui.fragment.WorkFragment;
import com.example.ysl.mywps.utils.CommonUtil;
import com.example.ysl.mywps.utils.NoDoubleClickListener;
import com.example.ysl.mywps.utils.SharedPreferenceUtils;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.JPushMessage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity implements View.OnClickListener {

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

    @BindView(R.id.main_vp_container)
    ViewPager viewPager;


    private Fragment messageFragment, contactFragment, workFragment, mineFragment;
    private ColorStateList colorNomal, colorSelect;
    //    private MyclickListener myclickListener;
    private PagerAdapter pagerAdapter;


    //    公文流转  内部公文 代办事项   流程 调专业版
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

//        myclickListener = new MyclickListener();

        llMessage.setOnClickListener(this);
        llWork.setOnClickListener(this);
        llMine.setOnClickListener(this);
        llContact.setOnClickListener(this);


        colorNomal = getResources().getColorStateList(R.color.bottom_normal);
        colorSelect = getResources().getColorStateList(R.color.bottom_selected);

    }

    @Override
    public Resources getResources() {

        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    @Override
    public void initView() {

        ArrayList<Fragment> fragments = new ArrayList<>();

        messageFragment = new MessageFragment();
        workFragment = new WorkFragment();
        contactFragment = new ContactFragment();
        mineFragment = new MineFragment();

        fragments.add(messageFragment);
        fragments.add(workFragment);
        fragments.add(contactFragment);
        fragments.add(mineFragment);

        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(pagerAdapter);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.i("aaa", "position  " + position);
                showMessage(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void initData() {


        final String token = SharedPreferenceUtils.loginValue(this, "token");


    }

    @Override
    protected void onResume() {
        super.onResume();
        viewPager.setCurrentItem(1);
        writePermission();

//        ContentResolver resolver = this.getContentResolver();
//        TransportBean bean = new TransportBean();
//        bean.setPath("222");
//        bean.setSize("1111");
//        bean.setName("3333");
//        ContentValues values = bean.toContentValues();
//
//        resolver.insert(UploadProvider.CONTENT_URI,values);
//
//
//
//        Cursor cursor = resolver.query(UploadProvider.CONTENT_URI,TransportBean.TRANSPORTBEANS,null,null,null);
//        if(cursor != null){
//
//            ArrayList<TransportBean> list = TransportBean.getTransportBeans(cursor);
//
//            Logger.i("uploadSize  "+list.size());
//        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    /**
     * 检查存储权限，如果没有就请求
     */
    @TargetApi(Build.VERSION_CODES.M)
    private void writePermission() {
        if (Build.VERSION.SDK_INT < 23) {
            return;
        }
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 11);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 11) {
            int grantResult = grantResults[0];
            boolean granted = grantResult == PackageManager.PERMISSION_GRANTED;

            Log.i("aaa", " " + granted);
        } else {
            writePermission();
            Toast.makeText(this, "请开启存储权限", Toast.LENGTH_SHORT).show();
//            finish();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    private void setTextBack(int index) {

        switch (index) {
            case 1:
                tvMessage.setTextColor(colorSelect);
                tvConact.setTextColor(colorNomal);
                tvWork.setTextColor(colorNomal);
                tvMine.setTextColor(colorNomal);

                setTitleText("消息");
                ibMessage.setBackground(getResources().getDrawable(R.mipmap.icon_message_selected));
                ibWork.setBackground(getResources().getDrawable(R.mipmap.icon_work_normal));
                ibContact.setBackground(getResources().getDrawable(R.mipmap.icon_contact_normal));
                ibMine.setBackground(getResources().getDrawable(R.mipmap.icon_mine_normal));

                break;
            case 2:
                setTitleText("工作");

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
                setTitleText("通讯录");
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
                setTitleText("我的");
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


    private void showMessage(int index) {


        switch (index) {

            case 0:

                setTextBack(1);

                break;
            case 1:
                setTextBack(2);
                break;

            case 2:
                setTextBack(3);

                break;
            case 3:
                setTextBack(4);
                break;
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {


        int id = v.getId();

        if (id == R.id.main_ll_message || id == R.id.main_ib_message) {

            viewPager.setCurrentItem(0);
        } else if (id == R.id.main_ll_work || id == R.id.main_ib_work) {
            viewPager.setCurrentItem(1);
        } else if (id == R.id.main_ll_contact || id == R.id.main_ib_contact) {
            viewPager.setCurrentItem(2);
        } else if (id == R.id.main_ll_mine || id == R.id.main_ib_mine) {
            viewPager.setCurrentItem(3);
        }


    }

//    private class MyclickListener extends NoDoubleClickListener {
//        @Override
//        public void click(View v) {
//
//
//
//        }
//    }

}
