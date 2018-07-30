package com.sevensignal.infocollector.asynctasks;

import android.os.AsyncTask;
import android.util.Log;

import com.sevensignal.infocollector.models.NetworkInfo;
import com.sevensignal.infocollector.utils.LoggingTags;
import com.sevensignal.infocollector.utils.Network;

import java.util.ArrayList;
import java.util.List;

public class CollectNetworkInfo extends AsyncTask<NetworkInfoObserver, Void, List<NetworkInfo>> {

	private NetworkInfoObserver networkInfoObserver;

	protected List<NetworkInfo> doInBackground(NetworkInfoObserver... networkInfoObserver) {
		this.networkInfoObserver = networkInfoObserver[0];
		List<NetworkInfo> networkInfoList = new ArrayList<>();
		try {
			networkInfoList = Network.collectNetworkInfo();
		} catch (Exception ex) {
			Log.e(LoggingTags.NETWORK_INFO, "Could not get info for the network interfaces", ex);
		}
		return networkInfoList;
	}

	protected void onPostExecute(List<NetworkInfo> networkInfoList) {
		networkInfoObserver.onNetworkInfoUpdate(networkInfoList);
	}
}
