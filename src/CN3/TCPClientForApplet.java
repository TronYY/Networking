package CN3;
import java.io.*;
import java.net.*;
public class TCPClientForApplet {
	
	public TCPClientForApplet(){
	}
	
	public String C2S(String inputInfix) throws Exception{
		
		String result;
		
		Socket clientSocket=new Socket("localhost",10000);
		
		DataOutputStream outToServer=new DataOutputStream(clientSocket.getOutputStream());
		BufferedReader inFromServer=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		
		
		outToServer.writeBytes(inputInfix+'\n');
		result=inFromServer.readLine();
	
		clientSocket.close();	
		return(result); 
	}
}
