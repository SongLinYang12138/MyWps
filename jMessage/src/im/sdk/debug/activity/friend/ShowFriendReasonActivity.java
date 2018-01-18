package im.sdk.debug.activity.friend;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.jpush.im.android.api.ContactManager;
import cn.jpush.im.api.BasicCallback;
import im.sdk.debug.R;

/**
 * Created by ${chenyn} on 16/4/17.
 *
 * @desc :同意或拒绝好友申请
 */
public class ShowFriendReasonActivity extends Activity {

    private TextView mTv_showAddFriendInfo;
    private Button   mAccept_invitation;
    private Button mDeclined_invitation;
    private EditText mEt_reason;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initData();
    }

    private void initData() {
        final Intent intent = getIntent();
        if (intent.getFlags() == 1) {
            mTv_showAddFriendInfo.append(intent.getStringExtra("invite_received"));
        }else if (intent.getFlags() == 2) {
            mEt_reason.setVisibility(View.GONE);
            mAccept_invitation.setVisibility(View.GONE);
            mDeclined_invitation.setVisibility(View.GONE);
            mTv_showAddFriendInfo.append(intent.getStringExtra("invite_accepted"));
        }else if (intent.getFlags() == 3) {
            mEt_reason.setVisibility(View.GONE);
            mAccept_invitation.setVisibility(View.GONE);
            mDeclined_invitation.setVisibility(View.GONE);
            mTv_showAddFriendInfo.append(intent.getStringExtra("invite_declined"));
        }else if (intent.getFlags() == 4) {
            mEt_reason.setVisibility(View.GONE);
            mAccept_invitation.setVisibility(View.GONE);
            mDeclined_invitation.setVisibility(View.GONE);
            mTv_showAddFriendInfo.append(intent.getStringExtra("contact_deleted"));
        }
        //同意好友添加邀请
        mAccept_invitation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactManager.acceptInvitation(intent.getStringExtra("username"), intent.getStringExtra("appkey"), new BasicCallback() {
                    @Override
                    public void gotResult(int i, String s) {
                        if (i == 0) {
                            Toast.makeText(getApplicationContext(), "添加成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "添加失败", Toast.LENGTH_SHORT).show();
                            Log.i("ShowFriendReasonActivity", "ContactManager.acceptInvitation" + ", responseCode = " + i + " ; LoginDesc = " + s);
                        }
                    }
                });
            }
        });

        //拒绝好友添加邀请
        mDeclined_invitation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reason = mEt_reason.getText().toString();
                ContactManager.declineInvitation(intent.getStringExtra("username"), intent.getStringExtra("appkey"), reason, new BasicCallback() {
                    @Override
                    public void gotResult(int i, String s) {
                        if (i == 0) {
                            Toast.makeText(getApplicationContext(), "拒绝成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "拒绝失败", Toast.LENGTH_SHORT).show();
                            Log.i("ShowFriendReasonActivity", "ContactManager.declineInvitation" + ", responseCode = " + i + " ; LoginDesc = " + s);
                        }
                    }
                });
            }
        });
    }

    private void initView() {
        setContentView(R.layout.activity_show_friend_reason);
        mTv_showAddFriendInfo = (TextView) findViewById(R.id.tv_show_add_friend_info);
        mAccept_invitation = (Button) findViewById(R.id.accept_invitation);
        mDeclined_invitation = (Button) findViewById(R.id.declined_invitation);
        mEt_reason = (EditText) findViewById(R.id.et_reason);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
