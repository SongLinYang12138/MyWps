package im.sdk.debug.activity.createmessage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import im.sdk.debug.R;
import im.sdk.debug.activity.imagecontent.ImageContentActivity;

/**
 * Created by ${chenyn} on 16/3/29.
 *
 * @desc :创建消息引导界面
 */
public class CreateMessageActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setContentView(R.layout.activity_create_message);

        Button bt_createSigText = (Button) findViewById(R.id.bt_create_single_text_message);
        Button bt_createGroupTextMessage = (Button) findViewById(R.id.bt_create_group_text_message);
        Button bt_createSingleImageMessage = (Button) findViewById(R.id.bt_create_single_image_message);
        Button bt_createSingleCustomMessage = (Button) findViewById(R.id.bt_create_single_custom_message);
        Button bt_createGroupImageMessage = (Button) findViewById(R.id.bt_create_group_image_message);
        Button bt_createSingleVoiceMessage = (Button) findViewById(R.id.bt_create_single_voice_message);
        Button bt_createGroupCustomMessage = (Button) findViewById(R.id.bt_create_group_custom_message);
        Button bt_createGroupVoiceMessage = (Button) findViewById(R.id.bt_create_group_voice_message);
        Button bt_createSendFileMessage = (Button) findViewById(R.id.bt_create_send_file_message);
        Button bt_sendLocation = (Button) findViewById(R.id.bt_send_location);
        Button bt_sendTransCommand = (Button) findViewById(R.id.bt_send_trans_command);

        bt_createSigText.setOnClickListener(this);
        bt_createGroupTextMessage.setOnClickListener(this);
        bt_createSingleImageMessage.setOnClickListener(this);
        bt_createSingleCustomMessage.setOnClickListener(this);
        bt_createGroupImageMessage.setOnClickListener(this);
        bt_createSingleVoiceMessage.setOnClickListener(this);
        bt_createGroupCustomMessage.setOnClickListener(this);
        bt_createGroupVoiceMessage.setOnClickListener(this);
        bt_createSendFileMessage.setOnClickListener(this);
        bt_sendLocation.setOnClickListener(this);
        bt_sendTransCommand.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.bt_create_single_text_message://创建单聊文本信息
                intent.setClass(getApplicationContext(), CreateSigTextMessageActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_create_group_text_message://创建群聊文本信息
                intent.setClass(getApplicationContext(), CreateGroupTextMsgActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_create_single_image_message://创建单聊图片信息
                intent.setClass(getApplicationContext(), ImageContentActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_create_single_custom_message://创建单聊自定义信息
                intent.setClass(getApplicationContext(), CreateSigCustomMsgActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_create_group_image_message://创建群聊图片信息
                intent.setClass(getApplicationContext(), CreateGroupImageMsgActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_create_single_voice_message://创建单聊语音信息
                intent.setClass(getApplicationContext(), CreateSigVoiceMsgActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_create_group_voice_message://创建群聊语音信息
                intent.setClass(getApplicationContext(), CreateGroupVoiceMsgActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_create_group_custom_message://创建群聊自定义信息
                intent.setClass(getApplicationContext(), CreateGroupCustomMsgActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_create_send_file_message://创建发送文件消息
                intent.setClass(getApplicationContext(), CreateSendFileActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_send_location://创建发送位置消息
                intent.setClass(getApplicationContext(), CreateLocationMessageActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_send_trans_command://消息透传
                intent.setClass(getApplicationContext(), CreateTransCommandActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
