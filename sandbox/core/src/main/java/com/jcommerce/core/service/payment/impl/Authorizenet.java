package com.jcommerce.core.service.payment.impl;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.jcommerce.core.model.Order;
import com.jcommerce.core.model.OrderGoods;
import com.jcommerce.core.model.Payment;
import com.jcommerce.core.payment.IPaymentMetaPlugin;
import com.jcommerce.core.payment.PaymentConfigFieldMeta;
import com.jcommerce.core.service.payment.PaymentConfigMeta;

public class Authorizenet extends BasePaymentMetaPlugin implements IPaymentMetaPlugin {
	
	private static final PaymentConfigMeta defaultPaymentConfigMeta;
    private static final Map<String, String> defaultConfigValues;
    private static final Map<String, PaymentConfigFieldMeta> fieldMetas;
    public static final String API_LOGIN_ID = "x_login";
    public static final String TRANSACTION_KEY = "x_tran_key";
    public static final String VIRTUAL_METHOD = "alipay_virtual_method";
    public static final String PARTNER = "alipay_partner";
    public static final String CARD_NUM = "x_card_num";
    public static final String LABEL = "label";
    public static final String TEST_MODE = "testMode";
    public static final String URL = "https://test.authorize.net/gateway/transact.dll";
    
    // agent code for gcshop
    public static final String AGENT="C4335319945672464113";
    
    @Override
    protected Map<String, PaymentConfigFieldMeta> getFieldMetas() {
    	return fieldMetas;
    }
    static {
        defaultConfigValues = new HashMap<String, String>();
        defaultConfigValues.put(API_LOGIN_ID, "3x2j2YJ2cz");
        defaultConfigValues.put(TRANSACTION_KEY, "38v5R22X924uHzMc");
        defaultConfigValues.put(LABEL, "Submit Payment Now!!!");
        defaultConfigValues.put(URL, "Submit Payment");
        defaultConfigValues.put(TEST_MODE, "false");
//        defaultConfigValues.put ("x_version", "3.1");
//        defaultConfigValues.put ("x_delim_data", "TRUE");
//        defaultConfigValues.put ("x_delim_char", "|");
//        defaultConfigValues.put ("x_relay_response", "FALSE");
//        defaultConfigValues.put ("x_type", "AUTH_CAPTURE");//The type of credit card transaction    format: AUTH_CAPTURE (default), AUTH_ONLY
//        defaultConfigValues.put ("x_method", "CC");
//        defaultConfigValues.put ("x_card_num", "4111111111111111");
//        defaultConfigValues.put ("x_exp_date", "0115");
        
        
        fieldMetas = new HashMap<String, PaymentConfigFieldMeta>();
        
        PaymentConfigFieldMeta m = null;
        Map<String, String> options = null;
        
        
        m = new PaymentConfigFieldMeta();
        m.setLable("API Login ID");
        m.setOptions(null);
        m.setTip(null);
        fieldMetas.put(API_LOGIN_ID, m);
        
        m = new PaymentConfigFieldMeta();
        m.setLable("Transaction Key");
        m.setOptions(null);
        m.setTip(null);
        fieldMetas.put(TRANSACTION_KEY, m);
        
        m = new PaymentConfigFieldMeta();
        m.setLable("Please choose payment environment");
        options = new HashMap<String, String>();
        options.put("https://test.authorize.net/gateway/transact.dll", "live environment");
        options.put("https://secure.authorize.net/gateway/transact.dll", "test environment");
        m.setOptions(options);
        m.setTip("You can choose the payment environment, but this depends on the type of your Authorize.Net Account. For detail please consult Authorize.Net");
        fieldMetas.put(URL, m);
        
        m = new PaymentConfigFieldMeta();
        m.setLable("Use test mode");
        options = new HashMap<String, String>();
        options.put("true", "Test Mode open");
        options.put("false", "Test Mode close");
        m.setOptions(options);
        m.setTip("Only real account could choose to enable test mode. It is by default disabled. For detail please consult Authorize.Net");
        fieldMetas.put(TEST_MODE, m);
        
        
        
        m = new PaymentConfigFieldMeta();
        m.setLable("Submit button text");
        m.setOptions(null);
        m.setTip(null);
        fieldMetas.put(LABEL, m);
        
        defaultPaymentConfigMeta = new PaymentConfigMeta();
        defaultPaymentConfigMeta.setCode("authorizenet");
        defaultPaymentConfigMeta.setName("Authorize.net");
        defaultPaymentConfigMeta.setFee("40");
        defaultPaymentConfigMeta.setOnline(true);
        defaultPaymentConfigMeta.setCod(false);
        defaultPaymentConfigMeta.setDescription(getDescription());
        defaultPaymentConfigMeta.setConfig(getDefaultConfig());
//        defaultPaymentConfigMeta.setKeyName("_pay4");
    }
    public PaymentConfigMeta getDefaultConfigMeta() {
    	return defaultPaymentConfigMeta;
    }
    public Authorizenet() {
        super();
    }


    public static String getDescription() {
        return "Global payment solution. see http://www.authorize.net/\r\nThis is test account";
    }


    public String getCode(Order order, Payment payment , List<OrderGoods> orderGoods) { 
        PaymentConfigMeta configMeta = deserializeConfig(payment.getConfig());
        Map<String, String> values = configMeta.getFieldValues();
        
     // an invoice is generated using the date and time 
        Date myDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String invoice = dateFormat.format(myDate);
        // a sequence number is randomly generated
        Random generator = new Random();
        int sequence = generator.nextInt(1000);
        // a timestamp is generated
        long timeStamp = System.currentTimeMillis()/1000;

        //This section uses Java Cryptography functions to generate a fingerprint
        // First, the Transaction key is converted to a "SecretKey" object
        String fingerprint = null;
        try {
			KeyGenerator kg = KeyGenerator.getInstance("HmacMD5");
			
		
        	SecretKey key = new SecretKeySpec(values.get(TRANSACTION_KEY).getBytes(), "HmacMD5");
        	// A MAC object is created to generate the hash using the HmacMD5 algorithm
        	Mac mac = Mac.getInstance("HmacMD5");
        	mac.init(key);
        	String inputstring = values.get(API_LOGIN_ID) + "^" + sequence + "^" + timeStamp + "^" + order.getOrderAmount() + "^";
        	byte[] result = mac.doFinal(inputstring.getBytes());
        	// Convert the result from byte[] to hexadecimal format
        	StringBuffer strbuf = new StringBuffer(result.length * 2);
        	for(int i=0; i< result.length; i++)
        	{
        		if(((int) result[i] & 0xff) < 0x10)
        			strbuf.append("0");
        		strbuf.append(Long.toString((int) result[i] & 0xff, 16));
        	}
        	fingerprint = strbuf.toString();
        // end of fingerprint generation
        } catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        // Create the HTML form containing necessary SIM post values
        StringBuffer buf = new StringBuffer();
        buf.append("<FORM method='post' action='" + URL + "' target='_blank' >");
    	// Additional fields can be added here as outlined in the SIM integration guide
    	// at: http://developer.authorize.net
        buf.append ("	<INPUT type='hidden' name='x_login' value='" + values.get(API_LOGIN_ID) + "' />");
        buf.append ("	<INPUT type='hidden' name='x_amount' value='" + order.getOrderAmount() + "' />");
        buf.append ("	<INPUT type='hidden' name='x_description' value='" + order.getPayName() + "' />");
        buf.append ("	<INPUT type='hidden' name='x_invoice_num' value='" + invoice + "' />");
        buf.append ("	<INPUT type='hidden' name='x_fp_sequence' value='" + sequence + "' />");
        buf.append ("	<INPUT type='hidden' name='x_fp_timestamp' value='" + timeStamp + "' />");
        buf.append ("	<INPUT type='hidden' name='x_fp_hash' value='" + fingerprint + "' />");
        buf.append ("	<INPUT type='hidden' name='x_test_request' value='" + TEST_MODE + "' />");
        buf.append ("	<INPUT type='hidden' name='x_show_form' value='PAYMENT_FORM' />");
        buf.append ("	<input type='submit' value='" + values.get(LABEL) + "' />");
        buf.append ("</FORM>");
        
        
        return buf.toString();
        
    }
    

    private static String getDefaultConfig() {
        return serialize(defaultConfigValues);
    }



}
