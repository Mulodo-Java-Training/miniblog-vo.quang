package com.mulodo.miniblog.encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysql.jdbc.Util;

public class Encrypt {

	
//	public static String MD5Hashing(String pass) throws NoSuchAlgorithmException {
//		
//		MessageDigest md = MessageDigest.getInstance("MD5");
//        md.update(pass.getBytes());
// 
//        byte byteData[] = md.digest();
// 
//        //convert the byte to hex format method 1
//        StringBuffer sb = new StringBuffer();
//        for (int i = 0; i < byteData.length; i++) {
//         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
//        }
// 
//        System.out.println("Digest(in hex format):: " + sb.toString());
//// 
////        //convert the byte to hex format method 2
////        StringBuffer hexString = new StringBuffer();
////    	for (int i=0;i<byteData.length;i++) {
////    		String hex=Integer.toHexString(0xff & byteData[i]);
////   	     	if(hex.length()==1) hexString.append('0');
////   	     	hexString.append(hex);
////    	}
//		return sb.toString();
//	}
//
	
	
	
	private static final Logger logger = LoggerFactory.getLogger(Util.class);

    /**
     * Hash input string
     * 
     * @param value
     *            string which need to hash
     * @return hash value of input
     */
    public static String hashSHA256(String value) {

	MessageDigest md;
	try {
	    md = MessageDigest.getInstance("SHA-256");
	} catch (NoSuchAlgorithmException e) {
	    // Log exception
	    logger.error("HASH_ALGORITHM=SHA-256", e);
	    return null;
	}

	md.update(value.getBytes());

	byte byteData[] = md.digest();

	// convert the byte to hex format method 1
	StringBuffer sb = new StringBuffer();
	for (int i = 0; i < byteData.length; i++) {
	    sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));	
	}

	System.out.println("Hex format : " + sb.toString());

	// convert the byte to hex format method 2
	StringBuffer hexString = new StringBuffer();
	for (int i = 0; i < byteData.length; i++) {
	    String hex = Integer.toHexString(0xff & byteData[i]);
	    if (hex.length() == 1)
		hexString.append('0');
	    hexString.append(hex);
	}

	// Log result
	String result = hexString.toString();
	logger.debug("Value: [{}]; Hash: [{}]", value, result);

	return result;
    }

    /**
     * Create new token by hash user ID and current date time (to make sure token
     * is unique)
     * 
     * @param id
     * User's id
     * @return new token if id > 0 and NULL otherwise
     */
    public static String createToken(int id) {
	// Validate userID
	if (0 > id) {
	    return null;
	}
	// Get current date time
	String currentDateTime = (new Date()).toString();
	// Append username and current date time and then hash to create new token
	return hashSHA256(id + currentDateTime);
    }
	
}



