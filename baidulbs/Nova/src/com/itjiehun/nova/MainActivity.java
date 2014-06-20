package com.itjiehun.nova;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.widget.Toast;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.SupportMapFragment;
import com.baidu.mapapi.model.LatLng;

public class MainActivity extends FragmentActivity {
	private MapView mapView = null;
	private LocationClient locationClient = null;
	private BaiduMapSDKReceiver baiduMapSDKReceiver;
	SupportMapFragment map;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());
		// 地图俯仰角度。-45~0
		MapStatus ms = new MapStatus.Builder().overlook(0).target(new LatLng(31, 121)).zoom(3).build();
		BaiduMapOptions bo = new BaiduMapOptions().mapStatus(ms).overlookingGesturesEnabled(true)
				.rotateGesturesEnabled(true).scaleControlEnabled(true).scrollGesturesEnabled(true)
				.zoomGesturesEnabled(true).mapType(BaiduMap.MAP_TYPE_NORMAL).compassEnabled(true)
				.zoomControlsEnabled(true);
		mapView = new MapView(this, bo);
		setContentView(mapView);
		mapView.getMap().setMyLocationEnabled(true);
		mapView.getMap().setTrafficEnabled(true);
		locationClient = new LocationClient(getApplicationContext());
		LocationListener listener = new LocationListener(mapView, this);
		locationClient.registerLocationListener(listener); // 注册监听函数
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式
		// 返回百度经纬度坐标系 coor=bd09ll 国测局经纬度坐标系 coor=gcj02 默认值gcj02
		option.setCoorType("bd09ll");
		option.setOpenGps(true);
		option.setScanSpan(100);
		option.setIsNeedAddress(true);
		option.setNeedDeviceDirect(true);
		locationClient.setLocOption(option);
		locationClient.start();
		locationClient.requestLocation();
		IntentFilter filter = new IntentFilter();
		filter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
		filter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
		filter.addAction(SDKInitializer.SDK_BROADTCAST_INTENT_EXTRA_INFO_KEY_ERROR_CODE);
		baiduMapSDKReceiver = new BaiduMapSDKReceiver();
		registerReceiver(baiduMapSDKReceiver, filter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onDestroy() {
		locationClient.stop();
		mapView.getMap().setMyLocationEnabled(false);
		mapView.onDestroy();
		mapView = null;
		unregisterReceiver(baiduMapSDKReceiver);
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mapView.onPause();
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	public class BaiduMapSDKReceiver extends BroadcastReceiver {
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR.equals(action)) {
				Toast.makeText(context, "签名验证出错！", Toast.LENGTH_LONG).show();
			} else if (SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR.equals(action)) {
				Toast.makeText(context, "请检查您的网络！", Toast.LENGTH_LONG).show();
			}
		}
	}
}
