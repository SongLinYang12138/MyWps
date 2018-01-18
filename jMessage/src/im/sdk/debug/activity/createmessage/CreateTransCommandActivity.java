package im.sdk.debug.activity.createmessage;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;
import im.sdk.debug.R;
import im.sdk.debug.activity.conversation.ForwardMessageActivity;

public class CreateTransCommandActivity extends Activity {
    private static final String TAG = CreateTransCommandActivity.class.getSimpleName();

    private EditText mEt_target_username;
    private EditText mEt_target_appKey;
    private EditText mEt_target_gid;
    private EditText mEt_cmd;
    private Button mBt_send_trans_command;
    private BasicCallback basicCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initData();
    }

    private void initData() {
        basicCallback = new BasicCallback() {
            @Override
            public void gotResult(int responseCode, String responseMessage) {
                if (0 == responseCode) {
                    Toast.makeText(CreateTransCommandActivity.this, "消息透传成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CreateTransCommandActivity.this, "消息透传失败, code = " + responseCode +
                            "\nmsg = " + responseMessage, Toast.LENGTH_SHORT).show();
                }
            }
        };
        mBt_send_trans_command.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String target_username = mEt_target_username.getText().toString();
                String target_appKey = mEt_target_appKey.getText().toString();
                String target_gid = mEt_target_gid.getText().toString();
                String cmd = mEt_cmd.getText().toString();

                if (!TextUtils.isEmpty(target_username) && !TextUtils.isEmpty(target_gid)) {
                    Toast.makeText(CreateTransCommandActivity.this, "请确定消息透传类型", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!TextUtils.isEmpty(target_username)) {
                    JMessageClient.sendSingleTransCommand(target_username, target_appKey, cmd, basicCallback);
                } else if (!TextUtils.isEmpty(target_gid)) {
                    long gid = Long.parseLong(target_gid);
                    JMessageClient.sendGroupTransCommand(gid, cmd, basicCallback);
                } else {
                    Toast.makeText(CreateTransCommandActivity.this, "请填写会话相关属性username/gid", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initView() {
        setContentView(R.layout.activity_create_trans_command);
        mEt_target_username = (EditText) findViewById(R.id.et_target_username);
        mEt_target_appKey = (EditText) findViewById(R.id.et_target_appKey);
        mEt_target_gid = (EditText) findViewById(R.id.et_target_gid);
        mEt_cmd = (EditText) findViewById(R.id.et_cmd);
        mBt_send_trans_command = (Button) findViewById(R.id.bt_send_trans_command);
    }
}
