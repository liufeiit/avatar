package com.itjiehun.nova;

import android.app.Application;
import android.content.res.Configuration;
import android.widget.Toast;

public class NovaApplication extends Application {

	private static NovaApplication application = null;

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		application = this;
		Toast.makeText(this, "welcome to Nova Application!", Toast.LENGTH_SHORT).show();
	}

	public static NovaApplication getApplication() {
		return application;
	}
}