package com.liang.virtualdoctor.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.liang.virtualdoctor.app.App;
import com.liang.virtualdoctor.beans.WeatherData;
import com.liang.virtualdoctor.ui.activitys.WeatherActivity;

import java.util.List;

/**
 * Created by Administrator on 2016/7/14.
 */
public class SaveWeatherUtils {

    public  static  void saveWeather(List<WeatherData> weatherData, int[] imageRes, App appContext){
        SharedPreferences sPf= appContext.getSharedPreferences
                ("weather", Context.MODE_PRIVATE);
        clearWeatherData(appContext);
        if(weatherData==null)return;
        String  jsonObj=  JSON.toJSONString(weatherData,
                SerializerFeature.DisableCircularReferenceDetect);
        SharedPreferences.Editor editor= sPf.edit();
        editor.putString(WeatherActivity.STORGE_WEATHERKEY,jsonObj).commit();

    }
    public static List<WeatherData> getWeatherData(String sPfKey,App appContext){
        if(sPfKey==null)return null;
        SharedPreferences  sPf= appContext.getSharedPreferences
                ("weather",Context.MODE_PRIVATE);
        String obj= sPf.getString(sPfKey,null);
        if(obj==null)return null;
        List<WeatherData> weatherDatas=null;
        return weatherDatas=JSON.parseArray(obj,WeatherData.class);
    }
    public static boolean clearWeatherData(App appContext){
        SharedPreferences  sPf= appContext.getSharedPreferences
                ("weather",Context.MODE_PRIVATE);
        return  sPf.edit().clear().commit();
    }

}
