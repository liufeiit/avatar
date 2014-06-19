package com.itjiehun.nova;

import android.app.Application;
import android.content.res.Configuration;
import android.widget.Toast;

public class NovaApplication extends Application {

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Toast.makeText(this, "Application start..", Toast.LENGTH_LONG).show();
	}
}