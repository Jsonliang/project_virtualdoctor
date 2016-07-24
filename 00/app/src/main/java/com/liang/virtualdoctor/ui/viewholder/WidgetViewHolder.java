package com.liang.virtualdoctor.ui.viewholder;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.liang.virtualdoctor.R;
import com.liang.virtualdoctor.app.App;
import com.liang.virtualdoctor.beans.WeatherData;
import com.liang.virtualdoctor.fragments.SelfCheck_Fragment;
import com.liang.virtualdoctor.ui.activitys.WeatherActivity;
import com.liang.virtualdoctor.utils.Constants;
import com.liang.virtualdoctor.utils.HttpHelper;
import com.liang.virtualdoctor.utils.JsonParserUtils;
import com.liang.virtualdoctor.utils.SaveWeatherUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/7/13.
 */
public class WidgetViewHolder {
    private static App appContext;
    public YesterDayWidgetViewHolder yesVh;
    public ToDayWidgetVholder toDayVh;
    public TormorrowWidgetVholder tormVh;
    public TormorrowAfterViewHolder tormAVh;
    public WidgetViewHolder(YesterDayWidgetViewHolder yesViewHolder,
                            ToDayWidgetVholder toDayViewHolder,
                            TormorrowWidgetVholder torViewHolder,
                            TormorrowAfterViewHolder torAfterViewHolder,
                            App appContext) {
        this.toDayVh = toDayViewHolder;
        this.yesVh = yesViewHolder;
        this.tormAVh = torAfterViewHolder;
        this.tormVh = torViewHolder;
        this.appContext = appContext;

    }

    public void setWidgetDataByCityName(String cityName, String sPfKey, App appContext) {

        if( TextUtils.isEmpty(cityName))return ;
        /*if request city's weather where is current city
        * */
        if (appContext.getCurrentCityName().equals(cityName)){
            /*read weather data from  file
            * */
           weatherDatas= SaveWeatherUtils.getWeatherData(sPfKey,appContext);
            /*if selfcheck fragment did not update weather data
            * */
            if(weatherDatas==null&& Constants.DEFAULTCITY.equals(appContext.getCurrentCityName())){
                Toast.makeText(appContext,"网络连接失败",Toast.LENGTH_SHORT).show();
                return;
            }
            setTemp(weatherDatas);
            return;
        }
        downLoadWeatherDataByCityName(cityName);
    }


    /**
     * this method for give  every widget set value
     *
     * @param weatherDatas weather datas
     */
    public void setTemp(List<WeatherData> weatherDatas) {
        //today
        WeatherData weatherData = weatherDatas.get(0);
        toDayVh.toDayState.setText(weatherData.getText_day());
        toDayVh.toDayHigTemp.setText(weatherData.getHigh() + "℃");
        toDayVh.toDayLowTemp.setText(weatherData.getLow() + "℃");
        toDayVh.toDayTempIcon.setImageResource(getIconID(weatherData.getCode_day()));

        //tormorrow
        weatherData = weatherDatas.get(1);
        Log.i("TAG", "setTemp: tormVh.tormorrowDayState---》" + tormVh.tormorrowDayState);
        tormVh.tormorrowDayState.setText(weatherData.getText_day());
        tormVh.tormorrowDayLowTemp.setText(weatherData.getLow() + "℃");
        tormVh.tormorrowDayHigTemp.setText(weatherData.getHigh() + "℃");
        tormVh.tormorrowDayTempIcon.setImageResource(getIconID(weatherData.getCode_day()));
        //tormorrowAfter day
        weatherData = weatherDatas.get(2);
        tormAVh.tormorrowAfterDayState.setText(weatherData.getText_day());
        tormAVh.tormorrowAfterDayLowTemp.setText(weatherData.getLow() + "℃");
        tormAVh.tormorrowAfterDayHigTemp.setText(weatherData.getHigh() + "℃");
        tormAVh.tormorrowAfterDayTempIcon.setImageResource(getIconID(weatherData.getCode_day()));
    }


    /**
     * @param code icon code
     * @return
     */
  public  static   int[] imageResource = {R.drawable.zero, R.drawable.one, R.drawable.two,
            R.drawable.there, R.drawable.four, R.drawable.five,
            R.drawable.six, R.drawable.seven, R.drawable.eight,
            R.drawable.nine, R.drawable.ten, R.drawable.eleven,
            R.drawable.twelve, R.drawable.thirteen, R.drawable.fourteen,
            R.drawable.fifteen, R.drawable.sixteen, R.drawable.seveteen,
            R.drawable.eighteen, R.drawable.nineteen, R.drawable.twenty,
            R.drawable.twentyone, R.drawable.twentytwo, R.drawable.twentythere,
            R.drawable.twentyfour, R.drawable.twentfive, R.drawable.twentysix,
            R.drawable.twentseven, R.drawable.twenteight, R.drawable.twentynine,
            R.drawable.thirty, R.drawable.thirtyone, R.drawable.thirtytwo,
            R.drawable.thirtythere, R.drawable.thirtyfour, R.drawable.thirtyfive,
            R.drawable.thirtsix, R.drawable.thirtseven, R.drawable.thirteight
            , R.drawable.ninetynine,};

    /**
     * get  today weather data update selfFragment weather widget
     *
     * @return
     */
    public Bundle getTodyWeatherDatas() {
        Bundle bundle = new Bundle();
        if (weatherDatas == null) return null;
        WeatherData weatherData = weatherDatas.get(0);

        bundle.putParcelable(SelfCheck_Fragment.
                RENTURN_WEATHERKEY, weatherData);
        bundle.putInt(SelfCheck_Fragment.RENTURN_IMAGEID,
                getIconID(weatherData.getCode_day()));

        return bundle;

    }

    public static int getIconID(String code) {
        return imageResource[Integer.parseInt(code)];
    }
        /*//lightning
        if (code.equals("16") || code.equals("17") || code.equals("18")
                || code.equals("34") || code.equals("35") || code.equals("36")) {
            return R.drawable.daytime_lightning;
        }
        // sunny
        if (code.equals("0") || code.equals("2") || code.equals("8")) {
            return R.drawable.daytime_sunny;
        }
        //rainning
        if (code.equals("10") || code.equals("11") || code.equals("12") ||
                code.equals("13") || code.equals("14") || code.equals("15") ||
                code.equals("19") || code.equals("20")) {
            return R.drawable.daytime_rainning;
        }*/

    private List<WeatherData> weatherDatas = null;

    private void downLoadWeatherDataByCityName(String cityName) {
        /**
         * acording cityName download weather data
         * @param cityName
         * @return
         */
        String url = "https://api.thinkpage.cn/v3/weather/daily.json?" +
                "key=h3h2bsufdpl600e6&language=zh-Hans&unit=c&start=0" +
                "&days=3&location=" + cityName;
        HttpHelper.getInstance().requestByGet(url, new HttpHelper.StringCallBack() {
            @Override
            public void onFailure(Exception e) {
                Log.i("TAG", "onFailure: " + e.getMessage());
            }
            @Override
            public void onResult(Object string) {
                String json = (String) string;
                weatherDatas = JsonParserUtils.parseJson2WeatherList(json);
                // Log.i("TAG", "onResult: " + listWeather.get(0).toString());
                //when data return updata temp
                if (weatherDatas != null && weatherDatas.size() != 0) {
                    /*set the weater datalist in global variable
                    */
                    setTemp(weatherDatas);
                    SaveWeatherUtils.saveWeather(weatherDatas,null,appContext);
                }
            }
        });
        appContext.setCurrentCityName(cityName);
    }




}
