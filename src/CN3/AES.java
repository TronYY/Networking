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
	//简单密钥加密
	public static void simplCipherTest() throws NoSuchAlgorithmException, NoSuchPaddingException, 
	InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
		
		//创建Cipher的实例，AES是一个转换名称
		//有关标准转换名称的信息，请参见 Java Cryptography Architecture Reference Guide 的附录 A。
		Cipher cipher = Cipher.getInstance("AES");
		
		//产生key
		SecretKey key=KeyGenerator.getInstance("AES").generateKey();
		
		//初始化cipher，参数1是Cipher 的操作模式（ENCRYPT_MODE、DECRYPT_MODE、WRAP_MODE 或 UNWRAP_MODE），这里选加密模式
		//ENCRYPT_MODE,参数二是密钥key
		cipher.init(Cipher.ENCRYPT_MODE, key);
		
		//开始加密数据的操作
		cipher.update("hello java !".getBytes());
		byte[] result=cipher.doFinal();//这里返回结果打印出来，便于调试
		System.out.println("数据加密的结果："+new String(result));
		
		//这里进行解密操作
		cipher.init(Cipher.DECRYPT_MODE, key);
		System.out.println("数据解密的结果："+new String(cipher.doFinal(result)));
		
	}

}

