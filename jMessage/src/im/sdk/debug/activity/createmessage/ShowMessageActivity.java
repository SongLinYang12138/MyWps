package im.sdk.debug.activity.createmessage;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.DownloadCompletionCallback;
import cn.jpush.im.android.api.content.FileContent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import im.sdk.debug.R;
import im.sdk.debug.activity.TypeActivity;

/**
 * Created by ${chenyn} on 16/4/6.
 *
 * @desc :
 */
public class ShowMessageActivity extends Activity {
    private final String TAG = ShowMessageActivity.class.getSimpleName();
    private ImageView mIv_showImage;
    private TextView mTv_showText;
    private Button mPlay;
    private Button mDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();

        initData();
    }

    private void initData() {

        Intent intent = getIntent();

        if (intent.getFlags() == 1) {
            mPlay.setVisibility(View.GONE);
            mDownload.setVisibility(View.GONE);
            String stringExtra = intent.getStringExtra(CreateSigTextMessageActivity.TEXT_MESSAGE);
            if (stringExtra != null) {
                mTv_showText.append(stringExtra);
            }
        } else if (intent.getFlags() == 2) {
            mPlay.setVisibility(View.GONE);
            mDownload.setVisibility(View.GONE);
            String imagePath = intent.getStringExtra("image_path");
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            Log.d(TAG, "bitmap length = " + bitmap.getByteCount());
            mIv_showImage.setImageBitmap(bitmap);
            ArrayList<String> intentStringExtra = intent.getStringArrayListExtra(TypeActivity.SET_DOWNLOAD_PROGRESS);
            String intentStringExtraExists = intent.getStringExtra(TypeActivity.IS_DOWNLOAD_PROGRESS_EXISTS);
            if (intentStringExtra != null) {
                mTv_showText.append("setOnContentDownloadProgressCallback = " + intentStringExtra.toString() + "\n");
            }
            mTv_showText.append("isContentDownloadProgressCallbackExists = " + intentStringExtraExists);
        } else if (intent.getFlags() == 3) {
            mDownload.setVisibility(View.GONE);
            String voiceExtra = intent.getStringExtra("voice");
            if (voiceExtra != null) {
                mTv_showText.append(voiceExtra);
            }
        } else if (intent.getFlags() == 4) {
            mPlay.setVisibility(View.GONE);
            mDownload.setVisibility(View.GONE);
            String address = intent.getStringExtra("address");
            String latitude = intent.getStringExtra("latitude");
            String scale = intent.getStringExtra("scale");
            String longitude = intent.getStringExtra("longitude");

            mTv_showText.append("address = " + address + "\nlatitude = " + latitude + "\nscale = " + scale + "\nlongitude = " + longitude);
        }


        mPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer player = new MediaPlayer();
                Intent intent = getIntent();
                try {
                    String path = intent.getStringExtra("voice");
                    if (TextUtils.isEmpty(path)) {
                        Toast.makeText(getApplicationContext(), "先发送单聊语音消息", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    player.setDataSource(path);
                    player.prepare();
                    player.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        mDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTv_showText.setText("");
                final ProgressDialog dialog = ProgressDialog.show(ShowMessageActivity.this, "提示", "正在下载中...");
                dialog.setCanceledOnTouchOutside(true);
                Intent intent = getIntent();
                String user = intent.getStringExtra("user");
                String appkey = intent.getStringExtra("appkey");
                int msgid = intent.getIntExtra("msgid", 0);

                Conversation conversation = JMessageClient.getSingleConversation(user, appkey);
                if (conversation == null) {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "先发送文件消息", Toast.LENGTH_SHORT).show();
                    return;
                }

                Message message = conversation.getMessage(msgid);
                if (message != null) {
                    FileContent content = (FileContent) message.getContent();
                    content.downloadFile(message, new DownloadCompletionCallback() {
                        @Override
                        public void onComplete(int i, String s, File file) {
                            if (i == 0) {
                                dialog.dismiss();
                                mTv_showText.append(file.getPath());
                                Toast.makeText(getApplicationContext(), "下载成功", Toast.LENGTH_SHORT).show();
                            } else {
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(), "下载失败", Toast.LENGTH_SHORT).show();
                                Log.i("ShowMessageActivity", "FileContent.downloadFile" + ", responseCode = " + i + " ; Desc = " + s);
                            }
                        }
                    });
                } else {
                    Toast.makeText(ShowMessageActivity.this, "未能获取到message", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initView() {
        setContentView(R.layout.activity_show_image);

        mIv_showImage = (ImageView) findViewById(R.id.iv_show_image);
        mTv_showText = (TextView) findViewById(R.id.tv_show_text);
        mPlay = (Button) findViewById(R.id.play);
        mDownload = (Button) findViewById(R.id.download);

        Intent intent = getIntent();
        if (intent.getStringExtra("voice") == null) {
            mPlay.setVisibility(View.GONE);
        }
        if (intent.getFlags() == 10 && intent.getStringExtra("isGroup").equals("group")) {
            mDownload.setVisibility(View.GONE);
            mTv_showText.setText("收到消息处理逻辑参考单聊文件消息处理方式");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
