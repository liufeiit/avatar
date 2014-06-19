package com.itjiehun.nova;

import java.util.List;

import android.content.Context;
import android.widget.Button;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.GroundOverlayOptions;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;

/**
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * @version 1.0
 * @since 2014年6月19日 下午7:45:27
 */
public class BaiduMapUtil {

	/**
	 * 定义Maker坐标点
	 */
	public static void marker(BaiduMap baiduMap, double latitude, double longitude, int icon) {
		LatLng point = new LatLng(latitude, longitude);
		BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(icon);
		OverlayOptions option = new MarkerOptions().position(point).icon(bitmap);
		baiduMap.addOverlay(option);
	}

	/**
	 * 定义多边形的五个顶点
	 */
	public void stroke(BaiduMap baiduMap, List<LatLng> pts) {
		OverlayOptions polygonOption = new PolygonOptions().points(pts).stroke(new Stroke(pts.size(), 0xAA00FF00))
				.fillColor(0xAAFFFF00);
		baiduMap.addOverlay(polygonOption);
	}

	public void search() {
		PoiSearch mPoiSearch = PoiSearch.newInstance();

		OnGetPoiSearchResultListener poiListener = new OnGetPoiSearchResultListener() {
			public void onGetPoiResult(PoiResult result) {
				// 获取POI检索结果
			}
			public void onGetPoiDetailResult(PoiDetailResult result) {
				// 获取Place详情页检索结果
			}
		};
		mPoiSearch.setOnGetPoiSearchResultListener(poiListener);

		mPoiSearch.searchInCity((new PoiCitySearchOption()).city("上海").keyword("美食").pageNum(10));
	}

	/**
	 * 定义文字所显示的坐标点
	 */
	public void cc(BaiduMap baiduMap, Context context) {
		LatLng llText = new LatLng(39.86923, 116.397428);
		// 构建文字Option对象，用于在地图上添加文字
		OverlayOptions textOption = new TextOptions().bgColor(0xAAFFFF00).fontSize(24).fontColor(0xFFFF00FF)
				.text("百度地图SDK").rotate(-30).position(llText);
		baiduMap.addOverlay(textOption);

		// 创建InfoWindow展示的view
		Button button = new Button(context);
		button.setBackgroundResource(R.drawable.icon_175_220);
		// 定义用于显示该InfoWindow的坐标点
		LatLng pt = new LatLng(39.86923, 116.397428);
		// 创建InfoWindow的点击事件监听者
		OnInfoWindowClickListener listener = new OnInfoWindowClickListener() {
			public void onInfoWindowClick() {
				// 添加点击后的事件响应代码
			}
		};
		// 创建InfoWindow
		InfoWindow mInfoWindow = new InfoWindow(button, pt, listener);
		// 显示InfoWindow
		baiduMap.showInfoWindow(mInfoWindow);
	}

	/**
	 * 定义Ground的显示地理范围
	 */
	public void cc(BaiduMap baiduMap) {
		LatLng southwest = new LatLng(39.92235, 116.380338);
		LatLng northeast = new LatLng(39.947246, 116.414977);
		LatLngBounds bounds = new LatLngBounds.Builder().include(northeast).include(southwest).build();
		// 定义Ground显示的图片
		BitmapDescriptor bdGround = BitmapDescriptorFactory.fromResource(R.drawable.icon_175_220);
		// 定义Ground覆盖物选项
		OverlayOptions ooGround = new GroundOverlayOptions().positionFromBounds(bounds).image(bdGround)
				.transparency(0.8f);
		// 在地图中添加Ground覆盖物
		baiduMap.addOverlay(ooGround);
	}
}