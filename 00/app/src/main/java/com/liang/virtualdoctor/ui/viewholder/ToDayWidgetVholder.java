package com.liang.virtualdoctor.ui.viewholder;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.liang.virtualdoctor.R;
import com.liang.virtualdoctor.beans.WeatherData;
import com.liang.virtualdoctor.utils.HttpHelper;
import com.liang.virtualdoctor.utils.JsonParserUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/7/13.
 */
public class ToDayWidgetVholder {

    //today
    @BindView(R.id.weather_today_state)
    public TextView toDayState;
    @BindView(R.id.weather_today_graph)
    public ImageView toDayTempIcon;
    @BindView(R.id.weather_today_hightemp)
    public TextView toDayHigTemp;
    @BindView(R.id.weather_today_lowtemp)
    public TextView toDayLowTemp;

    public ToDayWidgetVholder(View view) {
        ButterKnife.bind(this, view);
    }



}

