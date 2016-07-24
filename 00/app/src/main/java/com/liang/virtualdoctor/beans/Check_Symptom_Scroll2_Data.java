package com.liang.virtualdoctor.beans;

import android.graphics.Bitmap;

/**
 * Created by from -sky on 2016/7/14.
 */
public class Check_Symptom_Scroll2_Data {
    private String title;
    private Bitmap bitmap;
    private String index;

    public Check_Symptom_Scroll2_Data(Bitmap bitmap, String index, String title) {
        this.bitmap = bitmap;
        this.index = index;
        this.title = title;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
