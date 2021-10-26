package com.sevensignal.infocollector.models;

import lombok.Data;

@Data
public class DeviceInfo {

	private String serialNumber;
	private String osName;
	private int sdk;
}
