package com.jcommerce.gwt.client.form;


public class SimpleOptionData extends BeanObject {
	public static final String TEXT = "text";
	public static final String VALUE = "value";
	
	public SimpleOptionData(String text, Object value) {
		setText(text);
		setValue(value);
	}
	
	public String getText() {
		return get(TEXT);
	}
	public void setText(String text) {
		set(TEXT, text);	
	}
	public String getValue() {
		return get(VALUE);
	}
	public void setValue(Object value) {
		set(VALUE, value);
	}
	
	
}
