package CN5;

import java.io.IOException;
import java.net.*;

/**
 * 
 * @author 金洋
 *
 */
public class Receiver_rdt_2_2 {
	//protected static int packetSize=20;//每次传送的分组中数据的长度
	protected byte[] sendDataACKNAK;
	protected byte[] receiveData;
	protected int receivedNum;//已收到的分组个数
	protected DatagramSocket receiverSocket;
	protected String data;
	protected int port;
	protected static double p=0.4;//事先规定一个合理的出现比特差错的界限p
	protected static double q=0.3;//事先规定一个合理的出现丢包界限q
	
	public Receiver_rdt_2_2() throws Exception{
		port=9999;
		receiverSocket=new DatagramSocket(port);
		receivedNum=0;
	}
	
	
	/*从分组中提取data*/
	public String extract(DatagramPacket pkt) {
		
		return new String(pkt.getData());
	}
	
	
	/*向本地上层发送data*/
	public void deliver_data(String data,int i){
		System.out.println("接收到第"+i+"个分组:"+data);	
	}
	
	
	public DatagramPacket make_pkt(DatagramPacket rcvPacket, byte ACK,byte ord) {
		
		
		byte[] buf = new byte[2];
		buf[0]=ACK;
		buf[1]=ord;
		
		/*抽取客户端IP地址和端口号*/
		InetAddress IPAddress=rcvPacket.getAddress();
		int port=rcvPacket.getPort();
		
      	/*构造一个分组sendPacket，包含数据、数据长度、服务器IP、端口号*/
		DatagramPacket sendPacket=new DatagramPacket(buf,2,IPAddress,port);
		return sendPacket;
		
	}
	
	/*不可靠传输*/
	public void udt_send(DatagramPacket packet) throws IOException {
		
		receiverSocket.send(packet);
	}
	
	
	public void start() throws Exception {
		while (true) {
			//byte[] sendDataACKNAK=new byte[1];//保存发送的数据，储存ACK 1或NAK 0
			byte[] receiveData=new byte[1024];//保存接收的数据
			
			DatagramPacket receivePacket=new DatagramPacket(receiveData,receiveData.length);
			receiverSocket.receive(receivePacket);
			
			++receivedNum;
			
			while (true) {
				double randomP = Math.random();//通过随机数来模拟是否出现比特错误
				double randomQ = Math.random();//通过随机数来模拟是否出现丢包
				
				data=new String(extract(receivePacket));
				byte ord;//接收的分组中的序号
				byte ACK=1;//反馈的ACK值,默认未出错为1
				ord=(byte) (data.charAt(0)-48);
				
				
				/*未出现比特错误*/
				if (randomP>p) {					
					/*且序号正确*/
					if (randomQ>q) {
						deliver_data(data,receivedNum);
						System.out.println("未出现比特错误,序号正确,返回ACK=1,ord="+ord+".");
						udt_send(make_pkt(receivePacket,(byte) 1,ord));
						break;//继续传下一个	
					}					
				}
				
				/*模拟比特出错*/
				if (randomP<=p) ACK=0;
				/*模拟序号出错*/
				if (randomQ<=q) ord=(byte) ((ord+1)%2);
				
				
				System.out.println("接收到的分组有误,需重传,返回ACK="+ACK+",ord="+ord+".");
				udt_send(make_pkt(receivePacket,ACK,ord));
				/*如果重传则接收下一个分组*/
				receiverSocket.receive(receivePacket);	
			}		
		}
	}
	
}
