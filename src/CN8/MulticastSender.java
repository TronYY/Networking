package CN8;
import java.net.*;
import java.net.MulticastSocket;
import java.io.*;

/*�ಥ���ͷ����൱�ڿͻ���*/
public class MulticastSender{
      public static void main ( String [] args ) throws Exception {
    	  BufferedReader inFromUser=new BufferedReader(new InputStreamReader(System.in));
    	  /*����MulticastSocket*/
    	  MulticastSocket sendMulticastSocket = new MulticastSocket();
    	  
    	  
    	  /*ָ��D �� IP ��ַ�ͱ�׼ UDP �˿ں�*/
    	  InetAddress group=InetAddress.getByName("224.0.0.1");
    	  int port = 10000;
    	  /*����Ⱥ��*/
    	  sendMulticastSocket.joinGroup(group);
    	  
    	  System.out.println("������Ҫ���͵���Ϣ��");
    	  String msg=inFromUser.readLine();
    	  
    	  DatagramPacket sendPacket = new DatagramPacket(msg.getBytes(),msg.length(),group,port);
    	  sendMulticastSocket.send(sendPacket);
    	  
           
          
	}

}
