package com.jcommerce.core.service.shipping;

import java.util.Map;

import com.jcommerce.core.model.ShippingArea;

public class ShippingAreaMeta extends ShippingArea {
	
    private Map<String, ShippingAreaFieldMeta> fieldMetas;
    private Map<String, String> fieldValues;
	public Map<String, ShippingAreaFieldMeta> getFieldMetas() {
		return fieldMetas;
	}
	public void setFieldMetas(Map<String, ShippingAreaFieldMeta> fieldMetas) {
		this.fieldMetas = fieldMetas;
	}
	public Map<String, String> getFieldValues() {
		return fieldValues;
	}
	public void setFieldValues(Map<String, String> fieldValues) {
		this.fieldValues = fieldValues;
	}
}
