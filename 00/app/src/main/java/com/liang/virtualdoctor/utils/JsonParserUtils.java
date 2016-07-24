package com.liang.virtualdoctor.utils;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.liang.virtualdoctor.beans.DoctorInfo;
import com.liang.virtualdoctor.beans.Subject;
import com.liang.virtualdoctor.beans.WeatherData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarOutputStream;

/**
 * Created by Administrator on 2016/7/9 0009.
 */
public class JsonParserUtils {

    /**
     * DoctorInfo Bean
     *
     * @param jsonStr
     * @return
     */
    public static List<DoctorInfo> parserJSON2DoctorInfo(String jsonStr) {
        List<DoctorInfo> doctorInfos;
        try {
            JSONObject object = new JSONObject(jsonStr);
            JSONArray array = object.getJSONArray("data");

            doctorInfos = com.alibaba.fastjson.JSONObject.parseArray(array.toString(),
                    DoctorInfo.class);
            return doctorInfos;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * parser String to Subject bean
     *
     * @param jsonStr
     * @return
     */
    public static List<Subject> parserJSON2Subject(String jsonStr) {
        List<Subject> list = new ArrayList<>();
        //substring start index
        int startindex = jsonStr.indexOf("(") + 1;
        //substring end index
        int endindex = jsonStr.indexOf(")");
        String json = jsonStr.substring(startindex, endindex);
        try {
            JSONObject object = new JSONObject(json);
            JSONArray array = object.getJSONArray("data");

            for (int i = 0; i < array.length(); i++) {
                Subject sub = new Subject();
                sub.setValue0(array.getJSONObject(i).optInt("0"));
                sub.setValue1(array.getJSONObject(i).optString("1"));
                int id;
                try {
                    id = array.getJSONObject(i).getInt("id");
                } catch (JSONException e) {
                    JSONArray array1 = array.getJSONObject(i).getJSONArray("id");
                    id = array1.optInt(1);
                }
                Log.i("TAG", "parserJSON2Subject: " + id);
                sub.setId(id);
                sub.setName(array.getJSONObject(i).optString("name"));
                list.add(sub);
            }
            Log.i("TAG", "parserJSON2Subject: " + list.toString());
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param jsonStr
     * @return weather list;
     */
    public static List<WeatherData> parseJson2WeatherList(String jsonStr) {
        List<WeatherData> weatherDatas ;//= new ArrayList<>();
        try {
          JSONObject object = new JSONObject(jsonStr);
            JSONArray resObj = object.getJSONArray("results");
           JSONObject obj= resObj.getJSONObject(0);
            JSONArray jsonArray=  obj.getJSONArray("daily");
           return weatherDatas= com.alibaba.fastjson.JSONObject.
                   parseArray(jsonArray.toString(),WeatherData.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

     public static List<String> getCitysList(String cityStr){
       List<String>  citys = null;
       try {
           JSONObject objects = new JSONObject(cityStr);
           JSONObject object = objects.getJSONObject("result");
           JSONArray  citylist = object.getJSONArray("cityList");
           JSONObject  city = null ;
           citys = new ArrayList<>();
           for(int i= 0 ;i < citylist.length() ;i++){
               city = citylist.getJSONObject(i);
              citys.add(city.optString("cityName"));
           }
       } catch (JSONException e) {
           e.printStackTrace();
       }

       return citys ;
   }
}
