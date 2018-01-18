package com.example.ysl.mywps.ui.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ysl.mywps.R;
import com.example.ysl.mywps.ui.view.MoviewImage;
import com.example.ysl.mywps.ui.view.WritingPadView;
import com.example.ysl.mywps.utils.CommonUtil;
import com.example.ysl.mywps.utils.NoDoubleClickListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/1/14 0014.
 */

public class WpcDetailActivity extends BaseActivity {

    @BindView(R.id.wpcdetail_iv_artical)
    ImageView ivArtical;
    @BindView(R.id.wpcdetail_ll_artical)
    LinearLayout llArtival;
    @BindView(R.id.wpcdetail_iv_message)
    ImageView ivMessage;
    @BindView(R.id.wpcdetail_ll_message)
    LinearLayout llMessage;
    @BindView(R.id.wpcdetail_iv_sign)
    ImageView ivSign;
    @BindView(R.id.wpcdetail_ll_sign)
    LinearLayout llSign;
    @BindView(R.id.wpcdetail_iv_send)
    ImageView ivSend;
    @BindView(R.id.wpcdetail_ll_send)
    LinearLayout llSend;
    @BindView(R.id.wpcdetail_iv_icon)
    MoviewImage ivIcon;

    private MyclickListener click = new MyclickListener();
    private WpsBroadCast reciver = new WpsBroadCast();
    private int screenH = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_wpc_details_layout);
        ButterKnife.bind(this);

        ivArtical.setOnClickListener(click);
        llArtival.setOnClickListener(click);
        ivMessage.setOnClickListener(click);
        llMessage.setOnClickListener(click);
        ivSign.setOnClickListener(click);
        llSign.setOnClickListener(click);
        ivSend.setOnClickListener(click);
        llSend.setOnClickListener(click);

        this.registerReceiver(reciver, new IntentFilter("com.example.ysl.mywps.sign"));

        screenH = CommonUtil.getScreenWH(this)[1];
        showLeftButton(true, "", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setTitleText("pptTest.ppt");
        showRight(true, "流程", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WpcDetailActivity.this, FlowActivity.class);
                startActivity(intent);
            }
        });
        writePermission();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        ivIcon.autoMouse(event);
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(reciver);
    }

    private class MyclickListener extends NoDoubleClickListener {
        @Override
        public void click(View v) {

            int id = v.getId();

            if (id == R.id.wpcdetail_iv_artical || id == R.id.wpcdetail_ll_artical) {

            } else if (id == R.id.wpcdetail_iv_message || id == R.id.wpcdetail_ll_message) {

            } else if (id == R.id.wpcdetail_iv_sign || id == R.id.wpcdetail_ll_sign) {

//                Intent intent = new Intent(WpcDetailActivity.this, SignActivity.class);
//                startActivity(intent);
                setSign();
            } else if (id == R.id.wpcdetail_iv_send || id == R.id.wpcdetail_ll_send) {
                Intent intent = new Intent(WpcDetailActivity.this, ContactActivity.class);
                startActivity(intent);
            }
        }
    }

    private class WpsBroadCast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "mybitmap", Toast.LENGTH_SHORT).show();
            Logger.i("static  " + CommonUtil.myPath);

        }


    }

    /**
     * 检查存储权限，如果没有就请求
     */
    @TargetApi(Build.VERSION_CODES.M)
    private void writePermission() {
        if (Build.VERSION.SDK_INT < 23) {
            return;
        }
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
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
            Toast.makeText(WpcDetailActivity.this, "请开启存储权限", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    PopupWindow popupWindow;
    WritingPadView writingPadView;
    TextView tvCancel;
    TextView tvClear;
    RelativeLayout rlSignConfirm;

    private void setSign() {

        if (popupWindow == null) {

            View view = LayoutInflater.from(this).inflate(R.layout.sign_layout, null);

            writingPadView = (WritingPadView) view.findViewById(R.id.sign_writing);
            tvCancel = (TextView) view.findViewById(R.id.sign_tv_cancel);
            tvClear = (TextView) view.findViewById(R.id.sign_tv_clear);
            rlSignConfirm = (RelativeLayout) view.findViewById(R.id.sign_rl_confirm);
            popupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT, screenH / 2, true);

            // 菜单背景色。加了一点透明度
            ColorDrawable dw = new ColorDrawable(0xddffffff);
            popupWindow.setBackgroundDrawable(dw);
            // 设置背景半透明
            view.setAlpha(0.7f);
            popupWindow.setOutsideTouchable(true);
//        popupWindow.setWidth();
            popupWindow.setFocusable(true);
            //TODO 注意：这里的 R.layout.activity_main，不是固定的。你想让这个popupwindow盖在哪个界面上面。就写哪个界面的布局。这里以主界面为例
            popupWindow.showAtLocation(LayoutInflater.from(this).inflate(R.layout.activity_wpc_details_layout, null),
                    Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

        } else {

            popupWindow.showAtLocation(LayoutInflater.from(this).inflate(R.layout.activity_wpc_details_layout, null),
                    Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

        }

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writingPadView.clear();
            }
        });
        tvClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writingPadView.clear();
            }
        });
        rlSignConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(WpcDetailActivity.this, "正在保存", Toast.LENGTH_SHORT).show();
                Thread saveThread = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "wpsSign";
                        File file = new File(path);
                        if (!file.exists()) file.mkdirs();
                        path += File.separator + "qianming.png";
                        try {
                            writingPadView.save(path);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Logger.i("原始   " + path);

                        final String mypath = path;
                        WpcDetailActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                ImageLoader.getInstance().displayImage("file://" + mypath, ivIcon);
                                popupWindow.dismiss();
                            }
                        });
                    }
                });
                saveThread.setDaemon(true);
                saveThread.start();
            }
        });


    }

    // 根据路径获得图片并压缩，返回bitmap用于显示
//        private Bitmap getSmallBitmap(String filePath) {
//            final BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inJustDecodeBounds = true;
//            BitmapFactory.decodeFile(filePath, options);
//
//            // Calculate inSampleSize
//            options.inSampleSize = calculateInSampleSize(options, 480, 800);
//
//            // Decode bitmap with inSampleSize set
//            options.inJustDecodeBounds = false;
//
//            return BitmapFactory.decodeFile(filePath, options);
//        }

}
