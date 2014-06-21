package com.itjiehun.flybird;

import android.app.Application;

public class BirdApplication extends Application {

	private static BirdApplication application = null;
	
	@Override
	public void onCreate() {
		super.onCreate();
		application = this;
	}

	public static BirdApplication getApplication() {
		return application;
	}
}