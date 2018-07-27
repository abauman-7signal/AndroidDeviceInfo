package com.sevensignal.infocollector.models;

public class NetworkInfo {

	private String networkDisplayName;
	private String hostName;
	private String hostAddress;

	public NetworkInfo(String networkDisplayName, String hostName, String hostAddress) {
		this.networkDisplayName = networkDisplayName;
		this.hostName = hostName;
		this.hostAddress = hostAddress;
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
}
