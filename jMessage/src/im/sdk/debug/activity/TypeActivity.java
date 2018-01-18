package im.sdk.debug.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.DownloadCompletionCallback;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.callback.GetUserInfoListCallback;
import cn.jpush.im.android.api.callback.ProgressUpdateCallback;
import cn.jpush.im.android.api.content.CustomContent;
import cn.jpush.im.android.api.content.EventNotificationContent;
import cn.jpush.im.android.api.content.ImageContent;
import cn.jpush.im.android.api.content.LocationContent;
import cn.jpush.im.android.api.content.MessageContent;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.content.VoiceContent;
import cn.jpush.im.android.api.enums.ConversationType;
import cn.jpush.im.android.api.event.CommandNotificationEvent;
import cn.jpush.im.android.api.event.ContactNotifyEvent;
import cn.jpush.im.android.api.event.ConversationRefreshEvent;
import cn.jpush.im.android.api.event.GroupApprovalEvent;
import cn.jpush.im.android.api.event.GroupApprovalRefuseEvent;
import cn.jpush.im.android.api.event.LoginStateChangeEvent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.event.MessageReceiptStatusChangeEvent;
import cn.jpush.im.android.api.event.MyInfoUpdatedEvent;
import cn.jpush.im.android.api.event.NotificationClickEvent;
import cn.jpush.im.android.api.event.OfflineMessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import im.sdk.debug.R;
import im.sdk.debug.activity.chatroom.ChatRoomActivity;
import im.sdk.debug.activity.conversation.ConversationActivity;
import im.sdk.debug.activity.createmessage.CreateGroupTextMsgActivity;
import im.sdk.debug.activity.createmessage.CreateMessageActivity;
import im.sdk.debug.activity.createmessage.CreateSigTextMessageActivity;
import im.sdk.debug.activity.createmessage.ShowCustomMessageActivity;
import im.sdk.debug.activity.createmessage.ShowDownloadVoiceInfoActivity;
import im.sdk.debug.activity.createmessage.ShowMessageActivity;
import im.sdk.debug.activity.createmessage.ShowTransCommandActivity;
import im.sdk.debug.activity.friend.FriendContactManager;
import im.sdk.debug.activity.friend.ShowFriendReasonActivity;
import im.sdk.debug.activity.groupinfo.GroupInfoActivity;
import im.sdk.debug.activity.groupinfo.ShowGroupApprovalActivity;
import im.sdk.debug.activity.imagecontent.ShowDownloadPathActivity;
import im.sdk.debug.activity.notify.ShowGroupNotificationActivity;
import im.sdk.debug.activity.setting.SettingMainActivity;
import im.sdk.debug.activity.setting.ShowLogoutReasonActivity;
import im.sdk.debug.activity.showinfo.ShowMyInfoUpdateActivity;

/**
 * Created by ${chenyn} on 16/3/23.
 *
 * @desc : 各个接口的的引导界面
 */
public class TypeActivity extends Activity implements View.OnClickListener {
    public static final String TAG = "TypeActivity";
    public static final String CREATE_GROUP_CUSTOM_KEY = "create_group_custom_key";
    public static final String SET_DOWNLOAD_PROGRESS = "set_download_progress";
    public static final String IS_DOWNLOAD_PROGRESS_EXISTS = "is_download_progress_exists";
    public static final String CUSTOM_MESSAGE_STRING = "custom_message_string";
    public static final String CUSTOM_FROM_NAME = "custom_from_name";
    public static final String DOWNLOAD_ORIGIN_IMAGE = "download_origin_image";
    public static final String DOWNLOAD_THUMBNAIL_IMAGE = "download_thumbnail_image";
    public static final String IS_UPLOAD = "is_upload";
    public static final String LOGOUT_REASON = "logout_reason";
    private TextView mTv_showOfflineMsg;
    private TextView tv_refreshEvent;
    private TextView tv_header;
    public static final String DOWNLOAD_INFO = "download_info";
    public static final String INFO_UPDATE = "info_update";
    public static final String TRANS_COMMAND_SENDER = "trans_command_sender";
    public static final String TRANS_COMMAND_TARGET = "trans_command_target";
    public static final String TRANS_COMMAND_TYPE = "trans_command_type";
    public static final String TRANS_COMMAND_CMD = "trans_command_cmd";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JMessageClient.registerEventReceiver(this);
        initView();
    }

    private void initView() {
        setContentView(R.layout.activity_type);
        tv_header = (TextView) findViewById(R.id.tv_header);
        findViewById(R.id.bt_about_setting).setOnClickListener(this);
        findViewById(R.id.bt_create_message).setOnClickListener(this);
        findViewById(R.id.bt_group_info).setOnClickListener(this);
        findViewById(R.id.bt_conversation).setOnClickListener(this);
        findViewById(R.id.bt_friend).setOnClickListener(this);
        findViewById(R.id.bt_chatroom).setOnClickListener(this);

        mTv_showOfflineMsg = (TextView) findViewById(R.id.tv_showOfflineMsg);
        tv_refreshEvent = (TextView) findViewById(R.id.tv_refreshEvent);

    }

    @Override
    protected void onResume() {
        super.onResume();
        tv_header.setText("");
        UserInfo info = JMessageClient.getMyInfo();
        if (null != info) {
            tv_header.append("当前已登录用户：" + info.getUserName() + "\n");
            tv_header.append("用户所属appkey：" + info.getAppKey() + "\n");
        }
        tv_header.append("版本号：" + JMessageClient.getSdkVersionString());
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.bt_about_setting:
                intent.setClass(getApplicationContext(), SettingMainActivity.class);
                startActivityForResult(intent, 0);
                break;
            case R.id.bt_create_message:
                intent.setClass(getApplicationContext(), CreateMessageActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_group_info:
                intent.setClass(getApplicationContext(), GroupInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_conversation:
                intent.setClass(getApplicationContext(), ConversationActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_friend:
                intent.setClass(getApplicationContext(), FriendContactManager.class);
                startActivity(intent);
                break;
            case R.id.bt_chatroom:
                intent.setClass(getApplicationContext(), ChatRoomActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 8) {
            mTv_showOfflineMsg.setText("");
            tv_refreshEvent.setText("");
        }
    }

    public void onEvent(NotificationClickEvent event) {

        Message msg = event.getMessage();

        final Intent notificationIntent = new Intent(getApplicationContext(), ShowMessageActivity.class);
        MessageContent content = msg.getContent();
        switch (msg.getContentType()) {
            case text:
                TextContent textContent = (TextContent) content;
                notificationIntent.setFlags(1);
                notificationIntent.putExtra(CreateSigTextMessageActivity.TEXT_MESSAGE, "消息类型 = " + msg.getContentType() +
                        "\n消息内容 = " + textContent.getText() + "\n附加字段 = " + textContent.getStringExtras() +
                        "\n群消息isAtMe = " + msg.isAtMe() + "\n群消息isAtAll = " + msg.isAtAll());
                startActivity(notificationIntent);
                break;
            case image:
                ImageContent imageContent = (ImageContent) content;
                imageContent.downloadThumbnailImage(msg, new DownloadCompletionCallback() {
                    @Override
                    public void onComplete(int i, String s, File file) {
                        if (i == 0) {
                            notificationIntent.setFlags(2);
                            notificationIntent.putExtra("image_path", file.getAbsolutePath());
                            startActivity(notificationIntent);
                        }
                    }
                });
                final List<String> list = new ArrayList<String>();
                msg.setOnContentDownloadProgressCallback(new ProgressUpdateCallback() {
                    @Override
                    public void onProgressUpdate(double v) {
                        String progressStr = (int) (v * 100) + "%";
                        list.add(progressStr);
                        notificationIntent.putStringArrayListExtra(SET_DOWNLOAD_PROGRESS, (ArrayList<String>) list);
                    }
                });

                boolean callbackExists = msg.isContentDownloadProgressCallbackExists();
                notificationIntent.putExtra(IS_DOWNLOAD_PROGRESS_EXISTS, callbackExists + "");
                break;
            case voice:
                VoiceContent voiceContent = (VoiceContent) content;
                voiceContent.downloadVoiceFile(msg, new DownloadCompletionCallback() {
                    @Override
                    public void onComplete(int i, String s, File file) {
                        if (i == 0) {
                            String path = file.getPath();
                            notificationIntent.setFlags(3);
                            notificationIntent.putExtra("voice", path);
                            startActivity(notificationIntent);
                        }
                    }
                });
                break;
            case file:
                UserInfo fromUser = msg.getFromUser();
                String userName = fromUser.getUserName();
                String appKey = fromUser.getAppKey();
                ConversationType targetType = msg.getTargetType();

                int id = msg.getId();

                notificationIntent.putExtra("user", userName);
                notificationIntent.putExtra("appkey", appKey);
                notificationIntent.putExtra("msgid", id);
                notificationIntent.putExtra("isGroup", targetType + "");
                notificationIntent.setFlags(10);

                startActivity(notificationIntent);
                break;

            case location:
                LocationContent locationContent = (LocationContent) content;
                String address = locationContent.getAddress();
                Number latitude = locationContent.getLatitude();
                Number scale = locationContent.getScale();
                Number longitude = locationContent.getLongitude();

                String la = String.valueOf(latitude);
                String sc = String.valueOf(scale);
                String lo = String.valueOf(longitude);

                notificationIntent.setFlags(4);
                notificationIntent.putExtra("address", address);
                notificationIntent.putExtra("latitude", la);
                notificationIntent.putExtra("scale", sc);
                notificationIntent.putExtra("longitude", lo);

                startActivity(notificationIntent);
                break;

            default:
                break;
        }
    }

    public void onEvent(MessageEvent event) {
        Message msg = event.getMessage();
        switch (msg.getContentType()) {
            case custom:
                final ConversationType targetType = event.getMessage().getTargetType();
                final Intent intent = new Intent(getApplicationContext(), ShowCustomMessageActivity.class);
                CustomContent customContent = (CustomContent) msg.getContent();
                Map allStringValues = customContent.getAllStringValues();
                if (targetType.equals(ConversationType.group)) {
                    intent.putExtra(CREATE_GROUP_CUSTOM_KEY, allStringValues.toString());
                    intent.setFlags(1);

                } else if (targetType.equals(ConversationType.single)) {
                    intent.putExtra(CUSTOM_MESSAGE_STRING, allStringValues.toString());
                    UserInfo fromUser = msg.getFromUser();
                    intent.putExtra(CUSTOM_FROM_NAME, fromUser.getUserName());
                    intent.setFlags(2);
                }
                startActivity(intent);
                break;
            //其实sdk是会自动下载语音的.本方法是当sdk自动下载失败时可以手动调用进行下载而设计的.同理于缩略图下载
            case voice:
                final Intent intentVoice = new Intent(getApplicationContext(), ShowDownloadVoiceInfoActivity.class);
                final VoiceContent voiceContent = (VoiceContent) msg.getContent();
                final int duration = voiceContent.getDuration();
                final String format = voiceContent.getFormat();
                /**=================     下载语音文件    =================*/
                voiceContent.downloadVoiceFile(msg, new DownloadCompletionCallback() {
                    @Override
                    public void onComplete(int i, String s, File file) {
                        if (i == 0) {
                            Toast.makeText(getApplicationContext(), "下载成功", Toast.LENGTH_SHORT).show();
                            intentVoice.putExtra(DOWNLOAD_INFO, "path = " + file.getPath() + "\n" + "duration = " + duration + "\n" + "format = " + format + "\n");
                            startActivity(intentVoice);
                        } else {
                            Toast.makeText(getApplicationContext(), "下载失败", Toast.LENGTH_SHORT).show();
                            Log.i(TAG, "downloadVoiceFile" + ", responseCode = " + i + " ; desc = " + s);
                        }
                    }
                });
                break;
            case eventNotification:
                String eventText = ((EventNotificationContent) msg.getContent()).getEventText();
                Intent intentNotification = new Intent(getApplicationContext(), ShowGroupNotificationActivity.class);
                intentNotification.putExtra(CreateGroupTextMsgActivity.GROUP_NOTIFICATION, eventText);

                List<String> userNames = ((EventNotificationContent) msg.getContent()).getUserNames();
                if (null != userNames) {
                    intentNotification.putExtra(CreateGroupTextMsgActivity.GROUP_NOTIFICATION_LIST, userNames.toString());
                }
                startActivity(intentNotification);
                break;
            case image:

                final Intent intentImage = new Intent(getApplicationContext(), ShowDownloadPathActivity.class);
                final ImageContent imageContent = (ImageContent) msg.getContent();
                //其实sdk是会自动下载缩略图的.本方法是当sdk自动下载失败时可以手动调用进行下载而设计的.同理于语音下载
                /**=================     下载图片信息中的缩略图    =================*/
                imageContent.downloadThumbnailImage(msg, new DownloadCompletionCallback() {
                    @Override
                    public void onComplete(int i, String s, File file) {
                        if (i == 0) {
                            Toast.makeText(getApplicationContext(), "下载缩略图成功", Toast.LENGTH_SHORT).show();
                            intentImage.putExtra(DOWNLOAD_THUMBNAIL_IMAGE, file.getPath());
                        } else {
                            Toast.makeText(getApplicationContext(), "下载原图失败", Toast.LENGTH_SHORT).show();
                            Log.i(TAG, "downloadThumbnailImage" + ", responseCode = " + i + " ; desc = " + s);
                        }
                    }
                });

                /**=================     下载图片消息中的原图    =================*/
                imageContent.downloadOriginImage(msg, new DownloadCompletionCallback() {
                    @Override
                    public void onComplete(int i, String s, File file) {
                        if (i == 0) {
                            Toast.makeText(getApplicationContext(), "下载原图成功", Toast.LENGTH_SHORT).show();
                            intentImage.putExtra(IS_UPLOAD, imageContent.isFileUploaded() + "");
                            intentImage.putExtra(DOWNLOAD_ORIGIN_IMAGE, file.getPath());
                            startActivity(intentImage);
                        } else {
                            Toast.makeText(getApplicationContext(), "下载原图失败", Toast.LENGTH_SHORT).show();
                            Log.i(TAG, "downloadOriginImage" + ", responseCode = " + i + " ; desc = " + s);
                        }
                    }
                });
                break;
            default:
                break;
        }
    }

    public void onEvent(ContactNotifyEvent event) {
        String reason = event.getReason();
        String fromUsername = event.getFromUsername();
        String appkey = event.getfromUserAppKey();

        Intent intent = new Intent(getApplicationContext(), ShowFriendReasonActivity.class);

        switch (event.getType()) {
            case invite_received://收到好友邀请
                intent.putExtra("invite_received", "fromUsername = " + fromUsername + "\nfromUserAppKey" + appkey + "\nreason = " + reason);
                intent.putExtra("username", fromUsername);
                intent.putExtra("appkey", appkey);
                intent.setFlags(1);
                startActivity(intent);
                break;
            case invite_accepted://对方接收了你的好友邀请
                intent.putExtra("invite_accepted", "对方接受了你的好友邀请");
                intent.setFlags(2);
                startActivity(intent);
                break;
            case invite_declined://对方拒绝了你的好友邀请
                intent.putExtra("invite_declined", "对方拒绝了你的好友邀请\n拒绝原因:" + event.getReason());
                intent.setFlags(3);
                startActivity(intent);
                break;
            case contact_deleted://对方将你从好友中删除
                intent.putExtra("contact_deleted", "对方将你从好友中删除");
                intent.setFlags(4);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    public void onEvent(LoginStateChangeEvent event) {
        LoginStateChangeEvent.Reason reason = event.getReason();
        UserInfo myInfo = event.getMyInfo();
        Intent intent = new Intent(getApplicationContext(), ShowLogoutReasonActivity.class);
        intent.putExtra(LOGOUT_REASON, "reason = " + reason + "\n" + "logout user name = " + myInfo.getUserName());
        startActivity(intent);
    }

    public void onEventMainThread(OfflineMessageEvent event) {
        Conversation conversation = event.getConversation();
        List<Message> newMessageList = event.getOfflineMessageList();//获取此次离线期间会话收到的新消息列表
        List<Integer> offlineMsgIdList = new ArrayList<>();
        if (conversation != null && newMessageList != null) {
            for (Message msg : newMessageList) {
                offlineMsgIdList.add(msg.getId());
            }
            mTv_showOfflineMsg.append(String.format(Locale.SIMPLIFIED_CHINESE, "收到%d条来自%s的离线消息。\n", newMessageList.size(), conversation.getTargetId()));
            mTv_showOfflineMsg.append("会话类型 = " + conversation.getType() + "\n消息ID = " + offlineMsgIdList + "\n\n");
        } else {
            mTv_showOfflineMsg.setText("conversation is null or new message list is null");
        }
    }

    public void onEventMainThread(ConversationRefreshEvent event) {
        Conversation conversation = event.getConversation();
        ConversationRefreshEvent.Reason reason = event.getReason();
        if (conversation != null) {
            tv_refreshEvent.append(String.format(Locale.SIMPLIFIED_CHINESE, "收到ConversationRefreshEvent事件,待刷新的会话是%s.\n", conversation.getTargetId()));
            tv_refreshEvent.append("事件发生的原因 : " + reason + "\n");
        } else {
            tv_refreshEvent.setText("conversation is null");
        }
    }

    public void onEvent(MyInfoUpdatedEvent event) {
        UserInfo myInfo = event.getMyInfo();
        Intent intent = new Intent(TypeActivity.this, ShowMyInfoUpdateActivity.class);
        intent.putExtra(INFO_UPDATE, myInfo.getUserName());
        startActivity(intent);
    }

    public void onEvent(final CommandNotificationEvent event) {
        event.getSenderUserInfo(new GetUserInfoCallback() {
            @Override
            public void gotResult(int responseCode, String responseMessage, UserInfo info) {
                if (0 == responseCode) {
                    final String sender = info.toString();
                    event.getTargetInfo(new CommandNotificationEvent.GetTargetInfoCallback() {
                        @Override
                        public void gotResult(int responseCode, String responseMessage, Object targetInfo, CommandNotificationEvent.Type type) {
                            if (0 == responseCode) {
                                String target;
                                if (type == CommandNotificationEvent.Type.single) {
                                    target = ((UserInfo) targetInfo).toString();
                                } else {
                                    target = ((GroupInfo) targetInfo).toString();
                                }
                                Intent intent = new Intent(getApplicationContext(), ShowTransCommandActivity.class);
                                intent.putExtra(TRANS_COMMAND_SENDER, sender);
                                intent.putExtra(TRANS_COMMAND_TARGET, target);
                                intent.putExtra(TRANS_COMMAND_TYPE, event.getType().toString());
                                intent.putExtra(TRANS_COMMAND_CMD, event.getMsg());
                                startActivity(intent);
                            } else {
                                Log.w(TAG, "CommandNotificationEvent getSenderUserInfo failed. " + "code = " +
                                        responseCode + " desc = " + responseMessage);
                            }
                        }
                    });
                } else {
                    Log.w(TAG, "CommandNotificationEvent getTargetInfo failed. " + "code = " +
                            responseCode + " desc = " + responseMessage);
                }
            }
        });
    }

    public void onEvent(GroupApprovalEvent event) {
        Intent intent = new Intent(getApplicationContext(), ShowGroupApprovalActivity.class);
        Gson gson = new Gson();
        intent.putExtra("GroupApprovalEvent", gson.toJson(event));
        intent.setFlags(1);

        startActivity(intent);
    }

    public void onEvent(final GroupApprovalRefuseEvent event) {
        final Intent intent = new Intent(getApplicationContext(), ShowGroupApprovalActivity.class);
        event.getFromUserInfo(new GetUserInfoCallback() {
            @Override
            public void gotResult(int responseCode, String responseMessage, UserInfo info) {
                if (0 == responseCode) {
                    final String fromUsername = info.getUserName();
                    final String fromAppKey = info.getAppKey();
                    event.getToUserInfoList(new GetUserInfoListCallback() {
                        @Override
                        public void gotResult(int responseCode, String responseMessage, List<UserInfo> userInfoList) {
                            if (0 == responseCode) {
                                String toUsername = userInfoList.get(0).getUserName();
                                String toAppKey = userInfoList.get(0).getAppKey();
                                intent.putExtra("notification", "入群审批拒绝通知" + "\n群组gid:" + event.getGid()
                                        + "\n群主username:" + fromUsername
                                        + "\n群主appKey:" + fromAppKey
                                        + "\n被拒绝者username:" + toUsername
                                        + "\n被拒绝者appKey:" + toAppKey
                                        + "\n拒绝理由:" + event.getReason());
                                intent.setFlags(2);
                                startActivity(intent);
                            }
                        }
                    });
                }
            }
        });
    }

    public void onEventMainThread(MessageReceiptStatusChangeEvent event) {
        Conversation conv = event.getConversation();
        tv_refreshEvent.append(String.format(Locale.SIMPLIFIED_CHINESE, "\n收到MessageReceiptStatusChangeEvent事件,会话对象是%s\n", conv.getTargetId()));
        for (MessageReceiptStatusChangeEvent.MessageReceiptMeta meta : event.getMessageReceiptMetas()) {
            tv_refreshEvent.append(String.format(Locale.SIMPLIFIED_CHINESE,
                    "回执数有更新的消息serverMsgID：%d\n这条消息当前还未发送已读回执的人数：%d\n", meta.getServerMsgId(), meta.getUnReceiptCnt()));
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JMessageClient.unRegisterEventReceiver(this);
    }
}
