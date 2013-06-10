package com.jcommerce.core.service.payment.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jcommerce.core.model.Order;
import com.jcommerce.core.model.OrderGoods;
import com.jcommerce.core.model.Payment;
import com.jcommerce.core.payment.IPaymentMetaPlugin;
import com.jcommerce.core.payment.PaymentConfigFieldMeta;
import com.jcommerce.core.service.payment.PaymentConfigMeta;

public class AliPay extends BasePaymentMetaPlugin implements IPaymentMetaPlugin {
	
	private static final PaymentConfigMeta defaultPaymentConfigMeta;
    private static final Map<String, String> defaultConfigValues;
    private static final Map<String, PaymentConfigFieldMeta> fieldMetas;
    public static final String IS_INSTANCE = "is_instant";
    public static final String REAL_METHOD = "alipay_real_method";
    public static final String VIRTUAL_METHOD = "alipay_virtual_method";
    public static final String PARTNER = "alipay_partner";
    public static final String ACCOUNT = "alipay_account";
    public static final String KEY = "alipay_key";
    
    // agent code for gcshop
    public static final String AGENT="C4335319945672464113";
    
    @Override
    public Map<String, PaymentConfigFieldMeta> getFieldMetas() {
    	return fieldMetas;
    }
    
    static {
        defaultConfigValues = new HashMap<String, String>();
        defaultConfigValues.put(IS_INSTANCE, "0");
        defaultConfigValues.put(REAL_METHOD, "0");
        defaultConfigValues.put(VIRTUAL_METHOD, "0");
        defaultConfigValues.put(PARTNER, "");
        defaultConfigValues.put(ACCOUNT, "");
        defaultConfigValues.put(KEY, "");
        
        fieldMetas = new HashMap<String, PaymentConfigFieldMeta>();
        
        PaymentConfigFieldMeta m = null;
        Map<String, String> options = null;
        
        m = new PaymentConfigFieldMeta();
        m.setLable("是否开通即时到帐");
        options = new HashMap<String, String>();
        options.put("0", "未开通");
        options.put("1", "已开通");
        m.setOptions(options);
        m.setTip("即时到帐功能默认未开通，当您确认您的帐号已经开通该功再选择已开通。当你选择未开通时，所有交易使用普通实体商品接口");
        fieldMetas.put(IS_INSTANCE, m);
        
        m = new PaymentConfigFieldMeta();
        m.setLable("选择实体商品接口");
        options = new HashMap<String, String>();
        options.put("0", "使用普通实物商品交易接口");
        options.put("1", "使用即时到帐交易接口");
        m.setOptions(options);
        m.setTip("您可以选择支付时采用的接口类型，不过这和支付宝的帐号类型有关，具体情况请咨询支付宝");
        fieldMetas.put(REAL_METHOD, m);
        
        m = new PaymentConfigFieldMeta();
        m.setLable("选择虚拟商品接口");
        options = new HashMap<String, String>();
        options.put("0", "使用普通虚拟商品交易接口");
        options.put("1", "使用即时到帐交易接口");
        m.setOptions(options);
        m.setTip("您可以选择支付时采用的接口类型，不过这和支付宝的帐号类型有关，具体情况请咨询支付宝");
        fieldMetas.put(VIRTUAL_METHOD, m);
        
        
        m = new PaymentConfigFieldMeta();
        m.setLable("合作者身份ID");
        m.setOptions(null);
        m.setTip(null);
        fieldMetas.put(PARTNER, m);
        
        m = new PaymentConfigFieldMeta();
        m.setLable("支付宝帐户");
        m.setOptions(null);
        m.setTip(null);
        fieldMetas.put(ACCOUNT, m);
        
        m = new PaymentConfigFieldMeta();
        m.setLable("交易安全校验码");
        m.setOptions(null);
        m.setTip(null);
        fieldMetas.put(KEY, m);
        
        defaultPaymentConfigMeta = new PaymentConfigMeta();
        defaultPaymentConfigMeta.setCode("alyPay");
        defaultPaymentConfigMeta.setName("支付宝");
        defaultPaymentConfigMeta.setFee("10");
        defaultPaymentConfigMeta.setOnline(true);
        defaultPaymentConfigMeta.setCod(false);
        defaultPaymentConfigMeta.setDescription("支付宝，是支付宝公司针对网上交易而特别推出的安全付款服务.<br/><a href=\"https://www.alipay.com/himalayas/market.htm?type=from_agent_contract&id=C4335319945672464113\" target=\"_blank\"><font color=\"red\">点此申请免费签约接口</font></a><br/><a href=\"https://www.alipay.com/himalayas/market.htm?type=from_agent_contract&id=C4335319945674798119\" target=\"_blank\"><font color=\"red\">点此申请预付费签约接口(600包4.2万、1800包18万交易额度)</font></a>");
        defaultPaymentConfigMeta.setConfig(getDefaultConfig());
//        defaultPaymentConfigMeta.setKeyName("_pay1");
    }
    public PaymentConfigMeta getDefaultConfigMeta() {
    	return defaultPaymentConfigMeta;
    }
    
    public AliPay() {
    }

//    public String getCode() {
//        return "AlyPay";
//    }
//
//    public String getName() {
//        return "支付宝";
//    }
//    // 支付手续费
//    public String getPayFee() {
//        return "10";
//    }
//    
//    // 在线支付
//    public Boolean isOnline() {
//        return true;
//    }
//    // 货到付款
//    public Boolean isCod() {
//        return false;
//    }
//
//    public String getDescription() {
//        return "支付宝，是支付宝公司针对网上交易而特别推出的安全付款服务.<br/><a href=\"https://www.alipay.com/himalayas/market.htm?type=from_agent_contract&id=C4335319945672464113\" target=\"_blank\"><font color=\"red\">点此申请免费签约接口</font></a><br/><a href=\"https://www.alipay.com/himalayas/market.htm?type=from_agent_contract&id=C4335319945674798119\" target=\"_blank\"><font color=\"red\">点此申请预付费签约接口(600包4.2万、1800包18万交易额度)</font></a>";
//    }

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
     * @see com.jcommerce.core.service.payment.IPaymentMetaPlugin#getCode(com.jcommerce.core.model.Order, com.jcommerce.core.model.Payment)
     */
    public String getCode(Order order, Payment payment , List<OrderGoods> orderGoods) { 
        PaymentConfigMeta configMeta = deserializeConfig(payment.getConfig());
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
        // 合作伙伴交易号（确保在合作伙伴系统中唯一）， order.getPayLog().getPkId()
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
    
    private static String getDefaultConfig() {
        return serialize(defaultConfigValues);
    }
}
