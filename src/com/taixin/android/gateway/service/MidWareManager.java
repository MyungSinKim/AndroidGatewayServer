package com.taixin.android.gateway.service;

import android.content.Context;

import com.taixin.android.gateway.api.IMidWareManager;
import com.taixin.android.gateway.log.GLog;
import com.taixin.idtv.IdtvManager;
import com.taixin.idtv.system.DtvListener;

public class MidWareManager implements IMidWareManager {
	
	private static final String TAG 				= "--MidWareManager--";
	private Context context;
	
	public MidWareManager(){
		
	}
	
	public MidWareManager(Context context){
		this.context = context;
	}
	@Override
	public void MidWareInit() {
		/*Mid Ware init*/
		IdtvManager.getInstance().registerDtvListener(new DtvListener(){

			@Override
			public void OnIdtvEvent(int arg0, int arg1, int arg2) {
				if(arg1 == DtvListener.ACTION_SYS_RELEASED){
					//IdtvManager.resourceTerm();
				}				
			}
			
		});
		IdtvManager.resourceInit();
		GLog.d(TAG, "MidWareInit");
	}

	@Override
	public void MideWareTerm() {
		/*Mid Ware term*/
		GLog.d(TAG, "MideWareTerm");
		IdtvManager.resourceTerm();
	}

}
