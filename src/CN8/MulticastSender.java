package CN8;
import java.net.*;
import java.net.MulticastSocket;
import java.io.*;

/*多播发送方，相当于客户端*/
public class MulticastSender{
      public static void main ( String [] args ) throws Exception {
    	  BufferedReader inFromUser=new BufferedReader(new InputStreamReader(System.in));
    	  /*建立MulticastSocket*/
    	  MulticastSocket sendMulticastSocket = new MulticastSocket();
    	  
    	  
    	  /*指定D 类 IP 地址和标准 UDP 端口号*/
    	  InetAddress group=InetAddress.getByName("224.0.0.1");
    	  int port = 10000;
    	  /*加入群组*/
    	  sendMulticastSocket.joinGroup(group);
    	  
    	  System.out.println("请输入要发送的信息：");
    	  String msg=inFromUser.readLine();
    	  
    	  DatagramPacket sendPacket = new DatagramPacket(msg.getBytes(),msg.length(),group,port);
    	  sendMulticastSocket.send(sendPacket);
    	  
           
          
	}

}
