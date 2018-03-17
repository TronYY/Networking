package CN4;

import java.io.*;
import java.net.*;

public class UDPClient {

	public static void main(String[] args) throws Exception {
		String inputInfix;//�����û��������׺���ʽ
		String result;//�ӷ������õ��Ĳ����͵��û���׼������ַ���
		
		
		BufferedReader inFromUser=new BufferedReader(new InputStreamReader(System.in));
		
		/*����û������TCP���ӣ��ͻ�������ִ����һ��ʱû�������������������ϵ����û���κ������Ĳ������൱��ֻΪ�ͻ����̴�����һ���ţ�����û������������֮�䴴���ܵ�*/
		DatagramSocket clientSocket=new DatagramSocket();
		
		/*Ϊ�˽��ֽڷ��͵�Ŀ�Ľ��̣���Ҫ�õ����̵ĵ�ַ�����е�����һ��������������Ϊip��ַ��DNS����*/
		InetAddress IPAddress=InetAddress.getByName("localhost");
		
		byte[] sendData=new byte[1024];//����ͻ��˷��͵�����
		byte[] receiveData=new byte[1024];//����ͻ��˽��յ�����
		
		
		System.out.println("��������׺���ʽ��");
		/*���û������һ�з��뵽�ַ���inputInfix�У����дӱ�׼���뿪ʼ��������inFromUser�������ַ���inputInfix��*/
		inputInfix=inFromUser.readLine();
		/*ִ��һ������ת�����õ�һ���ַ������ض���Ϊһ���ַ�����*/
		sendData=inputInfix.getBytes();
		
		/*����һ������sendPacket���������ݡ����ݳ��ȡ�������IP���˿ں�*/
		DatagramPacket sendPacket=new DatagramPacket(sendData,sendData.length,IPAddress,9999);
		/*���ոչ���õķ���ͨ��clientSocket��������*/
		clientSocket.send(sendPacket);
		
		/*���յ����ķ���*/
		DatagramPacket receivePacket=new DatagramPacket(receiveData,receiveData.length);
		clientSocket.receive(receivePacket);
		
		/*�����ݴ�receivePacket�г�ȡ��������ִ������ת�������ַ�����ת�����ַ���*/
		result=new String(receivePacket.getData());
		System.out.println("From Server ������Ϊ��"+result); 
		
		clientSocket.close();
		

	}

}
