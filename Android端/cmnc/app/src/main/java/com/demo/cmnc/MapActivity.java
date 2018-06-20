package com.demo.cmnc;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;

public class MapActivity extends Activity {
    private GeoCoder mSearch;
    BaiduMap mBaiduMap;
    private MapView mMapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_map);
        mSearch = GeoCoder.newInstance();
        mMapView=findViewById(R.id.mapview);
        mBaiduMap = mMapView.getMap();

        OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {

            public void onGetGeoCodeResult(GeoCodeResult result) {

                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    //没有检索到结果
                    Log.i("result111","2133");
                }
                else {
                    LatLng point = new LatLng(result.getLocation().latitude, result.getLocation().longitude);
                    MapStatus.Builder builder = new MapStatus.Builder();
                    builder.target(point).zoom(18.0f);
                    mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

//构建Marker图标

                    BitmapDescriptor bitmap = BitmapDescriptorFactory
                            .fromResource(R.drawable.geo);

//构建MarkerOption，用于在地图上添加Marker

                    OverlayOptions option = new MarkerOptions()
                            .position(point)
                            .icon(bitmap);

//在地图上添加Marker，并显示

                    mBaiduMap.addOverlay(option);
                    Log.i("result111",result.getLocation().latitude+"");
                }
                //获取地理编码结果
            }

            @Override

            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {

                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    //没有找到检索结果
                    Log.i("result111","2133");


                }
       else {

                   LatLng point = new LatLng(result.getLocation().latitude, result.getLocation().longitude);
                    MapStatus.Builder builder = new MapStatus.Builder();
                    builder.target(point).zoom(18.0f);
                    mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

//                    LatLng point = new LatLng(39.963175, 116.400244);

//构建Marker图标

                    BitmapDescriptor bitmap = BitmapDescriptorFactory
                            .fromResource(R.drawable.geo);

//构建MarkerOption，用于在地图上添加Marker

                    OverlayOptions option = new MarkerOptions()
                            .position(point)
                            .icon(bitmap);

//在地图上添加Marker，并显示

                    mBaiduMap.addOverlay(option);

                    Log.i("result111",result.getLocation().latitude+"");
                }
                //获取反向地理编码结果
            }
        };
        mSearch.setOnGetGeoCodeResultListener(listener);

        mSearch.geocode(new GeoCodeOption()
                .city("上海")
                .address("上海市崇明区北七滧现代农业开发区园区南路北50米"));


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
