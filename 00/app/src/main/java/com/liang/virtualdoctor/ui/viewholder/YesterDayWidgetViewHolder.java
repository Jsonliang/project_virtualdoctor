package com.liang.virtualdoctor.ui.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.liang.virtualdoctor.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/7/13.
 */
public class YesterDayWidgetViewHolder {
    //yesterday
    @BindView(R.id.weather_yesterday_state)
    public TextView yesterDayState;
    @BindView(R.id.weather_yesterday_graph)
    public ImageView yesterTempIcon;
    @BindView(R.id.weather_yesterday_hightemp)
    public TextView yesterDayHigTemp;
    @BindView(R.id.weather_yesterday_lowtemp)
    public TextView yesterDayLowTemp;

    public YesterDayWidgetViewHolder(View view) {
        ButterKnife.bind(YesterDayWidgetViewHolder.this,view);

    }

}
