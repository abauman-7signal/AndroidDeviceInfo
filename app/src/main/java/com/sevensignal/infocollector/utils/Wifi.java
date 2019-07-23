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
		return com.sevensignal.infocollector.models.WifiInfo.build(
						wifiInfo.getBSSID(),
						wifiInfo.getFrequency(),
						frequencyToChannel(wifiInfo.getFrequency()),
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

	private static String frequencyToChannel(int frequency) {
		switch(frequency) {
			case 2412 :
				return "ch 1 - 2.4ghz";
			case 2417 :
				return "ch 2 - 2.4ghz";
			case 2422 :
				return "ch 3 - 2.4ghz";
			case 2427 :
				return "ch 4 - 2.4ghz";
			case 2432 :
				return "ch 5 - 2.4ghz";
			case 2437 :
				return "ch 6 - 2.4ghz";
			case 2442 :
				return "ch 7 - 2.4ghz";
			case 2447 :
				return "ch 8 - 2.4ghz";
			case 2452 :
				return "ch 9 - 2.4ghz";
			case 2457 :
				return "ch 10 - 2.4ghz";
			case 2462 :
				return "ch 11 - 2.4ghz";
			case 2467 :
				return "ch 12 - 2.4ghz";
			case 2472 :
				return "ch 13 - 2.4ghz";
			case 2484 :
				return "ch 14 - 2.4ghz";
			case 5035 :
				return "ch 7 - 5.0ghz";
			case 5040 :
				return "ch 8 - 5.0ghz";
			case 5045 :
				return "ch 9 - 5.0ghz";
			case 5055 :
				return "ch 11 - 5.0ghz";
			case 5060 :
				return "ch 12 - 5.0ghz";
			case 5080 :
				return "ch 16 - 5.0ghz";
			case 5170 :
				return "ch 34 - 5.0ghz";
			case 5180 :
				return "ch 36 - 5.0ghz";
			case 5190 :
				return "ch 38 - 5.0ghz";
			case 5200 :
				return "ch 40 - 5.0ghz";
			case 5210 :
				return "ch 42 - 5.0ghz";
			case 5220 :
				return "ch 44 - 5.0ghz";
			case 5230 :
				return "ch 46 - 5.0ghz";
			case 5240 :
				return "ch 48 - 5.0ghz";
			case 5260 :
				return "ch 52 - 5.0ghz";
			case 5280 :
				return "ch 56 - 5.0ghz";
			case 5300 :
				return "ch 60 - 5.0ghz";
			case 5320 :
				return "ch 64 - 5.0ghz";
			case 5500 :
				return "ch 100 - 5.0ghz";
			case 5520 :
				return "ch 104 - 5.0ghz";
			case 5540 :
				return "ch 108 - 5.0ghz";
			case 5560 :
				return "ch 112 - 5.0ghz";
			case 5580 :
				return "ch 116 - 5.0ghz";
			case 5600 :
				return "ch 120 - 5.0ghz";
			case 5620 :
				return "ch 124 - 5.0ghz";
			case 5640 :
				return "ch 128 - 5.0ghz";
			case 5660 :
				return "ch 132 - 5.0ghz";
			case 5680 :
				return "ch 136 - 5.0ghz";
			case 5700 :
				return "ch 140 - 5.0ghz";
			case 5720 :
				return "ch 144 - 5.0ghz";
			case 5745 :
				return "ch 149 - 5.0ghz";
			case 5765 :
				return "ch 153 - 5.0ghz";
			case 5785 :
				return "ch 157 - 5.0ghz";
			case 5805 :
				return "ch 161 - 5.0ghz";
			case 5825 :
				return "ch 165 - 5.0ghz";
			case 4915 :
				return "ch 183 - 5.0ghz";
			case 4920 :
				return "ch 184 - 5.0ghz";
			case 4925 :
				return "ch 185 - 5.0ghz";
			case 4935 :
				return "ch 187 - 5.0ghz";
			case 4940 :
				return "ch 188 - 5.0ghz";
			case 4945 :
				return "ch 189 - 5.0ghz";
			case 4960 :
				return "ch 192 - 5.0ghz";
			case 4980 :
				return "ch 196 - 5.0ghz";
			default:
				return "Could not detect channel based on frequency of " + frequency;
		}
	}
}
