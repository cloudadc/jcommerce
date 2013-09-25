package com.jcommerce.gwt.client.form;

import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.HiddenField;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.Radio;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.jcommerce.gwt.client.model.IArticleCategory;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.widgets.MyRadioGroup;

public class ArticleCatForm extends BeanObject implements IArticleCategory{

	private static final long serialVersionUID = 4816197240791706163L;

	public ArticleCatForm(){
		super();
	} 
	
	public static TextField<String> getNameField(String fieldTitle){
		TextField<String> field = new TextField<String>();
		field.setName(NAME);
		field.setAutoValidate(true);
		field.setAllowBlank(false);
		TextField<String>.TextFieldMessages tfm = field.new TextFieldMessages();
		tfm.setBlankText(Resources.messages.blankText(fieldTitle));
		field.setMessages(tfm);
		return field;
	}
	
	public static ComboBox<BeanObject> getParentCatField(){
		ComboBox<BeanObject> field = new ComboBox<BeanObject>();
		field.setName(PARENT);
		field.setTriggerAction(TriggerAction.ALL);
		//field.setReadOnly(true);
		field.setDisplayField(NAME);
		field.setValueField(ID);
		return field;
	}
	
	public static HiddenField<String> getCatType(){
		HiddenField<String> field = new HiddenField<String>();
		field.setName(TYPE);
		field.setValue("1");
		return field;
	}
	
	public static NumberField getOrderField(){
		NumberField field = new NumberField();
		field.setName(SORT_ORDER);
		field.setAllowDecimals(false);
		field.setAllowNegative(false);
		return field;
		
	}
	
	public  static MyRadioGroup getIsShowInNavField(){
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
		return field;
	}
	
	public static TextField<String> getKeywordField(){
		TextField<String> field = new TextField<String>();
		field.setName(KEYWORDS);
		field.setToolTip("关键字为选填项,目的在于方便外部搜索引擎搜索");
		return field;
	}
	
	public static TextArea getDescField(){
		TextArea field = new TextArea();
		field.setName(DESCRIPTION);
		return field;
		
	}

}
