package com.jcommerce.gwt.client.form;

import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.TimeField;
import com.jcommerce.gwt.client.model.IUser;
import com.jcommerce.gwt.client.resources.Resources;

public class UserForm  extends BeanObject implements IUser{

	public UserForm() {
		super();
	}
	
	public static TextField<String> getNameField(String fieldTitle) {
		TextField<String> field = new TextField<String>();
		field.setName(NAME);
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
		field.setRegex("^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$");
		TextField<String>.TextFieldMessages tfm = field.new TextFieldMessages();
		tfm.setBlankText(Resources.messages.blankText(fieldTitle));
//		tfm.setRegexText(Resources.constants.User_wrongemail());
		field.setMessages(tfm);
		return field;
	}
	
	public static TextField<String> getMsnField() {
		TextField<String> field = new TextField<String>();
		field.setName(MSN);
		return field;
	}
	
	public static TextField<String> getPasswordField(String fieldTitle) {
		TextField<String> field = new TextField<String>();
		field.setPassword(true);
		field.setName(PASSWORD);
		field.setAutoValidate(true);
		field.setAllowBlank(false);
		TextField<String>.TextFieldMessages tfm = field.new TextFieldMessages();
		tfm.setBlankText(Resources.messages.blankText(fieldTitle));
		field.setMessages(tfm);
		return field;
	}
	
	public static NumberField getCreditField() {
		NumberField field = new NumberField();
		field.setName(CREDITLINE);
		field.setAllowDecimals(false);
		field.setAllowNegative(false);
		return field;
	}
	
	public static TextField<String> getQqField() {
		TextField<String> field = new TextField<String>();
		field.setName(QQ);
		field.setRegex("^[0-9]*$");
		TextField<String>.TextFieldMessages tfm = field.new TextFieldMessages();
		//tfm.setRegexText("QQ号码只能为数字");
//		tfm.setRegexText(Resources.constants.User_wrongqq());
		field.setMessages(tfm);
		return field;
	}

	public static TextField<String> getOfficePhoneField() {
		TextField<String> field = new TextField<String>();
		field.setName(OFFICEPHONE);
		field.setRegex("^[0-9]*$");
		TextField<String>.TextFieldMessages tfm = field.new TextFieldMessages();
//		tfm.setRegexText(Resources.constants.User_wrongphone());
		field.setMessages(tfm);
		return field;
	}

	public static TextField<String> getHomePhomeField() {
		TextField<String> field = new TextField<String>();
		field.setName(HOMEPHONE);
		field.setRegex("^[0-9]*$");
		TextField<String>.TextFieldMessages tfm = field.new TextFieldMessages();
//		tfm.setRegexText(Resources.constants.User_wrongphone());
		field.setMessages(tfm);
		return field;
	}
	
	public static TextField<String> getMobilePhomeField() {
		TextField<String> field = new TextField<String>();
		field.setName(MOBILEPHONE);
		field.setRegex("^[0-9]*$");
		TextField<String>.TextFieldMessages tfm = field.new TextFieldMessages();
//		tfm.setRegexText(Resources.constants.User_wrongphone());
		field.setMessages(tfm);
		return field;
	}

	public static DateField getPromoteEndDateField() {
		DateField field = new DateField();
		field.setName(BIRTHDAY);
		return field;
	}

	public static TextField<String> getQuestionField() {
		TextField<String> field = new TextField<String>();
		field.setName(QUESTION);
		field.setVisible(false);
		return field;
	}

	public static TextField<String> getAnswerField() {
		TextField<String> field = new TextField<String>();
		field.setName(ANSWER);
		field.setVisible(false);
		return field;
	}

	public static TextField<String> getUserMoneyField() {
		TextField<String> field = new TextField<String>();
		field.setName(MONEY);
		field.setVisible(false);
		return field;
	}

	public static TextField<String> getFrozenMoneyField() {
		TextField<String> field = new TextField<String>();
		field.setName(FROZENMONEY);
		field.setVisible(false);
		return field;
	}

//	public static TextField<String> getAddressIdField() {
//		TextField<String> field = new TextField<String>();
//		field.setName(ADDRESSES);
//		field.setVisible(false);
//		return field;
//	}

	public static TextField<String> getRegTimeField() {
		TextField<String> field = new TextField<String>();
		field.setName(REGISTERTIME);
		field.setVisible(false);
		return field;
	}

	public static TextField<String> getLastLoginField() {
		TextField<String> field = new TextField<String>();
		field.setName(LASTLOGIN);
		field.setVisible(false);
		return field;
	}

	public static TextField<String> getLastIpField() {
		TextField<String> field = new TextField<String>();
		field.setName(LASTIP);
		field.setVisible(false);
		return field;
	}
}
