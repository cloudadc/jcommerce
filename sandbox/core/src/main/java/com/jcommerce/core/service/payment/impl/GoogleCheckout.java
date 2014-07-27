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

public class GoogleCheckout extends BasePaymentMetaPlugin implements IPaymentMetaPlugin {
	
	private static final PaymentConfigMeta defaultPaymentConfigMeta;
    private static final Map<String, String> defaultConfigValues;
    private static final Map<String, PaymentConfigFieldMeta> fieldMetas;
    public static final String KEY = "alipay_key";
    
    // agent code for gcshop
    public static final String MERCHANT_ID="726838326316485";
    
    @Override
    protected Map<String, PaymentConfigFieldMeta> getFieldMetas() {
    	return fieldMetas;
    }
    static {
        defaultConfigValues = new HashMap<String, String>();
        defaultConfigValues.put(KEY, MERCHANT_ID);
        
        fieldMetas = new HashMap<String, PaymentConfigFieldMeta>();
        
        PaymentConfigFieldMeta m = null;
        Map<String, String> options = null;
        
        m = new PaymentConfigFieldMeta();
        m.setLable("Checkout账号");
        m.setOptions(null);
        m.setTip(null);
        fieldMetas.put(KEY, m);
        
        defaultPaymentConfigMeta = new PaymentConfigMeta();
        defaultPaymentConfigMeta.setCode("checkout");
        defaultPaymentConfigMeta.setName("Google Checkout");
        defaultPaymentConfigMeta.setFee("2.2%");
        defaultPaymentConfigMeta.setOnline(true);
        defaultPaymentConfigMeta.setCod(false);
        defaultPaymentConfigMeta.setDescription("Google Checkout test account");
        defaultPaymentConfigMeta.setConfig(getDefaultConfig());
//        defaultPaymentConfigMeta.setKeyName("_pay3");
    }
    public PaymentConfigMeta getDefaultConfigMeta() {
    	return defaultPaymentConfigMeta;
    }
    public GoogleCheckout() {
        super();
    }

    public String getCode(Order order, Payment payment , List<OrderGoods> orderGoods) { 
        PaymentConfigMeta configMeta = deserializeConfig(payment.getConfig());
        Map<String, String> values = configMeta.getFieldValues();

        StringBuffer buf = new StringBuffer();
        buf.append("<form method=\"POST\" action=\"https://checkout.google.com/api/checkout/v2/checkoutForm/Merchant/"+MERCHANT_ID+"\" accept-charset=\"utf-8\">");
        int i = 1;
        for(OrderGoods goods : orderGoods){
        	
        	buf.append("<input name=\"item_name_" + i + "\" type=\"hidden\" value=\"" + goods.getGoodsName() + "\"/>");
        	buf.append("<input name=\"item_description_" + i + "\" type=\"hidden\" value=\"" + goods.getGoodsName() + "\"/>");
        	buf.append("<input name=\"item_quantity_" + i + "\" type=\"hidden\" value=\"" + goods.getGoodsNumber() + "\"/>");
        	buf.append("<input name=\"item_price_" + i + "\" type=\"hidden\" value=\"" + goods.getGoodsPrice() + "\"/>");
        	i++;
        }
//        buf.append("<input name=\"item_name_" + i + "\" type=\"hidden\" value=\"" + order.getShippingName() + "\"/>");
//    	buf.append("<input name=\"item_description_" + i + "\" type=\"hidden\" value=\"" + order.getShippingName() + "\"/>");
//    	buf.append("<input name=\"item_quantity_" + i + "\" type=\"hidden\" value=\"" + 1 + "\"/>");
//    	buf.append("<input name=\"item_price_" + i + "\" type=\"hidden\" value=\"" + order.getShippingFee() + "\"/>");
    	
        buf.append("<input name=\"_charset_\" type=\"hidden\" value=\"utf-8\"/>");
        buf.append("<input type=\"image\" name=\"Google Checkout\" alt=\"Fast checkout through Google\" src=\"http://checkout.google.com/buttons/checkout.gif?merchant_id=726838326316485&w=180&h=46&style=white&variant=text&loc=en_US\" height=\"46\" width=\"180\" />");
        buf.append("</form>");
        return buf.toString();
        
    }
    

    private static String getDefaultConfig() {
        return serialize(defaultConfigValues);
    }



}
