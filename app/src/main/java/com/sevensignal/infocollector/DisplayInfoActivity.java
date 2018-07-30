package com.sevensignal.infocollector;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.sevensignal.infocollector.asynctasks.CollectNetworkInfo;
import com.sevensignal.infocollector.models.DeviceInfo;
import com.sevensignal.infocollector.models.NetworkInfo;
import com.sevensignal.infocollector.asynctasks.NetworkInfoObserver;
import com.sevensignal.infocollector.utils.Device;
import com.sevensignal.infocollector.utils.Network;

import java.util.List;

public class DisplayInfoActivity extends AppCompatActivity implements NetworkInfoObserver {

	DeviceInfo deviceInfo = new DeviceInfo();
	List<NetworkInfo> networkInfoList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_info);
		TextView deviceInfoTextView = findViewById(R.id.text_view_device_info);
		deviceInfoTextView.setMovementMethod(new ScrollingMovementMethod());

		deviceInfo.setSerialNumber(Device.collectSerialNumber(this));
		new CollectNetworkInfo().execute(this);
	}

	private void updateDisplayedInfo() {
		TextView deviceInfoTextView = findViewById(R.id.text_view_device_info);
		StringBuilder formattedDisplayInfo = new StringBuilder();
		formattedDisplayInfo = addDeviceInfo(formattedDisplayInfo, deviceInfo);
		formattedDisplayInfo = addNetworkInfo(formattedDisplayInfo, networkInfoList);
		deviceInfoTextView.setText(formattedDisplayInfo);
	}

	private StringBuilder addDeviceInfo(StringBuilder infoToDisplay, DeviceInfo deviceInfo) {
		infoToDisplay.append("--------------------------------------" + System.lineSeparator());
		infoToDisplay.append("Device Information" + System.lineSeparator());
		infoToDisplay.append("--------------------------------------" + System.lineSeparator());
		if (deviceInfo != null) {
			infoToDisplay.append("Serial Number: " + deviceInfo.getSerialNumber() + System.lineSeparator());
		} else {
			infoToDisplay.append(getResources().getString(R.string.did_not_find_device_info));
		}
		return infoToDisplay;
	}

	private StringBuilder addNetworkInfo(StringBuilder infoToDisplay, List<NetworkInfo> networkInfoList) {
		infoToDisplay.append("--------------------------------------" + System.lineSeparator());
		infoToDisplay.append("Network Information" + System.lineSeparator());
		infoToDisplay.append("--------------------------------------" + System.lineSeparator());
		if (networkInfoList != null && !networkInfoList.isEmpty()) {
			int count = 0;
			for (NetworkInfo networkInfo : networkInfoList) {
				count++;
				infoToDisplay.append("NET " + count + " --> name: " + networkInfo.getNetworkDisplayName() + System.lineSeparator());
				infoToDisplay.append("  host addr: " + networkInfo.getHostAddress() + System.lineSeparator());
				infoToDisplay.append("  host name: " + networkInfo.getHostName() + System.lineSeparator());
				infoToDisplay.append("  MAC: " + Network.formatMacAddress(networkInfo.getMacAddress()) + System.lineSeparator());
			}
		} else {
			infoToDisplay.append(getResources().getString(R.string.did_not_find_network_info));
		}
		return infoToDisplay;
	}

	@Override
	public void onNetworkInfoUpdate(List<NetworkInfo> networkInfoList) {
		this.networkInfoList = networkInfoList;
		updateDisplayedInfo();
	}
}
