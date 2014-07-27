/**
* Author: Bob Chen
*/

package com.jcommerce.core.module;

import java.util.Map;

public class ConfigMetaData implements IConfigMetaData {
    private String name;
    private int id;
    private String description;
    private Map<String, IFieldMetaData> fieldMetas;
    private Map<String, String> fieldValues;
    private boolean enabled;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Map<String, IFieldMetaData> getFieldMetaData() {
        return fieldMetas;
    }
    
    public void setFieldMetaData(Map<String, IFieldMetaData> fieldMetas) {
        this.fieldMetas = fieldMetas;
    }
    
    public Map<String, String> getFieldValues() {
        return fieldValues;
    }

    public void setFieldValues(Map<String, String> fieldValues) {
        this.fieldValues = fieldValues;
    }

    public boolean isEnabled() {
        return enabled;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
