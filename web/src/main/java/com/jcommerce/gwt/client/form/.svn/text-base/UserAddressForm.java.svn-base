package com.jcommerce.gwt.client.form;

import java.util.Map;

import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.ComboBox.ComboBoxMessages;
import com.extjs.gxt.ui.client.widget.form.Field.FieldMessages;
import com.extjs.gxt.ui.client.widget.form.TextField.TextFieldMessages;
import com.jcommerce.gwt.client.model.IGoodsType;
import com.jcommerce.gwt.client.model.IRegion;
import com.jcommerce.gwt.client.model.IUserAddress;
import com.jcommerce.gwt.client.resources.Resources;

public class UserAddressForm  extends BeanObject implements IUserAddress{
	public UserAddressForm() {
		super();
	}
	public static final String USER_ADDRESS_LIST = "userAddressList"; 
	
	public static TextField<String> getConsigneeField(String fieldTitle) {
		TextField<String> field = new TextField<String>();
		field.setName(CONSIGNEE);
		field.setMaxLength(30);
		field.setAutoValidate(true);
		field.setAllowBlank(false);
		TextField<String>.TextFieldMessages tfm = field.new TextFieldMessages();
		tfm.setBlankText(Resources.messages.blankText(fieldTitle));		
		field.setMessages(tfm);
		return field;
	}
	
	public static TextField<String> getEmailField(String fieldTitle) {
		TextField<String> field = new TextField<String>();
		field.setName(EMAIL);
		field.setAutoValidate(true);
		field.setAllowBlank(false);
		TextField<String>.TextFieldMessages tfm = field.new TextFieldMessages();
		tfm.setBlankText(Resources.messages.blankText(fieldTitle));		
		field.setMessages(tfm);
		return field;
	}
	
	public static TextField<String> getAddressField(String fieldTitle) {
		TextField<String> field = new TextField<String>();
		field.setName(ADDRESS);
		field.setAutoValidate(true);
		field.setAllowBlank(false);
		TextField<String>.TextFieldMessages tfm = field.new TextFieldMessages();
		tfm.setBlankText(Resources.messages.blankText(fieldTitle));		
		field.setMessages(tfm);
		return field;
	}
	
	public static TextField<String> getTelField(String fieldTitle) {
		TextField<String> field = new TextField<String>();
		field.setName(PHONE);
		field.setAutoValidate(true);
		field.setAllowBlank(false);
		TextField<String>.TextFieldMessages tfm = field.new TextFieldMessages();
		tfm.setBlankText(Resources.messages.blankText(fieldTitle));		
		field.setMessages(tfm);
		return field;
	}
	
	public static TextField<String> getZipcodeField() {
		TextField<String> field = new TextField<String>();
		field.setName(ZIP);
		return field;
	}
	
	public static TextField<String> getMobileField() {
		TextField<String> field = new TextField<String>();
		field.setName(MOBILE);
		return field;
	}
	
	public static TextField<String> getSignBuildingField() {
		TextField<String> field = new TextField<String>();
		field.setName(SIGNBUILDING);
		return field;
	}
	
	public static TextField<String> getBestTimeField() {
		TextField<String> field = new TextField<String>();
		field.setName(BESTTIME);
		return field;
	}
	
	public static ComboBox<BeanObject> getCountryField() {
		ComboBox<BeanObject> field = new ComboBox<BeanObject>();
		field.setName("COUNTRY");
		field.setDisplayField(IRegion.NAME);
        field.setValueField(IRegion.ID);
		return field;
	}
	
	public static ComboBox<BeanObject> getProvinceField() {
		ComboBox<BeanObject> field = new ComboBox<BeanObject>();
		field.setName("PROVINCE");
		field.setDisplayField(IRegion.NAME);
        field.setValueField(IRegion.ID);
		return field;
	}
	
	public static ComboBox<BeanObject> getCityField() {
		ComboBox<BeanObject> field = new ComboBox<BeanObject>();
		field.setName("CITY");
		field.setDisplayField(IRegion.NAME);
        field.setValueField(IRegion.ID);
		return field;
	}
	
	public static ComboBox<BeanObject> getDistrictField(String fieldTitle) {
		ComboBox<BeanObject> field = new ComboBox<BeanObject>();
		field.setName("DISTRICT");
		field.setDisplayField(IRegion.NAME);
        field.setValueField(IRegion.ID);
//        field.setAllowBlank(false);
        field.setAutoValidate(true);
        FieldMessages tfm = field.new ComboBoxMessages();
		tfm.setInvalidText(Resources.messages.blankText(fieldTitle));		
		field.setMessages(tfm);
		return field;
	}
	
	public static ComboBox<BeanObject> getUaListField() {
		ComboBox<BeanObject> field = new ComboBox<BeanObject>();
		field.setName(USER_ADDRESS_LIST);
		field.setDisplayField(IUserAddress.CONSIGNEE);
        field.setValueField(IUserAddress.ID);
		return field;
	}

	public static String validate(Map<String, String> props) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
}
