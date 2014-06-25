package com.taixin.android.gateway.service;

import android.content.Context;

import com.taixin.android.gateway.api.IResourceManager;
import com.taixin.android.gateway.log.GLog;

public class GResourceManager implements IResourceManager {
	private static final String TAG 				= "----GResourceManager----";
	private Context context;
	public GResourceManager(){
		
	}
	
	public GResourceManager(Context context){
		this.context = context;
	}
	@Override
	public void ResourceInit() {
		GLog.i(TAG, "----GResourceManager, ResourceInit");

	}

	@Override
	public void ResourceTerm() {
		GLog.i(TAG, "----GResourceManager, ResourceTerm");

	}

}
