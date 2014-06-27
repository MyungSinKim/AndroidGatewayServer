package com.taixin.android.gateway.service;

import java.util.ArrayList;
import java.util.List;

import com.taixin.android.gateway.api.IGatewayClientManager;
import com.taixin.android.gateway.api.intHeader;
import com.taixin.android.gateway.api.obj.GatewayClientObj;
import com.taixin.android.gateway.log.GLog;

public class GClientManager implements IGatewayClientManager {

	private static final String TAG 				= "----GatewayClientManager----";
	private List<GatewayClientObj> clientList ;
	private List<Integer> stbTuners;
	private static GClientManager instance;
	
	public static GClientManager getInstance(){
		if(instance == null){
			instance = new GClientManager();
		}
		return instance;
	}
	
	public List<GatewayClientObj> getClientList() {
		return clientList;
	}

	public void setClientList(List<GatewayClientObj> clientList) {
		this.clientList = clientList;
	}

	public List<Integer> getStbTuners() {
		return stbTuners;
	}

	public void setStbTuners(List<Integer> stbTuners) {
		this.stbTuners = stbTuners;
	}

	@Override
	public void GatewayClientsInit() {
		GLog.d(TAG, "GatewayClientsInit");
		clientList = new ArrayList<GatewayClientObj>();
		stbTuners = new ArrayList<Integer>();
		for(int i = 0; i< intHeader.MAX_CLIENTS.value(); i++){
			GatewayClientObj client = new GatewayClientObj();
			client.setBusy(false);
			clientList.add(client);
			stbTuners.add(i);
		}
		GLog.d(TAG, "clientList size = "+clientList.size());
		GLog.d(TAG, "stbTuners size = "+stbTuners.size());
	}

	@Override
	public void GatewayClientsTerm() {
		GLog.d(TAG, "GatewayClientsTerm");
		clientList.clear();
	}

	@Override
	public boolean addGatewayClient(String clientIp) {
		GLog.d(TAG, "addGatewayClient ip = "+clientIp);
		if(this.inspectClientIsConn(clientIp)){
			this.setHeartFlagByIp(clientIp);
			return true;
		}
		for(int i = 0; i< intHeader.MAX_CLIENTS.value(); i++){
			GatewayClientObj client = new GatewayClientObj();
			if(!client.isBusy()){
				client.setIp(clientIp);
				client.setHeartFlag(intHeader.HEART_BEAT_FLAG.value());		
				client.setLiveStreaming(false);
				client.setTunerIndex(stbTuners.get(i));
				client.setBusy(true);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean delGatewayClient(String clientIp) {
		GLog.d(TAG, "delGatewayClient  clientIp = "+clientIp);
		for(int i = 0; i< intHeader.MAX_CLIENTS.value(); i++){
			GatewayClientObj client = new GatewayClientObj();
			if(client.getIp().equals(clientIp)){
				if(client.isLiveStreaming()){
					/**stop live streaming here need add**/
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
		for(int i = 0; i< intHeader.MAX_CLIENTS.value(); i++){
			GatewayClientObj client = new GatewayClientObj();
			if(client.getIp().equals(ip)){
				return client.getTunerIndex();
			}
		}
		return -1;
	}

	@Override
	public boolean inspectTunerIsBusyByIp(String ip) {
		for(int i = 0; i< intHeader.MAX_CLIENTS.value(); i++){
			GatewayClientObj client = new GatewayClientObj();
			if(client.isLiveStreaming()){
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean setLiveFlagByIp(String ip, boolean flag) {
		for(int i = 0; i< intHeader.MAX_CLIENTS.value(); i++){
			GatewayClientObj client = new GatewayClientObj();
			if(client.getIp().equals(ip)){
				client.setLiveStreaming(flag);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean setHeartFlagByIp(String ip) {
		for(int i = 0; i< intHeader.MAX_CLIENTS.value(); i++){
			GatewayClientObj client = new GatewayClientObj();
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
