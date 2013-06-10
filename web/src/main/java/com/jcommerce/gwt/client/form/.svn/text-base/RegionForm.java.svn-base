package com.jcommerce.gwt.client.form;

import com.extjs.gxt.ui.client.widget.form.HiddenField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.TextField.TextFieldMessages;
import com.jcommerce.gwt.client.model.IRegion;
import com.jcommerce.gwt.client.resources.Resources;

public class RegionForm  extends BeanObject implements IRegion{
	
	public static HiddenField<String> getIdField(){
		HiddenField<String> idField = new HiddenField<String>();
		idField.setName(ID);
		return idField;
	}

	public static TextField<String> getNameField(String fieldTitle){
		TextField<String> nameField = new TextField<String>();
		nameField.setName(NAME);
		nameField.setMaxLength(30);
		nameField.setAllowBlank(false);
		TextField<String>.TextFieldMessages tfm = nameField.new TextFieldMessages();
		tfm.setBlankText(Resources.messages.blankText(fieldTitle));
		nameField.setMessages(tfm);
		nameField.setAutoValidate(true);
		return nameField;
	}
	
	public static HiddenField<String> getTypeField(){
		HiddenField<String> typeField = new HiddenField<String>();
		typeField.setName(TYPE);
		return typeField;
	}
	
	public static HiddenField<String> getParentIdField(){
		HiddenField<String> parentIdField = new HiddenField<String>();
		parentIdField.setName(PARENT);
		return parentIdField;
	}
	
	public static HiddenField<String> getAgencyIdField(){
		HiddenField<String> agencyIdField = new HiddenField<String>();
		agencyIdField.setName(AGENCY);
		return agencyIdField;
	}
	
	
	
	public static TextField<String> getEditNameField(){
		TextField<String> nameField = new TextField<String>();
		nameField.setName(NAME);
		nameField.setMaxLength(30);
		return nameField;
	}
	
//	public static HiddenField<String> getOldNameField(){
//		HiddenField<String> nameField = new HiddenField<String>();
//		nameField.setName(OLD_NAME);
//		return nameField;
//	}
//
	public static HiddenField<String> getHiddenNameField(){
		HiddenField<String> nameField = new HiddenField<String>();
		nameField.setName(NAME);
		return nameField;
	}
}
