package CN3;
import java.io.*;
import java.net.*;
/**
 * 
 * @author ����
 * TCP�������ˣ��Ƚ��������֤��ap1.0
 * ���տͻ��˷��͵���׺���ʽ������ת��Ϊ��׺���ʽ���ټ�������������������ظ��ͻ���
 */

public class TCPServerap1 {

	public static void main(String[] args) throws Exception {
		String clientSentence;
		int result;//�������ռ�����
		/*����������Ȩ���û���*/
		final String USER_NAME = "JinYang";
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
