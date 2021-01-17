package com.ezypay.api.utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

public final class MD5 { 
    public static String getMd5(String input) throws NoSuchAlgorithmException
    { 
    	MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new NoSuchAlgorithmException();
		}
		md.update(input.getBytes());
		byte[] digest = md.digest();
		String myHash = DatatypeConverter.printHexBinary(digest).toUpperCase();
		return myHash;
    } 
    
  
    // Driver code 
//    public static void main(String args[]) throws NoSuchAlgorithmException 
//    { 
//    	
//    	String s = "879315988263689-87931598826368900-0x547005a954f7750d7ff309c24c6a248c65a04de4"; 
//        System.out.println("Your HashCode Generated by MD5 is: " + getMd5(s)); 
//    } 
} 