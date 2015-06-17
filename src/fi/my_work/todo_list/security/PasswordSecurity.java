package fi.my_work.todo_list.security;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordSecurity {
	
//	This method changes the password into unreadable form
	public static String getHash(String password) {
		String hash  = null;
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			digest.reset();
			String salts = " This is a bad idea";
			
			String toHash = password + salts;
			
			byte[] input = digest.digest(toHash.getBytes("UTF-8"));
			
			hash = new BigInteger(input).toString(16);
			
			return hash;
		} catch (UnsupportedEncodingException e) {

		} catch (NoSuchAlgorithmException e) {

		}
		return hash;
	}
}