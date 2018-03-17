package CN5;

import java.io.*;
import java.net.*;

import CN3.CalculatePostfixExpression;
import CN3.InfixToPostfix;

public class Sender {

	public static void main(String[] args) throws Exception {
		
		String clientSentence;//���յ����ַ���
		int result;//�����������괢�����ռ�����
		
		/*����һ��serverSocket�����е����ݶ���ͨ������׽��ֽ��д���*/
		
		/*����UDP�����ӣ�����Ҫ����һ���µ��׽����������µ��������ж���ͻ����ʴ�Ӧ�ó������ǽ����еķ��鶼���͵����serverSocket��*/
		DatagramSocket serverSocket=new DatagramSocket(9999);
		
		
		
		
		while (true) {
			/*ÿһ�ζ��������³�ʼ��������ᱣ����һ�ε�ֵ*/
			byte[] sendData=new byte[1024];//���淢�͵�����
			byte[] receiveData=new byte[1024];//������յ�����
			
			DatagramPacket receivePacket=new DatagramPacket(receiveData,receiveData.length);
			serverSocket.receive(receivePacket);
			
			
			
			/*�����ݴӷ����г�ȡ�����������ݷ��뵽�ַ�����*/
			clientSentence=new String(receivePacket.getData());
			
			/*��ȡ�ͻ���IP��ַ�Ͷ˿ں�*/
			InetAddress IPAddress=receivePacket.getAddress();
			int port=receivePacket.getPort();
			System.out.println("���յ��ı�:" +clientSentence);  
	        System.out.println("���յ�ip��ַ:" + IPAddress);  
	        System.out.println("���յĶ˿�::" + port);
			
	        
	        /*��ʼ��������*/
			/*����׺���ʽת��Ϊ��׺���ʽ*/
			InfixToPostfix ITP=new InfixToPostfix();
			ITP.toPostfix(clientSentence);
			/*�����׺���ʽ*/
			CalculatePostfixExpression CPE=new CalculatePostfixExpression();
			result=CPE.calculate(ITP.getpostfixString());
			
			/*��int�͵�resultת��ΪString����תΪ�ַ�����*/
			sendData=String.valueOf(result).getBytes();
			DatagramPacket sendPacket=new DatagramPacket(sendData,sendData.length,IPAddress,port);
			
			serverSocket.send(sendPacket);
			
		}
		
		
	}		
		
		
}