package com.sevensignal.infocollector;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.sevensignal.infocollector.asynctasks.CollectNetworkInfo;
import com.sevensignal.infocollector.models.NetworkInfo;
import com.sevensignal.infocollector.observers.NetworkInfoObserver;

import java.util.List;

public class DisplayInfoActivity extends AppCompatActivity implements NetworkInfoObserver {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_info);
		new CollectNetworkInfo().execute(this);
	}

	private void displayInfo(List<NetworkInfo> networkInfoList) {
		TextView deviceInfoTextView = findViewById(R.id.text_view_device_info);
		StringBuilder deviceInfo = new StringBuilder();
		if (networkInfoList != null && !networkInfoList.isEmpty()) {
			int count = 0;
			for (NetworkInfo networkInfo : networkInfoList) {
				count++;
				deviceInfo.append("NET " + count + " --> name: " + networkInfo.getNetworkDisplayName() + System.lineSeparator());
				deviceInfo.append("  host addr: " + networkInfo.getHostAddress() + System.lineSeparator());
				deviceInfo.append("  host name: " + networkInfo.getHostName() + System.lineSeparator());
			}
		} else {
			deviceInfo.append(getResources().getString(R.string.did_not_find_device_info));
		}
		deviceInfoTextView.setText(deviceInfo);
	}

	@Override
	public void onNetworkInfoUpdate(List<NetworkInfo> networkInfoList) {
		displayInfo(networkInfoList);
	}
}
