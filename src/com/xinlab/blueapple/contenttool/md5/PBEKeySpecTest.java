package com.xinlab.blueapple.contenttool.md5;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

/**
 * @author Administrator
 *	基于密码口令的加密与解密
 * 	由于密钥难于记忆，叫用户记住密钥是不可能的
 * 	可以让用户输入密码，然后根据用户输入的密码
 * 	生成相应的密钥，这样就只要求用户记住密码即合
 */
public class PBEKeySpecTest {
	public static void main(String[] args) throws Exception {

		Myencrypt();

		Mydecryption();
	}

	/**
	 * 加密方法
	 * @throws Exception
	 */
	public static void Myencrypt() throws Exception {
        
		//基于密码的加密要用PBEWithMD5AndDES算法
		Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");
        
		//给数据加盐，使加密更可靠
		PBEParameterSpec parameterSpec = new PBEParameterSpec(new byte[] { 1,
				2, 3, 4, 5, 6, 7, 8 }, 9);
         
		//创建密钥
		SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES")
				
				.generateSecret(new PBEKeySpec("123456789".toCharArray()));
        
		//用密钥和盐初始化Cipher
		cipher.init(Cipher.ENCRYPT_MODE, key, parameterSpec);
         
		//对数据进行加密
		byte[] result = cipher.doFinal("aaaa".getBytes());

		// 把密钥写到硬盘上
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(
				new FileOutputStream(new File("F://xxx.key")));

		objectOutputStream.writeObject(key);

		// 把加密后的结果存到硬盘上
		FileOutputStream outputStream = new FileOutputStream(new File(
				"F://xxx.data"));
		outputStream.write(result);

	}
     
	/**
	 * 解密方法
	 * @throws Exception
	 */
	public static void Mydecryption() throws Exception {
		
		Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");

		PBEParameterSpec parameterSpec = new PBEParameterSpec(new byte[] { 1,
				2, 3, 4, 5, 6, 7, 8 }, 9);

		// 从硬盘读取key
		ObjectInputStream objectInputStream = new ObjectInputStream(
				new FileInputStream(new File("F://xxx.key")));

		SecretKey key = (SecretKey) objectInputStream.readObject();

		cipher.init(Cipher.DECRYPT_MODE, key, parameterSpec);

		// 从硬盘中读取数据文件
		FileInputStream fileInputStream = new FileInputStream(new File(
				"F://xxx.data"));
		
        
		//把加密后的文件读取到ByteArrayOutputStream中
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;

		while ((len = fileInputStream.read(buffer)) != -1) {
			outputStream.write(buffer, 0, len);
		}

		fileInputStream.close();
        
		//解密，返回解密后的结果
		byte[] data = cipher.doFinal(outputStream.toByteArray());

		outputStream.close();

		System.out.println(new String(data));

	}
	
}
