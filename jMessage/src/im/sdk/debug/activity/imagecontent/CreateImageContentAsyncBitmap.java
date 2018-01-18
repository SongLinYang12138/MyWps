package im.sdk.debug.activity.imagecontent;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.ImageContent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import im.sdk.debug.R;
import im.sdk.debug.utils.FileUtils;

/**
 * Created by ${chenyn} on 16/4/9.
 *
 * @desc :异步创建ImageContent，传入bitmap
 */
public class CreateImageContentAsyncBitmap extends Activity {
    private Conversation mConversation;
    private EditText mEt_userName;
    private Button mBt_getImage;
    private ProgressDialog mProgressDialog;
    private Button mBt_send;
    private TextView mTv_showImagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initData() {
/**#################    加载assets文件夹中的图片    #################*/
        mBt_getImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTv_showImagePath.append("path = " + FileUtils.writeImageInAssetsToSDCard(getApplicationContext()));
                Toast.makeText(getApplicationContext(), "保存成功", Toast.LENGTH_SHORT).show();
            }
        });
/**#################    发送图片信息    #################*/
        mBt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressDialog = ProgressDialog.show(CreateImageContentAsyncBitmap.this, "提示：", "努力加载中。。。");
                mProgressDialog.setCanceledOnTouchOutside(true);
                final String name = mEt_userName.getText().toString();
                File file = new File(Environment.getExternalStorageDirectory().getPath() + "/Image/test.png");
                if (TextUtils.isEmpty(name) || !file.exists()) {
                    mProgressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "请先获取相关参数", Toast.LENGTH_SHORT).show();
                    return;
                }
                Bitmap bitmap = BitmapFactory.decodeFile(String.valueOf(file));
                ImageContent.createImageContentAsync(bitmap, new ImageContent.CreateImageContentCallback() {
                    @Override
                    public void gotResult(int i, String s, ImageContent imageContent) {
                        if (i == 0) {
                            mProgressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "发送成功", Toast.LENGTH_SHORT).show();
                            mConversation = JMessageClient.getSingleConversation(name);
                            if (null == mConversation) {
                                mConversation = Conversation.createSingleConversation(name);
                            }
                            Message message = mConversation.createSendMessage(imageContent);
                            JMessageClient.sendMessage(message);
                            /**=================     获取图片信息    =================*/
                            int height = imageContent.getHeight();
                            int width = imageContent.getWidth();
                            String localThumbnailPath = imageContent.getLocalThumbnailPath();
                            mTv_showImagePath.setText("");
                            mTv_showImagePath.append("height = " + height + "\n");
                            mTv_showImagePath.append("width = " + width + "\n");
                            mTv_showImagePath.append("localThumbnailPath = " + localThumbnailPath + "\n");
                        } else {
                            mProgressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "发送失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void initView() {
        setContentView(R.layout.activity_create_image_content_bitmap);
        mEt_userName = (EditText) findViewById(R.id.et_user_name);
        mBt_getImage = (Button) findViewById(R.id.bt_get_image);
        mBt_send = (Button) findViewById(R.id.bt_send);
        mTv_showImagePath = (TextView) findViewById(R.id.tv_show_image_path);
    }
}