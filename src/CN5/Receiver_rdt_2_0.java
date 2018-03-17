package CN5;

import java.io.IOException;
import java.net.*;

/**
 * 
 * @author ����
 *
 */
public class Receiver_rdt_2_0 {
	//protected static int packetSize=20;//ÿ�δ��͵ķ��������ݵĳ���
	protected byte[] sendDataACKNAK;
	protected byte[] receiveData;
	protected int receivedNum;//���յ��ķ������
	protected DatagramSocket receiverSocket;
	protected String data;
	protected int port;
	protected static double p=0.6;//���ȹ涨һ������ĳ��ֱ��ز��ĸ���p
	
	public Receiver_rdt_2_0() throws Exception{
		port=9999;
		receiverSocket=new DatagramSocket(port);
		receivedNum=0;
	}
	
	
	/*�ӷ�������ȡdata*/
	public String extract(DatagramPacket pkt) {
		
		return new String(pkt.getData());
	}
	
	
	/*�򱾵��ϲ㷢��data*/
	public void deliver_data(String data,int i){
		System.out.println("���յ���"+i+"������:"+data);	
	}
	
	
	public DatagramPacket make_pkt(DatagramPacket rcvPacket, byte AN) {
		
		
		byte[] buf = new byte[1];
		buf[0]=AN;
		
		/*��ȡ�ͻ���IP��ַ�Ͷ˿ں�*/
		InetAddress IPAddress=rcvPacket.getAddress();
		int port=rcvPacket.getPort();
		
      	/*����һ������sendPacket���������ݡ����ݳ��ȡ�������IP���˿ں�*/
		DatagramPacket sendPacket=new DatagramPacket(buf,1,IPAddress,port);
		return sendPacket;
		
	}
	
	/*���ɿ�����*/
	public void udt_send(DatagramPacket packet) throws IOException {
		
		receiverSocket.send(packet);
	}
	
	
	public void start() throws Exception {
		while (true) {
			//byte[] sendDataACKNAK=new byte[1];//���淢�͵����ݣ�����ACK 1��NAK 0
			byte[] receiveData=new byte[1024];//������յ�����
			
			DatagramPacket receivePacket=new DatagramPacket(receiveData,receiveData.length);
			receiverSocket.receive(receivePacket);
			
			++receivedNum;
			
			
			
			while (true) {
				double randomP = Math.random();//ͨ���������ģ���Ƿ���ֱ��ش���
				/*δ���ֱ��ش���*/
				if (randomP>p) {
					data=new String(extract(receivePacket));
					deliver_data(data,receivedNum);
					System.out.println("δ���ֱ��ش��󣬷���ACK.");
					udt_send(make_pkt(receivePacket,(byte) 1));
					break;
				}
				
				/*���ֱ��ش���*/
				else {
					System.out.println("���յ���"+receivedNum+"������:");
					System.out.println("���ֱ��ش���,����NAK.");
					udt_send(make_pkt(receivePacket,(byte) 0));
				}
				
				/*����ش��������һ������*/
				receiverSocket.receive(receivePacket);
				
				
			}
			
			
			
			
			
			
			
		}
	}
	
}
