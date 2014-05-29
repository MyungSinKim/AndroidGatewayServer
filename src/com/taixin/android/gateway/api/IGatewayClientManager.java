package com.taixin.android.gateway.api;

public interface IGatewayClientManager {
	public void   	GatewayClientsInit();
	
	public void     GatewayClientsTerm();
	
	public boolean 	addGatewayClient(String clientIp);
	
	public boolean	delGatewayClient(String clientIp);
	
	public boolean 	inspectClientIsConn(String ip);
	
	public int		getTunerIndexByIp(String ip);
	
	public boolean 	inspectTunerIsBusyByIp(String ip);
	
	public boolean 	setLiveFlagByIp(String ip, boolean flag);
	
	public boolean 	setHeartFlagByIp(String ip);
	
	public void     handleHeartFlagEveryTenSeconds();
}
