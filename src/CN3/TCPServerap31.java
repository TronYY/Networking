package CN3;
import java.io.*;
import java.net.*;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

/**
 * 
 * @author 金洋
 * TCP服务器，先进行身份认证，再进行IP认证，再进行加密口令验证    ap3.1
 * 接收客户端发送的中缀表达式，将其转化为后缀表达式后再计算出结果，并将结果返回给客户端
 */

public class TCPServerap31 {

	public static void main(String[] args) throws Exception {
		String clientSentence;
		int result;//储存最终计算结果
		/*服务器端授权的用户名*/
		final String USER_NAME = "JinYang";
		/*服务器端授权的IP,亦即客户端周知的IP地址*/
		final String IP = "127.0.0.1";
		/*服务器端授权的口令*/
		final String PASSWORD = "20160109";
		
		/*创建ServerSocket类型的welcomeSocket对象，相当于一扇等待着摸个客户端来敲击的门*/
		ServerSocket welcomeSocket=new ServerSocket(9999);
		
		while (true){
			System.out.println("Listening...");
			Socket connectionSocket=welcomeSocket.accept();
			/*创建流对象*/
			BufferedReader inFromClient=new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToClient=new DataOutputStream(connectionSocket.getOutputStream());
			clientSentence=inFromClient.readLine();
	
			/*认证成功则继续通信，否则结束通信*/
			System.out.println("用户名为"+clientSentence);
			if (clientSentence.equals(USER_NAME)){
				System.out.println("用户名正确.");
				outToClient.writeBytes(new String("success")+'\n');
			}	
			else {
				System.out.println("用户名错误,结束本次通信.");
				outToClient.writeBytes(new String("false")+'\n');
				continue;
			}
			
			
			/*进行IP认证，从connectionSocket中提取客户端IP，与客户器的周知IP进行比对*/
			InetAddress addr = connectionSocket.getInetAddress();
			String ip= addr.getHostAddress().toString();
			System.out.println("客户端IP:"+ip);
			if (ip.equals(IP)){
				System.out.println("IP正确.");
				outToClient.writeBytes(new String("success")+'\n');
			}	
			else {
				System.out.println("IP错误,结束本次通信.");
				outToClient.writeBytes(new String("false")+'\n');
				continue;
			}
			
		
			
			/*进行口令验证*/
			clientSentence=inFromClient.readLine();
			System.out.println("客户端的加密口令:"+clientSentence);
			
			
			byte[] decryptFrom = parseHexStr2Byte(clientSentence);  //加密后的byte数组是不能强制转换成字符串的，需作修改将16进制转换为二进制,
			byte[] decryptResult = decrypt(decryptFrom,"12345678");  //解密  ，第二个参数为解密密钥
			String decryptResultStr=new String(decryptResult);//解密后的字符串
			System.out.println("口令解密的结果:"+decryptResultStr);
			
			if (decryptResultStr.equals(PASSWORD)){
				System.out.println("口令正确.");
				outToClient.writeBytes(new String("success")+'\n');
			}	
			else {
				System.out.println("口令错误,结束本次通信.");
				outToClient.writeBytes(new String("false")+'\n');
				continue;
			}
			
			
			
			clientSentence=inFromClient.readLine();
			/*开始进行运算*/
			/*将中缀表达式转换为后缀表达式*/
			InfixToPostfix ITP=new InfixToPostfix();
			ITP.toPostfix(clientSentence);
			/*计算后缀表达式*/
			CalculatePostfixExpression CPE=new CalculatePostfixExpression();
			result=CPE.calculate(ITP.getpostfixString());
			
			
			outToClient.writeBytes(String.valueOf(result)+'\n');
			
		}
	}
	
	
	/**将16进制转换为二进制 
	 * @param hexStr 
	 * @return  byte[]
	 */  
	public static byte[] parseHexStr2Byte(String hexStr) {  
	        if (hexStr.length() < 1)  
	                return null;  
	        byte[] result = new byte[hexStr.length()/2];  
	        for (int i = 0;i< hexStr.length()/2; i++) {  
	                int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);  
	                int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);  
	                result[i] = (byte) (high * 16 + low);  
	        }  
	        return result;  
	}  
	
	
	
	
    /**解密
     * @param content  待解密内容
     * @param password 解密密钥
     * @return
     */
    public static byte[] decrypt(byte[] content, String password) {
            try {
                     KeyGenerator kgen = KeyGenerator.getInstance("AES");
                     kgen.init(128, new SecureRandom(password.getBytes()));
                     SecretKey secretKey = kgen.generateKey();
                     byte[] enCodeFormat = secretKey.getEncoded();
                     SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");            
                     Cipher cipher = Cipher.getInstance("AES");// 创建密码器
                    cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
                    byte[] result = cipher.doFinal(content);
                    return result; // 加密
            } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
            } catch (InvalidKeyException e) {
                    e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
            } catch (BadPaddingException e) {
                    e.printStackTrace();
            }
            return null;
    }

}




