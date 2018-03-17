package CN6;
import java.io.BufferedReader; 
 import java.io.IOException; 
 import java.io.InputStreamReader; 
 import java.net.Socket; 

 import javax.net.ssl.SSLServerSocket; 
 import javax.net.ssl.SSLServerSocketFactory; 
 import javax.net.ssl.SSLSocket; 

  public class Simple_SSLServerSocket{ 
  // �����˼����˿ں�
  private final static int LISTEN_PORT=54321;

   public static void main(String args[]) throws IOException{ 
    SSLServerSocket serverSocket=null; 
    SSLSocket clientSocket=null; 
    // ʹ��Ĭ�Ϸ�ʽ��ȡ�׽��ֹ���ʵ��
    SSLServerSocketFactory ssf=(SSLServerSocketFactory)SSLServerSocketFactory.getDefault();

   try{ 
      serverSocket=(SSLServerSocket)ssf.createServerSocket(LISTEN_PORT); 
      // ���ò���Ҫ��֤�ͻ������
      serverSocket.setNeedClientAuth(false);
      System.out.println("SSLServer is listening on "+LISTEN_PORT+" port"); 
      // ѭ�������˿ڣ�����пͻ���������¿�һ���߳���֮ͨ��
      while(true){ 
        // �����µĿͻ�������
        clientSocket=(SSLSocket)serverSocket.accept(); 
        ClientConnection clientConnection=new ClientConnection(clientSocket); 
        // ����һ���µ��߳�
        Thread clientThread=new Thread(clientConnection); 
        System.out.println("Client "+clientThread.getId()+" is connected"); 
        clientThread.run(); 
      } 
    }catch(IOException ioExp){ 
      ioExp.printStackTrace(); 
    }catch(Exception e){ 
      e.printStackTrace(); 
    }finally{ 
      serverSocket.close(); 
    } 
  } 
 } 

 class ClientConnection implements Runnable{ 
  private Socket clientSocket=null;
 
 public ClientConnection(SSLSocket sslsocket){ 
    clientSocket=sslsocket; 
  }

  public void run(){ 
    BufferedReader reader=null; 
    // �����յ������Կͻ��˵����ִ�ӡ����
    try{ 
      reader=new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); 
      while(true){ 
        String line=reader.readLine(); 
        if(line==null){ 
          System.out.println("Communication end."); 
          break; 
        } 
        System.out.println("Receive message: "+line); 
      } 
      reader.close(); 
      clientSocket.close(); 
    }catch(IOException ioExp){ 
      ioExp.printStackTrace(); 
    }catch(Exception e){ 
      e.printStackTrace(); 
    } 
  } 
 }