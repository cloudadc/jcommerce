package com.jcommerce.gwt.client.form;

import java.util.Map;

import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.form.FileUploadField;
import com.extjs.gxt.ui.client.widget.form.HiddenField;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.jcommerce.gwt.client.model.IBrand;
import com.jcommerce.gwt.client.resources.Resources;

public class BrandForm extends BeanObject implements IBrand{
    public BrandForm(){
    	super();
    }
    
    public BrandForm(String modelName) {
    	super(modelName);
    }
    
    public BrandForm (String modelName, Map<String, Object> values) {
        super(modelName, values);
    }
    
	public static HiddenField<String> getIdField() {
		HiddenField<String> idField = new HiddenField<String>();
		idField.setName(ID);
		return idField;
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
	
	public static TextField<String> getSiteField(String fieldTitle) {
		TextField<String> nameField = new TextField<String>();
		nameField.setName(SITE);
		nameField.setMaxLength(100);
		nameField.setAutoValidate(true);
		return nameField;
	}
	public static FileUploadField getLogoField(String fieldTitle) {
		FileUploadField nameField = new FileUploadField();
		nameField.setName(LOGO);
//		nameField.setMaxLength(10);
		nameField.setAutoValidate(true);
//		nameField.addAllowedTypes(new String[]{".jpg", ".gif"});
		return nameField;
	}
	
	public static TextArea getDescField(String fieldTitle) {
		TextArea agField = new TextArea();
		agField.setName(DESC);
		// max for DS String field is 500
		agField.setMaxLength(500);
		agField.setAutoValidate(true);
		agField.setAllowBlank(true);
		return agField;
	}
	
	public static TextField<String> getOrderField(String fieldTitle) {
		TextField<String> nameField = new TextField<String>();
		nameField.setName(ORDER);
		nameField.setMaxLength(10);
		nameField.setAutoValidate(true);
		return nameField;
	}
	
	public static CheckBox getShowField(String fieldTitle) {
		CheckBox nameField = new CheckBox();
		nameField.setName(SHOW);
		nameField.setAutoValidate(true);
		return nameField;
	}
}
