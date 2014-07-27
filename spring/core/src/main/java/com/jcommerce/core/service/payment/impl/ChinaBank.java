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
import com.jcommerce.core.util.MD5;

public class ChinaBank extends BasePaymentMetaPlugin implements IPaymentMetaPlugin {
	private static final PaymentConfigMeta defaultPaymentConfigMeta;
    private static final Map<String, String> defaultConfigValues;
    private static final Map<String, PaymentConfigFieldMeta> fieldMetas;
    public static final String ACCOUNT = "chinabank_account";
    public static final String KEY = "chinabank_key";

    public static final String URL = "https://pay3.chinabank.com.cn/PayGate";
    
    @Override
    protected Map<String, PaymentConfigFieldMeta> getFieldMetas() {
    	return fieldMetas;
    }
    static {
        defaultConfigValues = new HashMap<String, String>();
        defaultConfigValues.put(ACCOUNT, "21040782");
        defaultConfigValues.put(KEY, "aaaaabbbbbccccc");
        
        fieldMetas = new HashMap<String, PaymentConfigFieldMeta>();
        PaymentConfigFieldMeta m = null;
        
        m = new PaymentConfigFieldMeta();
        m.setLable("商户编号");
        m.setOptions(null);
        m.setTip(null);
        fieldMetas.put(ACCOUNT, m);
        
        m = new PaymentConfigFieldMeta();
        m.setLable("MD5 密钥");
        m.setOptions(null);
        m.setTip(null);
        fieldMetas.put(KEY, m);
        
        defaultPaymentConfigMeta = new PaymentConfigMeta();
        defaultPaymentConfigMeta.setCode("ChinaBank");
        defaultPaymentConfigMeta.setName("网银在线");
        defaultPaymentConfigMeta.setFee("2.5%");
        defaultPaymentConfigMeta.setOnline(true);
        defaultPaymentConfigMeta.setCod(false);
        defaultPaymentConfigMeta.setDescription("网银在线与中国工商银行、招商银行、中国建设银行、农业银行、民生银行等数十家金融机构达成协议。全面支持全国19家银行的信用卡及借记卡实现网上支付。（网址：http://www.chinabank.com.cn）");
        defaultPaymentConfigMeta.setConfig(getDefaultConfig());
//        defaultPaymentConfigMeta.setKeyName("_pay2");
    }
    public PaymentConfigMeta getDefaultConfigMeta() {
    	return defaultPaymentConfigMeta;
    }
    
    public ChinaBank() {
        super();
        // TODO Auto-generated constructor stub
    }

//    public String getCode() {
//        return "ChinaBank";
//    }
//
//    public String getName() {
//        return "网银在线";
//    }
//
//    public String getPayFee() {
//        return "2.5%";
//    }
//    public Boolean isOnline() {
//        return true;
//    }
//    public Boolean isCod() {
//        return false;
//    }
//
//    public String getDescription() {
//        return "网银在线与中国工商银行、招商银行、中国建设银行、农业银行、民生银行等数十家金融机构达成协议。全面支持全国19家银行的信用卡及借记卡实现网上支付。（网址：http://www.chinabank.com.cn）";
//    }

    
    /*
     *  (non-Javadoc)
<form style=\"text-align:center;\" method=post action=\"https://pay3.chinabank.com.cn/PayGate\" target=\"_blank\">
<input type=HIDDEN name='v_mid' value='1232132'>
<input type=HIDDEN name='v_oid' value='2008123009729'>
<input type=HIDDEN name='v_amount' value='2871.79'>
<input type=HIDDEN name='v_moneytype'  value='CNY'>
<input type=HIDDEN name='v_url'  value='http://localhost/respond.php?code=chinabank'>
<input type=HIDDEN name='v_md5info' value='2962934934F215BB2D86AF4A265BE7E3'>
<input type=HIDDEN name='remark1' value=''>
<input type=submit value='立即使用网银在线支付'>
</form>
  
     * @see com.jcommerce.core.service.payment.IPaymentMetaPlugin#getCode(com.jcommerce.core.model.Order, com.jcommerce.core.model.Payment)
     */
    
    public String getCode(Order order, Payment payment , List<OrderGoods> orderGoods) {
        PaymentConfigMeta configMeta = deserializeConfig(payment.getConfig());
        Map<String, String> values = configMeta.getFieldValues();
        
        String data_vid = values.get(ACCOUNT).trim();
        String data_orderid = order.getSN();
        double data_vamount = order.getOrderAmount();
        String data_vmoneytype="CNY";
        String data_returnurl="http://localhost:8080/jcommerce/respond.do?code=chinabank";
        String data_vpaykey = values.get(KEY).trim();
        String md5 = data_vamount+data_vmoneytype+data_orderid+data_vid+data_returnurl+data_vpaykey;
        md5 = MD5.encode(md5);
        
        StringBuffer buf = new StringBuffer();
        buf.append("<form style=\"text-align:center;\" method=post action=\"")
            .append(URL).append("\" target=\"_blank\">");
        buf.append("<input type=HIDDEN name='v_mid' value='").append(data_vid).append("'>");
        buf.append("<input type=HIDDEN name='v_oid' value='").append(data_orderid).append("'>");
        buf.append("<input type=HIDDEN name='v_amount' value='").append(data_vamount).append("'>");
        buf.append("<input type=HIDDEN name='v_moneytype' value='").append(data_vmoneytype).append("'>");
        buf.append("<input type=HIDDEN name='v_url' value='").append(data_returnurl).append("'>");
        buf.append("<input type=HIDDEN name='v_md5info' value='").append(md5).append("'>");
        buf.append("<input type=HIDDEN name='remark1' value='").append("").append("'>");
        buf.append("<input type=submit value='立即使用网银在线支付'></form>");
       
        
        return buf.toString();
    }

    private static String getDefaultConfig() {
        return serialize(defaultConfigValues);
    }

}
