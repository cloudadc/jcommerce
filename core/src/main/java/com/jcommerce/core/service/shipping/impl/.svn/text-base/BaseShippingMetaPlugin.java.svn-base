package com.jcommerce.core.service.shipping.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;

import com.jcommerce.core.service.shipping.IShippingMetaPlugin;
import com.jcommerce.core.service.shipping.ShippingAreaFieldMeta;
import com.jcommerce.core.service.shipping.ShippingAreaMeta;

public abstract class BaseShippingMetaPlugin implements IShippingMetaPlugin {
	protected abstract Map<String, ShippingAreaFieldMeta> getFieldMetas();
	
	public ShippingAreaMeta deserializeConfig(String serializedConfig) {
		ShippingAreaMeta configMeta = new ShippingAreaMeta();
        configMeta.setFieldMetas(getFieldMetas());
        configMeta.setFieldValues(deserialize(serializedConfig));
        return configMeta;
	}

	public static Map<String, String> deserialize(String serializedConfig) {
        Map<String, String> config = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(Base64.decodeBase64(serializedConfig.getBytes()));
            ObjectInputStream ois;
            ois = new ObjectInputStream(bis);
            config = (Map<String, String>)ois.readObject();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return config;
	}


	public String serializeConfig(Map<String, Object> props) {
        String res = null;
        Map<String, String> config = new HashMap<String, String>();
        // pick only those keys in fieldMetas
        for(String key:getFieldMetas().keySet() ) {
            Object value = props.get(key);
            System.out.println("getSerializedConfig: key="+key+", value="+value);
            config.put(key, value==null? null: value.toString());
        }
        res = serialize(config);
        return res;
	}
	
	
    protected static String serialize(Map<String, String> config) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(config);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        String serializedConfig = new String(Base64.encodeBase64(bos.toByteArray())); 

        System.out.println("serializedConfig: "+serializedConfig);
        return serializedConfig;
    }
    
	protected double getDouble(String s) {
		if(s==null) {
			return 0;
		}else {
			return Double.valueOf(s);
		}
	}
}
