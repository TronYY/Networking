package CN3;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

/*图形化界面的TCP客户端*/
public class InOutAppletTCP extends Applet implements ActionListener{
	Label inputLabel;
	Label outputLabel;
	
	TextField input,output;
	
	/*界面设计*/
	public void init() {
		inputLabel=new Label("Please input :");
		input=new TextField(20);
		outputLabel=new Label("From Server :");
		output=new TextField(20);
		add(inputLabel);
		add(input);
		add(outputLabel);
		add(output);
		input.addActionListener(this);
	}
	
	
	/*敲击回车事件发生后的处理*/
	public void actionPerformed(ActionEvent event) {
		
		/*由TCPClientForApplet类中的功能来完成*/
		TCPClientForApplet TCPC2S=new TCPClientForApplet();
		try {
			output.setText(TCPC2S.C2S(input.getText()));
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
