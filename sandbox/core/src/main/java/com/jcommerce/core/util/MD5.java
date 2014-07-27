package com.jcommerce.core.util;

import java.security.MessageDigest;

public class MD5 {

    private final static String[] hexDigits = { 
        "0", "1", "2", "3", "4", "5", "6", "7", 
        "8", "9", "a", "b", "c", "d", "e", "f"}; 
    
    public static void main(String... args) {
        try {
            String orig = "&seller_email=hyj_0105@163.com&partner=2088002230861529&service=create_direct_pay_by_usermysecurityCode";
            String dest = encode(orig);
            System.out.println("dest: "+dest);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static String encode(String source) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(source.getBytes());
            byte[] to = md.digest();
            String dest = byteArrayToHexString(to);
            System.out.println("dest: " + dest);
            return dest;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    public static String encode(String source, String encoding) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(source.getBytes(encoding));
            byte[] to = md.digest();
            String dest = byteArrayToHexString(to);
            System.out.println("dest: "+dest);
            return dest;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    public static String byteArrayToHexString(byte[] b) { 
        StringBuffer resultSb = new StringBuffer(); 
        for (int i = 0; i < b.length; i++) { 
          resultSb.append(byteToHexString(b[i])); 
        } 
        return resultSb.toString(); 
      } 

      private static String byteToHexString(byte b) { 
        int n = b; 
        if (n < 0) 
          n = 256 + n; 
        int d1 = n / 16; 
        int d2 = n % 16; 
        return (hexDigits[d1] + hexDigits[d2]).toUpperCase(); 
      } 

}
