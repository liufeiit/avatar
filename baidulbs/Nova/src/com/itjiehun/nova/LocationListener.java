package com.itjiehun.nova;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.widget.Button;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
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
	private InfoWindow mInfoWindow;

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

	protected void display(final BDLocation location) {
		BaiduMapUtil.marker(mapView.getMap(), location, R.drawable.icon_gcoding);
		mapView.getMap().setOnMarkerClickListener(new OnMarkerClickListener() {
			public boolean onMarkerClick(final Marker marker) {
				Button button = new Button(context);
				button.setTextColor(0);
				button.setBackgroundResource(R.drawable.popup);
				final LatLng ll = marker.getPosition();
				Point p = mapView.getMap().getProjection().toScreenLocation(ll);
				p.y -= 47;
				LatLng llInfo = mapView.getMap().getProjection().fromScreenLocation(p);
				OnInfoWindowClickListener listener = null;
				button.setText("我的位置");
				listener = new OnInfoWindowClickListener() {
					public void onInfoWindowClick() {
						mapView.getMap().hideInfoWindow();
						StringBuffer sb = new StringBuffer(256);
						sb.append("time : ");
						sb.append(location.getTime());
						sb.append("\nerror code : ");
						sb.append(location.getLocType());
						sb.append("\nlatitude : ");
						sb.append(location.getLatitude());
						sb.append("\nlontitude : ");
						sb.append(location.getLongitude());
						sb.append("\nradius : ");
						sb.append(location.getRadius());
						if (location.getLocType() == BDLocation.TypeGpsLocation) {
							sb.append("\nspeed : ");
							sb.append(location.getSpeed());
							sb.append("\nsatellite : ");
							sb.append(location.getSatelliteNumber());
						} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
							sb.append("\naddr : ");
							sb.append(location.getAddrStr());
						}
//						Toast.makeText(context, sb.toString(), Toast.LENGTH_LONG).show();
						
						Intent intent = null;
						intent = new Intent(context, LayersMap.class);
						context.startActivity(intent);
						TipHelper.vibrate((Activity) context, new long[]{1000L, 2000L, 1000L, 2000L, 1000L, 2000L, 1000L, 2000L, 1000L, 2000L}, true);
					}
				};
				mInfoWindow = new InfoWindow(button, llInfo, listener);
				mapView.getMap().showInfoWindow(mInfoWindow);
				return true;
			}
		});
	}
}