package CN3;
import java.io.*;
import java.net.*;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

/**
 * 
 * @author ����
 * TCP���������Ƚ��������֤���ٽ���IP��֤���ٽ�����������֤    ap4.0
 * ���տͻ��˷��͵���׺���ʽ������ת��Ϊ��׺���ʽ���ټ�������������������ظ��ͻ���
 */

public class TCPServerap4 {

	public static void main(String[] args) throws Exception {
		String clientSentence;
		int result;//�������ռ�����
		/*����������Ȩ���û���*/
		final String USER_NAME = "JinYang";
		/*����������Ȩ��IP,�༴�ͻ�����֪��IP��ַ*/
		final String IP = "127.0.0.1";
		/*����������Ȩ�Ŀ���*/
		final String PASSWORD = "20160109";
		
		/*Э������*/
		final int CYCLE =1000;
		final int MAXINT =1000000000;
		int[] nonce=new int[1000];
		
		/*����ServerSocket���͵�welcomeSocket�����൱��һ�ȵȴ��������ͻ������û�����*/
		ServerSocket welcomeSocket=new ServerSocket(9999);
		
		
		int k=-1;//��ʾһ��������ĵڼ���
		while (true){
			k++;
			/*�ʼ���Լ���һ��Э��������֮������һ���µ���������1000��*/
			if (k>=CYCLE ||k==0) {
				nonce=getNonce(-MAXINT,MAXINT,CYCLE);//����-2147483648��2147483648֮���1000�����ظ���
				k=0;
			}
			
			
			
			
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
			
		
			
			/*������������֤*/
			int sendR=nonce[k];
			System.out.println("����ͨ��������Ϊ:"+nonce[k]);
			outToClient.writeBytes(sendR+""+'\n');//�����������͸��ͻ���
			clientSentence=inFromClient.readLine();
			System.out.println("�ͻ��˶��������ļ���:"+clientSentence);
			byte[] decryptFrom = parseHexStr2Byte(clientSentence);  //���ܺ��byte�����ǲ���ǿ��ת�����ַ����ģ������޸Ľ�16����ת��Ϊ������,
			byte[] decryptResult = decrypt(decryptFrom,"12345678");  //����  ���ڶ�������Ϊ������Կ
			String decryptResultStr=new String(decryptResult);//���ܺ���ַ���
			System.out.println("���������ܵĽ��:"+decryptResultStr);
			
			if (Integer.parseInt(decryptResultStr)==nonce[k]){
				System.out.println("��������֤��ȷ.");
				outToClient.writeBytes(new String("success")+'\n');
			}	
			else {
				System.out.println("��������֤����,��������ͨ��.");
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
    
    
    
    
    
    /** 
     * ���ָ����Χ��N�����ظ����� 
     * ����ѭ��ȥ�� 
     * @param min ָ����Χ��Сֵ 
     * @param max ָ����Χ���ֵ 
     * @param n ��������� 
     */  
    public static int[] getNonce(int min, int max, int n){  
        if (n > (max - min + 1) || max < min) {  
               return null;  
           }  
        int[] result = new int[n];  
        int count = 0;  
        while(count < n) {  
            int num = (int) (Math.random() * (max - min)) + min;  
            boolean flag = true;  
            for (int j = 0; j < n; j++) {  
                if(num == result[j]){  
                    flag = false;  
                    break;  
                }  
            }  
            if(flag){  
                result[count] = num;  
                count++;  
            }  
        }  
        return result;  
    }  

}




