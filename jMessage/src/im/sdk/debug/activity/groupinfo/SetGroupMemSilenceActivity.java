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

import cn.jiguang.api.JCoreInterface;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetGroupInfoCallback;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.api.BasicCallback;
import im.sdk.debug.R;

public class SetGroupMemSilenceActivity extends Activity {
    private String TAG = SetGroupMemSilenceActivity.class.getSimpleName();
    private EditText mEt_groupID;
    private EditText mEt_memberName;
    private EditText mEt_memberAppKey;
    private Button mBt_keepSilence;
    private Button mBt_keepSilenceCancel;
    private ProgressDialog mProgressDialog = null;
    private long mGroupID;
    private String mNames;
    private String mAppKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initData() {
        mBt_keepSilence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGroupMemSilence(true);
            }
        });
        mBt_keepSilenceCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGroupMemSilence(false);
            }
        });
    }

    private void initView() {
        setContentView(R.layout.activity_set_group_mem_silence);
        mEt_groupID = (EditText) findViewById(R.id.et_group_id);
        mEt_memberName = (EditText) findViewById(R.id.et_member_name);
        mEt_memberAppKey = (EditText) findViewById(R.id.et_member_appkey);
        mBt_keepSilence = (Button) findViewById(R.id.bt_keep_silence);
        mBt_keepSilenceCancel = (Button) findViewById(R.id.bt_keep_silence_cancel);
    }

    private boolean preparedData() {
        mProgressDialog = ProgressDialog.show(SetGroupMemSilenceActivity.this, "提示：", "正在加载中。。。");
        if (TextUtils.isEmpty(mEt_groupID.getText()) || TextUtils.isEmpty(mEt_memberName.getText())) {
            mProgressDialog.dismiss();
            return false;
        }
        mGroupID = Long.parseLong(mEt_groupID.getText().toString());
        mNames = mEt_memberName.getText().toString();
        mAppKey = mEt_memberAppKey.getText().toString();
        return true;
    }

    private void setGroupMemSilence(final boolean keepSilence) {
        if (preparedData()) {
            JMessageClient.getGroupInfo(mGroupID, new GetGroupInfoCallback() {
                @Override
                public void gotResult(int responseCode, String responseMessage, GroupInfo groupInfo) {
                    if (0 == responseCode) {
                        groupInfo.setGroupMemSilence(mNames, mAppKey, keepSilence, new BasicCallback() {
                            @Override
                            public void gotResult(int i, String s) {
                                mProgressDialog.dismiss();
                                if (0 == i) {
                                    Toast.makeText(getApplicationContext(), keepSilence ? "设置禁言成功" : "取消禁言成功", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), keepSilence ? "设置禁言失败" : "取消禁言失败", Toast.LENGTH_SHORT).show();
                                    Log.i(TAG, "GroupInfo.setGroupMemSilence " + ", responseCode = " + i + " ; Desc = " + s);
                                }
                            }
                        });
                    } else {
                        mProgressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), keepSilence ? "设置禁言失败" : "取消禁言失败", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "getGroupInfo failed " + ", responseCode = " + responseCode + " :Desc = " + responseMessage);
                    }
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "请输入相关参数", Toast.LENGTH_SHORT).show();
        }
    }
}
