package CN5;

public class TestReceiver_rdt_2_2 {

	public static void main(String[] args) throws Exception {
		System.out.println("等待接收数据...");
		Receiver_rdt_2_2 Receiver2_2=new Receiver_rdt_2_2();  
    	
		Receiver2_2.start();
	}

}
