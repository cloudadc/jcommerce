package com.jcommerce.core.service.payment.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jcommerce.core.model.Order;
import com.jcommerce.core.model.OrderGoods;
import com.jcommerce.core.model.Payment;
import com.jcommerce.core.payment.IPaymentMetaPlugin;
import com.jcommerce.core.payment.PaymentConfigFieldMeta;
import com.jcommerce.core.service.payment.PaymentConfigMeta;

public class Paypal extends BasePaymentMetaPlugin implements IPaymentMetaPlugin {
	
	private static final PaymentConfigMeta defaultPaymentConfigMeta;
    private static final Map<String, String> defaultConfigValues;
    private static final Map<String, PaymentConfigFieldMeta> fieldMetas;
    public static final String BUTTON_TEXT = "button_text";
    public static final String PAY_DESC = "paypal_desc";
    public static final String CURRENCY = "paypal_currency";
    public static final String PAY_NAME = "pay_name";
    public static final String ACCOUNT = "paypal_account";
    
    
    @Override
    protected Map<String, PaymentConfigFieldMeta> getFieldMetas() {
    	return fieldMetas;
    }
    static {
        defaultConfigValues = new HashMap<String, String>();
        defaultConfigValues.put(PAY_NAME, "PayPal");
        defaultConfigValues.put(ACCOUNT, "jcommerce.test@gmail.com");
        defaultConfigValues.put(BUTTON_TEXT, "Pay Now!");
        defaultConfigValues.put(CURRENCY, "USD");
        
        fieldMetas = new HashMap<String, PaymentConfigFieldMeta>();
        
        PaymentConfigFieldMeta m = null;
        Map<String, String> options = null;
        
       
        m = new PaymentConfigFieldMeta();
        m.setLable("Paypal Account");
        m.setOptions(null);
        m.setTip(null);
        fieldMetas.put(ACCOUNT, m);
        
        
        m = new PaymentConfigFieldMeta();
        m.setLable("Currency Code");
        options = new HashMap<String, String>();
        options.put("AUD", "AUD");
        options.put("CAD", "CAD");
        options.put("EUR", "EUR");
        options.put("GBP", "GBP");
        options.put("JPY", "JPY");
        options.put("USD", "USD");
        options.put("HKD", "HKD");
        m.setOptions(options);
        m.setTip(null);
        fieldMetas.put(CURRENCY, m);
        
        
        m = new PaymentConfigFieldMeta();
        m.setLable("Button Text");
        m.setOptions(null);
        m.setTip(null);
        fieldMetas.put(BUTTON_TEXT, m);
        
        defaultPaymentConfigMeta = new PaymentConfigMeta();
        defaultPaymentConfigMeta.setCode("Paypal");
        defaultPaymentConfigMeta.setName("Paypal");
        defaultPaymentConfigMeta.setFee("0");
        defaultPaymentConfigMeta.setOnline(true);
        defaultPaymentConfigMeta.setCod(false);
        defaultPaymentConfigMeta.setDescription("PayPal (http://www.paypal.com) test account");
        defaultPaymentConfigMeta.setConfig(getDefaultConfig());
//        defaultPaymentConfigMeta.setKeyName("_pay5");
    }
    public PaymentConfigMeta getDefaultConfigMeta() {
    	return defaultPaymentConfigMeta;
    }
    public Paypal() {
        super();
    }
    
    public String getCode(Order order, Payment payment , List<OrderGoods> orderGoods) { 
        PaymentConfigMeta configMeta = deserializeConfig(payment.getConfig());
        Map<String, String> values = configMeta.getFieldValues();
        
        String dataOrderId = order.getId();
        Double dataAmount = order.getGoodsAmount();
        String url = "";
        String dataPayAccount = values.get(ACCOUNT);
        String payCurrencyCode = values.get(CURRENCY);
        String dataNotifyUrl = "";
      
        StringBuffer buf = new StringBuffer();
        buf.append("<br /><form style='text-align:center;' action='https://www.paypal.com/cgi-bin/webscr' method='post'>");
        buf.append("<input type='hidden' name='cmd' value='_xclick'>");
        buf.append("<input type='hidden' name='business' value='").append(dataPayAccount).append("'>");
        buf.append("<input type='hidden' name='return' value='").append(url).append("'>");
        buf.append("<input type='hidden' name='amount' value='").append(dataAmount).append("'>");
        buf.append("<input type='hidden' name='invoice' value='").append(dataOrderId).append("'>");
        buf.append("<input type='hidden' name='charset' value='").append("UTF-8").append("'>");
        buf.append("<input type='hidden' name='no_shipping' value='1'>");
        buf.append("<input type='hidden' name='no_note' value=''>");
        buf.append("<input type='hidden' name='currency_code' value='").append(payCurrencyCode).append("'>");
        buf.append("<input type='hidden' name='notify_url' value='").append(dataNotifyUrl).append("'>");
        buf.append("<input type='hidden' name='item_name' value='").append(order.getSN()).append("'>");
        buf.append("<input type='submit' value='").append(values.get(BUTTON_TEXT)).append("'>");
        return buf.toString();
        
    }
    

    private static String getDefaultConfig() {
        return serialize(defaultConfigValues);
    }



}
