package com.jcommerce.gwt.client.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.Radio;
import com.extjs.gxt.ui.client.widget.form.RadioGroup;
import com.jcommerce.gwt.client.Logger;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.widgets.MyRadioGroup;

public class FormUtils {

	public static void log(String s) {
    	StringBuffer buf = new StringBuffer();
    	buf.append("[FormUtils]:").append(s);
    	Logger.getClientLogger().log(buf.toString());
    	System.out.println(buf.toString());
    }
    
	public static Map<String, String> getPropsFromForm(FormPanel formPanel) {
    	List<Field<?>> fields = formPanel.getFields();
    	
    	Map<String, String> props = new HashMap<String, String>();
    	for(Field field:fields) {
    		String name = field.getName();
    		Object value = getValueFromField(field);
    		log("name: "+name+", value: ("+value+")");
    		if(name==null || value == null) {
    			continue;
    		}
    		props.put(name, ""+value);
    	}
    	return props;
	}
	
	public static Object getValueFromField(Field field) {
		String name = field.getName();
		Object value = field.getValue();
		if(value == null) {
			return null;
		}
		// TODO handle CheckBoxes which are special
		if(field instanceof MyRadioGroup) {
			Radio selected = (Radio)value;
//			name = selected.getName();

			value = selected.getValueAttribute();
		}
		else if(field instanceof RadioGroup) {
			throw new RuntimeException("please use MyRadioGroup instead of RadioGroup! name="+name+", value="+value);
		}
		else if(field instanceof ComboBox) {
			ComboBox box = (ComboBox)field;
			String key = box.getValueField();
			value = ((BeanObject)value).get(key);
		}

		return value;
	}
}
