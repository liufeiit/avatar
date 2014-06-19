package com.itjiehun.nova;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapView;

public class MainActivity extends FragmentActivity {
	private MapView mapView = null;
	private LocationClient mLocationClient = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 在使用SDK各组件之前初始化context信息，传入ApplicationContext
		// 注意该方法要再setContentView方法之前实现
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_main);
		// 获取地图控件引用
		mapView = (MapView) findViewById(R.id.bmapView);
		// 开启定位图层
		mapView.getMap().setMyLocationEnabled(true);
		// 普通地图
		// mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
		// 卫星地图
		// mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
		// 开启交通图
		// mBaiduMap.setTrafficEnabled(true);

		mLocationClient = new LocationClient(getApplicationContext()); // 声明LocationClient类
		LocationListener listener = new LocationListener(mapView, this);
		mLocationClient.registerLocationListener(listener); // 注册监听函数

		// 返回国测局经纬度坐标系 coor=gcj02
		// 返回国测局经纬度坐标系 coor=gcj02
		// 返回百度经纬度坐标系 coor=bd09ll
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度，默认值gcj02
		option.setOpenGps(true);
		option.setScanSpan(1001);
		option.setIsNeedAddress(true);// 返回的定位结果包含地址信息
		option.setNeedDeviceDirect(true);// 返回的定位结果包含手机机头的方向
		mLocationClient.setLocOption(option);
		mLocationClient.start();
		mLocationClient.requestLocation();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
		mapView.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
		mapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
		mapView.onPause();
	}
}
