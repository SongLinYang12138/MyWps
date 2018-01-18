package im.sdk.debug.activity.notify;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import im.sdk.debug.R;
import im.sdk.debug.activity.createmessage.CreateGroupTextMsgActivity;

/**
 * Created by ${chenyn} on 16/4/8.
 *
 * @desc :群操作时展示通知信息界面
 */
public class ShowGroupNotificationActivity extends Activity {

    private TextView mTv_show_group_notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        String stringExtra = intent.getStringExtra(CreateGroupTextMsgActivity.GROUP_NOTIFICATION);
        String intentStringExtra = intent.getStringExtra(CreateGroupTextMsgActivity.GROUP_NOTIFICATION_LIST);
        mTv_show_group_notification.append(stringExtra + "\n");
        mTv_show_group_notification.append("\n");
        if(null != intentStringExtra) {
            mTv_show_group_notification.append("通知事件涉及到的userName列表：" + intentStringExtra);
        }
    }

    private void initView() {
        setContentView(R.layout.activity_show_group_notification);

        mTv_show_group_notification = (TextView) findViewById(R.id.tv_show_group_notification);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
