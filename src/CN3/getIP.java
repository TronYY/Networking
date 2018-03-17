package CN3;
import java.io.Console;
import java.net.InetAddress;
import java.net.UnknownHostException;
public class getIP{

	public static void main(String[] args) throws Exception {
		InetAddress addr = InetAddress.getLocalHost();
		String ip=addr.getHostAddress().toString();//获得本机IP		
		System.out.println(ip);
		
		Console console = System.console();  
        String password ;  
        String password1 = new String(console.readPassword());  
        System.out.println("password="+password1); 
	}
}
