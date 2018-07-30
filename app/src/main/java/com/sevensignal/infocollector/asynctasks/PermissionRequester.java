package com.sevensignal.infocollector.asynctasks;

import android.Manifest;
import android.app.Activity;
import android.support.v4.app.ActivityCompat;

public class PermissionRequester {

	public static final int PERMISSION_TO_READ_PHONE_STATE = 777;

	public static void requestPermissionToReadPhoneState(Activity activity) {
		ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSION_TO_READ_PHONE_STATE);
	}
}
