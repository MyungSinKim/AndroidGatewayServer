package com.taixin.android.gateway.service;

import java.util.ArrayList;
import java.util.List;

import com.taixin.android.gateway.api.IGatewayClientManager;
import com.taixin.android.gateway.api.obj.GatewayClientObj;
import com.taixin.android.gateway.log.GLog;

public class GatewayClientManagerImpl implements IGatewayClientManager {

	private static GatewayClientManagerImpl instance;
	private List<GatewayClientObj> clientsList;
	private static final String TAG 				= "GatewayClientManagerImpl";
	private static final int GatewayClientsNum 		= 1;
	private int HEART_FLAG							= 6;
	
	public static GatewayClientManagerImpl getInstance(){
		if(instance == null){
			instance = new GatewayClientManagerImpl();
		}
		return instance;
	}
	
	@Override
	public void GatewayClientsInit() {
		clientsList = new ArrayList<GatewayClientObj>();
		for(int i = 0; i<GatewayClientsNum; i++){
			GatewayClientObj client = new GatewayClientObj(null, 0, i+1, false, false);
			clientsList.add(client);
		}
		GLog.getInstance().d(TAG, "GatewayClientsInit clientsList size = "+clientsList.size());
	}
	
	@Override
	public void GatewayClientsTerm() {
		GLog.getInstance().d(TAG, "GatewayClientTerm clientsList term");
		clientsList.clear();
	}
	
	@Override
	public boolean addGatewayClient(String clientIp) {
		GLog.getInstance().d(TAG, "addGatewayClient clientsList term");
		if(inspectClientIsConn(clientIp)){
			/*add heart flag*/
			return true;
		}
		for(GatewayClientObj client : clientsList){
			if(!client.isBusy()){
				client.setIp(clientIp);
				client.setHeartFlag(HEART_FLAG);
				client.setBusy(true);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean delGatewayClient(String clientIp) {
		GLog.getInstance().d(TAG, "delGatewayClient ip = "+clientIp);
		for(GatewayClientObj client : clientsList){
			if(client.getIp().equals(clientIp)){
				if(client.isLiveStreaming()){
					/*stop live streaming*/
					client.setLiveStreaming(false);
				}
				client.setBusy(false);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean inspectClientIsConn(String ip) {
		GLog.getInstance().d(TAG, "inspectTunerIsBusyByIp ip = "+ip);
		for(int i = 0; i<GatewayClientsNum; i++){
			if(clientsList.get(i).getIp().equals(ip) && clientsList.get(i).isBusy() == true)
				return true;
		}
		return false;
	}

	@Override
	public int getTunerIndexByIp(String ip) {
		GLog.getInstance().d(TAG, "getTunerIndexByIp ip = "+ip);
		for(GatewayClientObj client : clientsList){
			if(client.getIp().equals(ip) && client.isBusy())
				return client.getTunerIndex();
		}
		return -1;
	}

	@Override
	public boolean inspectTunerIsBusyByIp(String ip) {
		GLog.getInstance().d(TAG, "inspectTunerIsBusyByIp ip = "+ip);
		for(GatewayClientObj client : clientsList){
			if(client.getIp().equals(ip) && client.isBusy()){
				if(client.isLiveStreaming())
					return true;
			}
		}
		return false;
	}

	@Override
	public boolean setLiveFlagByIp(String ip, boolean flag) {
		GLog.getInstance().d(TAG, "setLiveFlagByIp ip = "+ip);
		for(GatewayClientObj client : clientsList){
			if(client.getIp().equals(ip) && client.isBusy()){
				client.setLiveStreaming(flag);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean setHeartFlagByIp(String ip) {
		GLog.getInstance().d(TAG, "setHeartFlagByIp ip = "+ip);
		for(GatewayClientObj client : clientsList){
			if(client.getIp().equals(ip) && client.isBusy()){
				client.setHeartFlag(HEART_FLAG);
			}
		}
		return false;
	}

	@Override
	public void handleHeartFlagEveryTenSeconds() {
		GLog.getInstance().d(TAG, "handleHeartFlagEveryTenSeconds");
		for(GatewayClientObj client : clientsList){
			if(client.isBusy()){
				if(client.getHeartFlag()>0){
					int heartFlag = client.getHeartFlag();
					client.setHeartFlag(heartFlag-1);
				}
				else{
					if(client.isLiveStreaming()){
						/*停止推送直播*/
					}
					client.setLiveStreaming(false);
					client.setBusy(false);
				}
			}
		}
	}

}
