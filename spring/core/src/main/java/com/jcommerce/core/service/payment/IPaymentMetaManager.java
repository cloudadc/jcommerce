package com.jcommerce.core.service.payment;

import java.util.List;
import java.util.Map;

import com.jcommerce.core.model.Payment;

public interface IPaymentMetaManager {
    
    public String getCode(Long orderId, Long paymentId);
    
    public void install(String paymentCode);
    
    public void uninstall(Long paymentId);
    
    public PaymentConfigMeta getPaymentConfigMeta(Long paymentId);
    
    public void savePaymentConfig(Map<String, Object> props);
    
    public List<Map<String, Object>> getCombinedPaymentMetaList();
    
    public List<Payment> getPaymentList();
    
    public List<PaymentConfigMeta> getInstalledPaymentMetaList();
    
}
