package com.sevensignal.infocollector.utils;

import android.net.wifi.ScanResult;
import android.util.Log;

import com.sevensignal.infocollector.models.InformationElementA;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ScanDataAdapter {
	private static final String TAG = ScanDataAdapter.class.getName();

	public List<InformationElementA> findInformationElements(final ScanResult scanResult) {
		List<InformationElementA> informationElementAList = new ArrayList<>();

		try {
			Field field = scanResult.getClass().getDeclaredField("informationElements");
			field.setAccessible(true);
			Log.d(TAG, "field.getType: " + field.getType());
			Object[] informationElements = (Object[])field.get(scanResult);
			if (informationElements == null) {
				return Collections.EMPTY_LIST;
			}
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

		return informationElementAList;
	}
}
