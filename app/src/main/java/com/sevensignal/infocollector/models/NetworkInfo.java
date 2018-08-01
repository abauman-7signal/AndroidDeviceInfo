package com.sevensignal.infocollector.models;

import lombok.Data;

@Data(staticConstructor="build")
public class NetworkInfo {

	private final String networkDisplayName;
	private final String canonicalHostName;
	private final String hostName;
	private final String hostAddress;
	private final String macAddress;

	public String getInfoForDisplay() {
		return this.toString()
				.replace("NetworkInfo(", " ")
				.replace(",", System.lineSeparator())
				.replace(")", "");
	}
}
