package CN3;
import java.io.*;
import java.net.*;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

/**
 * 
 * @author ����
 * TCP���������Ƚ��������֤���ٽ���IP��֤���ٽ��м��ܿ�����֤    ap3.1
 * ���տͻ��˷��͵���׺���ʽ������ת��Ϊ��׺���ʽ���ټ�������������������ظ��ͻ���
 */

public class TCPServerap31 {

	public static void main(String[] args) throws Exception {
		String clientSentence;
		int result;//�������ռ�����
		/*����������Ȩ���û���*/
		final String USER_NAME = "JinYang";
		/*����������Ȩ��IP,�༴�ͻ�����֪��IP��ַ*/
		final String IP = "127.0.0.1";
		/*����������Ȩ�Ŀ���*/
		final String PASSWORD = "20160109";
		
		/*����ServerSocket���͵�welcomeSocket�����൱��һ�ȵȴ��������ͻ������û�����*/
		ServerSocket welcomeSocket=new ServerSocket(9999);
		
		while (true){
			System.out.println("Listening...");
			Socket connectionSocket=welcomeSocket.accept();
			/*����������*/
			BufferedReader inFromClient=new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToClient=new DataOutputStream(connectionSocket.getOutputStream());
			clientSentence=inFromClient.readLine();
	
			/*��֤�ɹ������ͨ�ţ��������ͨ��*/
			System.out.println("�û���Ϊ"+clientSentence);
			if (clientSentence.equals(USER_NAME)){
				System.out.println("�û�����ȷ.");
				outToClient.writeBytes(new String("success")+'\n');
			}	
			else {
				System.out.println("�û�������,��������ͨ��.");
				outToClient.writeBytes(new String("false")+'\n');
				continue;
			}
			
			
			/*����IP��֤����connectionSocket����ȡ�ͻ���IP����ͻ�������֪IP���бȶ�*/
			InetAddress addr = connectionSocket.getInetAddress();
			String ip= addr.getHostAddress().toString();
			System.out.println("�ͻ���IP:"+ip);
			if (ip.equals(IP)){
				System.out.println("IP��ȷ.");
				outToClient.writeBytes(new String("success")+'\n');
			}	
			else {
				System.out.println("IP����,��������ͨ��.");
				outToClient.writeBytes(new String("false")+'\n');
				continue;
			}
			
		
			
			/*���п�����֤*/
			clientSentence=inFromClient.readLine();
			System.out.println("�ͻ��˵ļ��ܿ���:"+clientSentence);
			
			
			byte[] decryptFrom = parseHexStr2Byte(clientSentence);  //���ܺ��byte�����ǲ���ǿ��ת�����ַ����ģ������޸Ľ�16����ת��Ϊ������,
			byte[] decryptResult = decrypt(decryptFrom,"12345678");  //����  ���ڶ�������Ϊ������Կ
			String decryptResultStr=new String(decryptResult);//���ܺ���ַ���
			System.out.println("������ܵĽ��:"+decryptResultStr);
			
			if (decryptResultStr.equals(PASSWORD)){
				System.out.println("������ȷ.");
				outToClient.writeBytes(new String("success")+'\n');
			}	
			else {
				System.out.println("�������,��������ͨ��.");
				outToClient.writeBytes(new String("false")+'\n');
				continue;
			}
			
			
			
			clientSentence=inFromClient.readLine();
			/*��ʼ��������*/
			/*����׺���ʽת��Ϊ��׺���ʽ*/
			InfixToPostfix ITP=new InfixToPostfix();
			ITP.toPostfix(clientSentence);
			/*�����׺���ʽ*/
			CalculatePostfixExpression CPE=new CalculatePostfixExpression();
			result=CPE.calculate(ITP.getpostfixString());
			
			
			outToClient.writeBytes(String.valueOf(result)+'\n');
			
		}
	}
	
	
	/**��16����ת��Ϊ������ 
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
	
	
	
	
    /**����
     * @param content  ����������
     * @param password ������Կ
     * @return
     */
    public static byte[] decrypt(byte[] content, String password) {
            try {
                     KeyGenerator kgen = KeyGenerator.getInstance("AES");
                     kgen.init(128, new SecureRandom(password.getBytes()));
                     SecretKey secretKey = kgen.generateKey();
                     byte[] enCodeFormat = secretKey.getEncoded();
                     SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");            
                     Cipher cipher = Cipher.getInstance("AES");// ����������
                    cipher.init(Cipher.DECRYPT_MODE, key);// ��ʼ��
                    byte[] result = cipher.doFinal(content);
                    return result; // ����
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




