package com.taixin.android.gateway.api;

public interface IGatewayClientManager {
	
	public boolean 	addGatewayClient(String clientIp);
	
	public boolean	delGatewayClient(String clientIp);
	
	public boolean 	inspectClientIsConn(String ip);
	
	public int 	   	getTunerIndexByIp(String ip);
	
	public boolean 	inspectTunerIsBusyByIp(String ip);
	
	public boolean 	setLiveFlagByIp(String ip);
	
	public boolean 	setHeartFlagByIp(String ip);
}
