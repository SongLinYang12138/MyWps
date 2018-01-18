package im.sdk.debug.activity.imagecontent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import im.sdk.debug.R;
import im.sdk.debug.activity.createmessage.CreateSigImageMessageActivity;

/**
 * Created by ${chenyn} on 16/3/30.
 *
 * @desc :图片消息信息,三种创建图片消息的接口区别在于除了接收参数file和bitmap的不同之外,CreateImageContentAsyncFile和CreateImageContentAsyncBitmap
 * 都是需要创建content的,通过创建的content再利用其中的接口可以获取图片的相关属性,比如宽,高,大小,路径等.发送之后接收方通过Event事件可以进行相应操作.demo
 * 展示了下载图片的操作(具体Event请看{@link im.sdk.debug.activity.TypeActivity})
 */
public class ImageContentActivity extends Activity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setContentView(R.layout.activity_image_content);
        Button bt_image_message =  (Button) findViewById(R.id.bt_image_message);

        Button bt_createImageContentAsyncFile = (Button) findViewById(R.id.bt_create_image_content_async_file);
        Button bt_createImageContentAsyncBitmap = (Button) findViewById(R.id.bt_create_image_content_async_Bitmap);

        bt_createImageContentAsyncFile.setOnClickListener(this);
        bt_createImageContentAsyncBitmap.setOnClickListener(this);
        bt_image_message.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.bt_create_image_content_async_file:
                intent.setClass(getApplicationContext(), CreateImageContentAsyncFile.class);
                startActivity(intent);
                break;
            case R.id.bt_create_image_content_async_Bitmap:
                intent.setClass(getApplicationContext(), CreateImageContentAsyncBitmap.class);
                startActivity(intent);
                break;
            case R.id.bt_image_message:
                intent.setClass(ImageContentActivity.this, CreateSigImageMessageActivity.class);
                startActivity(intent);
            default:
                break;
        }
    }


}
