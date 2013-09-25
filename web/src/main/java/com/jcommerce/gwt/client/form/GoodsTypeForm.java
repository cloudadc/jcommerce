package com.jcommerce.gwt.client.form;

import java.util.Map;

import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.i18n.client.Messages;
import com.jcommerce.gwt.client.ValidationException;
import com.jcommerce.gwt.client.model.IGoodsType;
import com.jcommerce.gwt.client.resources.Resources;

public class GoodsTypeForm extends BeanObject implements IGoodsType {

	private static final long serialVersionUID = -7786244549930241230L;

	public GoodsTypeForm() {
		super();
	}
	public GoodsTypeForm (String modelName, Map<String, Object> values) {
		super(modelName, values);
	}
	

	
	public interface GoodsTypeFormMessage extends Messages {
		
	}
	
	public static TextField<String> getNameField(String fieldTitle) {
		TextField<String> nameField = new TextField<String>();
		nameField.setName(NAME);
		nameField.setMaxLength(50);
		nameField.setAutoValidate(true);
		nameField.setAllowBlank(false);
		TextField<String>.TextFieldMessages tfm = nameField.new TextFieldMessages();
		tfm.setBlankText(Resources.messages.blankText(fieldTitle));
		nameField.setMessages(tfm);
		return nameField;
	}
	
	public static TextArea getAttributeGroupField(String fieldTitle) {
		TextArea agField = new TextArea();
		agField.setName(ATTRIBUTEGROUP);
		agField.setMaxLength(2000);
		agField.setAutoValidate(true);
		agField.setAllowBlank(true);
		return agField;
	}
	
    public void validate() throws ValidationException{
    	System.out.println("----validate GoodsTypeForm");
    	StringBuffer errorBuf = new StringBuffer();
    	
    	String name = getString(NAME);
    	System.out.println("name: "+name+", size: "+(name==null? -1:name.length()));
    	
    	if(name == null || name.trim().length() == 0) {
    		errorBuf.append("商品类型名称不能为空").append("\r\n");
    		
    	}


    	if(errorBuf.length()>0) {
    		throw new ValidationException(errorBuf.toString().trim());
    	}
    }
}
