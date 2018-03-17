package CN5;

import java.io.IOException;
import java.net.*;

/**
 * 
 * @author ����
 *
 */
public class Receiver_rdt_2_2 {
	//protected static int packetSize=20;//ÿ�δ��͵ķ��������ݵĳ���
	protected byte[] sendDataACKNAK;
	protected byte[] receiveData;
	protected int receivedNum;//���յ��ķ������
	protected DatagramSocket receiverSocket;
	protected String data;
	protected int port;
	protected static double p=0.4;//���ȹ涨һ������ĳ��ֱ��ز��Ľ���p
	protected static double q=0.3;//���ȹ涨һ������ĳ��ֶ�������q
	
	public Receiver_rdt_2_2() throws Exception{
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
	
	
	public DatagramPacket make_pkt(DatagramPacket rcvPacket, byte ACK,byte ord) {
		
		
		byte[] buf = new byte[2];
		buf[0]=ACK;
		buf[1]=ord;
		
		/*��ȡ�ͻ���IP��ַ�Ͷ˿ں�*/
		InetAddress IPAddress=rcvPacket.getAddress();
		int port=rcvPacket.getPort();
		
      	/*����һ������sendPacket���������ݡ����ݳ��ȡ�������IP���˿ں�*/
		DatagramPacket sendPacket=new DatagramPacket(buf,2,IPAddress,port);
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
				double randomQ = Math.random();//ͨ���������ģ���Ƿ���ֶ���
				
				data=new String(extract(receivePacket));
				byte ord;//���յķ����е����
				byte ACK=1;//������ACKֵ,Ĭ��δ����Ϊ1
				ord=(byte) (data.charAt(0)-48);
				
				
				/*δ���ֱ��ش���*/
				if (randomP>p) {					
					/*�������ȷ*/
					if (randomQ>q) {
						deliver_data(data,receivedNum);
						System.out.println("δ���ֱ��ش���,�����ȷ,����ACK=1,ord="+ord+".");
						udt_send(make_pkt(receivePacket,(byte) 1,ord));
						break;//��������һ��	
					}					
				}
				
				/*ģ����س���*/
				if (randomP<=p) ACK=0;
				/*ģ����ų���*/
				if (randomQ<=q) ord=(byte) ((ord+1)%2);
				
				
				System.out.println("���յ��ķ�������,���ش�,����ACK="+ACK+",ord="+ord+".");
				udt_send(make_pkt(receivePacket,ACK,ord));
				/*����ش��������һ������*/
				receiverSocket.receive(receivePacket);	
			}		
		}
	}
	
}
