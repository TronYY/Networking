package CN5;

import java.io.*;

public class TestSender_rdt_2_2 {
	
	public static void main(String args [ ] ) throws Exception {
		System.out.println("�ȴ������ϲ�ĵ���...");
		System.out.println("����send���ɿ�ʼ����...");
		
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
    	String sentence = inFromUser.readLine();		// �Ӽ��̵ȴ�"send"����	
    	while (!sentence.equals("send")) sentence = inFromUser.readLine();	        	    	
    	
    	
    	
    	Sender_rdt_2_2 Sender2_2=new Sender_rdt_2_2();  
    	/*�����ı�*/
    	Sender2_2.readText();
    	Sender2_2.start();
		
	}
	
}
