package CN3;
import java.io.*;//包含了java输入和输出流的包
import java.net.*;//提供了网络支持类

/**
 * 
 * @author 金洋
 * TCP客户端，客户端由用户输入表达式，送至服务器端进行计算并返回结果
 */
public class TCPClient {

	public static void main(String[] args) throws Exception{
		String inputInfix;//接收用户输入的中缀表达式
		String result;//从服务器得到的并发送到用户标准输出的字符串
		
		/*创建了BufferedReader类型的流对象inFromUser*/
		/*输入流用System.in初始化，System.in将该流加入到标准输入上。该命令使得客户端能够从它的键盘读入文本*/
		BufferedReader inFromUser=new BufferedReader(new InputStreamReader(System.in));
		
		/*创建Socket类型的对象clientSocket，同时对客户和服务器之间的TCP连接进行了初始化*/
		/*构造方法第一个参数为服务器IP"localhost"表示服务器主机为本机，本机也可以用IP地址"127.0.0.1"表示;第二个参数表示端口号，必须与服务器上的端口号一致*/
		Socket clientSocket=new Socket("113.55.38.84",10000);
		
		/*创建了连接在套接字上的两个流对象，分别表示输出流和输入流*/
		DataOutputStream outToServer=new DataOutputStream(clientSocket.getOutputStream());
		BufferedReader inFromServer=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		
		
		System.out.println("请输入中缀表达式：");
		/*将用户输入的一行放入到字符串inputInfix中，该行从标准输入开始，经过流inFromUser，到达字符串inputInfix中*/
		inputInfix=inFromUser.readLine();
		
		/*将inputInfix加回车 传送到outToServer流中，进入到TCP管道*/
		outToServer.writeBytes(inputInfix+'\n');
		
		/*当字符从服务器到来的时候，经过流inFromServer并被放入到字符串result中。字符会一直在result中积累，直到接收到一个回车符*/
		result=inFromServer.readLine();
		
		
		System.out.println("From Server 计算结果为："+result); 
		/*关闭套接字，同时也关闭了客户和服务器端之间的TCP连接*/
		clientSocket.close();
	}
}
