package com.jcommerce.gwt.client.form;

import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.HtmlEditor;
import com.extjs.gxt.ui.client.widget.form.Radio;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.jcommerce.gwt.client.model.IArticle;
import com.jcommerce.gwt.client.model.IArticleCategory;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.widgets.MyHTMLEditor;
import com.jcommerce.gwt.client.widgets.MyRadioGroup;

public class ArticleForm extends BeanObject implements IArticle {
	public ArticleForm (){
		super();
	}
	
	public static TextField<String> getNameField(String fieldTitle){
		TextField<String> field = new TextField<String>();
		field.setName(IArticle.TITLE);
		field.setAutoValidate(true);
		field.setAllowBlank(false);
		TextField<String>.TextFieldMessages tfm = field.new TextFieldMessages();
		tfm.setBlankText(Resources.messages.blankText(fieldTitle));
		field.setMessages(tfm);
		return field;
	}
	
	public static ComboBox<BeanObject> getArticleCat(String fieldTitle){
		ComboBox<BeanObject> field = new ComboBox<BeanObject>();
		field.setAllowBlank(false);
		field.setTriggerAction(TriggerAction.ALL);
		field.setName(IArticle.ARTICLECATEGORY);
		field.setValueField(IArticleCategory.ID);
		field.setDisplayField(IArticleCategory.NAME);
		ComboBox<BeanObject>.ComboBoxMessages cbm = field.new ComboBoxMessages();
		cbm.setBlankText(Resources.messages.blankText(fieldTitle));
		field.setMessages(cbm);
		return field;
	}
	
	public static MyRadioGroup getIsOpen(){
		MyRadioGroup field = new MyRadioGroup();
		Radio yes = new Radio();
		yes.setValueAttribute("true");
		yes.setBoxLabel("yes");
		field.add(yes);
		Radio no = new Radio();
		no.setValue(true);
		no.setValueAttribute("false");
		no.setBoxLabel("no");
		field.add(no);
		field.setName(IArticle.OPEN);
		return field;
	}
	
	public static  MyRadioGroup getArticleType(){
		MyRadioGroup field = new  MyRadioGroup();
		Radio yes = new Radio();
		yes.setValueAttribute("true");
		yes.setBoxLabel("置顶");
		field.add(yes);
		Radio no = new Radio();
		no.setValue(true);
		no.setValueAttribute("false");
		no.setBoxLabel("普通");
		field.add(no);
		field.setName(IArticle.ARTICLETYPE);
		return field;
	}
	
	public static TextField<String> getAuthorField(){
		TextField<String> field = new TextField<String>();
		field.setName(IArticle.AUTHOR);
		return field;	
	}
	
	public static TextField<String> getAuthorEmail(){
		TextField<String> field = new TextField<String>();
		field.setName(IArticle.AUTHOREMAIL);
	    return field;
	}
	
	public static TextField<String> getKeyword(){
		TextField<String> field = new TextField<String>();
		field.setName(IArticle.AUTHOREMAIL);
	    return field;
	}
	
	public static HtmlEditor getContent(){
		MyHTMLEditor field = new MyHTMLEditor(){
			@Override
			protected boolean validateValue(String value) {
				if(super.validateValue(value) == false) {
					return false;
				}
				if(value!=null && value.length()>500) {
					markInvalid("Size of content must be less than 500 words.");
					return false;
				}
				return true;
			}
		};
		field.setName(IArticle.ID);
		return field;
	}
	
	
	

}
