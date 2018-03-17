package CN5;

import java.io.*;
import java.net.*;

import CN3.CalculatePostfixExpression;
import CN3.InfixToPostfix;

public class Sender {

	public static void main(String[] args) throws Exception {
		
		String clientSentence;//接收到的字符串
		int result;//服务器运算完储存最终计算结果
		
		/*构造一个serverSocket，所有的数据都将通过这个套接字进行传送*/
		
		/*由于UDP无连接，不需要生成一个新的套接字来监听新的请求。若有多个客户访问此应用程序，他们将所有的分组都发送到这个serverSocket中*/
		DatagramSocket serverSocket=new DatagramSocket(9999);
		
		
		
		
		while (true) {
			/*每一次都必须重新初始化，否则会保留上一次的值*/
			byte[] sendData=new byte[1024];//保存发送的数据
			byte[] receiveData=new byte[1024];//保存接收的数据
			
			DatagramPacket receivePacket=new DatagramPacket(receiveData,receiveData.length);
			serverSocket.receive(receivePacket);
			
			
			
			/*将数据从分组中抽取出来并将数据放入到字符串中*/
			clientSentence=new String(receivePacket.getData());
			
			/*抽取客户端IP地址和端口号*/
			InetAddress IPAddress=receivePacket.getAddress();
			int port=receivePacket.getPort();
			System.out.println("接收的文本:" +clientSentence);  
	        System.out.println("接收的ip地址:" + IPAddress);  
	        System.out.println("接收的端口::" + port);
			
	        
	        /*开始进行运算*/
			/*将中缀表达式转换为后缀表达式*/
			InfixToPostfix ITP=new InfixToPostfix();
			ITP.toPostfix(clientSentence);
			/*计算后缀表达式*/
			CalculatePostfixExpression CPE=new CalculatePostfixExpression();
			result=CPE.calculate(ITP.getpostfixString());
			
			/*将int型的result转换为String后再转为字符数组*/
			sendData=String.valueOf(result).getBytes();
			DatagramPacket sendPacket=new DatagramPacket(sendData,sendData.length,IPAddress,port);
			
			serverSocket.send(sendPacket);
			
		}
		
		
	}		
		
		
}