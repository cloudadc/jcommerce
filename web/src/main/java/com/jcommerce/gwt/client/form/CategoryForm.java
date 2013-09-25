package com.jcommerce.gwt.client.form;

import java.util.Map;

import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.jcommerce.gwt.client.ValidationException;
import com.jcommerce.gwt.client.model.ICategory;
import com.jcommerce.gwt.client.resources.Resources;

public class CategoryForm extends BeanObject implements ICategory {
	 
	private static final long serialVersionUID = 6209018353073021080L;

	public CategoryForm(){
    	super();
    }
    
	public static TextField<String> getNameField(String fieldTitle) {
		TextField<String> field = new TextField<String>();
		field.setName(NAME);
		field.setMaxLength(50);
		field.setAutoValidate(true);
		field.setAllowBlank(false);
		TextField<String>.TextFieldMessages tfm = field.new TextFieldMessages();
		tfm.setBlankText(Resources.messages.blankText(fieldTitle));
		field.setMessages(tfm);
		return field;
	}
	
	public static ComboBox<BeanObject> getParentIdField() {
		ComboBox<BeanObject> field = new ComboBox<BeanObject>();
		field.setName(PARENT);
		field.setDisplayField(NAME);
        field.setValueField(ID);
		return field;
	}
	public static TextField<String> getMeasureUnitField() {
		TextField<String> field = new TextField<String>();
		field.setName(MEASUREUNIT);
		field.setMaxLength(10);
		return field;
	}
	
	public static NumberField getSortOrderField() {
		NumberField field = new NumberField();
		field.setName(SORTORDER);
		field.setAllowDecimals(false);
		field.setAllowNegative(false);
		return field;
	}
	public static TextField<String> getGradeField() {
		TextField<String> field = new TextField<String>();
		field.setName(GRADE);
		field.setMaxLength(10);
		return field;
	}
	public static TextField<String> getStyleField() {
		TextField<String> field = new TextField<String>();
		field.setName(STYLE);
		return field;
	}
	
    public CategoryForm (String modelName, Map<String, Object> values) {
        super(modelName, values);
    }
    
	public void validate() throws ValidationException{
		 
	 }
	 
	 
}
