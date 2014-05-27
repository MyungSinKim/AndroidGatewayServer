package com.taixin.android.gateway.log;

import android.util.Log;

public class GLog {

	private final static boolean isDebug = true;
	private static GLog instance;
	
	public static GLog getInstance(){
		if(instance == null){
			instance = new GLog();
		}
		return instance;
	}
	
	public void d(Object tag, Object str){
		if(isDebug){
			Log.d(tag.toString(), str.toString());
		}
	}
	
	public void i(Object tag, Object str){
		Log.i(tag.toString(), str.toString());
	}
	
}
