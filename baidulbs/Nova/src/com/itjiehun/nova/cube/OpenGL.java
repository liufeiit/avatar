package com.itjiehun.nova.cube;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.itjiehun.nova.R;

public class OpenGL extends Activity {

	private GLSurfaceView glSurfaceView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		glSurfaceView = new GLSurfaceView(this);
		glSurfaceView.setRenderer(new CubeRender());
		setContentView(R.layout.opengl);
		RelativeLayout layout = (RelativeLayout) findViewById(R.id.opengl);
		layout.addView(glSurfaceView);
		Button back = (Button) findViewById(R.id.back_view);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				OpenGL.this.finish();
			}
		});
	}

	@Override
	protected void onPause() {
		super.onPause();
		glSurfaceView.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		glSurfaceView.onResume();
	}
}