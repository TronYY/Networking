package CN3;
import java.io.*;//������java�����������İ�
import java.net.*;//�ṩ������֧����

/**
 * 
 * @author ����
 * TCP�ͻ��ˣ��ͻ������û�������ʽ�������������˽��м��㲢���ؽ��
 */
public class TCPClient {

	public static void main(String[] args) throws Exception{
		String inputInfix;//�����û��������׺���ʽ
		String result;//�ӷ������õ��Ĳ����͵��û���׼������ַ���
		
		/*������BufferedReader���͵�������inFromUser*/
		/*��������System.in��ʼ����System.in���������뵽��׼�����ϡ�������ʹ�ÿͻ����ܹ������ļ��̶����ı�*/
		BufferedReader inFromUser=new BufferedReader(new InputStreamReader(System.in));
		
		/*����Socket���͵Ķ���clientSocket��ͬʱ�Կͻ��ͷ�����֮���TCP���ӽ����˳�ʼ��*/
		/*���췽����һ������Ϊ������IP"localhost"��ʾ����������Ϊ����������Ҳ������IP��ַ"127.0.0.1"��ʾ;�ڶ���������ʾ�˿ںţ�������������ϵĶ˿ں�һ��*/
		Socket clientSocket=new Socket("113.55.38.84",10000);
		
		/*�������������׽����ϵ����������󣬷ֱ��ʾ�������������*/
		DataOutputStream outToServer=new DataOutputStream(clientSocket.getOutputStream());
		BufferedReader inFromServer=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		
		
		System.out.println("��������׺���ʽ��");
		/*���û������һ�з��뵽�ַ���inputInfix�У����дӱ�׼���뿪ʼ��������inFromUser�������ַ���inputInfix��*/
		inputInfix=inFromUser.readLine();
		
		/*��inputInfix�ӻس� ���͵�outToServer���У����뵽TCP�ܵ�*/
		outToServer.writeBytes(inputInfix+'\n');
		
		/*���ַ��ӷ�����������ʱ�򣬾�����inFromServer�������뵽�ַ���result�С��ַ���һֱ��result�л��ۣ�ֱ�����յ�һ���س���*/
		result=inFromServer.readLine();
		
		
		System.out.println("From Server ������Ϊ��"+result); 
		/*�ر��׽��֣�ͬʱҲ�ر��˿ͻ��ͷ�������֮���TCP����*/
		clientSocket.close();
	}
}
