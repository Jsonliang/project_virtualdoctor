package com.liang.virtualdoctor.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.liang.virtualdoctor.R;
import com.liang.virtualdoctor.adapter.Medical_DoctorInfoLVAdapter;
import com.liang.virtualdoctor.beans.DoctorInfo;
import com.liang.virtualdoctor.ui.activitys.SearchActivity;
import com.liang.virtualdoctor.ui.activitys.SearchBySubjectActivity;
import com.liang.virtualdoctor.ui.activitys.WebViewActivity;
import com.liang.virtualdoctor.utils.Constants;
import com.liang.virtualdoctor.utils.HttpHelper;
import com.liang.virtualdoctor.utils.JsonParserUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/7/9 0009.
 * Doctor
 */
public class MedicalNews_Fragment extends Fragment {
    private static final String TAG = MedicalNews_Fragment.class.getSimpleName();
    private View mLayout ;
    @BindView(R.id.frag_medical_doctor_Lv)
    public ListView mLv;//list doctor info
    //listview item widget holderclass
    public Viewholder mVh;
    private Activity activity;
    public ProgressBar progressBar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         mLayout = inflater.inflate(R.layout.fragment_medicalnews_layout,
                 container,false);
        ButterKnife.bind(MedicalNews_Fragment.this,mLayout);

        View view=mLayout.findViewById(R.id.frag_medical_progressbar);
        View headView=inflater.inflate(R.layout.fragment_medical_lsitem_headerview,null);

        mLv.addHeaderView(headView);
        mLv.setEmptyView(view);

        mVh=new Viewholder(headView);
        initView(mLayout);
        initToolBar();
        return mLayout;
    }
    private void initToolBar() {
        view=mLayout.findViewById(R.id.frag_medical_toolbar);
        view.findViewById(R.id.toolbar_rightText).setVisibility(View.GONE);
        view.findViewById(R.id.toolbar_goback).setVisibility(View.GONE);
        view.findViewById(R.id.toolbar_lineview).setVisibility(View.VISIBLE);
        ((TextView)view.findViewById(R.id.toolbar_centerText)).setText("医讯");
    }

    private View view;

    //ListVIew  adapter
    private Medical_DoctorInfoLVAdapter adapter;
    private List<DoctorInfo> mDoctorInfos=new ArrayList<>();
    private void initView(View mLayout) {
        activity=getActivity();
        HttpHelper.getInstance().requestByGet(Constants.DOCTOR_LIST,
                new HttpHelper.StringCallBack() {
            @Override
            public void onFailure(Exception e) {
            }
            @Override
            public void onResult(Object string) {
                String json= (String) string;
                Log.i(TAG, "onResult: json-->"+json);
                if (!TextUtils.isEmpty(json))
                {
                   // Log.i(TAG, "onResponse: returnData-->"+json);
                    List<DoctorInfo> oPS = JsonParserUtils.parserJSON2DoctorInfo(json);
                    if(oPS!=null){
                        mDoctorInfos.clear();
                        mDoctorInfos.addAll(oPS);
                        //run On ui
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(adapter==null){
                                    adapter=new Medical_DoctorInfoLVAdapter(mDoctorInfos,getContext());
                                    mLv.setAdapter(adapter);
                                }else {
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        });
                    }
                }
            }

        });
        //set the listview listener
        listViewSetListener();
    }

    private void listViewSetListener(){
        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
               DoctorInfo doctor= (DoctorInfo) mLv.getItemAtPosition(position);
               String doctorid= doctor.getDoct_id();
                Intent intent=new Intent(getActivity().getApplicationContext(), WebViewActivity.class);
                intent.putExtra(WebViewActivity.URLKEYWORD,Constants.DOCTOR_INFO+doctorid);
                intent.putExtra(WebViewActivity.TOOLBARTITLE,doctor.getDoct_name());
                gotoMedical(intent);

            }
        });
    }

    public class Viewholder implements View.OnClickListener {
        @BindView(R.id.frag_medical_headview_searchedt)
        public EditText mEdt_search;
        @BindView(R.id.frag_medical_headview_searchbtn)
        public ImageView mImgSearch;
        @BindView(R.id.frag_medical_findBysection)
        public TextView mFindBySection;
        @BindView(R.id.frag_medical_findBysickness)
        public TextView mFindBySickness;
        public Viewholder(View view) {
            ButterKnife.bind(Viewholder.this,view);
            mImgSearch.setOnClickListener(Viewholder.this);
            mFindBySection.setOnClickListener(Viewholder.this);
            mFindBySickness.setOnClickListener(Viewholder.this);
        }

        @Override
        public void onClick(View v) {
            Intent intent=null;
            switch (v.getId()){
                case R.id.frag_medical_findBysection:
                    intent=new Intent(activity, SearchBySubjectActivity.class);
                    //showToast(getContext(),"按科室查找");
                    break;
                case R.id.frag_medical_findBysickness:
                    //find by sickness
                    intent=new Intent(activity, SearchActivity.class);
                    //showToast(getContext(),"按疾病查找");
                    break;
                case R.id.frag_medical_headview_searchbtn:
                    String keyword=getKeyWord(mEdt_search);
                 if(keyword!=null){
                     //search info by keyword
                    intent= new Intent(activity,WebViewActivity.class);
                     String url=Constants.QUER_KEYWORDINFO+keyword;
                     Log.i("TAG", "onClick: sear- url->"+url);
                     intent .putExtra(WebViewActivity.TOOLBARTITLE,"搜索");
                     intent.putExtra(WebViewActivity.URLKEYWORD,
                             url);
                 }else{
                     showToast(getContext(),"输入内容为空");
                    return;
                 }
                    break;
                default:
                    break;
            }
            gotoMedical(intent);
        }
    }

    private String getKeyWord(EditText editText){
        String keyword=editText.getText().toString();
        if(!TextUtils.isEmpty(keyword)){
           return keyword;
        }else {
            return null;
        }
    }
    public  static void showToast(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }

    private void gotoMedical(Intent intent){
      activity.startActivity(intent);
    }

}
