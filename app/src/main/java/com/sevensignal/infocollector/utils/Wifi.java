package com.sevensignal.infocollector.utils;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;

public class Wifi {

	public static com.sevensignal.infocollector.models.WifiInfo collectWifiInfo(final Context context) {
		WifiManager wifiManager = (WifiManager) context.getSystemService((Context.WIFI_SERVICE));
		android.net.wifi.WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		com.sevensignal.infocollector.models.WifiInfo collectedWifiInfo =
				com.sevensignal.infocollector.models.WifiInfo.build(
						wifiInfo.getBSSID(),
						wifiInfo.getFrequency(),
						wifiInfo.getHiddenSSID(),
						Formatter.formatIpAddress(wifiInfo.getIpAddress()),
						wifiInfo.getLinkSpeed(),
						wifiInfo.getMacAddress(),
						wifiInfo.getNetworkId(),
						wifiInfo.getRssi(),
						wifiInfo.getSSID(),
						wifiInfo.getSupplicantState()
		);
		return collectedWifiInfo;
	}
}
