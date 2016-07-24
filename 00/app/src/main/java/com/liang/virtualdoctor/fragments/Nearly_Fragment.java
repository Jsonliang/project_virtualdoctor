package com.liang.virtualdoctor.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.poi.PoiSortType;
import com.baidu.mapapi.utils.DistanceUtil;
import com.liang.virtualdoctor.R;
import com.liang.virtualdoctor.fragments.nearly_fragment.overlayutil.PoiOverlay;
import com.liang.virtualdoctor.ui.activitys.CityChoiceActivity;
import com.liang.virtualdoctor.utils.CalcLatLngDistanceUtils;
import com.liang.virtualdoctor.utils.UnitConvertUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/7/9 0009.
 */
public class Nearly_Fragment extends Fragment{
    public static final int  NEARLY_REQUESTCODE = 1000 ;
    private boolean DEFAULT = true ;
    private View mLayout ;
    @BindView(R.id.drug_infos_layout)
    public View mDrugStoreInfoLayout;
    @BindView(R.id.drug_name)
    public TextView mTv_StoreName; // 药店名
    @BindView(R.id.drug_address)
    public TextView mTv_StoreAddress; // 地址
    @BindView(R.id.drug_distance)
    public TextView mTv_Store_Distance; // 距离
    @BindView(R.id.drug_info_phone)
    public TextView mTv_Store_Phone; // 电话
    @BindView(R.id.drug_info_route)
    public TextView mTv_Store_Route;//线路

    @BindView(R.id.nearly_place)
    public TextView mTv_CityChoice ; //

    private PoiSearch poiSearch; //检索封装的信息
    private List<PoiInfo> searchInfo;
    public MapView mMap;
    public BaiduMap mBaiduMap;
    public Overlay currentOverlay ; //当前覆盖物
    //覆盖物图片资源
    private int[] overLayout = new int[]{
            R.drawable.poi_mark_1, R.drawable.poi_mark_2, R.drawable.poi_mark_3, R.drawable.poi_mark_4
            , R.drawable.poi_mark_5, R.drawable.poi_mark_6, R.drawable.poi_mark_7, R.drawable.poi_mark_8
            , R.drawable.poi_mark_9, R.drawable.poi_mark_10};

    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = null;
    private LatLng mLatLng = new LatLng(23.120113,113.307671); //默认广州
    @BindViews({R.id.nearly_menu_hos,R.id.nearly_menu_outp,R.id.nearly_menu_drugstore})
    public RadioButton[] mMenu ;
    private String cityName  = "广州";
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            final int index = msg.arg1;
            mDrugStoreInfoLayout.setVisibility(View.VISIBLE);
            mTv_StoreName.setText((index+1) + "." + searchInfo.get(index).name + "");
            mTv_StoreAddress.setText(searchInfo.get(index).address + "");
            /*double distance = CalcLatLngDistanceUtils.Distance(mLatLng.longitude,mLatLng.latitude,
                    searchInfo.get(index).location.longitude,
                    searchInfo.get(index).location.latitude);*/
            double distance =  DistanceUtil.getDistance(mLatLng,searchInfo.get(index).location);
            if(distance/1000 == 0) {

                distance = UnitConvertUtils.decimalPlaces(2,distance);
                mTv_Store_Distance.setText(distance + "米");
            }else {
                distance = UnitConvertUtils.decimalPlaces(2,distance/1000);
                mTv_Store_Distance.setText(distance + "千米");
            }
            // 电话监听事件
            mTv_Store_Phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String phoneNum = searchInfo.get(index).phoneNum;
                    if(searchInfo.get(index).phoneNum != null &&
                            !"".equals(phoneNum.trim())){
                        Intent intent = new Intent(Intent.ACTION_DIAL,
                                Uri.parse("tel://"+phoneNum));//显示拨号面板
                        Log.i("TAG", "onClick: " +phoneNum);
                        startActivity(intent);
                    }else {
                        Toast.makeText(getContext(), "对不起，该目标没有公布电话号码", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            // 线路监听
            mTv_Store_Route.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {

                }
            });

        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         mLayout = inflater.inflate(R.layout.fragment_nearly_layout,container,false);
         ButterKnife.bind(this,mLayout);
         mMap = (MapView) mLayout.findViewById(R.id.nearly_mapview);
         mBaiduMap = mMap.getMap() ;
         initMap();
        return mLayout;
    }

    @OnClick({R.id.nearly_menu_hos,R.id.nearly_menu_outp,R.id.nearly_menu_drugstore,R.id.nearly_place})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.nearly_menu_hos:
                mDrugStoreInfoLayout.setVisibility(View.GONE);
                mBaiduMap.clear();
                startPoiSearch(0,"医院", 20000);
                setViewEnable(R.id.nearly_menu_hos);
                break;
            case R.id.nearly_menu_outp:
                mBaiduMap.clear();
                mDrugStoreInfoLayout.setVisibility(View.GONE);
                startPoiSearch(0,"门诊",5000);
                setViewEnable(R.id.nearly_menu_outp);
                break;
            case R.id.nearly_menu_drugstore:
                mBaiduMap.clear();
                mDrugStoreInfoLayout.setVisibility(View.GONE);
                startPoiSearch(0,"药店", 2000);
                setViewEnable(R.id.nearly_menu_drugstore);
                break ;
            case R.id.nearly_place:
                Intent intent = new Intent(getContext(), CityChoiceActivity.class);
                intent.putExtra("type",NEARLY_REQUESTCODE);
                startActivityForResult(intent, NEARLY_REQUESTCODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == NEARLY_REQUESTCODE){
            if(resultCode == Activity.RESULT_OK){
                String cityName = data.getStringExtra("nearly_city");
                if(cityName != null){
                    this.cityName = cityName ;
                    mTv_CityChoice.setText(cityName+"");
                    mDrugStoreInfoLayout.setVisibility(View.GONE);
                    mBaiduMap.clear();
                    startPoiSearch(1,"医院", 20000);
                    setViewEnable(R.id.nearly_menu_hos);
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setViewEnable(int id){
        for(int i = 0 ;i < mMenu.length ;i++){
            if(mMenu[i].getId() == id){
                mMenu[i].setEnabled(false);
                mMenu[i].setTextColor(Color.parseColor("#ffffff"));
            }else{
                mMenu[i].setEnabled(true);
                mMenu[i].setTextColor(Color.parseColor("#04af20"));
            }
        }
    }

    /**
     * 初始化地图
     */
    private void initMap(){
        mBaiduMap = mMap.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);

        mLocationClient = new LocationClient(getActivity()
                .getApplicationContext());
        myListener = new MyLocationListener();
        initLocation();

        mLocationClient.registerLocationListener(myListener);
        mLocationClient.start();
        mLocationClient.requestLocation();
    }
    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        option.setScanSpan(5 * 1000);
        option.setOpenGps(true);
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }
    @Override
    public void onPause() {
        super.onPause();
        mMap.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMap.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMap.onDestroy();
    }

    /**
     * 定位返回响应监听
     */
    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //Log.i("TAG", "onReceiveLocation: " + location.getAddress().address);
            double lat = location.getLatitude();//得到纬度
            double longitude = location.getLongitude();//得到经度
            mLatLng = new LatLng(lat, longitude); //得到经纬度对象
            if(DEFAULT){
                startPoiSearch(0, "医院", 20000);//默认检索医
               DEFAULT = false ;

            }
            mLocationClient.unRegisterLocationListener(myListener);
            mLocationClient.stop();
           // mLocationClient.re
        }
    }
    /**
     * 开始检索
     */
    public void startPoiSearch(int type,String keyWord,int range) {

        poiSearch = PoiSearch.newInstance(); //获取检索对象
        //检索监听
        OnGetPoiSearchResultListener poiSearchRes = new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                //检索失败
                if (poiResult.error != PoiResult.ERRORNO.NO_ERROR) {
                    Toast.makeText(getContext(), "搜索失败", Toast.LENGTH_SHORT).show();
                } else {
                    // 检索成功

                    searchInfo = poiResult.getAllPoi();
                    for(PoiInfo p : searchInfo){
                        Log.i("TAG", "onGetPoiResult: " + p.phoneNum);
                    }
                    // 添加覆盖物
                    PoiOverlay poiOverlay = new MyPoiOverlay(mBaiduMap);
                    mBaiduMap.setOnMarkerClickListener(poiOverlay);
                    poiOverlay.setData(poiResult);
                    poiOverlay.addToMap();
                    poiOverlay.zoomToSpan();
                    //添加选中的覆盖物
                    addChoiceOverLayout(0);
                }
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

            }
        };
        // 注册检索监听
        poiSearch.setOnGetPoiSearchResultListener(poiSearchRes);
        // 发起周边检索
        if(type ==0) {//周边检索
            poiSearch.searchNearby(new PoiNearbySearchOption().radius(range).keyword(keyWord).
                    location(mLatLng).pageNum(10).sortType(PoiSortType.distance_from_near_to_far));
        }else { // 城市检索
            poiSearch.searchInCity(new PoiCitySearchOption().city(cityName)
                    .keyword(keyWord).pageNum(10));
        }
    }

    public class MyPoiOverlay extends PoiOverlay {
        /**
         * 构造函数
         * @param baiduMap 该 PoiOverlay 引用的 BaiduMap 对象
         */
        public MyPoiOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public boolean onPoiClick(final int i) {
            //单击覆盖物，该信息会返回到 onGetPoiDetailResult方法 ;
            PoiInfo info = searchInfo.get(i);
            poiSearch.searchPoiDetail(new PoiDetailSearchOption().poiUid(info.uid));
            //
            addChoiceOverLayout(i);
            return true;
        }
    }

    private void addChoiceOverLayout(final int index) {
        if(currentOverlay != null)
            currentOverlay.remove();

        // 添加自已选中信息的覆盖物
        MapStatusUpdate update = MapStatusUpdateFactory.
                newLatLngZoom(searchInfo.get(index).location, 14);
        mBaiduMap.setMapStatus(update);
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource
                (overLayout[index]);
        OverlayOptions options = new MarkerOptions()
                .icon(bitmap).position(searchInfo.get(index).location);

        currentOverlay =  mBaiduMap.addOverlay(options);

        //更新显示信息
        new HandlerThread(Nearly_Fragment.class.getSimpleName()) {
            @Override
            public void run() {
                Message msg = handler.obtainMessage();
                msg.arg1 = index;
                handler.sendMessage(msg);
            }
        }.start();
    }
}
