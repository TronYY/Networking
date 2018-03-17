package CN5;

import java.io.*;
import java.net.*;
public class Receiver_rdt_3_0 {
    public static void main( String args[ ] )  throws Exception
    {
    	int packetsize = 20;	
    	int expectednum = 1;	// 客户端期待的packet的序号
    	int n = 10;	//	客户端将接收的packet数
    	DatagramSocket clientSocket = new DatagramSocket ( );
    	InetAddress IPAddress = InetAddress.getByName ( "localhost" );
    	byte [ ] sendData =new byte [packetsize + 3];
    	byte [ ] receiveData =new byte [packetsize + 3];
    	
    	
    	
    	String sentence = "request";
    	sendData = sentence.getBytes ( );    	
    	DatagramPacket sendPacket = new DatagramPacket ( sendData,sendData.length,IPAddress,10001);
    	clientSocket.send ( sendPacket );
    	
    	
    	System.out.println("等待接收数据：");    	
    	while (expectednum <= n)
    	{
    		DatagramPacket receivePacket = new DatagramPacket ( receiveData,receiveData.length );
        	clientSocket.receive ( receivePacket );
        	String strRec = new String ( receivePacket.getData () );
        	System.out.println ( "收到的packet: " + strRec );
        	String strNum = strRec.substring(0, strRec.indexOf('#'));
        	System.out.println ( "收到的packet序号为: " + strNum );
        	System.out.println ( "期待的序号为:" + expectednum );
        	if (strNum.equals(String.valueOf(expectednum)))
        	{
        		String packetCon = strRec.substring(strRec.indexOf('#') + 1);
        		System.out.println( "序号正确！分组内容为：" +  packetCon);
        		// 发送确认信息给服务器
        	   	sentence = String.valueOf(expectednum) + "#";
        	  // 	for (i = 0; )
            	expectednum++;
        	}
        	else
        	{
        		System.out.println("失序！丢弃该包并发送冗余Ack");
        		// 发送冗余Ack
        	   	sentence = String.valueOf(expectednum - 1) + "#";
        	}
        	System.out.println("发送ACK: " + sentence);
        	System.out.println("");
        	sendData = sentence.getBytes ( );
        	sendPacket = new DatagramPacket ( sendData,sendData.length,IPAddress,9999 );
        	clientSocket.send ( sendPacket );   	                 	
    	}
    	System.out.println("接收完毕"); 
    	clientSocket.close ( );
    }
}