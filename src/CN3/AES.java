package CN3;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class AES {

	
	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, 
	IllegalBlockSizeException, BadPaddingException {
		// TODO Auto-generated method stub
		simplCipherTest();

	}
	//����Կ����
	public static void simplCipherTest() throws NoSuchAlgorithmException, NoSuchPaddingException, 
	InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
		
		//����Cipher��ʵ����AES��һ��ת������
		//�йر�׼ת�����Ƶ���Ϣ����μ� Java Cryptography Architecture Reference Guide �ĸ�¼ A��
		Cipher cipher = Cipher.getInstance("AES");
		
		//����key
		SecretKey key=KeyGenerator.getInstance("AES").generateKey();
		
		//��ʼ��cipher������1��Cipher �Ĳ���ģʽ��ENCRYPT_MODE��DECRYPT_MODE��WRAP_MODE �� UNWRAP_MODE��������ѡ����ģʽ
		//ENCRYPT_MODE,����������Կkey
		cipher.init(Cipher.ENCRYPT_MODE, key);
		
		//��ʼ�������ݵĲ���
		cipher.update("hello java !".getBytes());
		byte[] result=cipher.doFinal();//���ﷵ�ؽ����ӡ���������ڵ���
		System.out.println("���ݼ��ܵĽ����"+new String(result));
		
		//������н��ܲ���
		cipher.init(Cipher.DECRYPT_MODE, key);
		System.out.println("���ݽ��ܵĽ����"+new String(cipher.doFinal(result)));
		
	}

}

