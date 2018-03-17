package CN3;
import java.awt.*; 
import java.net.*; 
import java.awt.event.*; 
public class ClientFrame { 
	private Frame f; 
 	private TextField tf; 
 	private Button but; 
 	private TextArea ta; 

 	ClientFrame() { 
 		init(); 
 	} 

 	public TextArea getTextArea() { 
 		return this.ta; 
 	} 

 	public void init() { 
 		f = new Frame("客户端"); 
 		f.setBounds(300, 100, 400, 300); 
 		f.setLayout(new FlowLayout()); 
 		tf = new TextField(40); 
 		but = new Button("发送"); 
 		ta = new TextArea(25, 50); 
 		f.add(tf); 
 		f.add(but); 
 		f.add(ta); 
 		myEvent(); 
 		f.setVisible(true);  
 	} 


 	private void myEvent() { 
 		f.addWindowListener(new WindowAdapter() { 
 			public void windowClosing(WindowEvent e) { 
 				System.exit(0); 
 			} 
 		});
 		

 

 		but.addActionListener(new ActionListener() { 
 			public void actionPerformed(ActionEvent e) { 
 				//ta.setText(tf.getText()); 
 				try { 
 					DatagramSocket ds = new DatagramSocket(); 
 					byte[] buf = tf.getText().getBytes(); 
 					DatagramPacket dp = new DatagramPacket(buf, buf.length, 

 							InetAddress.getByName("127.0.0.1"), 10001); 
 					ds.send(dp); 	
 					ta.append("客户端说::"+tf.getText()+"\r\n"); 
 					tf.setText(""); 

 				} catch (Exception ex) { 
 					ex.toString(); 
 				} finally { 
 					// ds.close(); 
 				} 
 			} 
 		}); 
 	} 
}