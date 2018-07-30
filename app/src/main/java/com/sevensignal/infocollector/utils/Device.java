package com.sevensignal.infocollector.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;

public class Device {

	@TargetApi(Build.VERSION_CODES.O)
	public static String collectSerialNumber(Context context) {
		if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O) {
			return Build.SERIAL;
		} else {
			if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
				return Build.getSerial();
			} else {
				return "Unknown: Permission not granted";
			}
		}
	}
}
