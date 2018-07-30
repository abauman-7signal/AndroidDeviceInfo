package com.sevensignal.infocollector.utils;

import com.sevensignal.infocollector.models.NetworkInfo;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class Network {

	public static List<NetworkInfo> collectNetworkInfo() throws SocketException {
		List<NetworkInfo> networks = new ArrayList<>();
		Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
		while (networkInterfaces != null && networkInterfaces.hasMoreElements()) {
			NetworkInterface networkInterface = networkInterfaces.nextElement();
			String displayName = networkInterface.getDisplayName();
			byte[] macAddress = networkInterface.getHardwareAddress();
			Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
			while (inetAddresses != null && inetAddresses.hasMoreElements()) {
				InetAddress inetAddress = inetAddresses.nextElement();
				String canonicalHostName = inetAddress.getCanonicalHostName();
				String hostAddress = inetAddress.getHostAddress();
				NetworkInfo networkInfo = new NetworkInfo(displayName, canonicalHostName, hostAddress, macAddress);
				networks.add(networkInfo);
			}
		}
		return networks;
	}

	public static String formatMacAddress(byte[] macAddress) {
		StringBuilder sb = new StringBuilder();
		if (macAddress != null) {
			for (int i = 0; i < macAddress.length; i++) {
				sb.append(String.format("%02X%s", macAddress[i], (i < macAddress.length - 1) ? ":" : ""));
			}
		} else {
			sb.append("N/A");
		}
		return sb.toString();
	}
}
