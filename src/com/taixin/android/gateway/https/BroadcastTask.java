package com.taixin.android.gateway.https;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import com.taixin.android.gateway.api.intHeader;
import com.taixin.android.gateway.log.GLog;

import android.util.Log;

public class BroadcastTask implements Runnable {

	private static final String TAG 				= "----BroadcastTask----";
	
	@Override
	public void run() {
		GLog.d(TAG, "------gatewya broadcast thread run! port = "+intHeader.BROADCAST_PORT.value());
		DatagramSocket ds = null;
		try {
			ds = new DatagramSocket(intHeader.BROADCAST_PORT.value());   //固定端口3001
			byte[] buf = new byte[1024];
			DatagramPacket dp = new DatagramPacket(buf,1024);
			while(true){
				ds.receive(dp);
				String strRecv = new String(dp.getData(),0,dp.getLength()) + " from "+ dp.getAddress().getHostAddress() + ":"+dp.getPort();
				GLog.d(TAG, "BroadcastTask UDP 收到 "+strRecv);
	
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
