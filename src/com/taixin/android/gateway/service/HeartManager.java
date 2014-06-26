package com.taixin.android.gateway.service;

import com.taixin.android.gateway.api.obj.GatewayClientObj;
import com.taixin.android.gateway.log.GLog;

public class HeartManager implements Runnable{
	
	private static final String TAG 				= "----ProtocolTask----";
	
	@Override
	public void run() {
		GLog.d(TAG, "------HeartManager  thread run! ");
		while(true){
			GLog.d(TAG, "------HeartManager  10 seconds run~~~~~~~~~~~~~~~~~ ");
			for(GatewayClientObj client : GClientManager.getInstance().getClientList()){
				if(client.isBusy()){
					GLog.i(TAG, "find live client ip =  "+client.getIp());
					if(client.getHeartFlag() > 0){
						client.setHeartFlag(client.getHeartFlag() - 1);
					}else{
						if(client.isLiveStreaming()){
							//live stream stop here need add
							client.setHeartFlag(0);
						}
						client.setBusy(false);
					}
					
				}
			}
			try {
				Thread.sleep(10 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
