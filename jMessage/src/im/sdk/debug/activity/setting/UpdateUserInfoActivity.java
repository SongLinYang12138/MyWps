package im.sdk.debug.activity.setting;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;
import im.sdk.debug.R;

/**
 * Created by ${chenyn} on 16/3/25.
 *
 * @desc :更新用户信息
 */
public class UpdateUserInfoActivity extends Activity {

    public static EditText mEt_nickname;
    public static EditText mEt_birthday;
    public static EditText mEt_region;
    public static EditText mEt_signature;
    public static Button mBt_updateUserInfo;
    private TextView mTv_updateInfo;
    private InputMethodManager mImm;
    private RadioButton mRb_male;
    private RadioButton mRb_female;
    private RadioButton mRb_unknown;
    private RadioGroup mRg_gender;
    private UserInfo mMyInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initData();
    }

    private void initData() {
        mImm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mBt_updateUserInfo.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!mImm.isActive()) {
                            mImm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
                        }
                        mTv_updateInfo.setText("");

                        final UserInfo myInfo = JMessageClient.getMyInfo();
                        if (myInfo != null) {
/**=================     调用sdk 更新nickName    =================*/
                            myInfo.setNickname(mEt_nickname.getText().toString());
                            JMessageClient.updateMyInfo(UserInfo.Field.nickname, myInfo, new BasicCallback() {
                                @Override
                                public void gotResult(int i, String s) {
                                    if (i == 0) {
                                        mTv_updateInfo.append("更新nickname成功" + "\n");
                                        Log.i("UpdateUserInfoActivity", "updateNickName," + " responseCode = " + i + "; desc = " + s);
                                    } else {
                                        mTv_updateInfo.append("更新nickname失败" + "\n");
                                        Log.i("UpdateUserInfoActivity", "updateNickName," + " responseCode = " + i + "; desc = " + s);
                                    }
                                }
                            });
/**=================     调用sdk 更新region    =================*/
                            myInfo.setRegion(mEt_region.getText().toString());
                            JMessageClient.updateMyInfo(UserInfo.Field.region, myInfo, new BasicCallback() {
                                @Override
                                public void gotResult(int i, String s) {
                                    if (i == 0) {
                                        mTv_updateInfo.append("更新region成功" + "\n");
                                        Log.i("UpdateUserInfoActivity", "updateRegion," + " responseCode = " + i + "; desc = " + s);
                                    } else {
                                        mTv_updateInfo.append("更新region失败" + "\n");
                                        Log.i("UpdateUserInfoActivity", "updateRegion," + " responseCode = " + i + "; desc = " + s);
                                    }
                                }
                            });
/**=================     调用sdk 更新signature    =================*/
                            myInfo.setSignature(mEt_signature.getText().toString());
                            JMessageClient.updateMyInfo(UserInfo.Field.signature, myInfo, new BasicCallback() {
                                @Override
                                public void gotResult(int i, String s) {
                                    if (i == 0) {
                                        mTv_updateInfo.append("更新signature成功" + "\n");
                                        Log.i("UpdateUserInfoActivity", "updateSignature," + " responseCode = " + i + "; desc = " + s);
                                    } else {
                                        mTv_updateInfo.append("更新signature失败" + "\n");
                                        Log.i("UpdateUserInfoActivity", "updateSignature," + " responseCode = " + i + "; desc = " + s);
                                    }
                                }
                            });
/**=================     调用sdk 更新birthday    =================*/
                            String data = mEt_birthday.getText().toString();
                            if (!TextUtils.isEmpty(data)) {
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                                try {
                                    long time = sdf.parse(data).getTime();
                                    myInfo.setBirthday(time);
                                    JMessageClient.updateMyInfo(UserInfo.Field.birthday, myInfo, new BasicCallback() {
                                        @Override
                                        public void gotResult(int i, String s) {
                                            if (i == 0) {
                                                mTv_updateInfo.append("更新birthday成功" + "\n");
                                                Log.i("UpdateUserInfoActivity", "updateBirthday," + " responseCode = " + i + "; desc = " + s);
                                            } else {
                                                mTv_updateInfo.append("更新birthday失败" + "\n");
                                                Log.i("UpdateUserInfoActivity", "updateBirthday," + " responseCode = " + i + "; desc = " + s);
                                            }
                                        }
                                    });
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                mTv_updateInfo.append("更新birthday失败" + "\n");
                            }
/**=================     调用sdk 更新gender    =================*/
                            JMessageClient.updateMyInfo(UserInfo.Field.gender, mMyInfo, new BasicCallback() {
                                @Override
                                public void gotResult(int i, String s) {
                                    if (i == 0) {
                                        mTv_updateInfo.append("更新gender成功" + "\n");
                                        Log.i("UpdateUserInfoActivity", "updateGender," + " responseCode = " + i + "; desc = " + s);
                                    } else {
                                        mTv_updateInfo.append("更新gender失败" + "\n");
                                        Log.i("UpdateUserInfoActivity", "updateGender," + " responseCode = " + i + "; desc = " + s);

                                    }
                                }
                            });

/**=================     调用sdk 更新address    =================*/
                            myInfo.setAddress("test address");//暂时写死测试address
                            JMessageClient.updateMyInfo(UserInfo.Field.address, myInfo, new BasicCallback() {
                                @Override
                                public void gotResult(int responseCode, String responseMessage) {
                                    if (responseCode == 0) {
                                        mTv_updateInfo.append("更新address成功" + "\n");
                                        Log.i("UpdateUserInfoActivity", "updateAddress," + " responseCode = " + responseCode + "; desc = " + responseMessage);
                                    } else {
                                        mTv_updateInfo.append("更新address失败" + "\n");
                                        Log.i("UpdateUserInfoActivity", "updateAddress," + " responseCode = " + responseCode + "; desc = " + responseMessage);

                                    }
                                }
                            });
                        } else {
                            Toast.makeText(UpdateUserInfoActivity.this, "更新失败info == null", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        mRg_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                mMyInfo = JMessageClient.getMyInfo();
                if (checkedId == mRb_female.getId()) {
                    mMyInfo.setGender(UserInfo.Gender.female);
                } else if (checkedId == mRb_male.getId()) {
                    mMyInfo.setGender(UserInfo.Gender.male);
                } else if (checkedId == mRb_unknown.getId()) {
                    mMyInfo.setGender(UserInfo.Gender.unknown);
                }
            }
        });
    }


    private void initView() {
        setContentView(R.layout.activity_update_user_info);

        mEt_nickname = (EditText) findViewById(R.id.et_nickname);
        mEt_birthday = (EditText) findViewById(R.id.et_birthday);
        mEt_region = (EditText) findViewById(R.id.et_region);
        mEt_signature = (EditText) findViewById(R.id.et_signature);

        mTv_updateInfo = (TextView) findViewById(R.id.tv_updateInfo);

        mRb_male = (RadioButton) findViewById(R.id.rb_male);
        mRb_female = (RadioButton) findViewById(R.id.rb_female);
        mRb_unknown = (RadioButton) findViewById(R.id.rb_unknown);
        mRg_gender = (RadioGroup) findViewById(R.id.rg_gender);

        mBt_updateUserInfo = (Button) findViewById(R.id.bt_update_user_info);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
                mImm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.onTouchEvent(event);
    }

}
