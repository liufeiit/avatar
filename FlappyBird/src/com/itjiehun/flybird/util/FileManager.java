package com.itjiehun.flybird.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.util.EncodingUtils;

import com.itjiehun.flybird.BirdApplication;
import com.umeng.analytics.MobclickAgent;

import android.os.Environment;

public class FileManager {

	private File sdDir;
	private File dirPath;
	private File file;

	public String fileReader() {
		String score = null;
		try {
			FileInputStream fin = new FileInputStream(file);
			int length = fin.available();
			byte[] buffer = new byte[length];
			fin.read(buffer);
			score = EncodingUtils.getString(buffer, "UTF-8");
			fin.close();
		} catch (FileNotFoundException e) {
			MobclickAgent.reportError(BirdApplication.getApplication(), e);
		} catch (IOException e) {
			MobclickAgent.reportError(BirdApplication.getApplication(), e);
		}
		return score;
	}

	public void fileWriter(String msg) {
		try {
			FileOutputStream fout = new FileOutputStream(file);
			byte[] buffer = msg.getBytes();
			fout.write(buffer);
			fout.close();
		} catch (FileNotFoundException e) {
			MobclickAgent.reportError(BirdApplication.getApplication(), e);
		} catch (IOException e) {
			MobclickAgent.reportError(BirdApplication.getApplication(), e);
		}
	}

	public void initFile() {
		String dirpath = getSDPath() + File.separator + "FlyBird";
		dirPath = new File(dirpath);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}

		String filePath = dirpath + File.separator + "score.txt";
		file = new File(filePath);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				MobclickAgent.reportError(BirdApplication.getApplication(), e);
			}
		}
	}

	public boolean sdIsAvalible() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
	}

	public String getSDPath() {
		sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
		return sdDir.toString();
	}

}
