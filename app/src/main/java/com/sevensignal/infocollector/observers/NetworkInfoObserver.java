package com.sevensignal.infocollector.observers;

import com.sevensignal.infocollector.models.NetworkInfo;

import java.util.List;

public interface NetworkInfoObserver {

	void onNetworkInfoUpdate(List<NetworkInfo> networkInfoList);
}
