package com.jcommerce.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jcommerce.core.io.Base64u;

public class Tools {
	 public static String serialize(Map<String, String> config) {
	        ByteArrayOutputStream bos = new ByteArrayOutputStream();
	        try {
	            ObjectOutputStream oos = new ObjectOutputStream(bos);
	            oos.writeObject(config);
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	            throw new RuntimeException(e);
	        }
	        String serializedConfig = new String(new Base64u().encode(bos.toByteArray())); 

	        System.out.println("serializedConfig: "+serializedConfig);
	        return serializedConfig;
	    }
	 public static String serializeList(Map<String, List<Map<String,String>>> config){
		 ByteArrayOutputStream bos = new ByteArrayOutputStream();
	        try {
	            ObjectOutputStream oos = new ObjectOutputStream(bos);
	            oos.writeObject(config);
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	            throw new RuntimeException(e);
	        }
	        String serializedConfig = new String(new Base64u().encode(bos.toByteArray())); 

	        System.out.println("serializedConfig: "+serializedConfig);
	        return serializedConfig;
	 }
	 public static Map<String,String> deserialize(String s){
		 Map<String,String> values = new HashMap<String,String>();
		 byte[] b = new Base64u().decode(s);
		 ByteArrayInputStream bis = new ByteArrayInputStream(b);
		 try {
			ObjectInputStream in = new ObjectInputStream(bis);
			values = (Map<String,String>) in.readObject();
			System.out.println("values =   "+values);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return values;
	 }
	 public static Map<String,List> deserializeList(String s){
		 Map<String,List> values = new HashMap<String,List>();
		 byte[] b = new Base64u().decode(s);
		 ByteArrayInputStream bis = new ByteArrayInputStream(b);
		 try {
			ObjectInputStream in = new ObjectInputStream(bis);
			values = (Map<String,List>) in.readObject();
			System.out.println("values =   "+values);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return values;
	 }
	 public static String getSerializedConfig(Map<String, Object> props) {
	        String res = null;
	        String resList = null;
	        Map<String, String> config = new HashMap<String, String>();
	        // pick only those keys in fieldMetas
	        Map<String,List<Map<String,String>>> configList = new HashMap<String,List<Map<String,String>>>();
	        for(String key:props.keySet() ) {
	            Object value = props.get(key);
	            if(value instanceof List){
	            	configList.put(key,(List<Map<String,String>>)value);
	            	resList = serializeList(configList);
	            }else{
	            	System.out.println("getSerializedConfig: key="+key+", value="+value);
	            	config.put(key, value==null? null: value.toString());
	            }
	        }
	        res = serialize(config);
	        return res + "#" + resList;
	    }
	    
}
