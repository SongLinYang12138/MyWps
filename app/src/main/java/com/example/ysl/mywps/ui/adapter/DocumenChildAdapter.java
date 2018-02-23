package com.example.ysl.mywps.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ysl.mywps.R;
import com.example.ysl.mywps.bean.FileListChildBean;
import com.example.ysl.mywps.interfaces.PassFileChildList;
import com.example.ysl.mywps.interfaces.PasssString;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by ysl on 2018/2/8.
 * 介绍:
 */

public class DocumenChildAdapter extends MyBaseAdapter  {

    private Context context;
    private ArrayList<FileListChildBean> list;
    private int kindFlag;
    private Drawable wps, picture, video, music;
    private DisplayImageOptions options;
    private PassFileChildList passFileChildList;
    private ArrayList<FileListChildBean> selectList = new ArrayList<>();

    public DocumenChildAdapter(ArrayList<FileListChildBean> list, Context context, int kindFlag, PassFileChildList passFileChildList) {
        super(list, context);

        this.context = context;
        this.list = list;
        this.kindFlag = kindFlag;
        this.passFileChildList = passFileChildList;
        if (context != null) {

            wps = context.getResources().getDrawable(R.mipmap.ft_doc_l);
            picture = context.getResources().getDrawable(R.mipmap.ft_jpg_l);
            video = context.getResources().getDrawable(R.mipmap.ft_mov_l);
            music = context.getResources().getDrawable(R.mipmap.myapplication);
            options = new DisplayImageOptions.Builder()

                    .cacheInMemory(true)//设置下载的图片是否缓存在内存中
                    .cacheOnDisc(false)//设置下载的图片是否缓存在SD卡中
                    .considerExifParams(true)  //是否考虑JPEG图像EXIF参数（旋转，翻转）
                    .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型//
//.delayBeforeLoading(int delayInMillis)//int delayInMillis为你设置的下载前的延迟时间
//设置图片加入缓存前，对bitmap进行设置
//.preProcessor(BitmapProcessor preProcessor)
                    .resetViewBeforeLoading(true)//设置图片在下载前是否重置，复位
//                .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
//                .displayer(new FadeInBitmapDisplayer(100))//是否图片加载好后渐入的动画时间
                    .build();//

        }
        selectList.clear();
    }

    @Override
    public void update(ArrayList<?> list) {
        super.update(list);
        this.list = (ArrayList<FileListChildBean>) list;
    selectList.clear();
    }



    private class ViewHolder {
        ImageView ivIcon;
        TextView tvName, tvDate;
        CheckBox cb;

    }

    private ViewHolder holder;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View myView(final int postition, View view, ViewGroup viewGroup) {

        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.listveiw_item_documents_layout, viewGroup, false);
            holder.ivIcon = (ImageView) view.findViewById(R.id.documents_item_icon);
            holder.tvName = (TextView) view.findViewById(R.id.documents_item_title);
            holder.tvDate = (TextView) view.findViewById(R.id.documents_item_time);
            holder.cb = (CheckBox) view.findViewById(R.id.documents_item_cb);

            view.setTag(holder);
            holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    selectList.clear();
                    if (isChecked) {
                     if(list.size() > postition)   selectList.add(list.get(postition));
                        passFileChildList.passFileChild(selectList,0);
                    } else{
                        if(list.size() > postition)     selectList.remove(postition);
                    }
                }
            });
        } else {
            holder = (ViewHolder) view.getTag();
        }

        FileListChildBean bean = list.get(postition);

        if (kindFlag == 1) {
            ImageLoader.getInstance().displayImage(bean.getDownload_url(), holder.ivIcon, options);
//            holder.ivIcon.setBackground(picture);
        } else if (kindFlag == 2) {
            holder.ivIcon.setBackground(wps);
        } else if (kindFlag == 3) {
            holder.ivIcon.setBackground(video);
        } else if (kindFlag == 4) {
            holder.ivIcon.setBackground(music);
        }

        holder.tvName.setText(bean.getFilename());
        holder.tvDate.setText(bean.getCtime());
        return view;
    }
}
