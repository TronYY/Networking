package CN3;
import java.io.*;//������java�����������İ�
import java.net.*;//�ṩ������֧����

/**
 * 
 * @author ����
 * TCP�ͻ��ˣ��Ƚ��������֤��ap1.0
 * �ͻ������û�������ʽ�������������˽��м��㲢���ؽ��
 */
public class TCPClientap1 {

	public static void main(String[] args) throws Exception{
		
		Socket clientSocket=new Socket("127.0.0.1",9999);
		String user_name;//�û���
		
		String inputInfix;//�����û��������׺���ʽ
		String result;//�ӷ������õ��Ĳ����͵��û���׼������ַ���
		/*����������*/
		DataOutputStream outToServer=new DataOutputStream(clientSocket.getOutputStream());
		BufferedReader inFromServer=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		BufferedReader inFromUser=new BufferedReader(new InputStreamReader(System.in));
		
		
		/*���������֤���û���*/
		System.out.println("�������û���:");
		user_name=inFromUser.readLine();
		outToServer.writeBytes(user_name+'\n');
		
		result=inFromServer.readLine();
		
		/*��֤ʧ�ܣ���������*/
		if (result.equals("false")){
			System.out.println("��֤ʧ��,����ͨ��.");
			clientSocket.close();
			System.exit(0);
		}
		
		
		
		/*��֤�ɹ���������һ�����ʽ����*/
		System.out.println("��֤�ɹ�.");
		System.out.println("��������׺���ʽ��");
		inputInfix=inFromUser.readLine();
		
		
		outToServer.writeBytes(inputInfix+'\n');
		
		result=inFromServer.readLine();
		System.out.println("From Server ������Ϊ��"+result); 
		/*�ر��׽��֣�ͬʱҲ�ر��˿ͻ��ͷ�������֮���TCP����*/
		clientSocket.close();
	}
}
