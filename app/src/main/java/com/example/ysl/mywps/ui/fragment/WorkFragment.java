package com.example.ysl.mywps.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.ysl.mywps.R;
import com.example.ysl.mywps.bean.FileType;
import com.example.ysl.mywps.net.HttpUtl;
import com.example.ysl.mywps.ui.activity.DocumentFlowActivity;
import com.example.ysl.mywps.ui.activity.DocumentTurnActivity;
import com.example.ysl.mywps.ui.activity.LoginActivity;
import com.example.ysl.mywps.ui.activity.MaterialActivity;
import com.example.ysl.mywps.ui.activity.WebviewActivity;
import com.example.ysl.mywps.utils.CommonUtil;
import com.example.ysl.mywps.utils.NoDoubleClickListener;
import com.example.ysl.mywps.utils.SharedPreferenceUtils;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/12/23 0023.
 */

public class WorkFragment extends BaseFragment {

    @BindView(R.id.work_ll_document_turn)
    LinearLayout llTurn;
    @BindView(R.id.work_ll_material)
    LinearLayout llMaterial;
    @BindView(R.id.work_rl_social_condition)
    RelativeLayout rlSocialCondition;

    private String token = "";
    private MyclickListener myclickListener = new MyclickListener();

    @Override
    public void initData() {
        token = SharedPreferenceUtils.loginValue(getActivity(),"token");
//        saveFileTypes(token);
    }

    @Override
    public View setView(LayoutInflater inflater, ViewGroup container) {

        View view = inflater.inflate(R.layout.fragment_work_layout,container,false);
        ButterKnife.bind(this,view);



        return view;
    }


    @Override
    public void afterView(View view) {

        llTurn.setOnClickListener(myclickListener);
        llMaterial.setOnClickListener(myclickListener);
        rlSocialCondition.setOnClickListener(myclickListener);

    }

    @Override
    public void setKindFlag(int kindFlag) {

    }

    private class MyclickListener extends NoDoubleClickListener{

        @Override
        public void click(View v) {
            Intent intent = null;
            switch (v.getId()){

                case R.id.work_ll_document_turn:

                   intent = new Intent(getActivity(), DocumentTurnActivity.class);
                    break;

                case R.id.work_ll_material:

                     intent = new Intent(getActivity(), MaterialActivity.class);
                    break;
                case R.id.work_rl_social_condition:


                    intent = new Intent(getActivity(),WebviewActivity.class);
                    break;
            }

            startActivity(intent);
        }
    }
}
