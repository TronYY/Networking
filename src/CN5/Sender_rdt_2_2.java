package CN5;
import java.io.*;
import java.net.*;
import java.util.zip.CheckedOutputStream;

/**
 * 
 * @author ����
 *
 */
public class Sender_rdt_2_2 {
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
	public Sender_rdt_2_2() throws Exception {
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
	
	
	
	
	/*���͵������е�0λΪ���*/
	public DatagramPacket make_pkt(byte order,int j) {
		
		int i,k;
		byte buf[] = new byte [packetSize+1];
		buf[0]=(byte)(order+48);//Ϊ�˱����ڿ���̨��ʾ���ֵ������תΪ�ַ�
		
      	for (k=1,i=(j-1)*packetSize;i<Math.min(j*packetSize,size);i++,k++)
      	{
      			buf[k] = bufferText[i];
      	}
      	
		System.out.println(new String(buf));
      	/*����һ������sendPacket���������ݡ����ݳ��ȣ����ڶ�����ţ�����Ҫ���ȼ�һ����������IP���˿ں�*/
		DatagramPacket sendPacket=new DatagramPacket(buf,Math.min(packetSize,k)+1,IPAddress,sendPort);
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
		int sentNum=0;//�ѷ��͵ķ���
		
		
		
		int reACK,reord;//���յ���ACK�����
		while  (sentNum<packetNum) {
			sentNum++;
			/*������õķ���ͨ��UDP Socket��������*/
			System.out.print("���͵�"+sentNum+"������:");
			
			
			DatagramPacket sendPacket=make_pkt((byte) (sentNum%2),sentNum);
			udt_send(sendPacket);	
			
			
			
			/*�ȴ����շ������ķ���*/
			while (true) {
				DatagramPacket receivePacket=new DatagramPacket(receiveData,receiveData.length);
				/*rdt_rcv*/
				sendSocket.receive(receivePacket);
				byte[] rP=extract(receivePacket);
				reACK=rP[0];
				reord=rP[1];
				
				
				if (reACK==1 && reord==sentNum%2) {
					System.out.println("���յ���"+sentNum+"�������ACK=1,���="+reord+",������һ����");
					break;
				}
					
				else {
					System.out.println("���յ���"+sentNum+"�������ACK="+reACK+",���="+reord+",�ش�");
					udt_send(sendPacket);
				}
				
					
			}	
		}
		System.out.println("�������");
		sendSocket.close();
	}
}
