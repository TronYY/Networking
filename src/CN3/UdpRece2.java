package CN3;

import java.net.*; public class UdpRece2 { 

	 public static void main(String[] args) throws Exception { 

	  final ServerFrame frame = new ServerFrame(); 

	  new Thread(new Runnable() { 

	 

	   @Override 

	   public void run() { 

	    // TODO Auto-generated method stub 

	    try { 

	     DatagramSocket ds = new DatagramSocket(10001); 

	     while (true) { 

	 

	      byte[] buf = new byte[1024]; 

	      DatagramPacket dp = new DatagramPacket(buf, 

	buf.length); 

	 

	      ds.receive(dp); 

	 

	      String ip = dp.getAddress().getHostAddress(); 

	      String data = new String(dp.getData(), 0, dp 

	        .getLength()); 

	      frame.getTextArea().append("客户端好友说"+data+"\r\n"); 

	     } 

	    } catch (Exception e) { 

	     // TODO Auto-generated catch block 

	     e.printStackTrace();  

	

	     } 

	 

	   } 

	  }).start(); 

	 

	 } 

	}
