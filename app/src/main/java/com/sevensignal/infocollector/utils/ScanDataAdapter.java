package com.sevensignal.infocollector.utils;

import android.net.wifi.ScanResult;
import android.util.Log;

import com.sevensignal.infocollector.models.InformationElementA;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScanDataAdapter {
	private static final String TAG = ScanDataAdapter.class.getName();

	public List<InformationElementA> findInformationElements(List<ScanResult> scanResultList) {
//		try {
//			Constructor<ScanResult>[] scanResultConstructors = (Constructor<ScanResult>[]) ScanResult.class.getConstructors();
//			Log.d(TAG, "Number of constructors: " + String.valueOf(scanResultConstructors.length));
//			int i = 1;
//			for (Constructor<ScanResult> scanResultConstructor : scanResultConstructors) {
//				Log.d(TAG, "Constructor " + i++ + ": " + scanResultConstructor);
//			}
//			Class<ScanResult> scanResultClass = (Class<ScanResult>) Class.forName("android.net.wifi.ScanResult");
//			ScanResult scanResult = scanResultClass.newInstance();
//			Log.d(TAG, "ScanResult: " + scanResult);

			List<InformationElementA> informationElementAList = new ArrayList<>();

			scanResultList.forEach(scanResult -> {
				try {
					Field field = scanResult.getClass().getDeclaredField("informationElements");
					field.setAccessible(true);
					Log.d(TAG, "field.getType: " + field.getType());
					Object[] informationElements = (Object[])field.get(scanResult);
					for (Object informationElement : informationElements) {
						Field idField = informationElement.getClass().getDeclaredField("id");
						idField.setAccessible(true);
						final int informationElementId = (int)idField.get(informationElement);
						Log.d(TAG, "IE: " + informationElementId);

						Field bytesField = informationElement.getClass().getDeclaredField("bytes");
						bytesField.setAccessible(true);
						final byte[] informationElementBytes = (byte[]) bytesField.get(informationElement);
						Log.d(TAG, "  bytes: " + Arrays.toString(informationElementBytes));
						informationElementAList.add(new InformationElementA(
								informationElementId,
								informationElementBytes
						));
					}
				} catch (IllegalAccessException | NoSuchFieldException ex) {
					Log.e(TAG, "exception during reflection", ex);
				}
			});

//		} catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchFieldException ex) {
//			Log.e(TAG, "exception during reflection", ex);
//		}
		return informationElementAList;
	}
}
