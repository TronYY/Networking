package CN8;
import java.net.*;
import java.net.MulticastSocket;

/*多播接收方，相当于服务器*/
public class MulticastReciever {
	public static void main ( String [] args ) throws Exception {
		
		InetAddress group = InetAddress.getByName("224.0.0.1");
		MulticastSocket recieverMulticastSocket = new MulticastSocket(10000); 
		recieverMulticastSocket.joinGroup(group);
		
		while(true) {
				byte[] buf = new byte[1024];
				DatagramPacket rcvPacket =new DatagramPacket(buf,buf.length);
				recieverMulticastSocket.receive(rcvPacket);
				
				/*将数据从分组中抽取出来并将数据放入到字符串中*/
				String rcvSentence=new String(rcvPacket.getData());
				
				System.out.println("接收到信息:"+rcvSentence);
			}



      }

}