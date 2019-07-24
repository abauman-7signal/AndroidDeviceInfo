package com.sevensignal.infocollector.asynctasks;

import android.net.wifi.ScanResult;

import com.sevensignal.infocollector.models.NetworkInfo;

import java.util.List;

public interface WifiAccessPointScanningObserver {

	void onWifiAccessPointScannerUpdate(List<ScanResult> scanResultList);
}
