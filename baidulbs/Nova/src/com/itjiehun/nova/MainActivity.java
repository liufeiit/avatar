package com.itjiehun.nova;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDNotifyListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;

public class MainActivity extends Activity {
	private static final String TAG = MainActivity.class.getName();
	private MapView mapView = null;
	private LocationClient locationClient = null;
	private BaiduMapSDKReceiver baiduMapSDKReceiver;
	private NotifyLister notifyLister;
	private BaiduMapEventListener baiduMapEventListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		if (Build.VERSION.SDK_INT >= 11) {
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
					WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
		}
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		boolean isOrientationEnabled = false;
		try {
			isOrientationEnabled = Settings.System.getInt(getContentResolver(), Settings.System.ACCELEROMETER_ROTATION) == 1;
		} catch (SettingNotFoundException e) {
			Log.e(TAG, "System Setting Error.", e);
		}
		int screenLayout = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
		if ((screenLayout == Configuration.SCREENLAYOUT_SIZE_LARGE || screenLayout == Configuration.SCREENLAYOUT_SIZE_XLARGE)
				&& isOrientationEnabled) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
		}
		SDKInitializer.initialize(getApplicationContext());
		// 俯角范围： -45 ~ 0 , 单位： 度
		MapStatus ms = new MapStatus.Builder().overlook(0).target(new LatLng(31, 121)).zoom(3).build();
		BaiduMapOptions bo = new BaiduMapOptions().compassEnabled(true).mapStatus(ms).mapType(BaiduMap.MAP_TYPE_NORMAL)
				.overlookingGesturesEnabled(true).rotateGesturesEnabled(true).scaleControlEnabled(true)
				.scrollGesturesEnabled(true).zoomControlsEnabled(true).zoomGesturesEnabled(true);
		mapView = new MapView(this, bo);
		MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(3.0f);
		mapView.getMap().setMapStatus(msu);
		mapView.getMap().setBuildingsEnabled(true);
		mapView.getMap().setMyLocationEnabled(true);
		mapView.getMap().setTrafficEnabled(true);
		baiduMapEventListener = new BaiduMapEventListener(this, mapView);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(mapView);

		RelativeLayout actionBar = new RelativeLayout(this);
		actionBar.setGravity(Gravity.RIGHT);
		RelativeLayout.LayoutParams barLayout = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		RelativeLayout.LayoutParams naviLayout = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		RelativeLayout.LayoutParams nearLayout = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		ImageButton navi = new ImageButton(this);
		navi.setImageResource(R.drawable.navi48_48);
		navi.setMinimumHeight(48);
		navi.setMinimumWidth(48);
		navi.setMaxWidth(48);
		navi.setMaxHeight(48);
		navi.setScaleType(ScaleType.CENTER_INSIDE);
		// 0~255透明度值
		// navi.setAlpha(150F);
		// navi.setImageAlpha(100);
		navi.setId(1);

		ImageButton near = new ImageButton(this);
		near.setImageResource(R.drawable.near48_48);
		near.setMinimumHeight(48);
		near.setMinimumWidth(48);
		near.setMaxWidth(48);
		near.setMaxHeight(48);
		near.setScaleType(ScaleType.CENTER_INSIDE);
		// near.setAlpha(150F);
		// near.setImageAlpha(100);
		near.setId(2);

		naviLayout.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
		actionBar.addView(navi, 0, naviLayout);
		nearLayout.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
		nearLayout.addRule(RelativeLayout.BELOW, navi.getId());
		actionBar.addView(near, 1, nearLayout);
		barLayout.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
		barLayout.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
		addContentView(actionBar, barLayout);

		mapView.getMap().setOnMapClickListener(baiduMapEventListener);
		mapView.getMap().setOnMapDoubleClickListener(baiduMapEventListener);
		mapView.getMap().setOnMapLoadedCallback(baiduMapEventListener);
		mapView.getMap().setOnMapLongClickListener(baiduMapEventListener);
		mapView.getMap().setOnMapStatusChangeListener(baiduMapEventListener);
		mapView.getMap().setOnMarkerClickListener(baiduMapEventListener);
		mapView.getMap().setOnMyLocationClickListener(baiduMapEventListener);

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

			// locationClient.requestNotifyLocation();

		} else {
			locationClient.start();

			locationClient.requestOfflineLocation();

			notifyLister = new NotifyLister();
			// 4个参数代表要位置提醒的点的坐标，具体含义依次为：纬度，经度，距离范围，坐标系类型(gcj02,gps,bd09,bd09ll)
			notifyLister.SetNotifyLocation(31d, 121d, 30000000, "gps");
			locationClient.registerNotify(notifyLister);

			// locationClient.requestNotifyLocation();
		}
		IntentFilter filter = new IntentFilter();
		filter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
		filter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
		filter.addAction(SDKInitializer.SDK_BROADTCAST_INTENT_EXTRA_INFO_KEY_ERROR_CODE);
		baiduMapSDKReceiver = new BaiduMapSDKReceiver();
		registerReceiver(baiduMapSDKReceiver, filter);

		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		// sensorManager.getOrientation(R, values);
		// sensorManager.registerListener(sensor, Sensor.TYPE_ALL,
		// SensorManager.SENSOR_DELAY_UI);

		List<Sensor> allSensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
		StringBuffer sb = new StringBuffer("经检测该手机有").append(allSensors.size()).append("个传感器，他们分别是：\n");
		// 显示每个传感器的具体信息
		for (Sensor s : allSensors) {
			String tempString = "\n" + "  设备名称：" + s.getName() + "\n" + "  设备版本：" + s.getVersion() + "\n" + "  供应商："
					+ s.getVendor() + "\n";
			switch (s.getType()) {
			case Sensor.TYPE_ACCELEROMETER:
				sb.append(s.getType()).append(" 加速度传感器accelerometer").append(tempString);
				break;
			case Sensor.TYPE_GYROSCOPE:
				sb.append(s.getType()).append(" 陀螺仪传感器gyroscope").append(tempString);
				break;
			case Sensor.TYPE_LIGHT:
				sb.append(s.getType()).append(" 环境光线传感器light").append(tempString);
				break;
			case Sensor.TYPE_MAGNETIC_FIELD:
				sb.append(s.getType()).append(" 电磁场传感器magnetic field").append(tempString);
				break;
			case Sensor.TYPE_ORIENTATION:
				sb.append(s.getType()).append(" 方向传感器orientation").append(tempString);
				break;
			case Sensor.TYPE_PRESSURE:
				sb.append(s.getType()).append(" 压力传感器pressure").append(tempString);
				break;
			case Sensor.TYPE_PROXIMITY:
				sb.append(s.getType()).append(" 距离传感器proximity").append(tempString);
				break;
			case Sensor.TYPE_AMBIENT_TEMPERATURE:
				sb.append(s.getType()).append(" 温度传感器temperature").append(tempString);
				break;
			default:
				sb.append(s.getType()).append(" 未知传感器").append(tempString);
				break;
			}
		}

		DialogUtil.common(MainActivity.this, sb.toString(), "传感器信息");
	}

	SensorManager sensorManager = null;
	NovaSensor sensor = new NovaSensor();

	public class NovaSensor implements SensorListener {

		@Override
		public void onAccuracyChanged(int sensor, int accuracy) {
			DialogUtil.common(MainActivity.this, "传感器精度变化 : " + accuracy, "信息");
		}

		@Override
		public void onSensorChanged(int sensor, float[] values) {
			switch (sensor) {
			case SensorManager.SENSOR_ACCELEROMETER:
				DialogUtil.common(MainActivity.this, "加速度传感器精度：" + Arrays.toString(values), "信息");
				break;
			case SensorManager.SENSOR_ORIENTATION:
				DialogUtil.common(MainActivity.this, "姿势传感器精度：" + Arrays.toString(values), "信息");
				break;
			case SensorManager.SENSOR_MAGNETIC_FIELD:
				DialogUtil.common(MainActivity.this, "磁场传感器精度：" + Arrays.toString(values), "信息");
				break;
			case SensorManager.SENSOR_LIGHT:
				DialogUtil.common(MainActivity.this, "光传感器精度：" + Arrays.toString(values), "信息");
				break;
			case SensorManager.SENSOR_PROXIMITY:
				DialogUtil.common(MainActivity.this, "距离传感器精度：" + Arrays.toString(values), "信息");
				break;
			default:
				break;
			}
		}
	}

	public class NotifyLister extends BDNotifyListener {
		public void onNotify(BDLocation location, float distance) {
			// TipHelper.vibrate(MainActivity.this, 2000);
			Toast.makeText(MainActivity.this, "距离 ：" + distance, Toast.LENGTH_LONG).show();
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
		sensorManager.unregisterListener(sensor, SensorManager.SENSOR_ALL);

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
