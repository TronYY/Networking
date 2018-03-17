package CN5;
import java.io.*;
import java.net.*;
import java.util.zip.CheckedOutputStream;

public class Sender_rdt_1_0 {
	protected static int packetSize=20;//每次传送的分组中数据的长度
	protected int packetNum;//分组的个数
	protected byte [ ] receiveData;//保存发送的数据
	protected byte [ ] sendData;  //保存接收的数据
	protected FileReader f;//从文件中读取数据
	protected byte[] bufferText;
	protected int size;
	
	protected BufferedReader inFromUser;
	protected DatagramSocket sendSocket;
	protected int port;//端口号
	InetAddress IPAddress;
	
	/*构造方法*/
	public Sender_rdt_1_0() throws Exception {
		receiveData = new byte [packetSize + 3];
		sendData = new byte [packetSize + 3];
		inFromUser=new BufferedReader(new InputStreamReader(System.in));
		sendSocket=new DatagramSocket();
		port=9999;
		IPAddress=InetAddress.getByName("localhost");
		
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
	
	
	
	
	public DatagramPacket make_pkt(int j) {
		
		int i,k;
		byte buf[] = new byte [packetSize];
      	
      	for (k=0,i=(j-1)*packetSize;i<Math.min(j*packetSize,size);i++,k++)
      	{
      			buf[k] = bufferText[i];
      	}
      	
		System.out.println(new String(buf));
      	/*构造一个分组sendPacket，包含数据、数据长度、服务器IP、端口号*/
		DatagramPacket sendPacket=new DatagramPacket(buf,Math.min(packetSize,k),IPAddress,port);
		return sendPacket;
		
	}
	
	/*不可靠传输*/
	public void udt_send(DatagramPacket packet) throws IOException {
		
		sendSocket.send(packet);
	}
	
	
	
	
	
	public void start() throws IOException {
		System.out.println("开始发送");
		int sentNum=0;//已发送的分组,也即序号
		while  (sentNum<packetNum) {
			sentNum++;
			/*将构造好的分组通过UDP Socket送入网络*/
			System.out.print("第"+sentNum+"个分组:");
			udt_send(make_pkt(sentNum));	
		}
	}
}
