package CN5;
import java.io.*;
import java.net.*;
import java.util.zip.CheckedOutputStream;

/**
 * 
 * @author ����
 *
 */
public class Sender_rdt_2_1 {
	protected static int packetSize=20;//ÿ�δ��͵ķ��������ݵĳ���
	protected int packetNum;//����ĸ���
	protected byte [ ] receiveData;//���淢�͵�����
	protected byte [ ] sendData;  //������յ�����
	protected FileReader f;//���ļ��ж�ȡ����
	protected byte[] bufferText;
	protected int size;
	
	protected BufferedReader inFromUser;
	protected DatagramSocket sendSocket;
	//protected DatagramSocket receiveSocket;
	protected int sendPort;//���͹�ȥ�Ķ˿ں�
	//protected int receivePort;//���ؽ��ն˿ں�
	InetAddress IPAddress;
	
	/*���췽��*/
	public Sender_rdt_2_1() throws Exception {
		receiveData = new byte [packetSize + 3];
		sendData = new byte [packetSize + 3];
		inFromUser=new BufferedReader(new InputStreamReader(System.in));
		sendSocket=new DatagramSocket();
		sendPort=9999;
		IPAddress=InetAddress.getByName("localhost");
		
		//receivePort=10000;
		//receiveSocket=new DatagramSocket(receivePort);
		
		f = new FileReader("E:/Computer/Java/Project/Networking/src/CN5/HappyNewYear.txt");
		
	}
	
	
	
	
	/**
	 * �����ı�
	 * @throws Exception
	 */
	public void readText() throws IOException {
		BufferedReader buffer = new BufferedReader(f);
    	String tempStr = buffer.readLine();
    	size = tempStr.length();
    	bufferText= new byte[size];
    	bufferText= tempStr.getBytes(); 
    	
    	packetNum=size/packetSize+1;
    	
    	
    	
    	System.out.println("������򻺴��ж����ı�,�ı���С:"+size);
      	System.out.println("�ı�����:" + tempStr);
	}
	
	
	
	
	public DatagramPacket make_pkt(int j) {
		
		int i,k;
		byte buf[] = new byte [packetSize];
      	
      	for (k=0,i=(j-1)*packetSize;i<Math.min(j*packetSize,size);i++,k++)
      	{
      			buf[k] = bufferText[i];
      	}
      	
		System.out.println(new String(buf));
      	/*����һ������sendPacket���������ݡ����ݳ��ȡ�������IP���˿ں�*/
		DatagramPacket sendPacket=new DatagramPacket(buf,Math.min(packetSize,k),IPAddress,sendPort);
		return sendPacket;
		
	}
	
	
	
	
	/*���ɿ�����*/
	public void udt_send(DatagramPacket packet) throws Exception {
		
		sendSocket.send(packet);
	}
	
	
	/*�ӷ�������ȡACK��NAK*/
	public byte[] extract(DatagramPacket pkt) {
		return pkt.getData();
	}
	
	
	
	
	public void start() throws Exception {
		System.out.println("��ʼ����");
		int sentNum=0;//�ѷ��͵ķ���,Ҳ�����
		while  (sentNum<packetNum) {
			sentNum++;
			/*������õķ���ͨ��UDP Socket��������*/
			System.out.print("���͵�"+sentNum+"������:");
			
			
			DatagramPacket sendPacket=make_pkt(sentNum);
			udt_send(sendPacket);	
			
			
			/*�ȴ����շ������ķ���*/
			while (true) {
				DatagramPacket receivePacket=new DatagramPacket(receiveData,receiveData.length);
				/*rdt_rcv*/
				sendSocket.receive(receivePacket);
				byte[] AN=extract(receivePacket);
				
				
				if (AN[0]==1 && AN[1]==sentNum%2) {
					System.out.println("���յ���"+sentNum+"�������ACK"+AN[1]+",������һ����");
					break;
				}
					
				else if (AN[0]==1){
					System.out.println("���յ���"+sentNum+"�������ACK"+AN[1]+",�ش�");
					udt_send(sendPacket);
				}
				else {
					System.out.println("���յ���"+sentNum+"�������NAK"+AN[1]+",�ش�");
					udt_send(sendPacket);
				}
					
			}
			
			
			
		}
		System.out.println("�������");
		sendSocket.close();
	}
}
