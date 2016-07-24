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
public class TormorrowAfterViewHolder {
    //today
    @BindView(R.id.weather_tomafter_state)
    public TextView tormorrowAfterDayState;
    @BindView(R.id.weather_tomafter_graph)
    public ImageView tormorrowAfterDayTempIcon;
    @BindView(R.id.weather_tomafter_hightemp)
    public TextView tormorrowAfterDayHigTemp;
    @BindView(R.id.weather_tomafter_lowtemp)
    public TextView tormorrowAfterDayLowTemp;

    public TormorrowAfterViewHolder(View view) {
        ButterKnife.bind(TormorrowAfterViewHolder.this,view);
    }

}
