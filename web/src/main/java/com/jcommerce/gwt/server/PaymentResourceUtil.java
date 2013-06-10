/**
 * Author: Kylin Soong
 */

package com.jcommerce.gwt.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jcommerce.core.module.ModuleManager;
import com.jcommerce.core.module.payment.IPaymentModule;
import com.jcommerce.core.module.shipping.IShippingModule;
import com.jcommerce.core.payment.IPaymentMetaPlugin;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IPayment;

public class PaymentResourceUtil {
	
	private static List<BeanObject> payments = null;

	public static List<BeanObject> getPaymentBeanObjects() {
		
		if(payments == null) {
			payments = new ArrayList<BeanObject>();
			Map<String, Object> maps = new HashMap<String, Object>();
			MetaPluginManager manager = new MetaPluginManager();
			List<IPaymentMetaPlugin> lists = manager.getPayments();
			int id = 1000;
			for(IPaymentMetaPlugin module : lists){
			
				maps.put(IPayment.ID, ++id);
				
//				maps.put(IPayment.CODE, module.getCode());
//				
//				maps.put(IPayment.NAME, module.getName());
//				
//				maps.put(IPayment.DESCRIPTION, module.getDescription());
//				
//				maps.put(IPayment.CONFIG, module.getDefaultConfig());
//				
//				maps.put(IPayment.FEE, module.getPayFee());
//				
//				maps.put(IPayment.COD, module.isCod());
//				
//				maps.put(IPayment.ONLINE, module.isOnline());
				
				payments.add(new BeanObject(ModelNames.PAYMENT,maps));
				
			}
			
		}
		
		return payments;
	}

}
