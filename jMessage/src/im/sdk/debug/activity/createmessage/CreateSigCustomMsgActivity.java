package im.sdk.debug.activity.createmessage;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.api.BasicCallback;
import im.sdk.debug.R;

/**
 * Created by ${chenyn} on 16/4/1.
 *
 * @desc :创建单聊自定义消息
 */
public class CreateSigCustomMsgActivity extends Activity {
    private static final String TAG = CreateSigCustomMsgActivity.class.getSimpleName();

    private EditText mEt_username;
    private EditText mEt_key;
    private EditText mEt_value;
    private Button mBt_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initData() {
        mBt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mEt_username.getText().toString();
                String key = mEt_key.getText().toString();
                String value = mEt_value.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(getApplicationContext(), "请输入userName", Toast.LENGTH_SHORT).show();
                    return;
                }

                Map<String, String> valuesMap = new HashMap<>();
                valuesMap.put(key, value);

                Message customMessage = JMessageClient.createSingleCustomMessage(name, valuesMap);
                customMessage.setOnSendCompleteCallback(new BasicCallback() {
                    @Override
                    public void gotResult(int i, String s) {
                        if (i == 0) {
                            Toast.makeText(getApplicationContext(), "发送成功", Toast.LENGTH_SHORT).show();
                            Log.i(TAG, "JMessageClient.createSingleCustomMessage" + ", responseCode = " + i + " ; LoginDesc = " + s);
                        } else {
                            Toast.makeText(getApplicationContext(), "发送失败", Toast.LENGTH_SHORT).show();
                            Log.i(TAG, "JMessageClient.createSingleCustomMessag" + ", responseCode = " + i + " ; LoginDesc = " + s);
                        }
                    }
                });
                JMessageClient.sendMessage(customMessage);
            }
        });
    }

    private void initView() {
        setContentView(R.layout.activity_create_single_custom_message);
        mEt_username = (EditText) findViewById(R.id.et_username);
        mEt_key = (EditText) findViewById(R.id.et_key);
        mEt_value = (EditText) findViewById(R.id.et_value);
        mBt_send = (Button) findViewById(R.id.bt_send);
    }
}
