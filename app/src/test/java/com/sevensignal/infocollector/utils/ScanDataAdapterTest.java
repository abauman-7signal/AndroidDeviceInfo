package com.sevensignal.infocollector.utils;

import android.net.wifi.ScanResult;

import junit.framework.TestCase;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.util.List;

public class ScanDataAdapterTest extends TestCase {
	private List<ScanResult> scanResults;
	private ScanResult scanResult;

	@Test
	public void testReflection() throws Exception {
//		Constructor<ScanResult> scanResultConstructor = ScanResult.class.getConstructor();
		Constructor<ScanResult>[] scanResultConstructors = (Constructor<ScanResult>[]) ScanResult.class.getConstructors();
		System.out.println(scanResultConstructors.length);
		for (Constructor<ScanResult> scanResultConstructor : scanResultConstructors) {
			System.out.println(scanResultConstructor);
		}
		Class<ScanResult> scanResultClass = (Class<ScanResult>) Class.forName("android.net.wifi.ScanResult");
//		scanResultClass.
//		.newInstance();
//				ScanResult.class.newInstance();
//		System.out.println("hi");
	}

}