package com.sevensignal.infocollector.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;

import com.sevensignal.infocollector.asynctasks.PermissionRequester;

public class Device {

	@TargetApi(Build.VERSION_CODES.O)
	public static String collectSerialNumber(Activity activity) {
		if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O) {
			return Build.SERIAL;
		} else {
			if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
				if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
					return Build.getSerial();
				} else {
					return "Unknown: Cannot access serial number for SDK " + Build.VERSION.SDK_INT;
				}
			} else {
				PermissionRequester.requestPermissionToReadPhoneState(activity);
				return "Unknown: Permission not granted";
			}
		}
	}
}
