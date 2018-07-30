package com.sevensignal.infocollector.asynctasks;

import com.sevensignal.infocollector.models.NetworkInfo;

import java.util.List;

public interface NetworkInfoObserver {

	void onNetworkInfoUpdate(List<NetworkInfo> networkInfoList);
}
