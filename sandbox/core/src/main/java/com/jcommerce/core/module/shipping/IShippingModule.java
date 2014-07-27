/**
* Author: Bob Chen
*/

package com.jcommerce.core.module.shipping;

import com.jcommerce.core.module.IModule;

public interface IShippingModule extends IModule {
    public float getShippingFee(float goodsWeight, float goodsTotalMoney);
    
    public String getQueryForm(String invoice_sn);
}
