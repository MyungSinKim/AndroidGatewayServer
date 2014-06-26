package com.taixin.android.gateway.service;

import android.content.Context;

import com.taixin.android.gateway.api.IGatewayClientManager;
import com.taixin.android.gateway.api.IResourceManager;
import com.taixin.android.gateway.https.BroadcastTask;
import com.taixin.android.gateway.https.HeartTask;
import com.taixin.android.gateway.https.ProtocolTask;
import com.taixin.android.gateway.log.GLog;

public class GResourceManager implements IResourceManager {
	
	private static final String TAG 				= "----GResourceManager----";
	private IGatewayClientManager clientMgr;
	private Thread broadTask;
	private Thread heartTask;
	private Thread protocolTask;
	private Thread heartMgrTask;
	private Context context;
	public GResourceManager(){
		
	}
	
	public GResourceManager(Context context){
		this.context = context;
		clientMgr = GClientManager.getInstance();
		broadTask = new Thread(new BroadcastTask());
		heartTask = new Thread(new HeartTask());
		protocolTask = new Thread(new ProtocolTask());
		heartMgrTask = new Thread(new HeartManager());
	}
	
	@Override
	public void ResourceInit() {
		GLog.i(TAG, "----GResourceManager, ResourceInit");
		clientMgr.GatewayClientsInit();
		broadTask.start();
		protocolTask.start();
		heartTask.start();
		heartMgrTask.start();
	}

	@Override
	public void ResourceTerm() {
		GLog.i(TAG, "----GResourceManager, ResourceTerm");
		clientMgr.GatewayClientsTerm();
		broadTask.destroy();
	}

}
