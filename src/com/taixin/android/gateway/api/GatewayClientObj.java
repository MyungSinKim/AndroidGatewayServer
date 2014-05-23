package com.taixin.android.gateway.api;

public class GatewayClientObj {
	private String 		ip;						/*client ip					*/
	private int 		heartFlag;				/*client heart flag			*/
	private int 		tunerIndex;				/*client tuner index		*/
	private boolean 	isLiveStreaming;		/*client is liveStreaming	*/
	private boolean 	isIdle;					/*client is idle			*/
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getHeartFlag() {
		return heartFlag;
	}
	public void setHeartFlag(int heartFlag) {
		this.heartFlag = heartFlag;
	}
	public int getTunerIndex() {
		return tunerIndex;
	}
	public void setTunerIndex(int tunerIndex) {
		this.tunerIndex = tunerIndex;
	}
	public boolean isLiveStreaming() {
		return isLiveStreaming;
	}
	public void setLiveStreaming(boolean isLiveStreaming) {
		this.isLiveStreaming = isLiveStreaming;
	}
	public boolean isIdle() {
		return isIdle;
	}
	public void setIdle(boolean isIdle) {
		this.isIdle = isIdle;
	}
	
}
