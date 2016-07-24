package com.liang.virtualdoctor.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.liang.virtualdoctor.R;
import com.liang.virtualdoctor.ui.activitys.FeekbackActivity;
import com.liang.virtualdoctor.ui.activitys.HealthHelperActivity;
import com.liang.virtualdoctor.ui.activitys.LoginActivity;
import com.liang.virtualdoctor.ui.activitys.SettingActivity;

/**
 * Created by Administrator on 2016/7/9 0009.
 */
public class User_Fragment extends Fragment implements View.OnClickListener {

    private View mLayout ;
    private RelativeLayout rl_health_healper;
    private RelativeLayout rl_disease_history;
    private RelativeLayout rl_collect;
    private RelativeLayout rl_feekback;
    private RelativeLayout rl_login;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         mLayout = inflater.inflate(R.layout.fragment_my_layout, container, false);
       initView(mLayout);
        return mLayout;

    }

   private TextView textView;
    private void initView(View mLayout) {
        rl_health_healper = (RelativeLayout) mLayout.findViewById(R.id.rl_health_healper);
        rl_health_healper.setOnClickListener(this);

        rl_disease_history = (RelativeLayout) mLayout.findViewById(R.id.rl_disease_history);
        rl_disease_history.setOnClickListener(this);

        rl_collect = (RelativeLayout) mLayout.findViewById(R.id.rl_collect);
        rl_collect.setOnClickListener(this);

        rl_feekback = (RelativeLayout) mLayout.findViewById(R.id.rl_feekback);
        rl_feekback.setOnClickListener(this);

        rl_login= (RelativeLayout) mLayout.findViewById(R.id.rl_login);
        rl_login.setOnClickListener(this);

        textView= (TextView) mLayout.findViewById(R.id.setting);
        textView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.setting:
                startActivity(new Intent(getActivity(),SettingActivity.class));
                break;
            case R.id.rl_login:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.rl_health_healper:
                startActivity(new Intent(getActivity(), HealthHelperActivity.class));
                break;
            case R.id.rl_disease_history:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.rl_collect:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.rl_feekback:
                startActivity(new Intent(getActivity(), FeekbackActivity.class));
                break;
        }
    }
}
