package CN6; 

import java.io.*; 
import javax.net.ssl.SSLSocket; 
import javax.net.ssl.SSLSocketFactory; 

public class SSLClient { 

	private SSLSocket socket = null; 

	public SSLClient() throws IOException { 
		// ͨ���׽��ֹ�������ȡһ���ͻ����׽���
		SSLSocketFactory socketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault(); 
		socket = (SSLSocket) socketFactory.createSocket("localhost", 7070); 
	} 

	public void connect() { 
		try { 
			
			
			// ��ȡ�ͻ����׽��������
			PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream())); 
			// ���û���������ͨ����������͵���������
			String userName = "JinYang"; 
			output.println(userName); 
			String password = "999999999"; 
			output.println(password); 
			output.flush(); 
		
			// ��ȡ�ͻ����׽���������
			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
			// ���������ж�ȡ�������˴��͵��������ݣ�����ӡ����
			String response = input.readLine(); 
			response += "\n " + input.readLine(); 
			System.out.println(response); 
			// �ر�����Դ���׽�����Դ
			output.close(); 
			input.close(); 
			socket.close(); 
		} catch (IOException ioException) { 
			ioException.printStackTrace(); 
		} finally { 
			System.exit(0); 
	} 
} 

	public static void main(String args[]) throws IOException { 
		new SSLClient().connect(); 
	} 
} 