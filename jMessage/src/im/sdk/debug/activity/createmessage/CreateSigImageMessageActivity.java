package im.sdk.debug.activity.createmessage;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.api.BasicCallback;
import im.sdk.debug.R;

/**
 * Created by ${chenyn} on 16/4/1.
 *
 * @desc :创建单聊图片信息
 */
public class CreateSigImageMessageActivity extends Activity {
    private static final String TAG = CreateSigImageMessageActivity.class.getSimpleName();
    private static int RESULT_LOAD_IMAGE = 1;
    private Button mBt_localImage;
    private Button mBt_send;
    private EditText mEt_singleImageMessage;
    private String mPicturePath;
    private ProgressDialog mProgressDialog;
    private EditText mEt_singleImageAppkey;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initData();
    }

    private void initData() {
        mBt_localImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RESULT_LOAD_IMAGE);
            }
        });

        mBt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressDialog = ProgressDialog.show(CreateSigImageMessageActivity.this, "提示:", "正在加载中。。。");
                mProgressDialog.setCanceledOnTouchOutside(true);
                String name = mEt_singleImageMessage.getText().toString();
                String appkey = mEt_singleImageAppkey.getText().toString();
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(mPicturePath)) {
                    mProgressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "请输入相关参数并选择图片", Toast.LENGTH_SHORT).show();
                    return;
                }
                File file = new File(mPicturePath);
                try {
                    Message imageMessage = JMessageClient.createSingleImageMessage(name, appkey, file);
                    imageMessage.setOnSendCompleteCallback(new BasicCallback() {
                        @Override
                        public void gotResult(int i, String s) {
                            if (i == 0) {
                                mProgressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "发送成功", Toast.LENGTH_SHORT).show();
                            } else {
                                mProgressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "发送失败", Toast.LENGTH_SHORT).show();
                                Log.i(TAG, "JMessageClient.createSingleImageMessage " + ", responseCode = " + i + " ; LoginDesc = " + s);
                            }
                        }
                    });
                    JMessageClient.sendMessage(imageMessage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initView() {
        setContentView(R.layout.activity_create_single_image_message);
        mBt_localImage = (Button) findViewById(R.id.bt_local_image);
        mBt_send = (Button) findViewById(R.id.bt_send);
        mEt_singleImageMessage = (EditText) findViewById(R.id.et_single_image_message);
        mEt_singleImageAppkey = (EditText) findViewById(R.id.et_single_image_appkey);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            mPicturePath = cursor.getString(columnIndex);
            cursor.close();
            ImageView imageView = (ImageView) findViewById(R.id.iv_show_image);
//            mBitmap = BitmapFactory.decodeFile(mPicturePath);

            Bitmap getimage = CreateGroupImageMsgActivity.getimage(mPicturePath);
            imageView.setImageBitmap(getimage);

        }

    }
}
