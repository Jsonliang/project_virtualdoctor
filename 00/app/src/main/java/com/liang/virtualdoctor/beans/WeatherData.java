package com.liang.virtualdoctor.beans;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.liang.virtualdoctor.utils.HttpHelper;
import com.liang.virtualdoctor.utils.JsonParserUtils;

import org.xml.sax.helpers.LocatorImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/7/11 0011.
 * weather url:https://api.thinkpage.cn/v3/weather/daily.json?key=h3h2bsufdpl600e6&location=
 * 广州&language=zh-Hans&unit=c&start=0&days=3
 */
public class WeatherData implements Parcelable {

    /**
     * daily : [{"code_day":"14","code_night":"14","date":"2016-07-13","high":"32","low":"25","precip":"","text_day":"中雨","text_night":"中雨","wind_direction":"无持续风向","wind_direction_degree":"","wind_scale":"2","wind_speed":"10"},{"code_day":"13","code_night":"13","date":"2016-07-14","high":"33","low":"25","precip":"","text_day":"小雨","text_night":"小雨","wind_direction":"无持续风向","wind_direction_degree":"","wind_scale":"2","wind_speed":"10"},{"code_day":"13","code_night":"13","date":"2016-07-15","high":"34","low":"26","precip":"","text_day":"小雨","text_night":"小雨","wind_direction":"无持续风向","wind_direction_degree":"","wind_scale":"2","wind_speed":"10"}]
     * last_update : 2016-07-13T08:00:00+08:00
     * location : {"country":"CN","id":"WS0E9D8WN298","name":"广州","path":"广州,广州,广东,中国","timezone":"Asia/Shanghai","timezone_offset":"+08:00"}
     */

    /**
     * code_day : 14
     * code_night : 14
     * date : 2016-07-13
     * high : 32
     * low : 25
     * precip :
     * text_day : 中雨
     * text_night : 中雨
     * wind_direction : 无持续风向
     * wind_direction_degree :
     * wind_scale : 2
     * wind_speed : 10
     */

    private String code_day;
    private String code_night;
    private String date;
    private String high;
    private String low;
    private String precip;
    private String text_day;
    private String text_night;
    private String wind_direction;
    private String wind_direction_degree;
    private String wind_scale;
    private String wind_speed;

    public WeatherData() {
    }

    protected WeatherData(Parcel in) {
        code_day = in.readString();
        code_night = in.readString();
        date = in.readString();
        high = in.readString();
        low = in.readString();
        precip = in.readString();
        text_day = in.readString();
        text_night = in.readString();
        wind_direction = in.readString();
        wind_direction_degree = in.readString();
        wind_scale = in.readString();
        wind_speed = in.readString();
    }

    public static final Creator<WeatherData> CREATOR = new Creator<WeatherData>() {
        @Override
        public WeatherData createFromParcel(Parcel in) {
            return new WeatherData(in);
        }

        @Override
        public WeatherData[] newArray(int size) {
            return new WeatherData[size];
        }
    };

    public String getCode_day() {
        return code_day;
    }

    public void setCode_day(String code_day) {
        this.code_day = code_day;
    }

    public String getCode_night() {
        return code_night;
    }

    public void setCode_night(String code_night) {
        this.code_night = code_night;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getPrecip() {
        return precip;
    }

    public void setPrecip(String precip) {
        this.precip = precip;
    }

    public String getText_day() {
        return text_day;
    }

    public void setText_day(String text_day) {
        this.text_day = text_day;
    }

    public String getText_night() {
        return text_night;
    }

    public void setText_night(String text_night) {
        this.text_night = text_night;
    }

    public String getWind_direction() {
        return wind_direction;
    }

    public void setWind_direction(String wind_direction) {
        this.wind_direction = wind_direction;
    }

    public String getWind_direction_degree() {
        return wind_direction_degree;
    }

    public void setWind_direction_degree(String wind_direction_degree) {
        this.wind_direction_degree = wind_direction_degree;
    }

    public String getWind_scale() {
        return wind_scale;
    }

    public void setWind_scale(String wind_scale) {
        this.wind_scale = wind_scale;
    }

    public String getWind_speed() {
        return wind_speed;
    }

    public void setWind_speed(String wind_speed) {
        this.wind_speed = wind_speed;
    }

    @Override
    public String toString() {
        return "DailyBean{" +
                "code_day='" + code_day + '\'' +
                ", code_night='" + code_night + '\'' +
                ", date='" + date + '\'' +
                ", high='" + high + '\'' +
                ", low='" + low + '\'' +
                ", precip='" + precip + '\'' +
                ", text_day='" + text_day + '\'' +
                ", text_night='" + text_night + '\'' +
                ", wind_direction='" + wind_direction + '\'' +
                ", wind_direction_degree='" + wind_direction_degree + '\'' +
                ", wind_scale='" + wind_scale + '\'' +
                ", wind_speed='" + wind_speed + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(code_day);
        dest.writeString(code_night);
        dest.writeString(date);
        dest.writeString(high);
        dest.writeString(low);
        dest.writeString(precip);
        dest.writeString(text_day);
        dest.writeString(text_night);
        dest.writeString(wind_direction);
        dest.writeString(wind_direction_degree);
        dest.writeString(wind_scale);
        dest.writeString(wind_speed);
    }
}




