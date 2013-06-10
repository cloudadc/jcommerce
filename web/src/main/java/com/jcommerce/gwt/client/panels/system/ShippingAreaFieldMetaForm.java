package com.jcommerce.gwt.client.panels.system;

import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IShippingAreaFieldMeta;

public class ShippingAreaFieldMetaForm extends BeanObject implements IShippingAreaFieldMeta {
    public String getLable() {
        return get(LABEL);
    }
    public void setLable(String label) {
        set(LABEL, label);
    }
	

}
