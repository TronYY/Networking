package CN5;

import java.io.*;

public class TestSender_rdt_2_2 {
	
	public static void main(String args [ ] ) throws Exception {
		System.out.println("等待来自上层的调用...");
		System.out.println("输入send即可开始发送...");
		
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
    	String sentence = inFromUser.readLine();		// 从键盘等待"send"命令	
    	while (!sentence.equals("send")) sentence = inFromUser.readLine();	        	    	
    	
    	
    	
    	Sender_rdt_2_2 Sender2_2=new Sender_rdt_2_2();  
    	/*读入文本*/
    	Sender2_2.readText();
    	Sender2_2.start();
		
	}
	
}
