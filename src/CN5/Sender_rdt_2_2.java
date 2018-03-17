package CN5;
import java.io.*;
import java.net.*;
import java.util.zip.CheckedOutputStream;

/**
 * 
 * @author 金洋
 *
 */
public class Sender_rdt_2_2 {
	protected static int packetSize=20;//每次传送的分组中数据的长度
	protected int packetNum;//分组的个数
	protected byte [ ] receiveData;//保存发送的数据
	protected byte [ ] sendData;  //保存接收的数据
	protected FileReader f;//从文件中读取数据
	protected byte[] bufferText;
	protected int size;
	
	protected BufferedReader inFromUser;
	protected DatagramSocket sendSocket;
	//protected DatagramSocket receiveSocket;
	protected int sendPort;//发送过去的端口号
	//protected int receivePort;//本地接收端口号
	InetAddress IPAddress;
	
	/*构造方法*/
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
	 * 读入文本
	 * @throws Exception
	 */
	public void readText() throws IOException {
		BufferedReader buffer = new BufferedReader(f);
    	String tempStr = buffer.readLine();
    	size = tempStr.length();
    	bufferText= new byte[size];
    	bufferText= tempStr.getBytes(); 
    	
    	packetNum=size/packetSize+1;
    	
    	
    	
    	System.out.println("已完成向缓存中读入文本,文本大小:"+size);
      	System.out.println("文本内容:" + tempStr);
	}
	
	
	
	
	/*传送的数据中第0位为序号*/
	public DatagramPacket make_pkt(byte order,int j) {
		
		int i,k;
		byte buf[] = new byte [packetSize+1];
		buf[0]=(byte)(order+48);//为了便于在控制台显示序号值，将其转为字符
		
      	for (k=1,i=(j-1)*packetSize;i<Math.min(j*packetSize,size);i++,k++)
      	{
      			buf[k] = bufferText[i];
      	}
      	
		System.out.println(new String(buf));
      	/*构造一个分组sendPacket，包含数据、数据长度（由于多了序号，便需要长度加一）、服务器IP、端口号*/
		DatagramPacket sendPacket=new DatagramPacket(buf,Math.min(packetSize,k)+1,IPAddress,sendPort);
		return sendPacket;
		
	}
	
	
	
	
	/*不可靠传输*/
	public void udt_send(DatagramPacket packet) throws Exception {
		
		sendSocket.send(packet);
	}
	
	
	/*从分组中提取ACK或NAK*/
	public byte[] extract(DatagramPacket pkt) {
		return pkt.getData();
	}
	
	
	
	
	public void start() throws Exception {
		System.out.println("开始发送");
		int sentNum=0;//已发送的分组
		
		
		
		int reACK,reord;//接收到的ACK和序号
		while  (sentNum<packetNum) {
			sentNum++;
			/*将构造好的分组通过UDP Socket送入网络*/
			System.out.print("发送第"+sentNum+"个分组:");
			
			
			DatagramPacket sendPacket=make_pkt((byte) (sentNum%2),sentNum);
			udt_send(sendPacket);	
			
			
			
			/*等待接收方发来的反馈*/
			while (true) {
				DatagramPacket receivePacket=new DatagramPacket(receiveData,receiveData.length);
				/*rdt_rcv*/
				sendSocket.receive(receivePacket);
				byte[] rP=extract(receivePacket);
				reACK=rP[0];
				reord=rP[1];
				
				
				if (reACK==1 && reord==sentNum%2) {
					System.out.println("接收到第"+sentNum+"个分组的ACK=1,序号="+reord+",传送下一分组");
					break;
				}
					
				else {
					System.out.println("接收到第"+sentNum+"个分组的ACK="+reACK+",序号="+reord+",重传");
					udt_send(sendPacket);
				}
				
					
			}	
		}
		System.out.println("传输完毕");
		sendSocket.close();
	}
}
