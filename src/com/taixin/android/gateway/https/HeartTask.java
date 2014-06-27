package com.taixin.android.gateway.https;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import com.taixin.android.gateway.api.IGatewayClientManager;
import com.taixin.android.gateway.api.intHeader;
import com.taixin.android.gateway.log.GLog;
import com.taixin.android.gateway.service.GClientManager;

public class HeartTask implements Runnable {

	private static final String TAG 				= "----HeartTask----";
	
	@Override
	public void run() {
		GLog.d(TAG, "------gatewya HeartTask thread run! port = "+intHeader.HEART_BEAT_PORT.value());
		DatagramSocket ds = null;
		try {
			ds = new DatagramSocket(intHeader.HEART_BEAT_PORT.value());   
			byte[] buf = new byte[1024];
			DatagramPacket dp = new DatagramPacket(buf,1024);
			while(true){
				ds.receive(dp);
				String strRecv = new String(dp.getData(),0,dp.getLength()) + " from "+ dp.getAddress().getHostAddress() + ":"+dp.getPort();
				GLog.d(TAG, "HeartTask udp 收到 "+strRecv);
				if(buf[0]==intHeader.GATE_CMD_READ.value()){
					GLog.d(TAG, "intHeader.GATE_CMD_READ buf[0]==0x40");
					if(buf[1] == intHeader.GATE_CMD_SEND_HEART_BEAT.value()){
						GLog.d(TAG, "intHeader.GATE_CMD_SEND_HEART_BEAT == 0x05");
						IGatewayClientManager clientMgr = GClientManager.getInstance();
						clientMgr.setHeartFlagByIp(dp.getAddress().getHostAddress());
						
						byte[] backData = HeartTask.backHeartData();
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
	public static byte[] backHeartData(){
		String backStr = "Oh yeah!";
		byte[] backStrByte = backStr.getBytes();
		byte[] backData = new byte[intHeader.GATE_MAX_STRING_NUM.value()];
		backData[0] = (byte) intHeader.GATE_CMD_READ.value();
		backData[1] = (byte) intHeader.GATE_CMD_RECV_HEART_BEAT.value();
		backData[2] = 0x00;
		backData[3] = 0x01;
		backData[4] = 0x00;
		backData[5] = 0x01;
		backData[6] = (byte) ((backStrByte.length&0xff00)>>8);
		backData[7] = (byte) (backStrByte.length&0xff00);
		System.arraycopy(backStrByte, 0, backData, 8, backStrByte.length);
		
		return backData;
	}

}
