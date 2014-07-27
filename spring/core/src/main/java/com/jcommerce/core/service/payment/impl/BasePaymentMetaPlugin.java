package com.jcommerce.core.service.payment.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;

import com.jcommerce.core.payment.IPaymentMetaPlugin;
import com.jcommerce.core.payment.PaymentConfigFieldMeta;
import com.jcommerce.core.service.payment.PaymentConfigMeta;

public abstract class BasePaymentMetaPlugin implements IPaymentMetaPlugin {

	protected abstract Map<String, PaymentConfigFieldMeta> getFieldMetas();
	
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
    
    public PaymentConfigMeta deserializeConfig(String serializedConfig) {
        PaymentConfigMeta configMeta = new PaymentConfigMeta();
        configMeta.setFieldMetas(getFieldMetas());

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
        
        configMeta.setFieldValues(config);
        
        return configMeta;
    }


}
