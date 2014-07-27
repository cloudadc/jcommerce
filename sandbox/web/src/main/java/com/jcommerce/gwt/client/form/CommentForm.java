package com.jcommerce.gwt.client.form;

import java.util.Map;

import com.extjs.gxt.ui.client.widget.form.HiddenField;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.jcommerce.gwt.client.model.IComment;
import com.jcommerce.gwt.client.resources.Resources;

public class CommentForm extends BeanObject implements IComment {

	private static final long serialVersionUID = 8775690196212517268L;
	
	public static String validate(Map<String, String> props) {
		String error = null;
		
		return error;
		
	}
	
	public static HiddenField<String> getRankField() {
		HiddenField<String> field = new HiddenField<String>();
		field.setName(COMMENTRANK);
		return field;
	}
	public static TextField<String> getUserNameField() {
		TextField<String> field = new TextField<String>();
		field.setName(USERNAME);
		return field;
	}
	public static TextField<String> getCommentTypeField() {
		TextField<String> field = new TextField<String>();
		field.setName(COMMENTTYPE);
		return field;
	}
	public static TextField<String> getIDField() {
		TextField<String> field = new TextField<String>();
		field.setName(ID);
		return field;
	}
	public static TextField<String> getIdValueField() {
		TextField<String> field = new TextField<String>();
		field.setName(IDVALUE);
		return field;
	}
	public static TextField<String> getIpAddressField() {
		TextField<String> field = new TextField<String>();
		field.setName(IPADDRESS);
		return field;
	}
	public static TextField<String> getAddTimeField() {
		TextField<String> field = new TextField<String>();
		field.setName(ADDTIME);
		return field;
	}
	public static TextField<String> getStatusField() {
		TextField<String> field = new TextField<String>();
		field.setName(STATUS);
		return field;
	}
	
	public static HiddenField<String> getUserIdField() {
		HiddenField<String> field = new HiddenField<String>();
		field.setName(USER);
		return field;
	}
	
	public static HiddenField<String> getParentIdField() {
		HiddenField<String> field = new HiddenField<String>();
		field.setName(PARENT);
		return field;
	}
	public static TextArea getContentField(String fieldTitle) {
		TextArea field = new TextArea();
		field.setName(CONTENT);
		field.setAllowBlank(false);
		TextArea.TextFieldMessages tfm = field.new TextFieldMessages();
		tfm.setBlankText(Resources.messages.blankText(fieldTitle));
		field.setMessages(tfm);
		return field;
	}
	public static TextField<String> getEmailField() {
		TextField<String> field = new TextField<String>();
		field.setName(EMAIL);
		return field;
	}
	
}
