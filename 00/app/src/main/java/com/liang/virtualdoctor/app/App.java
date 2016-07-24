package com.liang.virtualdoctor.app;

import android.app.Application;
import android.content.SharedPreferences;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.liang.virtualdoctor.beans.WeatherData;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/10.
 * 初始化一些依赖库 some variable
 * but can't storge cache
 */
public class App extends Application {

    @Override
    public SharedPreferences getSharedPreferences(String name, int mode) {
        return super.getSharedPreferences(name, mode);
    }

    /**
     *global city Name
     */
    private String cityName="广州";

    public String getCurrentCityName() {
        return cityName;
    }

    public void setCurrentCityName(String cityName) {
        this.cityName = cityName;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化fresco
        Fresco.initialize(this);
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
