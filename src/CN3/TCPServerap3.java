package CN3;
import java.io.*;
import java.net.*;
/**
 * 
 * @author ����
 * TCP���������Ƚ��������֤���ٽ���IP��֤���ٽ��п�����֤    ap3.0
 * ���տͻ��˷��͵���׺���ʽ������ת��Ϊ��׺���ʽ���ټ�������������������ظ��ͻ���
 */

public class TCPServerap3 {

	public static void main(String[] args) throws Exception {
		String clientSentence;
		int result;//�������ռ�����
		/*����������Ȩ���û���*/
		final String USER_NAME = "JinYang";
		/*����������Ȩ��IP,�༴�ͻ�����֪��IP��ַ*/
		final String IP = "127.0.0.1";
		/*����������Ȩ�Ŀ���*/
		final String PASSWORD = "20160109";
		
		/*����ServerSocket���͵�welcomeSocket�����൱��һ�ȵȴ��������ͻ������û�����*/
		ServerSocket welcomeSocket=new ServerSocket(9999);
		
		while (true){
			System.out.println("Listening...");
			Socket connectionSocket=welcomeSocket.accept();
			/*����������*/
			BufferedReader inFromClient=new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToClient=new DataOutputStream(connectionSocket.getOutputStream());
			clientSentence=inFromClient.readLine();
	
			/*��֤�ɹ������ͨ�ţ��������ͨ��*/
			System.out.println("�û���Ϊ"+clientSentence);
			if (clientSentence.equals(USER_NAME)){
				System.out.println("�û�����ȷ.");
				outToClient.writeBytes(new String("success")+'\n');
			}	
			else {
				System.out.println("�û�������,��������ͨ��.");
				outToClient.writeBytes(new String("false")+'\n');
				continue;
			}
			
			
			/*����IP��֤����connectionSocket����ȡ�ͻ���IP����ͻ�������֪IP���бȶ�*/
			InetAddress addr = connectionSocket.getInetAddress();
			String ip= addr.getHostAddress().toString();
			System.out.println("�ͻ���IP:"+ip);
			if (ip.equals(IP)){
				System.out.println("IP��ȷ.");
				outToClient.writeBytes(new String("success")+'\n');
			}	
			else {
				System.out.println("IP����,��������ͨ��.");
				outToClient.writeBytes(new String("false")+'\n');
				continue;
			}
			
		
			/*���п�����֤*/
			clientSentence=inFromClient.readLine();
			System.out.println("�ͻ�������Ŀ���:"+clientSentence);
			if (clientSentence.equals(PASSWORD)){
				System.out.println("������ȷ.");
				outToClient.writeBytes(new String("success")+'\n');
			}	
			else {
				System.out.println("�������,��������ͨ��.");
				outToClient.writeBytes(new String("false")+'\n');
				continue;
			}
			
			
			
			clientSentence=inFromClient.readLine();
			/*��ʼ��������*/
			/*����׺���ʽת��Ϊ��׺���ʽ*/
			InfixToPostfix ITP=new InfixToPostfix();
			ITP.toPostfix(clientSentence);
			/*�����׺���ʽ*/
			CalculatePostfixExpression CPE=new CalculatePostfixExpression();
			result=CPE.calculate(ITP.getpostfixString());
			
			
			outToClient.writeBytes(String.valueOf(result)+'\n');
			
		}
	}

}
