package CN8;
import java.net.*;
import java.net.MulticastSocket;

/*�ಥ���շ����൱�ڷ�����*/
public class MulticastReciever {
	public static void main ( String [] args ) throws Exception {
		
		InetAddress group = InetAddress.getByName("224.0.0.1");
		MulticastSocket recieverMulticastSocket = new MulticastSocket(10000); 
		recieverMulticastSocket.joinGroup(group);
		
		while(true) {
				byte[] buf = new byte[1024];
				DatagramPacket rcvPacket =new DatagramPacket(buf,buf.length);
				recieverMulticastSocket.receive(rcvPacket);
				
				/*�����ݴӷ����г�ȡ�����������ݷ��뵽�ַ�����*/
				String rcvSentence=new String(rcvPacket.getData());
				
				System.out.println("���յ���Ϣ:"+rcvSentence);
			}



      }

}