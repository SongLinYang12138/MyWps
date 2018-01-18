package com.example.ysl.mywps.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ysl.mywps.R;
import com.example.ysl.mywps.bean.ContactBean;

import java.util.ArrayList;

/**
 * Created by ysl on 2018/1/16.
 */

public class ContactMyAdapter extends MyBaseAdapter {

    private Context context;
    private ArrayList<ContactBean> list = new ArrayList<>();
    private ArrayList<String> exitContact = new ArrayList<>();

    public ContactMyAdapter(ArrayList<ContactBean> list, Context context) {
        super(list, context);
        this.list = list;
        this.context = context;

    }

    private class ViewHolder {
        TextView tvCapital;
        TextView tvName;
        LinearLayout llCapital;

    }

    private ViewHolder holder;

    @Override
    public View myView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.listview_item_contact_layout, viewGroup,false);
            holder.tvCapital = (TextView) view.findViewById(R.id.contact_tv_capital);
            holder.tvName = (TextView) view.findViewById(R.id.contact_tv_name);
            holder.llCapital = (LinearLayout) view.findViewById(R.id.contact_ll_capital);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        ContactBean info = list.get(i);

        if (exitContact.contains(info.getCapital())) {

            holder.llCapital.setVisibility(View.GONE);
        }else {
            exitContact.add(info.getCapital());
            holder.llCapital.setVisibility(View.VISIBLE);
            holder.tvCapital.setText(info.getCapital());
        }
        holder.tvName.setText(info.getName());

        return view;
    }
}
