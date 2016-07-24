package com.liang.virtualdoctor.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.liang.virtualdoctor.R;
import com.liang.virtualdoctor.adapter.RecyclerAdapter;
import com.liang.virtualdoctor.app.App;
import com.liang.virtualdoctor.beans.WeatherData;
import com.liang.virtualdoctor.ui.activitys.BodyMapCheckActivity;
import com.liang.virtualdoctor.ui.activitys.ExamineActivity;
import com.liang.virtualdoctor.ui.activitys.SearchActivity;
import com.liang.virtualdoctor.ui.activitys.WeatherActivity;
import com.liang.virtualdoctor.ui.customviews.Check_Symptom_Scroll;
import com.liang.virtualdoctor.ui.viewholder.WidgetViewHolder;
import com.liang.virtualdoctor.utils.Constants;
import com.liang.virtualdoctor.utils.HttpHelper;
import com.liang.virtualdoctor.utils.JsonParserUtils;
import com.liang.virtualdoctor.utils.SaveWeatherUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/7/9 0009.
 */
public class SelfCheck_Fragment extends Fragment {

    /**
     * this fragment request weather data request code
     */
    public static final int SELFCHECK_REQUESTWEATER_CODE = 200;
    public static final int SELFCHECK_CODE = 200;
    /**
     * weahter ativity return weahter data
     */
    public static final String RENTURN_WEATHERKEY = "WeatherData";
    public static final String RENTURN_IMAGEID = "ImageID";
    public static final String BUNDLEKEY = "bundleKey";
    private View mLayout ;
    private ViewHolder holder ;
    private Intent intent ;
    private App appCotext;
    private HttpHelper helper ;

    private RecyclerView mRecycler ;
    private RecyclerAdapter  mAdapter ;
    private List<String> data =  new ArrayList<>();

    private Check_Symptom_Scroll mScroll ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mLayout = inflater.inflate(R.layout.fragment_selfcheck_layout, container, false);
        holder = new ViewHolder();
        ButterKnife.bind(holder, mLayout);

        mRecycler = (RecyclerView) mLayout.findViewById(R.id.selfcheck_RecyclerView);
        mScroll = (Check_Symptom_Scroll) mLayout.findViewById(R.id.selfcheck_examine_infos);
        appCotext = (App) this.getActivity().getApplicationContext();

        holder.getWeatherData();
        return mLayout;
    }

    public boolean isNetworkAvailable() {
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager)
                appCotext.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return false;
        } else {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
                   /* System.out.println(i + "===状态===" + networkInfo[i].getState());
                    System.out.println(i + "===类型===" + networkInfo[i].getTypeName());
                    System.out.println(i + "===活动?===" + networkInfo[i].isAvailable());*/
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.
                            CONNECTED && networkInfo[i].isAvailable()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void onResume() {
        initRecyclerView();

        super.onResume();
    }

    public class ViewHolder{
        /**
         * 天气
         */
        @BindView(R.id.weather_bg)
        public ImageView mWeather_bg ;  //天气背景图,单击可跳转到天气详情页面
        @BindView(R.id.weather_tv_place)
        public TextView mWeather_place ; //显示地方
        @BindView(R.id.weather_temperature)
        public TextView mWeather_temperature ;// 显示温度状况
        //weather icon
        @BindView(R.id.selfcheck_weather_icon)
        public ImageView weatherIcon;
        /**
         * check and search
         */
        @BindView(R.id.selfcheck_body_check_symptom)
        public ImageButton  mCheckBody ; // 自检身体
        @BindView(R.id.selfcheck_examine_common)
        public ImageButton mExamine ;//标准查看
        @BindView(R.id.selfcheck_search)
        public TextView mTv_search ; //搜索

        public ViewHolder() {
        }

        /**
         * 单击事件监听
         * @param view
         */
        @OnClick({R.id.weather_bg,R.id.selfcheck_search,R.id.selfcheck_body_check_symptom,
                R.id.selfcheck_examine_common})
        public void onClick(View view){
            switch (view.getId()){
                case R.id.weather_bg:
                    intent = new Intent(getActivity(), WeatherActivity.class);
                    //TODO
                    // 这个位置可能需要传递天气参数
                    startActivityForResult(intent, SELFCHECK_REQUESTWEATER_CODE);
                           
                    break ;
                case R.id.selfcheck_search:
                    //TODO
                    intent = new Intent(getActivity(), SearchActivity.class);
                    startActivity(intent);
                    break;
                case R.id.selfcheck_body_check_symptom:
                    //TODO
                    intent = new Intent(getActivity(), BodyMapCheckActivity.class);
                    startActivity(intent);
                    break;
                case R.id.selfcheck_examine_common:
                    //TODO
                    intent = new Intent(getActivity(), ExamineActivity.class);
                    startActivity(intent);
                    break;
                default:

            }
        }


        /**
         * 获取天气数据
         */
        private void getWeatherData() {
            if (isNetworkAvailable()) {
                helper = HttpHelper.getInstance();
            /*if did not set this when in this page click image this will finish
            * */
                onLoadingWDSetView();
                helper.requestByGet(Constants.DEFAULT_WEATHER, new HttpHelper.StringCallBack() {
                    @Override
                    public void onFailure(Exception e) {
                      new Handler(Looper.getMainLooper()).post(new Runnable() {
                          @Override
                          public void run() {
                              Toast.makeText(getContext(), "天气数据获取失败，请保持网络通畅!",
                                      Toast.LENGTH_SHORT).show();
                              mWeather_bg.setClickable(true);
                              weatherIcon.setVisibility(View.INVISIBLE);
                              mWeather_temperature.setVisibility(View.INVISIBLE);
                          }
                      });
                        return;
                    }
                    @Override
                    public void onResult(Object string) {
                        String weatherObj = (String) string;
                        List<WeatherData> list = JsonParserUtils.parseJson2WeatherList(weatherObj);
                        if (list != null) {
                        /*save weather data in sharepreference
                        * */
                        SaveWeatherUtils.saveWeather(list,null,appCotext);
                        /*get today weather data
                        * */
                        WeatherData todayWeather=list.get(0);
                        /*get the temperature icon
                        * */
                       int imageResID= WidgetViewHolder.getIconID(todayWeather.getCode_day());
                        /*update widget
                        * */
                            bindViewHolder(imageResID, todayWeather);
                            lodedWDSetView();
                            appCotext.setCurrentCityName(Constants.DEFAULTCITY);
                            mWeather_bg.setClickable(true);
                        } else {
                            Toast.makeText(getContext(), "天气数据获取失败", Toast.LENGTH_SHORT).show();
                            mWeather_bg.setClickable(true);
                            return;
                        }

                    }
                });
            } else {
                Toast.makeText(appCotext, "请检查网络连接", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        private void onLoadingWDSetView(){
            mWeather_bg.setClickable(false);
            weatherIcon.setVisibility(View.INVISIBLE);
            mWeather_temperature.setVisibility(View.INVISIBLE);
            mWeather_bg.setImageResource(R.drawable.bg_weather_onloading);
        }
        private void lodedWDSetView(){
            mWeather_bg.setClickable(true);
            weatherIcon.setVisibility(View.VISIBLE);
            mWeather_temperature.setVisibility(View.VISIBLE);
            mWeather_bg.setImageResource(R.drawable.bg_home_weather);
        }
    }

   private void initRecyclerView(){
       String[] strings= getContext().getResources().getStringArray(R.array.array_summary);
       List<String> list = Arrays.asList(strings);
       data.addAll(list);
       mAdapter = new RecyclerAdapter(data);
       mRecycler.setAdapter(mAdapter);
       mRecycler.setLayoutManager(new GridLayoutManager(getContext(),
               4, LinearLayoutManager.HORIZONTAL, false));
       mRecycler.setItemAnimator(new DefaultItemAnimator());
       scrollCallBackListener();
   }

    private void scrollCallBackListener() {
        mScroll.setScrollCallBackListener(new Check_Symptom_Scroll.ScrollCallBackListener() {
            @Override
            public void scrollCallBack(int index) {
                String[] string;
                switch (index) {
                    case 0:
                        string = getContext().getResources().getStringArray(R.array.array_man);
                        notifyAdapter(string);
                        break;
                    case 1:
                        string = getContext().getResources().getStringArray(R.array.array_woman);
                        notifyAdapter(string);
                        break;
                    case 2:
                        string = getContext().getResources().getStringArray(R.array.array_summary);
                        notifyAdapter(string);
                        break;
                    case 3:
                        string = getContext().getResources().getStringArray(R.array.array_oldman);
                        notifyAdapter(string);
                        break;
                    case 4:
                        string = getContext().getResources().getStringArray(R.array.array_child);
                        notifyAdapter(string);
                        break;
                    default:
                }
            }
        });
    }

    private void notifyAdapter(String[] string) {
        List<String> list = Arrays.asList(string);
        data.clear();
        data.addAll(list);
        mAdapter.notifyDataSetChanged();
    }


    /**when the weather activity finished ,callback the weather date by this method
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            if (requestCode == SELFCHECK_REQUESTWEATER_CODE && resultCode == Activity.RESULT_OK) {
                Bundle bundle = data.getBundleExtra(SelfCheck_Fragment.BUNDLEKEY);
                if (bundle != null) {
                    /*get weather value
                    * */
                    WeatherData todayWeather = bundle.getParcelable(RENTURN_WEATHERKEY);
                    int imageResID = bundle.getInt(RENTURN_IMAGEID);
                    bindViewHolder(imageResID, todayWeather);
                    /**
                     * weather activity update weather failure
                     */
                } else {
                    //Toast.makeText(getContext(),"天气数据更新失败",Toast.LENGTH_SHORT).show();
                    /**
                     * use default city get weather data
                     */
                    holder.getWeatherData();
                    return;
                }


            }
        } else {
            Toast.makeText(getContext(), "天气数据更新失败", Toast.LENGTH_SHORT).show();
            return;
        }

    }






    /**
     * bind the widget
     *
     * @param imageResID
     * @param todayWeather
     */
    private void bindViewHolder(int imageResID, WeatherData todayWeather) {
        /*set widget value* */
        holder.weatherIcon.setImageResource(imageResID);
        String low = todayWeather.getLow();
        String high = todayWeather.getHigh();
        String cityName = ((App) getActivity().getApplicationContext()).getCurrentCityName();
        holder.mWeather_temperature.setText(low + "℃/" + high + "℃");
        /**
         * get city name from global variable
         */
        holder.mWeather_place.setText(cityName);
    }
}

