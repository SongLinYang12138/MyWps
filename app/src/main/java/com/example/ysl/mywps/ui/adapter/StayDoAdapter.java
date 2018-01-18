package com.example.ysl.mywps.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.ysl.mywps.R;
import com.example.ysl.mywps.ui.fragment.StayDoFragment;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/14 0014.
 */

public class StayDoAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> list;

    public StayDoAdapter(Context context, ArrayList<String> list) {

        this.context = context;
        this.list = list;

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

        ImageView ivStatus;
    }

    private ViewHolder holder;

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.listview_item_stay_to_do_layout, null);
            holder = new ViewHolder();
            holder.ivStatus = (ImageView) view.findViewById(R.id.stay_iv_status);

            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

        if (position != 0) holder.ivStatus.setVisibility(View.INVISIBLE);
        return view;
    }
}
