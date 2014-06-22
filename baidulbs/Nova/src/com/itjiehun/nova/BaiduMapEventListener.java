package com.itjiehun.nova;

import android.app.Activity;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMapDoubleClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMapLoadedCallback;
import com.baidu.mapapi.map.BaiduMap.OnMapLongClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMapStatusChangeListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMyLocationClickListener;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.model.LatLng;

public class BaiduMapEventListener implements OnMapClickListener, OnMapDoubleClickListener, OnMapLoadedCallback,
		OnMapLongClickListener, OnMapStatusChangeListener, OnMarkerClickListener, OnMyLocationClickListener {

	private Activity activity;
	private MapView mapView = null;

	public BaiduMapEventListener(Activity activity, MapView mapView) {
		super();
		this.activity = activity;
		this.mapView = mapView;
	}

	@Override
	public void onMapClick(LatLng latLng) {
		Toast.makeText(activity, "Click : (" + latLng.latitude + ", " + latLng.longitude + ")", Toast.LENGTH_LONG)
				.show();
	}

	@Override
	public boolean onMapPoiClick(MapPoi mapPoi) {
		return true;
	}

	@Override
	public void onMapDoubleClick(LatLng latLng) {
		MapStatus mapStatus = mapView.getMap().getMapStatus();
		MapStatus ms = new MapStatus.Builder(mapStatus).target(latLng).zoom(mapStatus.zoom - 1).build();
		MapStatusUpdate u = MapStatusUpdateFactory.newMapStatus(ms);
		mapView.getMap().animateMapStatus(u);
	}

	@Override
	public void onMapLoaded() {
		Toast.makeText(activity, "地图加载完毕!", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onMapLongClick(LatLng latLng) {
		Toast.makeText(activity, "LongClick : (" + latLng.latitude + ", " + latLng.longitude + ")", Toast.LENGTH_LONG)
				.show();
	}

	@Override
	public void onMapStatusChange(MapStatus arg0) {

	}

	@Override
	public void onMapStatusChangeFinish(MapStatus arg0) {

	}

	@Override
	public void onMapStatusChangeStart(MapStatus arg0) {

	}

	@Override
	public boolean onMarkerClick(Marker marker) {
		Toast.makeText(activity,
				"onMarkerClick : (" + marker.getPosition().latitude + ", " + marker.getPosition().latitude + ")",
				Toast.LENGTH_LONG).show();
		return true;
	}

	@Override
	public boolean onMyLocationClick() {
		Toast.makeText(activity, "onMyLocationClick", Toast.LENGTH_LONG).show();
		return true;
	}
}