package com.taixin.android.gateway.log;

import android.util.Log;

public class GLog {

	private final static boolean isDebug = true;
	
	public static void d(Object tag, Object str){
		if(isDebug){
			Log.d(tag.toString(), "----"+str.toString()+"----");
		}
	}
	
	public static void i(Object tag, Object str){
		Log.i(tag.toString(), str.toString());
	}
	
}
