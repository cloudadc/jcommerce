package com.jcommerce.gwt.client.panels.system;

import java.util.Map;

import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IShippingArea;
import com.jcommerce.gwt.client.model.IShippingAreaMeta;

public class ShippingAreaMetaForm extends BeanObject implements IShippingArea, IShippingAreaMeta{
	
	public String getName() {
		return get(IShippingArea.NAME);
	}
	public void setName(String name) {
		set(IShippingArea.NAME, name);
	}
	public String getShipping(){
		return get(SHIPPING);
	}
	public void setShipping(String id) {
		set(SHIPPING, id);
	}
	public String getPkId() {
		return get(ID);
	}
	public void setId(String pkId) {
		set(ID, pkId);
	}
	
    public Map<String, ShippingAreaFieldMetaForm> getFieldMetas() {
        return (Map<String, ShippingAreaFieldMetaForm>)get(FIELDMETAS);
    }

    public void setFieldMetas(Map<String, ShippingAreaFieldMetaForm> fieldMetas) {
        set(FIELDMETAS, fieldMetas);
    }

    public Map<String, String> getFieldValues() {
        return (Map<String, String>)get(FIELDVALUES);
    }

    public void setFieldValues(Map<String, String> fieldValues) {
        set(FIELDVALUES, fieldValues);
    }
}
