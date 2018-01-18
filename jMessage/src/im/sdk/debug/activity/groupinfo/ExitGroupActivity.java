package im.sdk.debug.activity.groupinfo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;
import im.sdk.debug.R;

/**
 * Created by ${chenyn} on 16/3/31.
 *
 * @desc :退出群组
 */
public class ExitGroupActivity extends Activity {

    private EditText mEt_exitGroupId;
    private Button   mBt_exitGroupId;
    private ProgressDialog mProgressDialog = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initData();
    }

    private void initData() {
        mBt_exitGroupId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressDialog = ProgressDialog.show(ExitGroupActivity.this, "提示：", "正在加载中。。。");
                String id = mEt_exitGroupId.getText().toString();
                if (TextUtils.isEmpty(id)) {
                    mProgressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "请输入id", Toast.LENGTH_SHORT).show();
                    return;
                }
                long groupId = Long.parseLong(id);
                JMessageClient.exitGroup(groupId, new BasicCallback() {
                    @Override
                    public void gotResult(int i, String s) {
                        if (i == 0) {
                            mProgressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "退出成功", Toast.LENGTH_SHORT).show();
                        } else {
                            mProgressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "退出失败", Toast.LENGTH_SHORT).show();
                            Log.i("ExitGroupActivity", "JMessageClient.exitGroup " + ", responseCode = " + i + " ; Desc = " + s);
                        }
                    }
                });
            }
        });
    }

    private void initView() {
        setContentView(R.layout.activity_exit_group);
        mEt_exitGroupId = (EditText) findViewById(R.id.et_exit_group_id);
        mBt_exitGroupId = (Button) findViewById(R.id.bt_exit_group_id);
    }
}
