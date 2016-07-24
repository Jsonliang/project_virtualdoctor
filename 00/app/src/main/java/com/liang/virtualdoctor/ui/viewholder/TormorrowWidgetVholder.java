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
public class TormorrowWidgetVholder {
    //today
    @BindView(R.id.weather_tormorrow_state)
    public TextView tormorrowDayState;
    @BindView(R.id.weather_tormorrow_graph)
    public ImageView tormorrowDayTempIcon;
    @BindView(R.id.weather_tormorrow_hightemp)
    public TextView tormorrowDayHigTemp;
    @BindView(R.id.weather_tormorrow_lowtemp)
    public TextView tormorrowDayLowTemp;

    public TormorrowWidgetVholder(View view) {
        ButterKnife.bind(TormorrowWidgetVholder.this,view);
    }
}
