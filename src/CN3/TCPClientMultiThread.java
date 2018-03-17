package CN3;

import java.io.*;
import java.net.*;

public class TCPClientMultiThread extends Thread
{

	Socket clientSocket = null;
	BufferedReader inFromServer = null;
	DataOutputStream outToServer = null;
	BufferedReader inFromUser = null;

	public TCPClientMultiThread()
	{
		inFromUser = new BufferedReader(new InputStreamReader(System.in));
		try
		{
			clientSocket = new Socket("127.0.0.1", 10000);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	
	/*Overriding run*/
	public void run()
	{
		try
		{
			/*输入输出流*/
			inFromServer=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			outToServer=new DataOutputStream(clientSocket.getOutputStream());
			String inputInfix = inFromUser.readLine();

			while (true)
			{
				if (inputInfix != null)
				{
					outToServer.writeBytes(inputInfix+'\n');
			
				}
				if (inFromServer != null)
				{
					String result = inFromServer.readLine();
					System.out.println("From Server 计算结果为：" + result);
					
				}
				
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String [] args)
	{
		new TCPClientMultiThread().start();
	}
}
