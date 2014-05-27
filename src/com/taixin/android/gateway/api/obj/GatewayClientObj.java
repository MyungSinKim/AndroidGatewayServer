package com.taixin.android.gateway.api.obj;

public class GatewayClientObj {
	private String 		ip;						/*client ip					*/
	private int 		heartFlag;				/*client heart flag			*/
	private int 		tunerIndex;				/*client tuner index		*/
	private boolean 	isLiveStreaming;		/*client is liveStreaming	*/
	private boolean 	isBusy;					/*client is busy			*/
	
	public GatewayClientObj(){
		
	}
	
	public GatewayClientObj(String ip, int heartFlag, int tunerIndex, boolean isLiveStreaming, boolean isBusy){
		this.ip 			 = ip;
		this.heartFlag 		 = heartFlag;
		this.tunerIndex 	 = tunerIndex;
		this.isLiveStreaming = isLiveStreaming;
		this.isBusy			 = isBusy;
	}

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
	public boolean isBusy() {
		return isBusy;
	}
	public void setBusy(boolean isBusy) {
		this.isBusy = isBusy;
	}
}
