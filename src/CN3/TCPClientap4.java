package CN3;
import java.io.*;//包含了java输入和输出流的包
import java.net.*;//提供了网络支持类
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;



/**
 * 
 * @author 金洋
 * TCP客户端，先进行身份认证，再进行IP认证，再进行新鲜数    ap4.0
 * 客户端由用户输入表达式，送至服务器端进行计算并返回结果
 */
public class TCPClientap4 {

	public static void main(String[] args) throws Exception{
		
		Socket clientSocket=new Socket("127.0.0.1",9999);
		String user_name;//用户名
		String password;//口令
		
		String inputInfix;//接收用户输入的中缀表达式
		String result;//从服务器得到的并发送到用户标准输出的字符串
		/*三个流对象*/
		DataOutputStream outToServer=new DataOutputStream(clientSocket.getOutputStream());
		BufferedReader inFromServer=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		BufferedReader inFromUser=new BufferedReader(new InputStreamReader(System.in));
		
		
		/*进行身份认证，用户名*/
		System.out.println("请输入用户名:");
		user_name=inFromUser.readLine();
		outToServer.writeBytes(user_name+'\n');
		result=inFromServer.readLine();
		/*用户名认证失败，结束程序*/
		if (result.equals("false")){
			System.out.println("用户名认证失败,结束通信.");
			clientSocket.close();
			System.exit(0);
		}
		
		
		
		/*进行IP认证*/
		System.out.println("用户名认证成功，正在进行IP地址进行验证");
		result=inFromServer.readLine();
		if (result.equals("false")){
			System.out.println("IP认证失败,结束通信.");
			clientSocket.close();
			System.exit(0);
		}
		
		
		
	     
		/*进行新鲜数认证 */
		System.out.println("IP地址验证成功,进行新鲜数验证.");
		String nonce=inFromServer.readLine();
		System.out.println("收到来自服务器的新鲜数"+nonce);
		/*加密新鲜数*/
		byte[] encryptResult = encrypt(nonce, "12345678"); //加密nonce,第二个参数是加密密钥
		String encryptResultStr = parseByte2HexStr(encryptResult); 
		
		System.out.println("新鲜数加密的结果："+encryptResultStr);
		outToServer.writeBytes(encryptResultStr+'\n');
		result=inFromServer.readLine();
		/*若新鲜数认证失败，结束程序*/
		if (result.equals("false")){
			System.out.println("口令认证失败,结束通信.");
			clientSocket.close();
			System.exit(0);
		}
		
       
		
		
		
		
		/*认证成功，进入下一步表达式运算*/
		System.out.println("认证成功.");
		System.out.println("请输入中缀表达式：");
		inputInfix=inFromUser.readLine();
		
		
		outToServer.writeBytes(inputInfix+'\n');
		
		result=inFromServer.readLine();
		System.out.println("From Server 计算结果为："+result); 
		/*关闭套接字，同时也关闭了客户和服务器端之间的TCP连接*/
		clientSocket.close();
	}
	
	
	
	/**将二进制转换成16进制 
	 * @param buf 
	 * @return 
	 */  
	public static String parseByte2HexStr(byte buf[]) {  
	        StringBuffer sb = new StringBuffer();  
	        for (int i = 0; i < buf.length; i++) {  
	                String hex = Integer.toHexString(buf[i] & 0xFF);  
	                if (hex.length() == 1) {  
	                        hex = '0' + hex;  
	                }  
	                sb.append(hex.toUpperCase());  
	        }  
	        return sb.toString();  
	}  
	
	
	
	
	
	/** 
	 * 加密 
	 *  
	 * @param content 需要加密的内容 
	 * @param password  加密密钥 
	 * @return 
	 */  
	public static byte[] encrypt(String content, String password) {  
	        try {             
	                KeyGenerator kgen = KeyGenerator.getInstance("AES");  
	                kgen.init(128, new SecureRandom(password.getBytes()));  
	                SecretKey secretKey = kgen.generateKey();  
	                byte[] enCodeFormat = secretKey.getEncoded();  
	                SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");  
	                Cipher cipher = Cipher.getInstance("AES");// 创建密码器  
	                byte[] byteContent = content.getBytes("utf-8");  
	                cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化  
	                byte[] result = cipher.doFinal(byteContent);  
	                return result; // 加密  
	        } catch (NoSuchAlgorithmException e) {  
	                e.printStackTrace();  
	        } catch (NoSuchPaddingException e) {  
	                e.printStackTrace();  
	        } catch (InvalidKeyException e) {  
	                e.printStackTrace();  
	        } catch (UnsupportedEncodingException e) {  
	                e.printStackTrace();  
	        } catch (IllegalBlockSizeException e) {  
	                e.printStackTrace();  
	        } catch (BadPaddingException e) {  
	                e.printStackTrace();  
	        }  
	        return null;  
	}  
}


