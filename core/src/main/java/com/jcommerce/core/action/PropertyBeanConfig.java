/**
* Author: Bob Chen
*/

package com.jcommerce.core.action;

import java.util.Properties;

public class PropertyBeanConfig implements BeanConfig {
    private Properties beanProps = new Properties();
    
    public PropertyBeanConfig(Properties beanProps) {
        this.beanProps = beanProps;
    }
    
    public String getRemoveMethod(String modelName) {
        String methodName = "remove"+modelName;
        return methodName;
    }

    public String getCountMethod(String modelName) {
        String methodName = "get"+modelName+"Count";
        return methodName;
    }

    public String getListMethod(String modelName) {
        String methodName = "get"+modelName+"List";
        return methodName;
    }

    public String getSaveMethod(String modelName) {
        String methodName = "save"+modelName;
        return methodName;
    }

    public String getGetMethod(String modelName) {
        String methodName = "get"+modelName;
        return methodName;
    }

//    public String getIDField(String modelName) {
//        String id = beanProps.getProperty("bean."+modelName+".ID");
//        if (id == null) {
//            id = "id";
//        }
//        return id;
//    }

    public String getNameField(String modelName) {
        String id = beanProps.getProperty("bean."+modelName+".name");
        if (id == null) {
            id = "name";
        }
        return id;
    }
    
    public String getItemType(String modelName, String fieldName) {
        String type = beanProps.getProperty("bean."+modelName+".type."+fieldName);
        if (type == null) {
            throw new RuntimeException("Field type must be defined: "+modelName+"."+fieldName);
        }
        return type;
    }
}
