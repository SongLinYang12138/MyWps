package im.sdk.debug.activity.imagecontent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import im.sdk.debug.R;
import im.sdk.debug.activity.TypeActivity;

/**
 * Created by ${chenyn} on 16/4/9.
 *
 * @desc :展示下载图片的路径
 */
public class ShowDownloadPathActivity extends Activity {

    private TextView mTv_showDownLoadPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        String stringExtra = intent.getStringExtra(TypeActivity.DOWNLOAD_ORIGIN_IMAGE);
        String intentStringExtra = intent.getStringExtra(TypeActivity.DOWNLOAD_THUMBNAIL_IMAGE);
        String isUpLoad = intent.getStringExtra(TypeActivity.IS_UPLOAD);
        mTv_showDownLoadPath.append("down load origin image path = " + stringExtra + "\n");
        mTv_showDownLoadPath.append("\n");
        mTv_showDownLoadPath.append("down load thumbnail image = " + intentStringExtra);
        mTv_showDownLoadPath.append("\n");
        mTv_showDownLoadPath.append("is file uploaded = " + isUpLoad);
    }

    private void initView() {
        setContentView(R.layout.activity_show_download_path);

        mTv_showDownLoadPath = (TextView) findViewById(R.id.tv_show_download_path);
    }
}
