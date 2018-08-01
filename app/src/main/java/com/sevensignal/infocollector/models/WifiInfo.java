package com.sevensignal.infocollector.models;

import android.net.wifi.SupplicantState;

import lombok.Data;

@Data(staticConstructor="build")
public class WifiInfo {
	private final String bssid;
	private final int frequency;
	private final boolean isHiddenSsid;
	private final String ipAddress;
	private final String ipRoute;
	private final int linkSpeed;
	private final String macAddress;
	private final int networkId;
	private final int rssi;
	private final String ssid;
	private final SupplicantState supplicantState;

	public String getInfoForDisplay() {
		return this.toString()
				.replace("WifiInfo(", " ")
				.replace(",", System.lineSeparator())
				.replace(")", "");
	}
}
