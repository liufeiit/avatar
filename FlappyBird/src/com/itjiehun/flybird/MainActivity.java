package com.itjiehun.flybird;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import cn.waps.AppConnect;

import com.itjiehun.flybird.config.Config;
import com.itjiehun.flybird.umeng.UmengStatic;
import com.itjiehun.flybird.util.SoundPlayer;
import com.itjiehun.flybird.view.LoadingView;
import com.itjiehun.flybird.view.MainView;
import com.itjiehun.flybird.waps.WapsStatic;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;

@SuppressLint("HandlerLeak")
public class MainActivity extends Activity {

	private LoadingView loadingView;// 游戏载入窗口
	private MainView mainView;// 游戏主窗口
	private SoundPlayer soundPlayer;// 音乐播放器

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == Config.TO_MAIN_VIEW) {
				toMainView();
			}
			if (msg.what == Config.END_GAME) {
				endGame();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.soundPlayer = new SoundPlayer(this);
		this.soundPlayer.initSounds();
		this.loadingView = new LoadingView(this, soundPlayer);
		this.setContentView(loadingView);
		
		AppConnect.getInstance(this);
		AppConnect.getInstance(WapsStatic.APP_ID, WapsStatic.APP_PID, this);
		
		MobclickAgent.onResume(this, UmengStatic.UMENG_APPKEY, UmengStatic.UMENG_CHANNEL);

		UmengUpdateAgent.setAppkey(UmengStatic.UMENG_APPKEY);
		UmengUpdateAgent.setChannel(UmengStatic.UMENG_CHANNEL);
		UmengUpdateAgent.update(this);
		MobclickAgent.updateOnlineConfig(this);
		UmengUpdateAgent.setUpdateCheckConfig(true);
		UmengUpdateAgent.setUpdateAutoPopup(true);
		
		String ads = MobclickAgent.getConfigParams(this, UmengStatic.AdsKey.def);
		if ("1".equals(ads) || "on".equalsIgnoreCase(ads) || "true".equalsIgnoreCase(ads)) {
			LinearLayout adlayout = new LinearLayout(this);
			adlayout.setGravity(Gravity.BOTTOM);
			RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
			AppConnect.getInstance(this).showBannerAd(this, adlayout);
			layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			addContentView(adlayout, layoutParams);
		}
	}

	public void toMainView() {
		if (this.mainView == null) {
			this.mainView = new MainView(this, soundPlayer);
		} else {
			this.mainView = null;
			this.mainView = new MainView(this, soundPlayer);
		}
		this.setContentView(this.mainView);
		this.loadingView = null;
	}

	public void endGame() {
		if (this.mainView != null) {
			this.mainView.setThreadFlag(false);
		}
		if (this.loadingView != null) {
			this.loadingView.setThreadFlag(false);
		}
		this.finish();
	}

	public Handler getHandler() {
		return this.handler;
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("MainScreen");
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("MainScreen");
		MobclickAgent.onPause(this);
		AppConnect.getInstance(this).close();
	}
}
