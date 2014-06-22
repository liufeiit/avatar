package com.itjiehun.nova;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class DialogUtil {
	public static void common(final Activity activity, String message, String title) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setMessage(message);
		builder.setTitle(title);
		builder.setPositiveButton("确认", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				activity.finish();
			}
		});
		builder.setNegativeButton("取消", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	public static void cs(final Activity activity, String message, String title) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setIcon(android.R.drawable.btn_star);
		builder.setMessage(message).setTitle(title).setPositiveButton("很喜欢", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(activity, "我很喜欢他的电影。", Toast.LENGTH_LONG).show();
			}
		}).setNegativeButton("不喜欢", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(activity, "我不喜欢他的电影。", Toast.LENGTH_LONG).show();
			}
		}).setNeutralButton("一般", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(activity, "谈不上喜欢不喜欢。", Toast.LENGTH_LONG).show();
			}
		});
		builder.create().show();
	}

	public static void in(final Activity activity, String message, String title) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setIcon(android.R.drawable.ic_dialog_info);
		builder.setView(new EditText(activity));
		builder.setMessage(message).setTitle(title).setPositiveButton("确定", null).setNegativeButton("取消", null);
		builder.create().show();
	}

	public static void items(final Activity activity, String message, String title) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setIcon(android.R.drawable.ic_dialog_info);
		builder.setMultiChoiceItems(new String[] { "Item1", "Item2" }, null, null);
		builder.setMessage(message).setTitle(title).setPositiveButton("确定", null).setNegativeButton("取消", null);
		builder.create().show();
	}

	public static void items2(final Activity activity, String message, String title) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setIcon(android.R.drawable.ic_dialog_info);
		builder.setSingleChoiceItems(new String[] { "Item1", "Item2" }, 0, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.setMessage(message).setTitle(title).setPositiveButton("确定", null).setNegativeButton("取消", null);
		builder.create().show();
	}
}