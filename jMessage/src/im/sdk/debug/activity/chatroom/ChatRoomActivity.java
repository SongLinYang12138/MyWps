package im.sdk.debug.activity.chatroom;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import cn.jpush.im.android.api.ChatRoomManager;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.RequestCallback;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.enums.ContentType;
import cn.jpush.im.android.api.event.ChatRoomMessageEvent;
import cn.jpush.im.android.api.model.ChatRoomInfo;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.api.BasicCallback;
import im.sdk.debug.R;

/**
 * Created by hxhg on 2017/10/18.
 */

public class ChatRoomActivity extends Activity implements View.OnClickListener {

    private EditText etRoomID;

    private EditText etGetStart;

    private EditText etGetCount;

    private EditText etInputText;

    private TextView tvDisplay;

    private ScrollView svScorll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JMessageClient.registerEventReceiver(this);

        setContentView(R.layout.activity_chatroom);
        etRoomID = (EditText) findViewById(R.id.et_roomID);
        etGetStart = (EditText) findViewById(R.id.et_chatroom_get_start);
        etGetCount = (EditText) findViewById(R.id.et_chatroom_get_count);
        etInputText = (EditText) findViewById(R.id.et_chatroom_inputtext);
        tvDisplay = (TextView) findViewById(R.id.tv_chatroom_display);
        svScorll = (ScrollView) findViewById(R.id.sv_chatroom_scroll);
        findViewById(R.id.bt_get_room_by_id).setOnClickListener(this);
        findViewById(R.id.bt_get_room_by_appkey).setOnClickListener(this);
        findViewById(R.id.bt_get_room_by_user).setOnClickListener(this);
        findViewById(R.id.bt_enter_chatroom).setOnClickListener(this);
        findViewById(R.id.bt_leave_chatroom).setOnClickListener(this);
        findViewById(R.id.bt_chatroom_sendtext).setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JMessageClient.unRegisterEventReceiver(this);
    }

    @Override
    public void onClick(View v) {
        long roomID = 0;
        try {
            roomID = Long.parseLong(etRoomID.getText().toString());
        } catch (NumberFormatException e) {
            //ignore
        }
        switch (v.getId()) {
            case R.id.bt_get_room_by_appkey:
                int start = 0;
                int count = 0;
                try {
                    start = Integer.parseInt(etGetStart.getText().toString());
                    count = Integer.parseInt(etGetCount.getText().toString());
                } catch (NumberFormatException e) {
                    //ignore
                }
                ChatRoomManager.getChatRoomListByApp(start, count, new RequestCallback<List<ChatRoomInfo>>() {
                    @Override
                    public void gotResult(int responseCode, String responseMessage, List<ChatRoomInfo> chatRoomInfos) {
                        String result = null != chatRoomInfos ? chatRoomInfos.toString() : null;
                        postTextToDisplay("getChatRoomListByApp", responseCode, responseMessage, result);
                    }
                });
                break;
            case R.id.bt_get_room_by_user:
                ChatRoomManager.getChatRoomListByUser(new RequestCallback<List<ChatRoomInfo>>() {
                    @Override
                    public void gotResult(int responseCode, String responseMessage, List<ChatRoomInfo> chatRoomInfos) {
                        String result = null != chatRoomInfos ? chatRoomInfos.toString() : null;
                        postTextToDisplay("getChatRoomListByUser", responseCode, responseMessage, result);
                    }
                });
                break;
            case R.id.bt_get_room_by_id:
                ChatRoomManager.getChatRoomInfos(Collections.singleton(roomID), new RequestCallback<List<ChatRoomInfo>>() {
                    @Override
                    public void gotResult(int responseCode, String responseMessage, List<ChatRoomInfo> chatRoomInfos) {
                        String result = null != chatRoomInfos ? chatRoomInfos.toString() : null;
                        postTextToDisplay("getChatRoomInfos", responseCode, responseMessage, result);
                    }
                });
                break;
            case R.id.bt_enter_chatroom:
                ChatRoomManager.enterChatRoom(roomID, new RequestCallback<Conversation>() {
                    @Override
                    public void gotResult(int responseCode, String responseMessage, Conversation conversation) {
                        String result = null != conversation ? conversation.toString() : null;
                        postTextToDisplay("enterChatRoom", responseCode, responseMessage, result);
                    }
                });
                break;
            case R.id.bt_leave_chatroom:
                ChatRoomManager.leaveChatRoom(roomID, new BasicCallback() {
                    @Override
                    public void gotResult(int responseCode, String responseMessage) {
                        postTextToDisplay("leaveChatRoom", responseCode, responseMessage, null);
                    }
                });
                break;
            case R.id.bt_chatroom_sendtext:
                if (0 == roomID) {
                    Toast.makeText(getApplicationContext(), "请输入聊天室roomID", Toast.LENGTH_SHORT).show();
                    return;
                }
                Conversation conv = JMessageClient.getChatRoomConversation(roomID);
                if (null == conv) {
                    conv = Conversation.createChatRoomConversation(roomID);
                }
                String text = etInputText.getText().toString();
                final Message msg = conv.createSendTextMessage(text);//实际聊天室可以支持所有类型的消息发送，demo为了简便，仅仅实现了文本类型的消息发送
                msg.setOnSendCompleteCallback(new BasicCallback() {
                    @Override
                    public void gotResult(int responseCode, String responseMessage) {
                        if (0 == responseCode) {
                            postMessageToDisplay("MessageSent", responseCode, responseMessage, msg);
                        } else {
                            postTextToDisplay("MessageSent", responseCode, responseMessage, "消息发送失败");
                        }
                    }
                });
                JMessageClient.sendMessage(msg);
                break;
        }
    }

    private void postTextToDisplay(String tag, int statusCode, String responseMsg, String text) {
        tvDisplay.append("-------------------\n");
        tvDisplay.append("[" + tag + "]");
        tvDisplay.append(" statusCode = " + statusCode);
        tvDisplay.append(" responseMsg = " + responseMsg + "\n");
        if (null != text) {
            tvDisplay.append(text);
        }
        tvDisplay.append("\n-------------------\n");
        tvDisplay.post(new Runnable() {
            @Override
            public void run() {
                svScorll.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    private void postMessageToDisplay(final String tag, final int statusCode, final String responseMsg, final Message msg) {
        if (null == msg) {
            Toast.makeText(getApplicationContext(), "消息对象为null", Toast.LENGTH_SHORT).show();
            return;
        }
        final ChatRoomInfo chatRoomInfo = ((ChatRoomInfo) msg.getTargetInfo());
        final String msgText;
        if (ContentType.text == msg.getContentType()) {
            msgText = "说：\n" + ((TextContent) msg.getContent()).getText();
        } else {
            msgText = "发了一条" + msg.getContentType() + "类型的消息";
        }
        String text = "用户：" + msg.getFromUser().getUserName()
                + "在聊天室" + chatRoomInfo.getName() + "(roomID=" + chatRoomInfo.getRoomID() + ")中"
                + msgText;
        postTextToDisplay(tag, statusCode, responseMsg, text);
    }

    //接收聊天室消息
    public void onEventMainThread(ChatRoomMessageEvent event) {
        Log.d("tag", "ChatRoomMessageEvent received .");
        List<Message> msgs = event.getMessages();
        for (Message msg : msgs) {
            //这个页面仅仅展示聊天室会话的消息
            postMessageToDisplay("MessageReceived", event.getResponseCode(), event.getResponseDesc(), msg);
        }
    }
}
