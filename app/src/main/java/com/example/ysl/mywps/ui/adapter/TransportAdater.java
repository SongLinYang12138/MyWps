package com.example.ysl.mywps.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.ysl.mywps.R;
import com.example.ysl.mywps.bean.TransportBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by ysl on 2018/2/10.
 * 介绍:展示数据
 */

public class TransportAdater extends BaseAdapter {

    private ArrayList<TransportBean>  list;
    private Context context;
    private DisplayImageOptions options;

    public TransportAdater(ArrayList<TransportBean> list, Context context) {

        this.list = list;
        this.context = context;
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
    public void update(ArrayList<TransportBean> list){

        this.list = list;
    notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    private ViewHolder holder;
    @Override
    public View getView(int position, View view, ViewGroup parent) {

        if(view == null){

            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.listview_item_transport_item_layout,parent,false);

            holder.ivIcon = (ImageView) view.findViewById(R.id.documents_item_icon1);
            holder.tvTitle = (TextView) view.findViewById(R.id.documents_item_title1);
            holder.tvDate = (TextView) view.findViewById(R.id.documents_item_time1);
            holder.tvSize = (TextView) view.findViewById(R.id.documents_item_size1);
            holder.tvPath = (TextView) view.findViewById(R.id.documents_item_path);

            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

        if(list.size() > position){
            TransportBean bean = list.get(position);

         if(bean.getName() != null && bean.getPath() != null){
             if(bean.getName().contains("png") || bean.getName().contains("jpg")){
                 ImageLoader.getInstance().displayImage("file:///"+bean.getPath(), holder.ivIcon, options);
             }
         }

            holder.tvTitle.setText(bean.getName());
            holder.tvSize.setText(bean.getSize());
            holder.tvDate.setText(bean.getDate());
            holder.tvPath.setText(bean.getPath());
        }

        return view;
    }


    private class ViewHolder {

        ImageView ivIcon;
        TextView tvTitle,tvDate,tvSize,tvPath;

    }



}