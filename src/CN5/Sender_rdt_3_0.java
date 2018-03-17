package CN5;
import java.io.*;
import java.net.*;
import java.util.Timer;

/*UDP连接的发送端实现可靠数据传输
 * 使用GBN算法，实现了：
 * 1.随即丢包(随机数产生器Math.random())
 * 2.丢包后遇冗余ack重传
 * 3.超时重传 (DatagramSocket和Socket都有成员函数setSoTimeout(delay),通过捕获超时时抛出的IO异常来实现超时重传)
 */

public class Sender_rdt_3_0 {
	static int packetsize = 20;
	static int n = 10;	// 服务器将向客户端发送的packet数
	static byte [ ] receiveData = new byte [packetsize + 3];
	static byte [ ] sendData = new byte [packetsize + 3];    	
	static int listeningPort = 10001;
	static double p = 0.5; // probability of dropping packets
	static int delay = 1000;	// 延时   
	
    public static void main(String args [ ] )  throws Exception    {    	
    	DatagramSocket serverSocket = new DatagramSocket (listeningPort); 	
    	int base = 1;
    	int nextseqnum = 1;
    	System.out.println("Listening on port" + listeningPort);    	
    	  	   	
    	FileReader f = new FileReader("E:/Computer/Java/Project/Networking/src/CN5/HappyNewYear.txt");
    	BufferedReader buffer = new BufferedReader(f);
    	String tempStr = buffer.readLine();
    	int size = tempStr.length();
    	byte bufferArray[] = new byte[size];
    	bufferArray = tempStr.getBytes();    	 
    	
    	  	
      	System.out.println("已完成向缓存中读入文本,文本大小:" + size);
      	System.out.println("文本内容:" + tempStr);
      	
     	DatagramPacket receivePacket = new DatagramPacket ( receiveData , receiveData.length ); 	
      	serverSocket.receive (receivePacket);
      	String sentence = new String ( receivePacket.getData ( ) );
      	System.out.println("已收到发送请求");
      	
      	InetAddress IPAddress = receivePacket.getAddress ( );
      	int port = receivePacket.getPort(); 
      	
  
 
    	serverSocket.setSoTimeout(delay);
      	int i;      	
      	System.out.println("开始传输:");    		
      	byte buf1[] = new byte [packetsize];
      	byte buf2[] = new byte [packetsize];
      	// 初始化buf1和buf2，即读入前两个包直接发送
      	for ( i = (nextseqnum - 1) * packetsize ; i < nextseqnum * packetsize; i++) {
      			buf1[i] = bufferArray[i];
      	}
      	nextseqnum++;
      	String str = "1#" + new String (buf1);
      	System.out.println("发送分组:" + str);
      	sendData = str.getBytes( );
      	DatagramPacket sendPacket = new DatagramPacket ( sendData, sendData.length ,IPAddress, port);
      	sendPacket(serverSocket, sendPacket );

      	int j;
      	for (j=0, i = (nextseqnum - 1) * packetsize; i < nextseqnum * packetsize; i++) {
      		buf2[j++] = bufferArray[i];
      	}
      	nextseqnum++;
      	str = "2#" + new String (buf2);;
      	System.out.println(""); 
      	System.out.println("发送分组:" + str);
      	sendData = str.getBytes( );
      	sendPacket = new DatagramPacket ( sendData, sendData.length ,IPAddress, port);     		
      	sendPacket(serverSocket, sendPacket );
 		
      	
      	String strACKNum;
      	while ( true ) 	{ 
        	try {        	  
	      		receivePacket = new DatagramPacket ( receiveData, receiveData.length );  
	      		serverSocket.receive (receivePacket );
	      		
	      		strACKNum = new String ( receivePacket.getData ( ) );
	      		System.out.println("");
	      		System.out.println("接收ACK:" + strACKNum.substring(0, strACKNum.indexOf('#') + 1));
	      		if (strACKNum.substring(0, strACKNum.indexOf('#')).equals(String.valueOf(base))) {// 接收到正确的Ack
	      			base++; 
	      			// 已经发出n个包并且都收到确认Ack了	
	      			if (base == n + 1) {
	      				break;
	      			}
	      			
	      			// 收到Ack，继续发送后面的包，但是当base==n-1时，没有文件可发送了，只要等待最后窗口长度个Ack就行了
      				else if (base <= n - 1) {
	      				buf1 = buf2;
	          			for (j = 0, i = (nextseqnum - 1) * packetsize ; i < nextseqnum * packetsize; i++) {
	          				buf2[j++] = bufferArray[i];
	          			}      		
	          	      	str = String.valueOf(nextseqnum) + "#" + new String (buf2); 
	          	      	System.out.println("");
	          	      	System.out.println("发送分组:" + str);
	          	      	sendData = str.getBytes( );
	          			sendPacket = new DatagramPacket ( sendData, sendData.length ,IPAddress, port);
	          			sendPacket(serverSocket, sendPacket );
	          			nextseqnum++;      				
	      			}      			     			
	      		}    
	      		// 冗余Ack, 重传
	      		else {
	      			System.out.println("");
	      			System.out.println("收到冗余ACK.");
	      			Resend(serverSocket, base, buf1, buf2, IPAddress, port);	      			
	      		}      			     
         	}   
         	catch (IOException e) {// 捕获超时抛出的异常，重传
         		System.out.println("");
         		System.out.println("超时，重传.");
         		Resend(serverSocket, base, buf1, buf2, IPAddress, port);	  
         	} 
      	}	  
      	serverSocket.close();
      	System.out.println("传输完毕");     	
    }
    
    
    
    
    
	public static void sendPacket(DatagramSocket serverSocket, DatagramPacket packet)
	{
		try 
		{// randomly dropping packet
			double i = Math.random();
			if (i > p)
			{
				serverSocket.send ( packet );			
			}			
			else
			{
				System.out.println("丢包!");
				//System.out.println( "packet  content:" + new String(packet.getData()) );
			}
		}
		catch (Exception e)
		{
			System.out.println("发送分组异常!");						
		}
	}
    
    public static void Resend(DatagramSocket serverSocket, int base, byte[] buf1, byte[]buf2, 
    									InetAddress IPAddress, int port)
    {
    	if (base < n)
    	{
	    	String str = String.valueOf(base) + "#" + new String(buf1);
	    	System.out.println("重传分组:" + str);
	    	sendData = str.getBytes( );
	    	DatagramPacket sendPack = new DatagramPacket ( sendData, sendData.length ,IPAddress, port);
	    	sendPacket(serverSocket, sendPack );
	    	str = String.valueOf(base + 1) + "#" + new String(buf2);
	    	System.out.println("重传分组:" + str);
	    	sendData = str.getBytes( );
	    	sendPack = new DatagramPacket ( sendData, sendData.length, IPAddress, port);
	    	sendPacket(serverSocket, sendPack );
	    }
    	else
    	{	// 当只有最后一个包未确认时，重传也只要传最后一个包了
	    	String str = String.valueOf(base) + "#" + new String(buf2);
	    	System.out.println("重传分组:" + str);
	    	sendData = str.getBytes( );
	    	DatagramPacket sendPack = new DatagramPacket ( sendData, sendData.length, IPAddress, port);
	    	sendPacket(serverSocket, sendPack );    		
    	}
    }
}



