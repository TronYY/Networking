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
					
					/*ÿ�����󽻸�һ���߳�ȥ����*/
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

	
	
	
	
	/*�߳�ʵ��*/
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
				System.out.println("�ӿͻ���������Ϣ��" + clientSentence);

				
				/*��ʼ��������*/
				/*����׺���ʽת��Ϊ��׺���ʽ*/
				InfixToPostfix ITP=new InfixToPostfix();
				ITP.toPostfix(clientSentence);
				/*�����׺���ʽ*/
				CalculatePostfixExpression CPE=new CalculatePostfixExpression();
				int result=CPE.calculate(ITP.getpostfixString());
				
				
				outToClient.writeBytes(String.valueOf(result)+'\n');
				outToClient.flush();
				System.out.println("Answer="+result+"�Ѿ����ظ��ͻ��ˣ�");
            }
            catch (IOException e)
            {
	            e.printStackTrace();
            }
			
		}
		
	}

}
