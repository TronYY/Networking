package CN3;
import java.io.*;
import java.net.*;
/**
 * 
 * @author ����
 * TCP�������ˣ����տͻ��˷��͵���׺���ʽ������ת��Ϊ��׺���ʽ���ټ�������������������ظ��ͻ���
 */

public class TCPServer {

	public static void main(String[] args) throws Exception {
		String clientSentence;
		
		int result;//�������ռ�����
		
		/*����ServerSocket���͵�welcomeSocket�����൱��һ�ȵȴ��������ͻ������û�����*/
		ServerSocket welcomeSocket=new ServerSocket(10000);
		
		/*һֱ����,ʵ���ϴ��������������Ϊʹ��ʹ��welcomeSocket��Ӧ�ó�����������������ͻ��˵��������󣻴˰汾��֧�֣��������߳������������޸��Ѵﵽ���������������������Ŀ��*/
		while (true){
			
			/*�������ͻ��û���welcomeSocket��ʱ�򣬴���һ���µ��׽���connectionSocket��Ȼ��TCP������һ���ڿͻ�clientSocket�ͷ�����connectionSocket֮���ֱ�ӵ���ܵ���ʹ�ͻ��˺ͷ���������ͨ���ιܵ����ഫ���ֽ�,�������з��͵��ֽڶ��ǰ����е�����һ��*/
			Socket connectionSocket=welcomeSocket.accept();
			
			/*����������*/
			BufferedReader inFromClient=new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToClient=new DataOutputStream(connectionSocket.getOutputStream());
			
			/*���ַ��ӿͻ��˵�����ʱ�򣬾�����inFromClient�������뵽�ַ���clientSentence�С��ַ���һֱ��result�л��ۣ�ֱ�����յ�һ���س���*/
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
