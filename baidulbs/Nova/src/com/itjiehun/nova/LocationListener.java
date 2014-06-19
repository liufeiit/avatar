package com.itjiehun.nova;

import android.content.Context;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;

/**
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * @version 1.0
 * @since 2014年6月19日 下午7:42:30
 */
public class LocationListener implements BDLocationListener {

	private MapView mapView;
	private Context context;
	
	public LocationListener(MapView mapView, Context context) {
		super();
		this.mapView = mapView;
		this.context = context;
	}

	@Override
	public final void onReceiveLocation(BDLocation location) {
		display(location);
	}

	@Override
	public final void onReceivePoi(BDLocation location) {
		display(location);
	}
	
	protected void display(BDLocation location) {
		
		LatLng llText = new LatLng(location.getLatitude(), location.getLongitude());
		// 构建文字Option对象，用于在地图上添加文字
		OverlayOptions textOption = new TextOptions().bgColor(0xAAFFFF00).fontSize(24).fontColor(0xFFFF00FF)
				.text("me").rotate(-30).position(llText);
		mapView.getMap().addOverlay(textOption);
		// 创建InfoWindow展示的view
		Button button = new Button(context);
		button.setBackgroundResource(R.drawable.icon_175_220);
		// 定义用于显示该InfoWindow的坐标点
		LatLng pt = new LatLng(location.getLatitude(), location.getLongitude());
		// 创建InfoWindow的点击事件监听者
		OnInfoWindowClickListener listener = new OnInfoWindowClickListener() {
			public void onInfoWindowClick() {
				// 添加点击后的事件响应代码
				Toast.makeText(context, "click point", Toast.LENGTH_LONG).show();
			}
		};
		// 创建InfoWindow
		InfoWindow mInfoWindow = new InfoWindow(button, pt, listener);
		// 显示InfoWindow
		mapView.getMap().showInfoWindow(mInfoWindow);
		
		
		/*LatLng llText = new LatLng(location.getLatitude(), location.getLongitude());
		// 开启定位图层
		mapView.getMap().setMyLocationEnabled(true);
		// 构造定位数据
		MyLocationData locData = new MyLocationData.Builder()
		    .accuracy(location.getRadius())
		    // 此处设置开发者获取到的方向信息，顺时针0-360
		    .direction(100).latitude(location.getLatitude())
		    .longitude(location.getLongitude()).build();
		// 设置定位数据
		mapView.getMap().setMyLocationData(locData);
		// 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
		BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
		    .fromResource(R.drawable.icon_175_220);
		MyLocationConfigeration config = new MyLocationConfigeration(MyLocationConfigeration.LocationMode.NORMAL, true, mCurrentMarker);
		mapView.getMap().setMyLocationConfigeration(config);
		
		OverlayOptions textOption = new TextOptions().bgColor(0xAAFFFF00).fontSize(24).fontColor(0xFFFF00FF)
				.text("me hear").rotate(-30).position(llText);
		mapView.getMap().addOverlay(textOption);*/
		
		// 当不需要定位图层时关闭定位图层
//		mapView.getMap().setMyLocationEnabled(false);

//		BaiduMapUtil.marker(mapView.getMap(), location.getLatitude(), location.getLongitude(), R.drawable.icon_175_220);
		Toast.makeText(context, location.getLatitude() + ", " + location.getLongitude(), Toast.LENGTH_LONG).show();
	}
}