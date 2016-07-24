package com.liang.virtualdoctor.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2016/6/28 0028.
 */
public class MyLinearLayout extends LinearLayout {
    public  RecyclerAdapter.OnPositionClick mOnClick ;
    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setClick(RecyclerAdapter.OnPositionClick mOnClick){
        this.mOnClick = mOnClick ;
        setOnClickListener(mOnClick);
    }



}
