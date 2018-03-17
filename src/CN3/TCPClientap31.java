package CN3;
import java.io.*;//������java�����������İ�
import java.net.*;//�ṩ������֧����
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;



/**
 * 
 * @author ����
 * TCP�ͻ��ˣ��Ƚ��������֤���ٽ���IP��֤���ٽ��м��ܿ�����֤    ap3.1
 * �ͻ������û�������ʽ�������������˽��м��㲢���ؽ��
 */
public class TCPClientap31 {

	public static void main(String[] args) throws Exception{
		
		Socket clientSocket=new Socket("127.0.0.1",9999);
		String user_name;//�û���
		String password;//����
		
		String inputInfix;//�����û��������׺���ʽ
		String result;//�ӷ������õ��Ĳ����͵��û���׼������ַ���
		/*����������*/
		DataOutputStream outToServer=new DataOutputStream(clientSocket.getOutputStream());
		BufferedReader inFromServer=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		BufferedReader inFromUser=new BufferedReader(new InputStreamReader(System.in));
		
		
		/*���������֤���û���*/
		System.out.println("�������û���:");
		user_name=inFromUser.readLine();
		outToServer.writeBytes(user_name+'\n');
		result=inFromServer.readLine();
		/*�û�����֤ʧ�ܣ���������*/
		if (result.equals("false")){
			System.out.println("�û�����֤ʧ��,����ͨ��.");
			clientSocket.close();
			System.exit(0);
		}
		
		
		
		/*����IP��֤*/
		System.out.println("�û�����֤�ɹ������ڽ���IP��ַ������֤");
		result=inFromServer.readLine();
		if (result.equals("false")){
			System.out.println("IP��֤ʧ��,����ͨ��.");
			clientSocket.close();
			System.exit(0);
		}
		
		
		
	     
		/*���п�����֤ */
		System.out.println("IP��ַ��֤�ɹ�,���п�����֤,���������:");
        password=inFromUser.readLine();
        /*���ܿ���*/
        byte[] encryptResult = encrypt(password, "12345678"); //����password,�ڶ��������Ǽ�����Կ
		String encryptResultStr = parseByte2HexStr(encryptResult); //�����ܺ������ת��Ϊ�ַ����������.���ǲ���ǿ��ת������Ҫ���������ֽ�����ת��Ϊʮ�������ַ��� 
		
		System.out.println("������ܵĽ����"+encryptResultStr);
		outToServer.writeBytes(encryptResultStr+'\n');
		result=inFromServer.readLine();
		/*��������֤ʧ�ܣ���������*/
		if (result.equals("false")){
			System.out.println("������֤ʧ��,����ͨ��.");
			clientSocket.close();
			System.exit(0);
		}
		
		
		
		
		/*��֤�ɹ���������һ�����ʽ����*/
		System.out.println("��֤�ɹ�.");
		System.out.println("��������׺���ʽ��");
		inputInfix=inFromUser.readLine();
		
		
		outToServer.writeBytes(inputInfix+'\n');
		
		result=inFromServer.readLine();
		System.out.println("From Server ������Ϊ��"+result); 
		/*�ر��׽��֣�ͬʱҲ�ر��˿ͻ��ͷ�������֮���TCP����*/
		clientSocket.close();
	}
	
	
	
	/**��������ת����16���� 
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
	 * ���� 
	 *  
	 * @param content ��Ҫ���ܵ����� 
	 * @param password  ������Կ 
	 * @return 
	 */  
	public static byte[] encrypt(String content, String password) {  
	        try {             
	                KeyGenerator kgen = KeyGenerator.getInstance("AES");  
	                kgen.init(128, new SecureRandom(password.getBytes()));  
	                SecretKey secretKey = kgen.generateKey();  
	                byte[] enCodeFormat = secretKey.getEncoded();  
	                SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");  
	                Cipher cipher = Cipher.getInstance("AES");// ����������  
	                byte[] byteContent = content.getBytes("utf-8");  
	                cipher.init(Cipher.ENCRYPT_MODE, key);// ��ʼ��  
	                byte[] result = cipher.doFinal(byteContent);  
	                return result; // ����  
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


