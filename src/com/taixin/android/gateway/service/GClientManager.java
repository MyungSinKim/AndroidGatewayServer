package com.taixin.android.gateway.service;

import java.util.ArrayList;
import java.util.List;

import com.taixin.android.gateway.api.IGatewayClientManager;
import com.taixin.android.gateway.api.intHeader;
import com.taixin.android.gateway.api.obj.GatewayClientObj;
import com.taixin.android.gateway.log.GLog;

public class GClientManager implements IGatewayClientManager {

	private static final String TAG 				= "----GatewayClientManager----";
	private List<GatewayClientObj> clientList = new ArrayList<GatewayClientObj>();
	@Override
	public void GatewayClientsInit() {
		GLog.d(TAG, "GatewayClientsInit");
		for(int i = 0; i< intHeader.MAX_CLIENTS.value(); i++){
			GatewayClientObj client = new GatewayClientObj();
			client.setBusy(false);
			clientList.add(client);
		}
		GLog.d(TAG, "clientList size = "+clientList.size());
	}

	@Override
	public void GatewayClientsTerm() {
		GLog.d(TAG, "GatewayClientsTerm");
		clientList.clear();
	}

	@Override
	public boolean addGatewayClient(String clientIp) {
		GLog.d(TAG, "addGatewayClient");
		if(this.inspectClientIsConn(clientIp)){
			this.setHeartFlagByIp(clientIp);
			return true;
		}
		return false;
	}

	@Override
	public boolean delGatewayClient(String clientIp) {
		GLog.d(TAG, "delGatewayClient  clientIp = "+clientIp);
		for(GatewayClientObj client : clientList){
			if(client.getIp().equals(clientIp)){
				if(client.isLiveStreaming()){
					/**stop live streaming**/
					client.setLiveStreaming(false);
				}
				client.setBusy(false);
				GLog.d(TAG, "delGatewayClient success ");
				return true;
			}
		}
		GLog.d(TAG, "delGatewayClient fail ");
		return false;
	}

	@Override
	public boolean inspectClientIsConn(String ip) {
		
		for(int i = 0; i < intHeader.MAX_CLIENTS.value(); i++){
			if(clientList.get(i).getIp().equals(ip)){
				return true;
			}
		}
		return false;
	}

	@Override
	public int getTunerIndexByIp(String ip) {
		for(GatewayClientObj client : clientList){
			if(client.getIp().equals(ip)){
				return client.getTunerIndex();
			}
		}
		return -1;
	}

	@Override
	public boolean inspectTunerIsBusyByIp(String ip) {
		for(GatewayClientObj client : clientList){
			if(client.isLiveStreaming()){
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean setLiveFlagByIp(String ip, boolean flag) {
		for(GatewayClientObj client : clientList){
			if(client.getIp().equals(ip)){
				client.setLiveStreaming(flag);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean setHeartFlagByIp(String ip) {
		for(GatewayClientObj client : clientList){
			if(client.getIp().equals(ip)){
				client.setHeartFlag(intHeader.HEART_BEAT_FLAG.value());
				return true;
			}
		}
		return false;
	}

	@Override
	public void handleHeartFlagEveryTenSeconds() {
		// TODO Auto-generated method stub

	}

}
