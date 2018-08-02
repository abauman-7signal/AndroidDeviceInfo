package com.sevensignal.infocollector.utils;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;

import static com.sevensignal.infocollector.utils.Network.formatMacAddress;

public class Wifi {
	private static final String NOT_AVAILABLE = "N/A";

	public static com.sevensignal.infocollector.models.WifiInfo collectWifiInfo(final Context context) {
		WifiManager wifiManager = (WifiManager) context.getSystemService((Context.WIFI_SERVICE));
		android.net.wifi.WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		com.sevensignal.infocollector.models.WifiInfo collectedWifiInfo =
				com.sevensignal.infocollector.models.WifiInfo.build(
						wifiInfo.getBSSID(),
						wifiInfo.getFrequency(),
						wifiInfo.getHiddenSSID(),
						Formatter.formatIpAddress(wifiInfo.getIpAddress()),
						collectIpRoute(),
						wifiInfo.getLinkSpeed(),
						wifiInfo.getMacAddress(),
						getMacFromIp(wifiInfo.getIpAddress()),
						wifiInfo.getNetworkId(),
						wifiInfo.getRssi(),
						wifiInfo.getSSID(),
						wifiInfo.getSupplicantState()
		);
		return collectedWifiInfo;
	}

	private static String collectIpRoute() {
		String command = "ip route";
		StringBuilder results = new StringBuilder();

		try {
			Process executingProcess = Runtime.getRuntime().exec(command);
			try {
				executingProcess.waitFor();
//			validateExitCode(validExitCodes, executingProcess.exitValue());
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				return NOT_AVAILABLE;
			}

			BufferedReader reader = new BufferedReader(new InputStreamReader(executingProcess.getInputStream()));
			String line;
			try {
				while ((line = reader.readLine()) != null) {
					results.append(line);
				}
			} catch (Exception ex) {
				return NOT_AVAILABLE;
			}

		} catch (Exception ex) {
			return NOT_AVAILABLE;
		}
		return results.toString();
	}

	private static String getMacFromIp(int ipAddress) {
		String[] ipAddressOctets = Formatter.formatIpAddress(ipAddress).split("\\.");
		byte[] octets = new byte[ipAddressOctets.length];
		int i = 0;
		for (String octet : ipAddressOctets) {
			octets[i++] = (byte)Integer.parseInt(octet);
		}
		try {
			InetAddress inetAddress = InetAddress.getByAddress(octets);
			return formatMacAddress(NetworkInterface.getByInetAddress(inetAddress).getHardwareAddress());
		} catch (Exception ex) {
			return NOT_AVAILABLE;
		}
	}
}
