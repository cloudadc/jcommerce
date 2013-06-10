/**
* Author: Bob Chen
*/

package com.jcommerce.core.module.payment;

import com.jcommerce.core.model.Order;
import com.jcommerce.core.model.Payment;
import com.jcommerce.core.module.IModule;

public interface IPaymentModule extends IModule {
    
    boolean isCashOnDeliverySupported();
    
    boolean isOnlinePaymentSupported();
    
    /**
     * get payment HTML code snippet
     */
    String getPaymentCode(Order order, Payment payment);
    
//    boolean isSuccess();
}
