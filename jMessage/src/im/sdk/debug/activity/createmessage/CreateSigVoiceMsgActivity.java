package im.sdk.debug.activity.createmessage;


import android.app.Activity;
import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.ProgressUpdateCallback;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.api.BasicCallback;
import im.sdk.debug.R;

/**
 * Created by ${chenyn} on 16/3/29.
 *
 * @desc :创建单聊语音信息,本示例是内置了一段音频文件
 */
public class CreateSigVoiceMsgActivity extends Activity {
    private File           mFileMp3;
    private Button         mBt_getVoice;
    private Button         mBt_send;
    private EditText       mEt_username;
    private Conversation   mConversation;
    private TextView       mTv_showVoiceInfo;
    private TextView       mTv_progress;
    private ProgressDialog mProgressDialog;
    private EditText       mEt_appkey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initData() {
        mBt_getVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(Environment.getExternalStorageDirectory().getPath() + "/voice/");
                if (!file.exists()) {
                    file.mkdir();
                }
                mFileMp3 = new File(Environment.getExternalStorageDirectory().getPath() + "/voice/test.mp3");
                InputStream in = null;
                try {
                    in = getApplicationContext().getAssets().open("test.mp3");

                    OutputStream out = new FileOutputStream(mFileMp3);
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                    in.close();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(), "voice文件加载成功", Toast.LENGTH_SHORT).show();
                mTv_showVoiceInfo.append("voice文件已加载到 ：" + mFileMp3 + "\n");
            }
        });

        mBt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTv_progress.setText("");
                mTv_showVoiceInfo.setText("");
                mProgressDialog = ProgressDialog.show(CreateSigVoiceMsgActivity.this, "提示:", "正在加载中。。。");
                mProgressDialog.setCanceledOnTouchOutside(true);
                String name = mEt_username.getText().toString();
                String appkey = mEt_appkey.getText().toString();
                File fileMp3 = new File(Environment.getExternalStorageDirectory().getPath() + "/voice/test.mp3");
                if (!fileMp3.exists()) {
                    mProgressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "先获取内置音频文件", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(name)) {
                    mProgressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "请输入userName", Toast.LENGTH_SHORT).show();
                    return;
                }
                mConversation = JMessageClient.getSingleConversation(name);
                if (null == mConversation) {
                    mConversation = Conversation.createSingleConversation(name);
                }

                MediaPlayer player = new MediaPlayer();
                try {
                    player.setDataSource(String.valueOf(fileMp3));
                    player.prepare();
                    int duration = player.getDuration();
                    Message voiceMessage = JMessageClient.createSingleVoiceMessage(name, appkey, fileMp3, duration);
                    voiceMessage.setOnSendCompleteCallback(new BasicCallback() {
                        @Override
                        public void gotResult(int i, String s) {
                            if (i == 0) {
                                mProgressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "发送成功", Toast.LENGTH_SHORT).show();
                            } else {
                                mProgressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "发送失败", Toast.LENGTH_SHORT).show();
                                Log.i("CreateSigVoiceMsgActivity", "JMessageClient.createSingleVoiceMessage " + ", responseCode = " + i + " ; LoginDesc = " + s);
                            }
                        }
                    });
/**=================     voice上传进度    =================*/
                    voiceMessage.setOnContentUploadProgressCallback(new ProgressUpdateCallback() {
                        @Override
                        public void onProgressUpdate(double v) {
                            String progressStr = (int) (v * 100) + "%";
                            mTv_progress.append("上传进度：" + progressStr + "\n");
                        }
                    });
                    JMessageClient.sendMessage(voiceMessage);
                    boolean callbackExists = voiceMessage.isContentUploadProgressCallbackExists();
                    boolean exists = voiceMessage.isSendCompleteCallbackExists();
                    mTv_showVoiceInfo.append("isSendCompleteCallbackExists = " + exists + "\n" +
                            "getServerMessageId = " + voiceMessage.getServerMessageId() + "\n" + "callbackExists = " + callbackExists);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initView() {
        setContentView(R.layout.activity_create_single_voice_message);
        mBt_getVoice = (Button) findViewById(R.id.bt_get_voice);
        mBt_send = (Button) findViewById(R.id.bt_send);
        mEt_username = (EditText) findViewById(R.id.et_username);
        mTv_showVoiceInfo = (TextView) findViewById(R.id.tv_show_voice_info);
        mTv_progress = (TextView) findViewById(R.id.tv_progress);
        mEt_appkey = (EditText) findViewById(R.id.et_appkey);
    }



}

