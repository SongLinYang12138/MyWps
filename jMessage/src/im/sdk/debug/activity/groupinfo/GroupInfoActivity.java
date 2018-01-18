package im.sdk.debug.activity.groupinfo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetGroupIDListCallback;
import im.sdk.debug.R;

/**
 * Created by ${chenyn} on 16/3/29.
 *
 * @desc :群组相关
 */
public class GroupInfoActivity extends Activity {

    private Button   mBt_createGroup;
    private Button   mBt_getGroupInfo;
    private Button   mBt_applyJoinGroup;
    private Button   mBt_addGroupMembers;
    private Button   mBt_setGroupMemSilence;
    private Button   mBt_updateGroupNameAndDesc;
    private Button   mBt_exitGroup;
    private TextView mTv_getList;
    private Button   mBt_getGroupIDList;
    private ProgressDialog mProgressDialog = null;
    private Button mBt_getGroupMembers;
    private Button mBt_blockedGroupMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initData() {
        mBt_createGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateGroupActivity.class);
                startActivity(intent);
            }
        });

        mBt_applyJoinGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ApplyJoinGroupActivity.class);
                startActivity(intent);
            }
        });

        mBt_getGroupInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GetGroupInfoActivity.class);
                startActivity(intent);
            }
        });

        mBt_addGroupMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddRemoveGroupMemberActivity.class);
                startActivity(intent);
            }
        });

        mBt_setGroupMemSilence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SetGroupMemSilenceActivity.class);
                startActivity(intent);
            }
        });

        mBt_updateGroupNameAndDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UpdateGroupInfoActivity.class);
                startActivity(intent);
            }
        });

        mBt_exitGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ExitGroupActivity.class);
                startActivity(intent);
            }
        });

        mBt_getGroupIDList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressDialog = ProgressDialog.show(GroupInfoActivity.this, "提示：", "正在加载中。。。");
                mProgressDialog.setCanceledOnTouchOutside(true);

                JMessageClient.getGroupIDList(new GetGroupIDListCallback() {
                    @Override
                    public void gotResult(int i, String s, List<Long> list) {
                        if (i == 0) {
                            mProgressDialog.dismiss();
                            mTv_getList.setText("");
                            mTv_getList.append(list.toString());
                        } else {
                            mProgressDialog.dismiss();
                            Log.i("GroupInfoActivity", "JMessageClient.getGroupIDList " + ", responseCode = " + i + " ; Desc = " + s);
                            Toast.makeText(getApplicationContext(), "获取失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        mBt_getGroupMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GetLocalGroupMembersActivity.class);
                startActivity(intent);
            }
        });

        mBt_blockedGroupMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BlockedGroupMsgActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        setContentView(R.layout.activity_group_info);
        mBt_createGroup = (Button) findViewById(R.id.bt_create_group);
        mBt_applyJoinGroup = (Button) findViewById(R.id.bt_apply_join_group);
        mBt_getGroupInfo = (Button) findViewById(R.id.bt_get_group_info);
        mBt_addGroupMembers = (Button) findViewById(R.id.bt_add_group_members);
        mBt_setGroupMemSilence = (Button) findViewById(R.id.bt_group_mem_silence);
        mBt_updateGroupNameAndDesc = (Button) findViewById(R.id.bt_update_group_name_and_desc);
        mBt_exitGroup = (Button) findViewById(R.id.bt_exit_group);
        mTv_getList = (TextView) findViewById(R.id.tv_get_list);
        mBt_getGroupIDList = (Button) findViewById(R.id.bt_get_group_id_list);
        mBt_getGroupMembers = (Button) findViewById(R.id.bt_get_group_members);
        mBt_blockedGroupMsg = (Button) findViewById(R.id.bt_blockedGroupMsg);
    }
}
