package com.liang.virtualdoctor.ui.activitys;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.SDKInitializer;
import com.liang.virtualdoctor.R;
import com.liang.virtualdoctor.app.App;
import com.liang.virtualdoctor.fragments.MedicalNews_Fragment;
import com.liang.virtualdoctor.fragments.Nearly_Fragment;
import com.liang.virtualdoctor.fragments.SelfCheck_Fragment;
import com.liang.virtualdoctor.fragments.User_Fragment;
import com.liang.virtualdoctor.utils.SaveWeatherUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {

    private Fragment mFragment;
    private Fragment currentFragment;
    private FragmentManager mFM;
    private FragmentTransaction mFT;
    private List<Fragment> mFragments = new ArrayList<>();

    @BindViews({R.id.home_menu_selfcheck, R.id.home_menu_medicalnews,
            R.id.home_menu_nearly, R.id.home_menu_my})
    public RadioButton[] mRadioButton;

    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化地图
        SDKInitializer.initialize(getApplicationContext());
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initLocation();
    }

    private void initView() {
        ButterKnife.bind(this);
        for (int i = 0; i < 4; i++) {
            mFragments.add(null);
        }
        mFragment = new SelfCheck_Fragment();
        currentFragment = mFragment;
        mFragments.set(0, mFragment);

        mFM = getSupportFragmentManager();
        mFT = mFM.beginTransaction();
        mFT.add(R.id.doctor_fragment_layout, mFragment, mFragment.getClass().getSimpleName());
        mFT.commit();
        setViewEnable(mRadioButton[0].getId());//设置默认不可单击

    }

    @OnClick({R.id.home_menu_selfcheck, R.id.home_menu_medicalnews,
            R.id.home_menu_nearly, R.id.home_menu_my})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_menu_selfcheck:
                showFragment(0);
                break;
            case R.id.home_menu_medicalnews:
                showFragment(1);
                break;
            case R.id.home_menu_nearly:
                showFragment(2);
                break;
            case R.id.home_menu_my:
                showFragment(3);
                break;
        }
        setViewEnable(view.getId());//对应的位置不能再次被单击
    }

    private void showFragment(int index) {
        mFT = mFM.beginTransaction();
        if (mFragments.get(index) == null) {
            switch (index) {
                case 0:
                    mFragment = new SelfCheck_Fragment();
                    break;
                case 1:
                    mFragment = new MedicalNews_Fragment();
                    break;
                case 2:
                    mFragment = new Nearly_Fragment();
                    break;
                case 3:
                    mFragment = new User_Fragment();
            }
            mFragments.set(index, mFragment);//替换原来的null
            mFT.add(R.id.doctor_fragment_layout, mFragment, mFragment.getClass().getSimpleName());
            mFT.hide(currentFragment);//当前显示隐藏
            mFT.show(mFragment);
            currentFragment = mFragment;
        } else {
            //直接调用隐藏
            mFragment = mFragments.get(index);
            mFT.hide(currentFragment);
            mFT.show(mFragment);
            currentFragment = mFragment;
        }
        mFT.commit();
    }

    private void setViewEnable(int id) {
        for (int i = 0; i < mRadioButton.length; i++) {
            if (mRadioButton[i].getId() == id) {
                mRadioButton[i].setEnabled(false);//选中之后。设置为不可再次单击
                mRadioButton[i].setTextColor(Color.parseColor("#1ff410"));
            } else {
                mRadioButton[i].setEnabled(true);
                mRadioButton[i].setTextColor(Color.parseColor("#afadad"));
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        /*when app quit delete record
        * */
        SaveWeatherUtils.clearWeatherData((App) getApplicationContext());
    }


    private void initLocation(){
        mLocationClient = new LocationClient(getApplicationContext());
        myListener = new MyLocationListener();
        initOption();
        mLocationClient.registerLocationListener(myListener);
        mLocationClient.start();
        mLocationClient.requestLocation();
    }
    private void initOption() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        option.setScanSpan(5 * 1000);
        option.setOpenGps(true);
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }

    /**
     * 定位返回响应监听
     */
    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            Log.i("TAG_Location", "onReceiveLocation: " + location.getAddress().city);
            double lat = location.getLatitude();//得到纬度
            double longitude = location.getLongitude();//得到经度
            if(true){
                mLocationClient.unRegisterLocationListener(myListener);
                mLocationClient.stop();
            }
        }
    }
}
