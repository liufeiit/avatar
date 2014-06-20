package com.itjiehun.nova;

import android.app.Activity;
import android.app.Service;
import android.os.Vibrator;

/**
 * <uses-permission android:name="android.permission.VIBRATE" />
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * @version 1.0
 * @since 2014-6-20 下午8:22:37
 */
public class TipHelper {

	/**
	 * @param activity 调用该方法的Activity实例
	 * @param milliseconds 震动的时长，单位是毫秒
	 */
	public static void vibrate(final Activity activity, final long milliseconds) {
		Vibrator vib = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
		vib.vibrate(milliseconds);
	}

	/**
	 * @param activity 调用该方法的Activity实例
	 * @param pattern 自定义震动模式 。数组中数字的含义依次是静止的时长，震动时长，静止时长，震动时长。。。时长的单位是毫秒
	 * @param isRepeat 是否反复震动，如果是true，反复震动，如果是false，只震动一次
	 */
	public static void vibrate(final Activity activity, final long[] pattern, final boolean isRepeat) {
		Vibrator vib = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
		vib.vibrate(pattern, isRepeat ? 1 : -1);
	}
}
