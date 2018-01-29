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
import com.example.ysl.mywps.utils.CommonUtil;

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


        holder.tvTitle.setText(info.getTitle()+0);
        holder.tvDate.setText(CommonUtil.isEmpty(info.getN_time()) ? info.getCtime():info.getN_time());
        holder.tvHandel.setText("处理人: "+info.getNow_nickname());
//        拟稿1-》审核2-》审核通过5-》签署3（不同意）-》审核通过4
       if(!info.getStatus().equals("4")){
           holder.ivStatus.setVisibility(View.VISIBLE);
           if(info.getStatus().equals("1"))
               holder.tvSend.setText("发文");
           if(info.getStatus().equals("2"))
               holder.tvSend.setText("审核");
           if(info.getStatus().equals("3"))
               holder.tvSend.setText("签署");
           if(info.getStatus().equals("5"))
               holder.tvSend.setText("审核通过");
       }else {
           holder.ivStatus.setVisibility(View.INVISIBLE);
           holder.tvSend.setText("审核通过");

       }


        return view;
    }
}
