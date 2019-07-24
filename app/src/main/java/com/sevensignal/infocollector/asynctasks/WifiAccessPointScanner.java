package com.sevensignal.infocollector.asynctasks;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.sevensignal.infocollector.utils.LoggingTags;
import com.sevensignal.infocollector.utils.Wifi;

import java.util.List;

public class WifiAccessPointScanner {

	private List<ScanResult> scanResults;
	private WifiAccessPointScanningObserver observer;

	private void processScanResults(WifiManager wifiManager) {
		scanResults = wifiManager.getScanResults();
		observer.onWifiAccessPointScannerUpdate(scanResults);
	}

	private void processScanFailure(WifiManager wifiManager) {
		Log.w(LoggingTags.SCAN_INFO, "Using cached scan data");
		scanResults = wifiManager.getScanResults();
		observer.onWifiAccessPointScannerUpdate(scanResults);
	}

	public void scanWifiChannels(final Activity activity, final WifiAccessPointScanningObserver observer) {
		this.observer = observer;

		final WifiManager wifiManager = (WifiManager)activity
				.getApplicationContext()
				.getSystemService(Context.WIFI_SERVICE);

		registerWifiScanningReceiver(activity, wifiManager);

		if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
			if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CHANGE_WIFI_STATE) == PackageManager.PERMISSION_GRANTED) {
				boolean successStartingScan = wifiManager.startScan();
				if (!successStartingScan) {
					Log.i(LoggingTags.SCAN_INFO, "Could not start scanning for access points");
					processScanFailure(wifiManager);
				}
			} else {
				PermissionRequester.requestPermissionToScanWifiAccessPoints(activity);
			}
		} else {
			PermissionRequester.requestPermissionToReadLocation(activity);
		}
	}

	private void registerWifiScanningReceiver(Activity activity, final WifiManager wifiManager) {
		BroadcastReceiver wifiScanReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				boolean success = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false);
				if (success) {
					processScanResults(wifiManager);
				} else {
					processScanFailure(wifiManager);
				}
			}
		};

		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
		activity.getApplicationContext().registerReceiver(wifiScanReceiver, intentFilter);
	}
}
