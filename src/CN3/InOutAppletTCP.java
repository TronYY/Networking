package CN3;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

/*ͼ�λ������TCP�ͻ���*/
public class InOutAppletTCP extends Applet implements ActionListener{
	Label inputLabel;
	Label outputLabel;
	
	TextField input,output;
	
	/*�������*/
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
	
	
	/*�û��س��¼�������Ĵ���*/
	public void actionPerformed(ActionEvent event) {
		
		/*��TCPClientForApplet���еĹ��������*/
		TCPClientForApplet TCPC2S=new TCPClientForApplet();
		try {
			output.setText(TCPC2S.C2S(input.getText()));
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
