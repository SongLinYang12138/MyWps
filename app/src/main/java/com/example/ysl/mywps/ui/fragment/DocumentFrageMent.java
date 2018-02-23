package com.example.ysl.mywps.ui.fragment;

import android.content.res.ColorStateList;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ysl.mywps.R;
import com.example.ysl.mywps.bean.FileListChildBean;
import com.example.ysl.mywps.interfaces.PassFileChildList;
import com.example.ysl.mywps.interfaces.PasssString;
import com.example.ysl.mywps.ui.adapter.PagerAdapter;
import com.example.ysl.mywps.utils.NoDoubleClickListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/2/4 0004.
 */

public class DocumentFrageMent extends BaseFragment {

    @BindView(R.id.material_document_viewpager)
     ViewPager viewPager;
    @BindView(R.id.fragment_documents_picture)
    TextView tvPicture;
    @BindView(R.id.fragment_documents_documents)
    TextView tvDocuments;
    @BindView(R.id.fragment_documents_video)
    TextView tvVideo;
    @BindView(R.id.fragment_documents_music)
    TextView tvMusic;

    @BindView(R.id.fragment_ll_picture)
    LinearLayout llPicture;
    @BindView(R.id.fragment_ll_documents)
    LinearLayout llDocuments;
    @BindView(R.id.fragment_ll_video)
    LinearLayout llVieo;
    @BindView(R.id.fragment_ll_music)
    LinearLayout llMusic;

    private   PagerAdapter pagerAdapter;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private DocufragmentsFragment fragment1,fragment2,fragment3,fragment4;
    private ColorStateList normal,select;
    private MyclickListener click = new MyclickListener();
    private PassFileChildList passFileChildList;
    private PasssString passsString1,passsString2,passsString3,passsString4;

    @Override
    public void initData() {

    }

    @Override
    public View setView(LayoutInflater inflater, ViewGroup container) {

        View view = inflater.inflate(R.layout.fragment_material_document,container,false);
        ButterKnife.bind(this,view);

        fragment1 = new DocufragmentsFragment();
        fragment1.setKindFlag(1);
        passsString1 = fragment1;
        fragment1.setPassFileChild(passFileChildList);
        fragment2 = new DocufragmentsFragment();
        fragment2.setKindFlag(2);
        passsString2 = fragment2;
        fragment2.setPassFileChild(passFileChildList);
        fragment3 = new DocufragmentsFragment();
        fragment3.setKindFlag(3);
        passsString3 = fragment3;
        fragment3.setPassFileChild(passFileChildList);
        fragment4 = new DocufragmentsFragment();
        fragment4.setKindFlag(4);
        passsString4 = fragment4;
        fragment4.setPassFileChild(passFileChildList);

        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);
        fragments.add(fragment4);

        pagerAdapter = new PagerAdapter(getFragmentManager(),fragments);
        return view;
    }

    public void setPassFileChildList(PassFileChildList fileChildList){
        this.passFileChildList = fileChildList;
    }

    @Override
    public void afterView(View view) {

        normal = getResources().getColorStateList(R.color.text_gray);
        select = getResources().getColorStateList(R.color.text_black);
        llPicture.setOnClickListener(click);
        llDocuments.setOnClickListener(click);
        llVieo.setOnClickListener(click);
        llMusic.setOnClickListener(click);

        viewPager.setAdapter(pagerAdapter);
        setTextBack(0);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                setTextBack(position);
                passsString1.setString("0");
                passsString2.setString("0");
                passsString3.setString("0");
                passsString4.setString("0");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setTextBack(int position){

        switch (position){
            case 0:

                tvPicture.setTextColor(select);
                tvDocuments.setTextColor(normal);
                tvVideo.setTextColor(normal);
                tvMusic.setTextColor(normal);


                break;
            case 1:

                tvPicture.setTextColor(normal);
                tvDocuments.setTextColor(select);
                tvVideo.setTextColor(normal);
                tvMusic.setTextColor(normal);


                break;

            case 2:

                tvPicture.setTextColor(normal);
                tvDocuments.setTextColor(normal);
                tvVideo.setTextColor(select);
                tvMusic.setTextColor(normal);


                break;
            case 3:

                tvPicture.setTextColor(normal);
                tvDocuments.setTextColor(normal);
                tvVideo.setTextColor(normal);
                tvMusic.setTextColor(select);


                break;



        }


    }
    @Override
    public void setKindFlag(int kindFlag) {

    }

    private class MyclickListener extends NoDoubleClickListener{

        @Override
        public void click(View v) {

            switch (v.getId()){

                case R.id.fragment_ll_picture:

                    viewPager.setCurrentItem(0);
                    break;
                case R.id.fragment_ll_documents:

                    viewPager.setCurrentItem(1);
                    break;
                case R.id.fragment_ll_video:

                    viewPager.setCurrentItem(2);
                    break;
                case R.id.fragment_ll_music:

                    viewPager.setCurrentItem(3);
                    break;


            }

        }
    }
}
