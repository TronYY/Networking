package CN3;
import java.io.*;//包含了java输入和输出流的包
import java.net.*;//提供了网络支持类

/**
 * 
 * @author 金洋
 * TCP客户端，先进行身份认证，再进行IP认证，ap2.0
 * 客户端由用户输入表达式，送至服务器端进行计算并返回结果
 */
public class TCPClientap2 {

	public static void main(String[] args) throws Exception{
		
		Socket clientSocket=new Socket("127.0.0.1",9999);
		String user_name;//用户名
		
		String inputInfix;//接收用户输入的中缀表达式
		String result;//从服务器得到的并发送到用户标准输出的字符串
		/*三个流对象*/
		DataOutputStream outToServer=new DataOutputStream(clientSocket.getOutputStream());
		BufferedReader inFromServer=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		BufferedReader inFromUser=new BufferedReader(new InputStreamReader(System.in));
		
		
		/*进行身份认证，用户名*/
		System.out.println("请输入用户名:");
		user_name=inFromUser.readLine();
		outToServer.writeBytes(user_name+'\n');
		result=inFromServer.readLine();
		/*用户名认证失败，结束程序*/
		if (result.equals("false")){
			System.out.println("用户名认证失败,结束通信.");
			clientSocket.close();
			System.exit(0);
		}
		
		
		
		/*进行IP认证*/
		System.out.println("用户名认证成功，正在进行IP地址进行验证");
		result=inFromServer.readLine();
		if (result.equals("false")){
			System.out.println("IP认证失败,结束通信.");
			clientSocket.close();
			System.exit(0);
		}
		
		
		
		
		/*认证成功，进入下一步表达式运算*/
		System.out.println("认证成功.");
		System.out.println("请输入中缀表达式：");
		inputInfix=inFromUser.readLine();
		
		
		outToServer.writeBytes(inputInfix+'\n');
		
		result=inFromServer.readLine();
		System.out.println("From Server 计算结果为："+result); 
		/*关闭套接字，同时也关闭了客户和服务器端之间的TCP连接*/
		clientSocket.close();
	}
}
