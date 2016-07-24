package com.liang.virtualdoctor.ui.activitys;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.liang.virtualdoctor.R;
import com.liang.virtualdoctor.app.App;
import com.liang.virtualdoctor.fragments.SelfCheck_Fragment;
import com.liang.virtualdoctor.ui.viewholder.ToDayWidgetVholder;
import com.liang.virtualdoctor.ui.viewholder.TormorrowAfterViewHolder;
import com.liang.virtualdoctor.ui.viewholder.TormorrowWidgetVholder;
import com.liang.virtualdoctor.ui.viewholder.WidgetViewHolder;
import com.liang.virtualdoctor.ui.viewholder.YesterDayWidgetViewHolder;
import com.liang.virtualdoctor.utils.Constants;
import com.liang.virtualdoctor.utils.SaveWeatherUtils;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WeatherActivity extends AppCompatActivity {
    /*City name request code
    * */
    public static final int CITYNAME_REQUEST_CODE = 100;
    public static final String STORGE_WEATHERKEY="storgeWeather";
    public static final String STORGE_IMAGEID = "storgeImage";
    /**
     * ToolBar 初始化
     */
    @BindView(R.id.toolbar_rightText)
    public TextView mCity;
    @BindView(R.id.toolbar_goback)
    public TextView mGoBack;
    @BindView(R.id.toolbar_centerText)
    public TextView mTitle;
    /**
     * 初始化 RadioButton 控件
     */
    @BindViews({R.id.weather_radio_today, R.id.weather_radio_tomorrow, R.id.weather_radio_tomorrowAfter})
    public RadioButton[] mDates;

    /**
     * dates 布局
     */
    @BindViews({R.id.weather_layout_today, R.id.weather_layout_tomorrow,
            R.id.weather_layout_tomorrowafter})
    public LinearLayout[] mLayouts;
    private WidgetViewHolder viewHolder;
    private App appContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);
        appContext = (App) getApplicationContext();
        initToolBar();

        //find wedgit
        viewHolder = getWidgetFromLayouts(layouts);
            //viewHolder.setTemp();
            viewHolder.setWidgetDataByCityName(appContext.getCurrentCityName(),
                    STORGE_WEATHERKEY,appContext);


    }

    @Override
    protected void onResume() {
        super.onResume();
    }
//0x7f0c0120

    private void initToolBar() {
        mTitle.setVisibility(View.VISIBLE);
        mTitle.setText("天气小医生");
        //城市单击事件
        mCity.setVisibility(View.VISIBLE);
        mCity.setText(appContext.getCurrentCityName());
        mCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WeatherActivity.this, CityChoiceActivity.class);
                /*Request the city weather where's you want
                by jack
                * */
                startActivityForResult(intent, CITYNAME_REQUEST_CODE);
            }
        });
        //返回事件
        mGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnCityName();
            }
        });
    }

    /**
     *
     */
    public void returnCityName() {
        Intent data = new Intent();
        data.setClass(WeatherActivity.this, SelfCheck_Fragment.class);
        Bundle bundle= viewHolder.getTodyWeatherDatas();

        /*Bundle bundle=new Bundle();
        bundle.putParcelable(SelfCheck_Fragment.BUNDLEKEY,
                context.getWeatherDatas().get(0));*/

        data.putExtra(SelfCheck_Fragment.BUNDLEKEY,bundle);
        setResult(RESULT_OK, data);
        WeatherActivity.this.finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (data != null) {
            //this Activity get weather data from CityChoiceActivity
            if (requestCode == (CITYNAME_REQUEST_CODE) && resultCode == CityChoiceActivity.CITYNAME_RETURN_CODE) {

                String returnCityName = data.getExtras().getString(CityChoiceActivity.CITYNAMEKEY);
                /**
                 * Update city's weather by city name(cityChoice activity return)
                 */
                if (appContext.getCurrentCityName().equals(returnCityName)) {
                    return;
                } else {
                    Toast.makeText(getApplicationContext(), returnCityName, Toast.LENGTH_SHORT).show();

                    if(TextUtils.isEmpty(returnCityName)){
                        Toast.makeText(getApplicationContext(),"联网失败",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    mCity.setText(returnCityName);
                    viewHolder.setWidgetDataByCityName(returnCityName,STORGE_WEATHERKEY,appContext);

                    /*update global variable
                    * */
                   // appContext.setCurrentCityName(returnCityName);
                }
            }
            //来自切换城市的数据
            if (requestCode == (CityChoiceActivity.CITYNAME_RETURN_CODE
                    + RESULT_FIRST_USER)) {
                if (resultCode == RESULT_OK) {
                    String cityName = data.getStringExtra("city");
                    //mCity.setText(cityName + null);
                    //update city weather
                    // viewHolder.setWidgetDataByCityName("广州");

                }
            }
        }
       //  super.onActivityResult(requestCode, resultCode, data);
    }


    @OnClick({R.id.weather_radio_today, R.id.weather_radio_tomorrow,
            R.id.weather_radio_tomorrowAfter})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.weather_radio_today:
                setCheckedColor(R.id.weather_radio_today);
                break;
            case R.id.weather_radio_tomorrow:
                setCheckedColor(R.id.weather_radio_tomorrow);
                break;
            case R.id.weather_radio_tomorrowAfter:
                setCheckedColor(R.id.weather_radio_tomorrowAfter);
                break;
            default:
        }
    }

    private void setCheckedColor(int id) {
        for (int i = 0; i < mDates.length; i++) {
            if (mDates[i].getId() == id) {
                mDates[i].setTextColor(Color.parseColor("#000000"));
                mLayouts[i].setBackgroundResource(R.color.colorWeatherDate);
            } else {
                mDates[i].setTextColor(Color.parseColor("#ffffff"));
                mLayouts[i].setBackgroundResource(R.color.colorTransparent);
            }

        }
    }

    /**
     * @param layouts Layout id array
     * get weather layout all widget
     */
    public int[] layouts = {R.id.weather_layout_yesterday, R.id.weather_layout_today,
            R.id.weather_layout_tomorrow, R.id.weather_layout_tomorrowafter};

    private WidgetViewHolder getWidgetFromLayouts(int[] layouts) {
        YesterDayWidgetViewHolder yesVH;
        ToDayWidgetVholder toDayVh;
        TormorrowWidgetVholder tormorVh;
        TormorrowAfterViewHolder torAfterVH;

        View view = findViewById(layouts[0]);
        yesVH = new YesterDayWidgetViewHolder(view);
        view = findViewById(layouts[1]);
        toDayVh = new ToDayWidgetVholder(view);
        view = findViewById(layouts[2]);
        tormorVh = new TormorrowWidgetVholder(view);
        view = findViewById(layouts[3]);
        torAfterVH = new TormorrowAfterViewHolder(view);

        return new WidgetViewHolder(yesVH, toDayVh, tormorVh,
                torAfterVH,appContext);
    }
}
