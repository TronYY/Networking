package CN6;

import java.io.*; 
import javax.net.ssl.SSLServerSocket; 
import javax.net.ssl.SSLServerSocketFactory; 
import javax.net.ssl.SSLSocket; 

public class SSLServer { 

	// ����������Ȩ���û���������
	private static final String USER_NAME = "JinYang"; 
	private static final String PASSWORD = "999999999"; 
	// �������˱�������
	private static final String SECRET_CONTENT = "����һ�����Է������Ļ�������!"; 

	private SSLServerSocket serverSocket = null; 

	public SSLServer() throws Exception { 
		// ͨ���׽��ֹ�������ȡһ�����������׽���
		SSLServerSocketFactory socketFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault(); 
		serverSocket = (SSLServerSocket)socketFactory.createServerSocket(7070); 
	} 

	private void runServer() { 
		while (true) { 
			try { 
				System.out.println("�ȴ�����..."); 
				// ���������׽��ֽ�������״̬���ȴ����Կͻ��˵���������
				SSLSocket socket = (SSLSocket) serverSocket.accept(); 
			
				// ��ȡ���������׽���������
				BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
				// ���������ж�ȡ�ͻ����û���������
				String userName = input.readLine(); 
				String password = input.readLine(); 
			
				// ��ȡ���������׽��������
				PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream())); 

				// �����������֤�����ͨ���򽫱������ݷ��͸��ͻ���
				if (userName.equals(USER_NAME) && password.equals(PASSWORD)) { 
					output.println("��ӭ, " + userName); 
					output.println(SECRET_CONTENT); 
				} 
				else { 
					output.println("�����֤ʧ��."); 
				} 
		
		 
				
				// �ر�����Դ���׽�����Դ
				output.close(); 
				input.close(); 
				socket.close(); 

			} catch (IOException ioException) { 
				ioException.printStackTrace(); 
			} 
		} 
	} 

	public static void main(String args[]) throws Exception { 
		SSLServer server = new SSLServer(); 
		server.runServer(); 
	} 
} 