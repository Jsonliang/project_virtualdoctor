package com.liang.virtualdoctor.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by Administrator on 2016/7/14 0014.
 */
public class ScreenWidthUtils {

    public static int getScreenWidth(Context context){
        int mWrapperWidth ;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        mWrapperWidth =  dm.widthPixels ; //得到屏幕的宽

       return mWrapperWidth ;
    }

    public static int getScreenHeight(Context context){
        int mWrapperHeight ;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        mWrapperHeight =  dm.heightPixels ; //得到屏幕的宽

        return mWrapperHeight ;
    }
}
