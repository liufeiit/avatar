package com.itjiehun.flybird.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import com.itjiehun.flybird.MainActivity;
import com.itjiehun.flybird.config.Constants;
import com.itjiehun.flybird.util.SoundPlayer;

@SuppressLint("ViewConstructor")
public class BaseView extends SurfaceView implements Callback, Runnable {

	protected float scaleX;// ����ͼƬ���ű���
	protected float scaleY;

	protected MainActivity mainActivity;
	protected SoundPlayer soundPlayer;

	protected Canvas canvas;// ��������
	protected Paint paint;// ���ʶ���
	protected SurfaceHolder sfh;

	protected Thread thread;// �滭�߳�
	protected boolean threadFlag;// ����߳�����

	// ���캯��
	public BaseView(Context context, SoundPlayer soundPlayer) {
		super(context);
		this.mainActivity = (MainActivity) context;
		this.soundPlayer = soundPlayer;
		this.sfh = this.getHolder();
		this.sfh.addCallback(this);
		this.paint = new Paint();
	}

	// �߳����еķ���
	@Override
	public void run() {
	}

	// ��ʼ��ͼƬ��Դ
	public void initBitmap() {
	}

	// �ͷ�ͼƬ��Դ
	public void release() {
	}

	// ��ͼ����
	public void drawSelf() {
	}

	// ��ͼ�ı�ķ���
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
	}

	// ��ͼ�����ķ���
	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		Constants.SCREEN_WIDTH = this.getWidth();
		Constants.SCREEN_HEIGHT = this.getHeight();
		this.threadFlag = true;
	}

	// ��ͼ���ٵķ���
	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		this.threadFlag = false;
	}

	public void setThreadFlag(boolean threadFlag) {
		this.threadFlag = threadFlag;
	}

}
