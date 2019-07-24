package com.sevensignal.infocollector.asynctasks;

import android.Manifest;
import android.app.Activity;
import android.support.v4.app.ActivityCompat;

public class PermissionRequester {

	public static final int PERMISSION_TO_READ_PHONE_STATE = 777;
	public static final int PERMISSION_TO_CHANGE_WIFI_STATE = 778;
	public static final int PERMISSION_TO_READ_LOCATION = 779;

	public static void requestPermissionToReadPhoneState(Activity activity) {
		ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSION_TO_READ_PHONE_STATE);
	}

	static void requestPermissionToReadLocation(Activity activity) {
		ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_TO_READ_LOCATION);
	}

	static void requestPermissionToScanWifiAccessPoints(Activity activity) {
		ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CHANGE_WIFI_STATE}, PERMISSION_TO_CHANGE_WIFI_STATE);
	}
}
