package com.sevensignal.infocollector.models;

public class NetworkInfo {

	private String networkDisplayName;
	private String hostName;
	private String hostAddress;
	private byte[] macAddress;

	public NetworkInfo(String networkDisplayName, String hostName, String hostAddress, byte[] macAddress) {
		this.networkDisplayName = networkDisplayName;
		this.hostName = hostName;
		this.hostAddress = hostAddress;
		this.macAddress = macAddress;
	}

	public String getNetworkDisplayName() {
		return networkDisplayName;
	}

	public String getHostName() {
		return hostName;
	}

	public String getHostAddress() {
		return hostAddress;
	}

	public byte[] getMacAddress() {
		return macAddress;
	}
}
