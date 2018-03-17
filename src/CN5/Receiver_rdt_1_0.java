package CN5;

import java.io.IOException;
import java.net.*;

public class Receiver_rdt_1_0 {
	//protected static int packetSize=20;//ÿ�δ��͵ķ��������ݵĳ���
	protected byte[] sendData;
	protected byte[] receiveData;
	protected int receivedNum;//���յ��ķ������
	protected DatagramSocket receiverSocket;
	protected String data;
	protected int port;
	
	public Receiver_rdt_1_0() throws Exception{
		port=9999;
		receiverSocket=new DatagramSocket(port);
		receivedNum=0;
	}
	
	
	
	public String extract(DatagramPacket pkt) {
		
		return new String(pkt.getData());
	}
	
	
	public void deliver_data(String data,int i){
		System.out.println("���յ���"+i+"������:"+data);
		
	}
	
	
	public void start() throws Exception {
		while (true) {
			//byte[] sendData=new byte[1024];//���淢�͵�����
			byte[] receiveData=new byte[1024];//������յ�����
			
			DatagramPacket receivePacket=new DatagramPacket(receiveData,receiveData.length);
			receiverSocket.receive(receivePacket);
			data=new String(extract(receivePacket));
			deliver_data(data,++receivedNum);
			
		}
	}
	
}
