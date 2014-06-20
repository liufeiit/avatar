package com.itjiehun.nova;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDNotifyListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMapDoubleClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMapLoadedCallback;
import com.baidu.mapapi.map.BaiduMap.OnMapLongClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMapStatusChangeListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMyLocationClickListener;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.model.LatLng;

public class MainActivity extends Activity {
	private MapView mapView = null;
	private LocationClient locationClient = null;
	private BaiduMapSDKReceiver baiduMapSDKReceiver;
	private NotifyLister notifyLister;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());
		// 俯角范围： -45 ~ 0 , 单位： 度
		MapStatus ms = new MapStatus.Builder().overlook(0).target(new LatLng(31, 121)).zoom(3).build();
		BaiduMapOptions bo = new BaiduMapOptions().mapStatus(ms).overlookingGesturesEnabled(true)
				.rotateGesturesEnabled(true).scaleControlEnabled(true).scrollGesturesEnabled(true)
				.zoomGesturesEnabled(true).mapType(BaiduMap.MAP_TYPE_NORMAL).compassEnabled(true)
				.zoomControlsEnabled(true);
		mapView = new MapView(this, bo);
		MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(3.0f);
		mapView.getMap().setMapStatus(msu);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(mapView);
		
		
		
		
		LinearLayout bottomBar = new LinearLayout(this);
//		RelativeLayout bottomBar = new RelativeLayout(this);
		bottomBar.setGravity(Gravity.RIGHT);
//		bottomBar.setOrientation(LinearLayout.VERTICAL);
//		bottomBar.setGravity(Gravity.BOTTOM);
//		bottomBar.setGravity(Gravity.TOP);
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		Button child1 = new Button(this);
		child1.setText("导航");
		Button child2 = new Button(this);
		child2.setText("附近");
		bottomBar.addView(child1, 0, new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		bottomBar.addView(child2, 1, new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		
		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		addContentView(bottomBar, layoutParams);
		
		
		
		
		mapView.getMap().setOnMapClickListener(new OnMapClickListener() {
			@Override
			public boolean onMapPoiClick(MapPoi mapPoi) {
				return true;
			}

			@Override
			public void onMapClick(LatLng latLng) {
				Toast.makeText(MainActivity.this, "Click : (" + latLng.latitude + ", " + latLng.longitude + ")",
						Toast.LENGTH_LONG).show();
			}
		});
		mapView.getMap().setOnMapDoubleClickListener(new OnMapDoubleClickListener() {

			@Override
			public void onMapDoubleClick(LatLng latLng) {
				Toast.makeText(MainActivity.this, "DoubleClick : (" + latLng.latitude + ", " + latLng.longitude + ")",
						Toast.LENGTH_LONG).show();
			}
		});
		mapView.getMap().setOnMapLoadedCallback(new OnMapLoadedCallback() {
			@Override
			public void onMapLoaded() {
				Toast.makeText(MainActivity.this, "地图加载完毕!", Toast.LENGTH_LONG).show();
			}
		});
		mapView.getMap().setOnMapLongClickListener(new OnMapLongClickListener() {

			@Override
			public void onMapLongClick(LatLng latLng) {
				Toast.makeText(MainActivity.this, "LongClick : (" + latLng.latitude + ", " + latLng.longitude + ")",
						Toast.LENGTH_LONG).show();
			}
		});
		mapView.getMap().setOnMapStatusChangeListener(new OnMapStatusChangeListener() {

			@Override
			public void onMapStatusChangeStart(MapStatus mapStatus) {
				/*Toast.makeText(
						MainActivity.this,
						"onMapStatusChangeStart : (" + mapStatus.overlook + ", " + mapStatus.rotate + ", "
								+ mapStatus.target + ", " + mapStatus.targetScreen + ", " + mapStatus.zoom + ")",
						Toast.LENGTH_LONG).show();*/
			}

			@Override
			public void onMapStatusChangeFinish(MapStatus mapStatus) {
				/*Toast.makeText(
						MainActivity.this,
						"onMapStatusChangeFinish : (" + mapStatus.overlook + ", " + mapStatus.rotate + ", "
								+ mapStatus.target + ", " + mapStatus.targetScreen + ", " + mapStatus.zoom + ")",
						Toast.LENGTH_LONG).show();*/
			}

			@Override
			public void onMapStatusChange(MapStatus mapStatus) {
				/*Toast.makeText(
						MainActivity.this,
						"onMapStatusChange : (" + mapStatus.overlook + ", " + mapStatus.rotate + ", "
								+ mapStatus.target + ", " + mapStatus.targetScreen + ", " + mapStatus.zoom + ")",
						Toast.LENGTH_LONG).show();*/
			}
		});
		mapView.getMap().setOnMarkerClickListener(new OnMarkerClickListener() {
			@Override
			public boolean onMarkerClick(Marker marker) {
				Toast.makeText(
						MainActivity.this,
						"onMarkerClick : (" + marker.getPosition().latitude + ", " + marker.getPosition().latitude
								+ ")", Toast.LENGTH_LONG).show();
				return true;
			}
		});
		mapView.getMap().setOnMyLocationClickListener(new OnMyLocationClickListener() {
			@Override
			public boolean onMyLocationClick() {
				Toast.makeText(
						MainActivity.this,
						"onMyLocationClick", Toast.LENGTH_LONG).show();
				return true;
			}
		});
		mapView.getMap().setBuildingsEnabled(true);
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

		if (locationClient.isStarted()) {
			// locationClient.requestLocation();
			 locationClient.requestOfflineLocation();

			notifyLister = new NotifyLister();
			// 4个参数代表要位置提醒的点的坐标，具体含义依次为：纬度，经度，距离范围，坐标系类型(gcj02,gps,bd09,bd09ll)
			notifyLister.SetNotifyLocation(31d, 121d, 3000, "gps");
			locationClient.registerNotify(notifyLister);

//			locationClient.requestNotifyLocation();

		} else {
			locationClient.start();

			 locationClient.requestOfflineLocation();

			notifyLister = new NotifyLister();
			// 4个参数代表要位置提醒的点的坐标，具体含义依次为：纬度，经度，距离范围，坐标系类型(gcj02,gps,bd09,bd09ll)
			notifyLister.SetNotifyLocation(31d, 121d, 30000000, "gps");
			locationClient.registerNotify(notifyLister);

//			locationClient.requestNotifyLocation();
		}

		IntentFilter filter = new IntentFilter();
		filter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
		filter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
		filter.addAction(SDKInitializer.SDK_BROADTCAST_INTENT_EXTRA_INFO_KEY_ERROR_CODE);
		baiduMapSDKReceiver = new BaiduMapSDKReceiver();
		registerReceiver(baiduMapSDKReceiver, filter);
	}

	public class NotifyLister extends BDNotifyListener {
		public void onNotify(BDLocation location, float distance) {
			TipHelper.vibrate(MainActivity.this, 2000);
		}
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
		locationClient.removeNotifyEvent(notifyLister);
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
