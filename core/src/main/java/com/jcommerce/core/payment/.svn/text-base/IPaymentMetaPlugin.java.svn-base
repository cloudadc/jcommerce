package com.jcommerce.core.payment;

import java.util.List;
import java.util.Map;

import com.jcommerce.core.model.Order;
import com.jcommerce.core.model.OrderGoods;
import com.jcommerce.core.model.Payment;
import com.jcommerce.core.service.payment.PaymentConfigMeta;

public interface IPaymentMetaPlugin {
    
//    public String getCode();
//    public String getName();
//    public String getPayFee();
//    public Boolean isCod();
//    public Boolean isOnline();
//    public String getDescription(); 
    
	public PaymentConfigMeta getDefaultConfigMeta();
    public String getCode(Order order, Payment payment , List<OrderGoods> orderGoods);
//    public String getDefaultConfig();
    public String serializeConfig(Map<String, Object> props);
    // key -> value
    // lable -> key -> type -> (options)
    
    public PaymentConfigMeta deserializeConfig(String serializedConfig);
    
    
}
