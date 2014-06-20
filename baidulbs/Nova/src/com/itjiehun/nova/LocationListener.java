package com.itjiehun.nova;

import android.content.Context;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
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
		BaiduMapUtil.marker(mapView.getMap(), location, R.drawable.icon_geo);
		
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
		
//		Toast.makeText(context, sb.toString(), Toast.LENGTH_LONG).show();
	}
}