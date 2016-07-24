package com.liang.virtualdoctor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.liang.virtualdoctor.R;
import com.liang.virtualdoctor.beans.Examine_Activity_ListView_Data;

import java.util.List;

/**
 * Created by from -sky on 2016/7/14.
 */
public class Examine_Activity_ListView_Adapter extends BaseAdapter {
    List<Examine_Activity_ListView_Data> list;
    Context context;
    LayoutInflater layoutInflater;
    public Examine_Activity_ListView_Adapter(List<Examine_Activity_ListView_Data> list, Context context) {
        this.list = list;
        this.context = context;
        layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            convertView=layoutInflater.inflate(R.layout.activity_examine_litem_layout,null);
            viewHolder=new ViewHolder();
            viewHolder.name= (TextView) convertView.findViewById(R.id.activity_examline_litem_layout_name);
            viewHolder.normal= (TextView) convertView.findViewById(R.id.activity_examline_litem_layout_normal);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(list.get(position).getName());
        viewHolder.normal.setText(list.get(position).getNormalValue());
        return convertView;
    }
    class  ViewHolder{
        TextView name;
        TextView normal;
    }
}
