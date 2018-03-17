package CN3;
import java.io.*;
import java.net.*;
/**
 * 
 * @author 金洋
 * TCP服务器端，先进行身份认证，ap1.0
 * 接收客户端发送的中缀表达式，将其转化为后缀表达式后再计算出结果，并将结果返回给客户端
 */

public class TCPServerap1 {

	public static void main(String[] args) throws Exception {
		String clientSentence;
		int result;//储存最终计算结果
		/*服务器端授权的用户名*/
		final String USER_NAME = "JinYang";
		/*创建ServerSocket类型的welcomeSocket对象，相当于一扇等待着摸个客户端来敲击的门*/
		ServerSocket welcomeSocket=new ServerSocket(9999);
		
		while (true){
			System.out.println("Listening...");
			Socket connectionSocket=welcomeSocket.accept();
			/*创建流对象*/
			BufferedReader inFromClient=new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToClient=new DataOutputStream(connectionSocket.getOutputStream());
			clientSentence=inFromClient.readLine();
	
			
			
			
			
			
			/*认证成功则继续通信，否则结束通信*/
			System.out.println("用户名为"+clientSentence);
			if (clientSentence.equals(USER_NAME)){
				System.out.println("用户名正确.");
				outToClient.writeBytes(new String("success")+'\n');
			}
				
			else {
				System.out.println("用户名错误,结束本次通信.");
				outToClient.writeBytes(new String("false")+'\n');
				continue;
			}
			
			
			
			
			clientSentence=inFromClient.readLine();
			/*开始进行运算*/
			/*将中缀表达式转换为后缀表达式*/
			InfixToPostfix ITP=new InfixToPostfix();
			ITP.toPostfix(clientSentence);
			/*计算后缀表达式*/
			CalculatePostfixExpression CPE=new CalculatePostfixExpression();
			result=CPE.calculate(ITP.getpostfixString());
			
			
			outToClient.writeBytes(String.valueOf(result)+'\n');
			
		}
	}

}
