package CN5;

import java.io.*;

public class TestSender_rdt_1_0 {
	
	public static void main(String args [ ] ) throws Exception {
		System.out.println("�ȴ������ϲ�ĵ���...");
		System.out.println("����send���ɿ�ʼ����...");
		
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
    	String sentence = inFromUser.readLine();		// �Ӽ��̵ȴ�"send"����	
    	while (!sentence.equals("send")) sentence = inFromUser.readLine();	        	    	
    	
    	
    	
    	Sender_rdt_1_0 Sender1_0=new Sender_rdt_1_0();  
    	/*�����ı�*/
    	Sender1_0.readText();
    	Sender1_0.start();
		
	}
	
}
