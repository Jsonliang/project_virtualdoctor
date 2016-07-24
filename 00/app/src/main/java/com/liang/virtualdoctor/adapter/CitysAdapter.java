package com.liang.virtualdoctor.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.liang.virtualdoctor.R;

import java.util.List;

/**
 * Created by Administrator on 2016/7/13 0013.
 */
public class CitysAdapter extends BaseAdapter {
    private List<String> citys = null;
    private List<String>  firstWorlds ;
    public CitysAdapter(List<String> citys,List<String>  firstWorlds) {
        this.citys = citys;
        this.firstWorlds = firstWorlds ;
    }

    @Override
    public int getCount() {
        return citys == null ? 0 : citys.size();
    }

    @Override
    public String getItem(int position) {
        return citys.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder  = null;
       if(view == null){
           view = LayoutInflater.from(parent.getContext()).inflate(
                   R.layout.citychocie_item,parent,false);
           holder = new ViewHolder();
           holder.tv_FirstWorld = (TextView) view.findViewById(R.id.city_firstname);
           holder.tv_CityName = (TextView) view.findViewById(R.id.city_cityname);
           view.setTag(holder);
       }


        holder = (ViewHolder) view.getTag();

        if(position != 0 &&
                firstWorlds.get(position).equals(firstWorlds.get(position -1))){
            holder.tv_FirstWorld.setVisibility(View.GONE);
        }else{
            holder.tv_FirstWorld.setVisibility(View.VISIBLE);
            holder.tv_FirstWorld.setText(firstWorlds.get(position)+"");
        }

        holder.tv_CityName.setText(citys.get(position)+"");
        return view;
    }

    public class ViewHolder {
        private TextView tv_FirstWorld;
        private TextView tv_CityName;
    }
}
