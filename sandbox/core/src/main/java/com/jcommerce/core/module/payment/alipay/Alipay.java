/**
* Author: Bob Chen
*/

package com.jcommerce.core.module.payment.alipay;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;

import com.jcommerce.core.model.Order;
import com.jcommerce.core.model.Payment;
import com.jcommerce.core.module.ConfigMetaData;
import com.jcommerce.core.module.FieldMetaData;
import com.jcommerce.core.module.IConfigMetaData;
import com.jcommerce.core.module.IFieldMetaData;
import com.jcommerce.core.module.payment.IPaymentModule;

public class Alipay implements IPaymentModule {
//    ResourceBundle labels = ResourceBundle.getBundle("alipay", Locale.getDefault());
    public static final String IS_INSTANCE = "is_instant";
    public static final String REAL_METHOD = "alipay_real_method";
    public static final String VIRTUAL_METHOD = "alipay_virtual_method";
    public static final String PARTNER = "alipay_partner";
    public static final String ACCOUNT = "alipay_account";
    public static final String KEY = "alipay_key";
    
    private static final Map<String, String> defaultConfigValues;
    private static final Map<String, IFieldMetaData> fieldMetas;

    // agent code for ecshop
    public static final String AGENT="C4335319945672464113";
    
    static {
        defaultConfigValues = new HashMap<String, String>();
        defaultConfigValues.put(IS_INSTANCE, "0");
        defaultConfigValues.put(REAL_METHOD, "0");
        defaultConfigValues.put(VIRTUAL_METHOD, "0");
        defaultConfigValues.put(PARTNER, "");
        defaultConfigValues.put(ACCOUNT, "");
        defaultConfigValues.put(KEY, "");
        
        fieldMetas = new HashMap<String, IFieldMetaData>();
        
        FieldMetaData m = null;
        Map<String, String> options = null;
        
        m = new FieldMetaData();
        m.setLable("是否开通即时到帐");
        options = new HashMap<String, String>();
        options.put("0", "未开通");
        options.put("1", "已开通");
        m.setOptions(options);
        m.setTip("即时到帐功能默认未开通，当您确认您的帐号已经开通该功再选择已开通。当你选择未开通时，所有交易使用普通实体商品接口");
        fieldMetas.put(IS_INSTANCE, m);
        
        m = new FieldMetaData();
        m.setLable("选择实体商品接口");
        options = new HashMap<String, String>();
        options.put("0", "使用普通实物商品交易接口");
        options.put("1", "使用即时到帐交易接口");
        m.setOptions(options);
        m.setTip("您可以选择支付时采用的接口类型，不过这和支付宝的帐号类型有关，具体情况请咨询支付宝");
        fieldMetas.put(REAL_METHOD, m);
        
        m = new FieldMetaData();
        m.setLable("选择虚拟商品接口");
        options = new HashMap<String, String>();
        options.put("0", "使用普通虚拟商品交易接口");
        options.put("1", "使用即时到帐交易接口");
        m.setOptions(options);
        m.setTip("您可以选择支付时采用的接口类型，不过这和支付宝的帐号类型有关，具体情况请咨询支付宝");
        fieldMetas.put(VIRTUAL_METHOD, m);
        
        
        m = new FieldMetaData();
        m.setLable("合作者身份ID");
        m.setOptions(null);
        m.setTip(null);
        fieldMetas.put(PARTNER, m);
        
        m = new FieldMetaData();
        m.setLable("支付宝帐户");
        m.setOptions(null);
        m.setTip(null);
        fieldMetas.put(ACCOUNT, m);
        
        m = new FieldMetaData();
        m.setLable("交易安全校验码");
        m.setOptions(null);
        m.setTip(null);
        fieldMetas.put(KEY, m);
    }
    
    public Alipay() {
    }
    
    public String getAuthor() {
        return null;
    }

    public String getCode() {
        return "alipay";
    }

    public String getDescription() {
//        return labels.getString("alipay_desc");
        return "desc";
    }

    public String getVersion() {
        return "1.0.0";
    }

    public URL getWebSite() {
        try {
            return new URL("http://www.alipay.com");
        } catch (MalformedURLException e) {
            return null;
        }
    }

    public boolean isCashOnDeliverySupported() {
        return false;
    }

    public boolean isOnlinePaymentSupported() {
        return true;
    }

    // 支付手续费
    public String getPayFee() {
        return "10";
    }
    
    /*
     *  (non-Javadoc)
_input_charset=utf-8
&agent=C4335319945672464113
&logistics_fee=0
&logistics_payment=BUYER_PAY_AFTER_RECEIVE
&logistics_type=EXPRESS
&notify_url=http%3A%2F%2Fqbd.maifou.net%2Frespond.php%3Fcode%3Dalipay
&out_trade_no=200812193564311
&partner=2088002230861529
&payment_type=1
&price=352.80
&quantity=1
&return_url=http%3A%2F%2Fqbd.maifou.net%2Frespond.php%3Fcode%3Dalipay
&seller_email=hyj_0105%40163.com
&service=create_direct_pay_by_user
&subject=2008121935643
&sign=86056fb8587d94947d89abb4d0c5a29b
&sign_type=MD5  
     * @see com.jcommerce.core.payment.IPaymentMetaPlugin#getCode(com.jcommerce.core.model.Order, com.jcommerce.core.model.Payment)
     */
    public String getPaymentCode(Order order, Payment payment) { 
        IConfigMetaData configMeta = getConfigMetaData(payment.getConfig());
        Map<String, String> values = configMeta.getFieldValues();
        
        String url = "http%3A%2F%2Fqbd.maifou.net%2Frespond.php%3Fcode%3Dalipay";
        String service = "";
        boolean isOrder = true;
        if (isOrder) {
            /* 检查订单是否全部为虚拟商品 */
            boolean allvirtual = false;
            if (!allvirtual) {
                /* 订单中存在实体商品 */
                service = "1".equals(values.get(REAL_METHOD)) ? "create_direct_pay_by_user"
                        : "trade_create_by_buyer";
            } else {
                /* 订单中全部为虚拟商品 */
                service = "1".equals(values.get(VIRTUAL_METHOD)) ? "create_direct_pay_by_user"
                        : "create_digital_goods_trade_p";
            }
        } else {
            /* 非订单方式，按照虚拟商品处理 */
            service = "1".equals(values.get(VIRTUAL_METHOD)) ? "create_direct_pay_by_user"
                    : "create_digital_goods_trade_p";
        }
        StringBuffer buf = new StringBuffer();
        buf.append("<input type=\"button\" onclick=\"window.open('");
        buf.append("https://www.alipay.com/cooperate/gateway.do?");
        buf.append("_input_charset=").append("utf-8");
        buf.append("&agent=").append(AGENT);
        buf.append("&logistics_fee=").append("0");
        buf.append("&logistics_payment=").append("BUYER_PAY_AFTER_RECEIVE");
        buf.append("&logistics_type=").append("EXPRESS");
        buf.append("&notify_url=").append(url);
        buf.append("&return_url=").append(url);
        // 合作伙伴交易号（确保在合作伙伴系统中唯一）， order.getPayLog().getId()
        buf.append("&out_trade_no=").append(
                order.getSN() + "_" + (new Date().getTime()));
        buf.append("&partner=").append(values.get(PARTNER));
        buf.append("&payment_type=").append("1");
        buf.append("&price=").append(order.getOrderAmount());
        buf.append("&quantity=").append("1");

        buf.append("&seller_email=").append(values.get(ACCOUNT));
        buf.append("&service=").append(service); // "create_direct_pay_by_user"
        buf.append("&subject=").append(order.getSN());
        buf.append("&sign=").append("86056fb8587d94947d89abb4d0c5a29b");

        buf.append("&sign_type=").append("MD5");
        buf.append("')\" value=\"立即使用支付宝支付\" />");
        return buf.toString();
        
    }
    
    public String getSerializedConfig(Map<String, Object> props) {
        String res = null;
        Map<String, String> config = new HashMap<String, String>();
        // pick only those keys in fieldMetas
        for(String key:fieldMetas.keySet() ) {
            Object value = props.get(key);
            System.out.println("getSerializedConfig: key="+key+", value="+value);
            config.put(key, value==null? null: value.toString());
        }
        res = serialize(config);
        return res;
    }
    
    private String serialize(Map<String, String> config) {
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
    public String getDefaultConfig() {
        return serialize(defaultConfigValues);
    }

    public IConfigMetaData getConfigMetaData(String serializedConfig) {
        IConfigMetaData configMeta = new ConfigMetaData();
        configMeta.setFieldMetaData(fieldMetas);

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

    public boolean isSuccess() {
        return false;
    }
}
