package CN4;

import java.io.*;
import java.net.*;

public class UDPClient {

	public static void main(String[] args) throws Exception {
		String inputInfix;//接收用户输入的中缀表达式
		String result;//从服务器得到的并发送到用户标准输出的字符串
		
		
		BufferedReader inFromUser=new BufferedReader(new InputStreamReader(System.in));
		
		/*此行没有引入TCP连接，客户主机在执行这一行时没有与服务器主机进行联系，故没有任何主机的参数。相当于只为客户进程创建了一个门，但是没有在两个进程之间创建管道*/
		DatagramSocket clientSocket=new DatagramSocket();
		
		/*为了将字节发送到目的进程，需要得到进程的地址。此行调用了一个将主机名解析为ip地址的DNS查找*/
		InetAddress IPAddress=InetAddress.getByName("localhost");
		
		byte[] sendData=new byte[1024];//保存客户端发送的数据
		byte[] receiveData=new byte[1024];//保存客户端接收的数据
		
		
		System.out.println("请输入中缀表达式：");
		/*将用户输入的一行放入到字符串inputInfix中，该行从标准输入开始，经过流inFromUser，到达字符串inputInfix中*/
		inputInfix=inFromUser.readLine();
		/*执行一个类型转换。得到一个字符串并重定义为一个字符数组*/
		sendData=inputInfix.getBytes();
		
		/*构造一个分组sendPacket，包含数据、数据长度、服务器IP、端口号*/
		DatagramPacket sendPacket=new DatagramPacket(sendData,sendData.length,IPAddress,9999);
		/*将刚刚构造好的分组通过clientSocket送入网络*/
		clientSocket.send(sendPacket);
		
		/*接收到来的分组*/
		DatagramPacket receivePacket=new DatagramPacket(receiveData,receiveData.length);
		clientSocket.receive(receivePacket);
		
		/*将数据从receivePacket中抽取出来并且执行类型转换，将字符数组转换成字符串*/
		result=new String(receivePacket.getData());
		System.out.println("From Server 计算结果为："+result); 
		
		clientSocket.close();
		

	}

}
