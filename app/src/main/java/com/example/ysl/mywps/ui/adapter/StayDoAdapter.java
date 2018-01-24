package com.example.ysl.mywps.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ysl.mywps.R;
import com.example.ysl.mywps.bean.DocumentListBean;
import com.example.ysl.mywps.ui.fragment.StayDoFragment;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/14 0014.
 */

public class StayDoAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<DocumentListBean> list;

    public StayDoAdapter(Context context, ArrayList<DocumentListBean> list) {

        this.context = context;
        this.list = list;

    }

    public void updateList(ArrayList<DocumentListBean> list){
        this.list  = list;
        notifyDataSetChanged();
    }
    public void addList(ArrayList<DocumentListBean> list){

        this.list.addAll(list);
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

    private class ViewHolder {

        TextView tvTitle,tvSend,tvDate,tvHandel;
        ImageView ivStatus;
    }

    private ViewHolder holder;

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.listview_item_stay_to_do_layout, null);
            holder = new ViewHolder();
            holder.ivStatus = (ImageView) view.findViewById(R.id.stay_iv_status);
            holder.tvTitle = (TextView) view.findViewById(R.id.stay_tv_address);
            holder.tvSend = (TextView) view.findViewById(R.id.stay_tv_send);
            holder.tvDate = (TextView) view.findViewById(R.id.stay_tv_dates);
            holder.tvHandel = (TextView) view.findViewById(R.id.stay_tv_handle);

            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

        DocumentListBean info = list.get(position);

        holder.tvTitle.setText(info.getTitle());
        holder.tvDate.setText(info.getCtime());
        holder.tvHandel.setText("处理人: "+info.getNickname());
        if (position != 0) holder.ivStatus.setVisibility(View.INVISIBLE);


        return view;
    }
}
