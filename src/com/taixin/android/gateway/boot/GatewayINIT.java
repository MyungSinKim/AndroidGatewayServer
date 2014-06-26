package com.taixin.android.gateway.boot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.taixin.android.gateway.api.IResourceManager;
import com.taixin.android.gateway.log.GLog;
import com.taixin.android.gateway.service.GResourceManager;

public class GatewayINIT extends BroadcastReceiver {
	private static final String TAG 				= "----GatewayINIT----";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		GLog.d(TAG, "receive bootcompleted广播!");
		Toast.makeText(context, "gateway收到boot广播 -- 启动", Toast.LENGTH_LONG).show();
		
		IResourceManager resourceMgr = new GResourceManager(context);
		resourceMgr.ResourceInit();
	}

}
