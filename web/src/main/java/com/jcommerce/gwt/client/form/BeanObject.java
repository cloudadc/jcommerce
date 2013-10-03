package com.jcommerce.gwt.client.form;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

import com.extjs.gxt.ui.client.data.BaseTreeModel;
import com.jcommerce.gwt.client.ValidationException;


public class BeanObject extends BaseTreeModel implements Serializable {

	private static final long serialVersionUID = -1751739928777621648L;
	
	private String modelName;
    
    public BeanObject() {
    }

    public BeanObject(String modelName) {
        this.modelName = modelName;
    }

    public BeanObject(String modelName, Map<String, Object> values) {
        this.modelName = modelName;
        setValues(values);
    }
    public BeanObject(Map<String, Object> values) {
    	setValues(values);
    }
    
    public String getID() {
        return getString("id");
    }
    
    public int getInt(String name) {
        Object o = get(name);
        if (o instanceof Number) {
            return ((Number)o).intValue();
        } else if (o instanceof String) {
            return new Integer((String)o).intValue();
        } else if (o == null) {
            return 0;
        }
        
        throw new RuntimeException("Unknown int value:"+o+" for name:"+name);
    }
    
    protected void validate() throws ValidationException{
    	
    }
    
    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public void setValues(Map<String, Object> values) {
        for (Iterator it = values.keySet().iterator(); it.hasNext();) {
            String name = (String) it.next();
            Object value = values.get(name);
            set(name, value);
        }
    }
    
    public String[] getIDs(String name) {
        Object value = get(name);
        if (value == null) {
            return new String[0]; 
        }
        
        if (!(value instanceof String)) {
            throw new RuntimeException("Invalid value:"+value+" name:"+name);
        }
        
        return ((String)value).split(",");
    }
    
    public Long getLong(String name) {
    	Object value = get(name);
        if (value == null) {
            return null;
        }
        return (Long) value;
    }
    
    public String getString(String name) {
        Object value = get(name);
        if (value == null) {
            return null;
        }
        
        return value.toString();
    }
}
