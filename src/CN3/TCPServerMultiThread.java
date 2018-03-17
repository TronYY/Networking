package CN3;

import java.io.*;
import java.net.*;

public class TCPServerMultiThread extends Thread
{

	ServerSocket welcomeSocket = null;
	Socket connectionSocket = null;
	BufferedReader inFromClient = null;
	DataOutputStream outToClient = null;

	public TCPServerMultiThread()
	{
		try
		{
			welcomeSocket = new ServerSocket(10000);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/*Overriding run*/
	public void run()
	{

		while (true)
			{
				System.out.println("Listenning...");
				try
                {
					
					/*每个请求交给一个线程去处理*/
	                connectionSocket = welcomeSocket.accept();
	                ServerThread th = new ServerThread(connectionSocket);
	                th.start();
					sleep(1000);
                }
                catch (Exception e)
                {
	                e.printStackTrace();
                }
				
			}
	}

	public static void main(String [] args)
	{
		new TCPServerMultiThread().start();
	}

	
	
	
	
	/*线程实体*/
	class ServerThread extends Thread
	{

		Socket connectionSocket = null;
		public ServerThread(Socket connectionSocket)
		{
			this.connectionSocket = connectionSocket;
		}
		public void run()
		{
			try
            {
	            outToClient = new DataOutputStream(connectionSocket.getOutputStream());
	            inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
				String clientSentence = inFromClient.readLine();
				System.out.println("从客户端来的信息：" + clientSentence);

				
				/*开始进行运算*/
				/*将中缀表达式转换为后缀表达式*/
				InfixToPostfix ITP=new InfixToPostfix();
				ITP.toPostfix(clientSentence);
				/*计算后缀表达式*/
				CalculatePostfixExpression CPE=new CalculatePostfixExpression();
				int result=CPE.calculate(ITP.getpostfixString());
				
				
				outToClient.writeBytes(String.valueOf(result)+'\n');
				outToClient.flush();
				System.out.println("Answer="+result+"已经返回给客户端！");
            }
            catch (IOException e)
            {
	            e.printStackTrace();
            }
			
		}
		
	}

}
