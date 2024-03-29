package com.sevensignal.infocollector;

import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.sevensignal.infocollector.asynctasks.CollectNetworkInfo;
import com.sevensignal.infocollector.asynctasks.PermissionRequester;
import com.sevensignal.infocollector.asynctasks.WifiAccessPointScanner;
import com.sevensignal.infocollector.asynctasks.WifiAccessPointScanningObserver;
import com.sevensignal.infocollector.models.DeviceInfo;
import com.sevensignal.infocollector.models.InformationElementA;
import com.sevensignal.infocollector.models.NetworkInfo;
import com.sevensignal.infocollector.asynctasks.NetworkInfoObserver;
import com.sevensignal.infocollector.models.WifiInfo;
import com.sevensignal.infocollector.utils.Device;
import com.sevensignal.infocollector.utils.LoggingTags;
import com.sevensignal.infocollector.utils.ScanDataAdapter;
import com.sevensignal.infocollector.utils.Wifi;

import java.util.List;

public class DisplayInfoActivity extends AppCompatActivity implements
		NetworkInfoObserver, ActivityCompat.OnRequestPermissionsResultCallback,
		WifiAccessPointScanningObserver {

	private DeviceInfo deviceInfo = new DeviceInfo();
	private WifiInfo wifiInfo;
	private List<NetworkInfo> networkInfoList;
	private List<ScanResult> scanResultList;
	private WifiAccessPointScanner wifiAccessPointScanner = new WifiAccessPointScanner();
	private ScanDataAdapter scanDataAdapter = new ScanDataAdapter();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_info);
		TextView deviceInfoTextView = findViewById(R.id.text_view_device_info);
		deviceInfoTextView.setMovementMethod(new ScrollingMovementMethod());

		Button button = findViewById(R.id.button_refresh_info);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				refreshInfo();
			}
		});

		refreshInfo();
	}

	private void refreshInfo() {
		deviceInfo.setSerialNumber(Device.collectSerialNumber(this));
		deviceInfo.setOsName(System.getProperty("os.name"));
		deviceInfo.setSdk(Build.VERSION.SDK_INT);
		wifiInfo = Wifi.collectWifiInfo(this);
		new CollectNetworkInfo().execute(this);
		wifiAccessPointScanner.scanWifiChannels(this, this);
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		switch (requestCode) {
			case PermissionRequester.PERMISSION_TO_READ_PHONE_STATE:
				Log.i(LoggingTags.UI, "notified that requested permission to read phone state");
				for(int grantResult : grantResults) {
					if (grantResult == PackageManager.PERMISSION_GRANTED) {
						deviceInfo.setSerialNumber(Device.collectSerialNumber(this));
						updateDisplayedInfo();
					}
				}
				break;

			case PermissionRequester.PERMISSION_TO_READ_LOCATION:
				Log.i(LoggingTags.UI, "notified that requested permission to change read location");
				retryScanningAccessPointsIfPermissionGranted(grantResults);
				break;

			case PermissionRequester.PERMISSION_TO_CHANGE_WIFI_STATE:
				Log.i(LoggingTags.UI, "notified that requested permission to change wifi state");
				retryScanningAccessPointsIfPermissionGranted(grantResults);
				break;

			default:
				break;
		}
	}

	private void retryScanningAccessPointsIfPermissionGranted(@NonNull int[] grantResults) {
		for (int grantResult : grantResults) {
			if (grantResult == PackageManager.PERMISSION_GRANTED) {
				wifiAccessPointScanner.scanWifiChannels(this, this);
			}
		}
	}

	private void updateDisplayedInfo() {
		TextView deviceInfoTextView = findViewById(R.id.text_view_device_info);
		StringBuilder formattedDisplayInfo = new StringBuilder();
		addDeviceInfo(formattedDisplayInfo, deviceInfo);
		addWifiInfo(formattedDisplayInfo, wifiInfo);
		addWifiAccessPointScanInfo(formattedDisplayInfo, scanResultList);
		addNetworkInfo(formattedDisplayInfo, networkInfoList);
		deviceInfoTextView.setText(formattedDisplayInfo);
	}

	private void addDeviceInfo(StringBuilder infoToDisplay, DeviceInfo deviceInfo) {
		infoToDisplay
				.append("--------------------------------------").append(System.lineSeparator())
				.append("Device Information").append(System.lineSeparator())
				.append("--------------------------------------").append(System.lineSeparator());
		if (deviceInfo != null) {
			infoToDisplay.append("Serial Number: ").append(deviceInfo.getSerialNumber()).append(System.lineSeparator());
			infoToDisplay.append("OS: ").append(deviceInfo.getOsName()).append(System.lineSeparator());
			infoToDisplay.append("SDK: ").append(deviceInfo.getSdk()).append(System.lineSeparator());
		} else {
			infoToDisplay.append(getResources().getString(R.string.did_not_find_device_info));
		}
	}

	private void addWifiInfo(StringBuilder infoToDisplay, WifiInfo wifiInfo) {
		infoToDisplay
				.append("--------------------------------------").append(System.lineSeparator())
				.append("Wi-Fi Information").append(System.lineSeparator())
				.append("--------------------------------------").append(System.lineSeparator());
		if (deviceInfo != null) {
			infoToDisplay.append(wifiInfo.getInfoForDisplay()).append(System.lineSeparator());
		} else {
			infoToDisplay.append(getResources().getString(R.string.did_not_find_wifi_info));
		}
	}

	private void addNetworkInfo(StringBuilder infoToDisplay, List<NetworkInfo> networkInfoList) {
		infoToDisplay
				.append("--------------------------------------").append(System.lineSeparator())
				.append("Network Information").append(System.lineSeparator())
				.append("--------------------------------------").append(System.lineSeparator());
		if (networkInfoList != null && !networkInfoList.isEmpty()) {
			int count = 0;
			for (NetworkInfo networkInfo : networkInfoList) {
				count++;
				infoToDisplay.append("NET ").append(count).append(" --> name: ").append(networkInfo.getNetworkDisplayName()).append(System.lineSeparator());
				infoToDisplay.append(networkInfo.getInfoForDisplay()).append(System.lineSeparator());
			}
		} else {
			infoToDisplay.append(getResources().getString(R.string.did_not_find_network_info));
		}
	}

	private void addWifiAccessPointScanInfo(StringBuilder infoToDisplay, List<ScanResult> scanResultList) {
		Switch switchScanDataForConnectedSsid = findViewById(R.id.switch_filter_by_connected_ssid);
		boolean shouldFilterOnConnectedSsid = switchScanDataForConnectedSsid.isChecked();

		infoToDisplay
				.append("--------------------------------------").append(System.lineSeparator())
				.append("Scan Result Information").append(System.lineSeparator())
				.append("--------------------------------------").append(System.lineSeparator());
		if (scanResultList != null && !scanResultList.isEmpty()) {
			int count = 0;
			for (ScanResult scanResult : scanResultList) {
				count++;
				if (shouldFilterOnConnectedSsid) {
					if (scanResult.SSID.equals(wifiInfo.getSsid().replace("\"", ""))) {
						addScanResult(infoToDisplay, count, scanResult);
					}
				} else {
					addScanResult(infoToDisplay, count, scanResult);
				}
			}
		} else {
			infoToDisplay.append(getResources().getString(R.string.did_not_find_access_point_info));
		}
	}

	private void addScanResult(StringBuilder infoToDisplay, int count, ScanResult scanResult) {
		List<InformationElementA> informationElementAList = scanDataAdapter.findInformationElements(scanResult);
		infoToDisplay.append("AP SCAN ").append(count).append(" --> SSID: ").append(scanResult.SSID).append(System.lineSeparator());
		infoToDisplay.append(
				scanResult.toString()
						.replace("SSID", "    SSID")
						.replace("B    SSID", "BSSID")
						.replace(",", System.lineSeparator() + "   ")
		)
				.append(System.lineSeparator());
		if (informationElementAList.isEmpty()) {
			infoToDisplay.append(" No Information Element Data");
		} else {
			for (InformationElementA informationElementA : informationElementAList) {
				infoToDisplay.append("    " + informationElementA.toString() + System.lineSeparator());
			}
		}
	}

	@Override
	public void onNetworkInfoUpdate(List<NetworkInfo> networkInfoList) {
		this.networkInfoList = networkInfoList;
		updateDisplayedInfo();
	}

	@Override
	public void onWifiAccessPointScannerUpdate(List<ScanResult> scanResultList) {
		this.scanResultList = scanResultList;
		updateDisplayedInfo();
	}
}
