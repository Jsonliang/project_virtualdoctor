package com.liang.virtualdoctor.utils;

import android.content.Context;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2016/7/11 0011.
 */
public class UnitConvertUtils {

    public static int px2Dip(Context context,int pxValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5f);
    }

    public static int dip2Px(Context context,int dipValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dipValue * scale + 0.5f);
    }


    public static double decimalPlaces(int pointNum ,double targetNum) {
        BigDecimal bg = new BigDecimal(targetNum);
        double newNum = bg.setScale(pointNum, BigDecimal.ROUND_HALF_UP).doubleValue();

        return newNum;
    }
}
