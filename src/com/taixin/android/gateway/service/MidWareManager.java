package com.taixin.android.gateway.service;

import android.content.Context;

import com.taixin.android.gateway.api.IMidWareManager;
import com.taixin.android.gateway.log.GLog;
import com.taixin.idtv.IdtvManager;
import com.taixin.idtv.system.DtvListener;

public class MidWareManager implements IMidWareManager {
	
	private static final String TAG 				= "----MidWareManager----";
	private Context context;
	private static MidWareManager instance;
	
	public static MidWareManager getInstance(){
		if(instance == null){
			instance = new MidWareManager();
		}
		return instance;
	}
	
	@Override
	public void MidWareInit() {
		IdtvManager.getInstance().registerDtvListener(new DtvListener(){

			@Override
			public void OnIdtvEvent(int arg0, int arg1, int arg2) {
				if(arg1 == DtvListener.ACTION_SYS_RELEASED){
					GLog.d(TAG, "dtvlistener on idtv event listener");
				}				
			}
			
		});
		IdtvManager.resourceInit();
		GLog.d(TAG, "MidWareInit");
	}

	@Override
	public void MideWareTerm() {
		IdtvManager.resourceTerm();
		GLog.d(TAG, "MideWareTerm");
	}

}
