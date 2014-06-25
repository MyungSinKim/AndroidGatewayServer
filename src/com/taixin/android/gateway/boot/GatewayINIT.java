package com.taixin.android.gateway.boot;

import com.taixin.android.gateway.log.GLog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class GatewayINIT extends BroadcastReceiver {
	private static final String TAG 				= "----GatewayINIT----";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		GLog.d(TAG, "receive bootcompleted广播!");
		Toast.makeText(context, "收到boot广播 gateway", Toast.LENGTH_LONG).show();
		

	}

}
