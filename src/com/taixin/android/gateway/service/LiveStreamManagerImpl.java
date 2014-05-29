package com.taixin.android.gateway.service;

import com.taixin.android.gateway.api.ILiveStreamManager;

public class LiveStreamManagerImpl implements ILiveStreamManager {

	private static LiveStreamManagerImpl instance;
	
	public static LiveStreamManagerImpl getInstance(){
		if(instance == null){
			instance = new LiveStreamManagerImpl();
		}
		return instance;
	}
	@Override
	public void liveStreamInit() {

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
