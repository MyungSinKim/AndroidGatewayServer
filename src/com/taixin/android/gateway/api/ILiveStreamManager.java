package com.taixin.android.gateway.api;

public interface ILiveStreamManager {
	
	public void 	liveStreamInit();
	
	public boolean 	liveStreamPlay(int tunerIndex, int serviceid, int tsid);
	
	public String  	liveStreamGetUri();
	
	public boolean 	liveStreamStop(int tunerIndex);
}
