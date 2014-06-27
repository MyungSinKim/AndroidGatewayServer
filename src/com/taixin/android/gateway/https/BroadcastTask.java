package com.taixin.android.gateway.https;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import com.taixin.android.gateway.api.intHeader;
import com.taixin.android.gateway.log.GLog;

public class BroadcastTask implements Runnable {

	private static final String TAG 				= "----BroadcastTask----";
	
	
	@Override
	public void run() {
		GLog.d(TAG, "------gatewya broadcast thread run! port = "+intHeader.BROADCAST_PORT.value());
		DatagramSocket ds = null;
		try {
			ds = new DatagramSocket(intHeader.BROADCAST_PORT.value()); 
			byte[] buf = new byte[intHeader.GATE_MAX_STRING_NUM.value()];
			DatagramPacket dp = new DatagramPacket(buf,intHeader.GATE_MAX_STRING_NUM.value());
			while(true){
				ds.receive(dp);
				String strRecv = new String(dp.getData(),0,dp.getLength()) + " from "+ dp.getAddress().getHostAddress() + ":"+dp.getPort();
				GLog.i(TAG, "BroadcastTask UDP 收到 "+strRecv);
				if(buf[0]==intHeader.GATE_CMD_READ.value()){
					GLog.d(TAG, "intHeader.GATE_CMD_READ buf[0]==0x40");
					if(buf[1] == intHeader.GATE_CMD_SEND_BROADCAST.value()){
						GLog.d(TAG, "intHeader.GATE_CMD_SEND_BROADCAST == 0x01");
						byte[] backData = BroadcastTask.backBroadcastData();
						DatagramSocket dst = new DatagramSocket();
						DatagramPacket packet2 = 
						new DatagramPacket(backData,backData.length,InetAddress.getByName(dp.getAddress().getHostAddress()),dp.getPort());
						dst.send(packet2);
						dst.close();
					}
				}
	
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static byte[] backBroadcastData(){
		String gatename = "泰信T2";
		byte[] nameByte = gatename.getBytes();
		byte[] backData = new byte[intHeader.GATE_MAX_STRING_NUM.value()];
		backData[0] = (byte) intHeader.GATE_CMD_WRITE.value();
		backData[1] = (byte) intHeader.GATE_CMD_RECV_BROADCAST.value();
		backData[2] = 0x00;
		backData[3] = 0x01;
		backData[4] = 0x00;
		backData[5] = 0x01;
		backData[6] = 0x00;
		backData[7] = (byte) (1 + nameByte.length);
		backData[8] = (byte) (nameByte.length);
		System.arraycopy(nameByte, 0, backData, 9, nameByte.length);
		
		return backData;
	}
}
