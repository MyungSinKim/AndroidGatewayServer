package com.taixin.android.gateway.service;

import com.taixin.android.gateway.api.ILiveStreamManager;
import com.taixin.android.gateway.log.GLog;

public class LiveStreamManager implements ILiveStreamManager {
	private static final String TAG 				= "--LiveStreamManager--";
	private static LiveStreamManager instance;
	
	public static LiveStreamManager getInstance(){
		if(instance == null){
			instance = new LiveStreamManager();
		}
		return instance;
	}
	@Override
	public void liveStreamInit() {
		GLog.d(TAG, "liveStreamInit");
	}

	@Override
	public boolean liveStreamPlay(int tunerIndex, int serviceid, int tsid) {
		return false;
	}

	@Override
	public String liveStreamGetUri(int tunerIndex) {
		return null;
	}

	@Override
	public boolean liveStreamStop(int tunerIndex) {
		return false;
	}

}
