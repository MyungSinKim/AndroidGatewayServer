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

public class ProtocolTask implements Runnable {

	private static final String TAG 				= "----ProtocolTask----";
	private IGatewayClientManager clientMgr;
	
	public ProtocolTask(){
		clientMgr = GClientManager.getInstance();
	}
	
	@Override
	public void run() {
		GLog.d(TAG, "------gatewya ProtocolTask thread run! port = "+intHeader.UNICAST_PORT.value());
		DatagramSocket ds = null;
		try {
			ds = new DatagramSocket(intHeader.UNICAST_PORT.value());   
			byte[] buf = new byte[1024];
			DatagramPacket dp = new DatagramPacket(buf,1024);
			while(true){
				ds.receive(dp);
				String strRecv = new String(dp.getData(),0,dp.getLength()) + " from "+ dp.getAddress().getHostAddress() + ":"+dp.getPort();
				GLog.d(TAG, "ProtocolTask udp 收到 "+strRecv);
				this.handleUdpMsg(buf, dp);
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void handleUdpMsg(byte[] data, DatagramPacket dp){
		
		if(data[0] == intHeader.GATE_CMD_WRITE.value()){
			
			if(data[1] == intHeader.GATE_CMD_SEND_MATCH.value()){
				GLog.d(TAG, "client = "+dp.getAddress().getHostAddress()+" is requesting to match");
				this.handleMatchMsg(dp);
			}
			else
			if(data[1] == intHeader.GATE_CMD_SEND_QUIT.value()){
				GLog.d(TAG, "client = "+dp.getAddress().getHostAddress()+" is requesting to quit");
				this.handleQuitMsg(dp);
			}
		}
		else 
		if(data[0] == intHeader.GATE_CMD_READ.value()){
			
		}
		
		
//		if(data[0] == intHeader.GATE_CMD_SEND_MATCH.value()){
//			
//		}
//		else
//		if(data[0] == intHeader.GATE_CMD_SEND_REQUEST_SERVICE_LIST.value()){
//			
//		}
//		else
//		if(data[0] == intHeader.GATE_CMD_SEND_REQUEST_PLAY_ADDRESS.value()){
//			
//		}
//		else
//		if(data[0] == intHeader.GATE_CMD_SEND_REQUEST_STOP_PLAY.value()){
//				
//		}
//		if(data[0] == intHeader.GATE_CMD_SEND_REQUEST_BOXNO.value()){
//			
//		}
	}
	
	public void handleMatchMsg(DatagramPacket dp){
		byte[] backData = new byte[intHeader.GATE_MAX_STRING_NUM.value()];
		backData[0] = (byte) intHeader.GATE_CMD_WRITE.value();
		backData[1] = (byte) intHeader.GATE_CMD_RECV_MATCH.value();
		backData[2] = 0x00;
		backData[3] = 0x01;
		backData[4] = 0x00;
		backData[5] = 0x01;
		backData[6] = 0x00;
		backData[7] = 0x01;
		
		if(clientMgr.addGatewayClient(dp.getAddress().getHostAddress())){
			GLog.i(TAG, "add client ip = "+dp.getAddress().getHostAddress()+" success!");
			backData[8] = 0x01;
		}else{
			GLog.i(TAG, "add client ip = "+dp.getAddress().getHostAddress()+" fail!");
			backData[8] = 0x00;
		}
		
		DatagramSocket dst;
		try {
			dst = new DatagramSocket();
			DatagramPacket packet2 = new DatagramPacket(backData,backData.length,InetAddress.getByName(dp.getAddress().getHostAddress()),dp.getPort());
			dst.send(packet2);
			dst.close();
		} catch (SocketException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void handleQuitMsg(DatagramPacket dp){
		byte[] backData = new byte[intHeader.GATE_MAX_STRING_NUM.value()];
		backData[0] = (byte) intHeader.GATE_CMD_WRITE.value();
		backData[1] = (byte) intHeader.GATE_CMD_RECV_QUIT.value();
		backData[2] = 0x00;
		backData[3] = 0x01;
		backData[4] = 0x00;
		backData[5] = 0x01;
		backData[6] = 0x00;
		backData[7] = 0x01;
		
		if(clientMgr.delGatewayClient(dp.getAddress().getHostAddress())){
			GLog.i(TAG, "del client ip = "+dp.getAddress().getHostAddress()+" success!");
			backData[8] = 0x01;
		}else{
			GLog.i(TAG, "add client ip = "+dp.getAddress().getHostAddress()+" fail!");
			backData[8] = 0x00;
		}
		
		DatagramSocket dst;
		try {
			dst = new DatagramSocket();
			DatagramPacket packet2 = new DatagramPacket(backData,backData.length,InetAddress.getByName(dp.getAddress().getHostAddress()),dp.getPort());
			dst.send(packet2);
			dst.close();
		} catch (SocketException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}
