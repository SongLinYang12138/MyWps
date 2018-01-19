package com.example.ysl.mywps.ui.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.ysl.mywps.R;
import com.example.ysl.mywps.ui.activity.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/12/23 0023.
 */

public class MineFragment extends BaseFragment {
    @BindView(R.id.mine_rl_bottom)
    RelativeLayout rlLoginOut;

    @Override
    public View setView(LayoutInflater inflater, ViewGroup container) {

        View view = inflater.inflate(R.layout.fragment_mine_layout, container, false);

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void afterView(View view) {

        rlLoginOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void setKindFlag(int kindFlag) {

    }
}
