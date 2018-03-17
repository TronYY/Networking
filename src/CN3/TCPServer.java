package CN3;
import java.io.*;
import java.net.*;
/**
 * 
 * @author 金洋
 * TCP服务器端，接收客户端发送的中缀表达式，将其转化为后缀表达式后再计算出结果，并将结果返回给客户端
 */

public class TCPServer {

	public static void main(String[] args) throws Exception {
		String clientSentence;
		
		int result;//储存最终计算结果
		
		/*创建ServerSocket类型的welcomeSocket对象，相当于一扇等待着摸个客户端来敲击的门*/
		ServerSocket welcomeSocket=new ServerSocket(10000);
		
		/*一直运行,实际上大多数服务器可以为使用使用welcomeSocket的应用程序继续监听从其他客户端到来的请求；此版本不支持，可以用线程来对它进行修改已达到继续监听更多连接请求的目的*/
		while (true){
			
			/*当摸个客户敲击了welcomeSocket的时候，创建一个新的套接字connectionSocket；然后TCP建立了一个在客户clientSocket和服务器connectionSocket之间的直接的虚管道，使客户端和服务器可以通过次管道互相传送字节,并且所有发送的字节都是按序列到达另一方*/
			Socket connectionSocket=welcomeSocket.accept();
			
			/*创建流对象*/
			BufferedReader inFromClient=new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToClient=new DataOutputStream(connectionSocket.getOutputStream());
			
			/*当字符从客户端到来的时候，经过流inFromClient并被放入到字符串clientSentence中。字符会一直在result中积累，直到接收到一个回车符*/
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
