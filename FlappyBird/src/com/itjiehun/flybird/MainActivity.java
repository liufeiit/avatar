package com.itjiehun.flybird;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;

import com.itjiehun.flybird.config.Config;
import com.itjiehun.flybird.util.SoundPlayer;
import com.itjiehun.flybird.view.LoadingView;
import com.itjiehun.flybird.view.MainView;

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
}
