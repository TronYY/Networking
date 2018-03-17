package CN3;

import java.net.*; 

import java.net.SocketException;public class UdpSend2 { 

 public static void main(String[] args) throws Exception { 

 

  final ClientFrame frame = new ClientFrame(); 
  new Thread(new Runnable() { 

 

   @Override 

   public void run() { 

    // TODO Auto-generated method stub 

 

    try { 

     DatagramSocket ds1 = new DatagramSocket(10002); 

     while (true) { 

 

      byte[] buf1 = new byte[1024]; 

      DatagramPacket dp1 = new DatagramPacket(buf1, 

        buf1.length); 

 

      ds1.receive(dp1); 

 

      String ip = dp1.getAddress().getHostAddress(); 

      String data = new String(dp1.getData(), 0, 

        dp1.getLength()); 

      frame.getTextArea().append("服 务 端 好 友 说"+data+"\r\n"); 

     } 

    } catch (Exception e) { 

     // TODO Auto-generated catch block 

     e.printStackTrace(); 

    } 

 

   } 

  }).start(); 

  



  } 

}