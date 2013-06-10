/**
* Author: Bob Chen
*/

package com.jcommerce.util;

import java.lang.reflect.Method;

import com.jcommerce.core.model.ModelObject;

public class BeanUtil {
    public static void copyModelObject2GwtObject(ModelObject mo, Object go) {
        Method[] methods = mo.getClass().getMethods();
        for (int i = 0; i < methods.length; i++) {
            Method m = methods[i];
            String mn = m.getName(); 
            if (mn.startsWith("is") || mn.startsWith("get") ) {
                String setn = "set";
                if (mn.startsWith("is")) {
                    setn += mn.substring(2);
                } else {
                    setn += mn.substring(3);
                }
                
                Class retCls = m.getReturnType();
                try {
                    Method _m = go.getClass().getMethod(setn, new Class[]{retCls});
                    _m.invoke(go, m.invoke(mo, new Object[0]));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void copyGwtObject2ModelObject(Object go, ModelObject mo) {
        Method[] methods = go.getClass().getMethods();
        for (int i = 0; i < methods.length; i++) {
            Method m = methods[i];
            String mn = m.getName(); 
            if (mn.startsWith("is") || mn.startsWith("get") ) {
                String setn = "set";
                if (mn.startsWith("is")) {
                    setn += mn.substring(2);
                } else {
                    setn += mn.substring(3);
                }
                
                Class retCls = m.getReturnType();
                try {
                    Method _m = mo.getClass().getMethod(setn, new Class[]{retCls});
                    _m.invoke(mo, m.invoke(go, new Object[0]));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Object getPropertyValue(Object obj, String propertyName) {
        try {
            Class<? extends Object> objClass = obj.getClass();
            Method method = objClass.getMethod("get" + propertyName);
            Object value = method.invoke(obj, (Object[])null);
            return value;
        }
        catch (Exception ex) {
            throw new RuntimeException(
                "Property not found " + propertyName, ex);
        }
    }
    
    public static String getPropertyStringValue(Object obj, String propertyName) {
        Object value = getPropertyValue(obj, propertyName);
        if (value == null) {
            return null;
        } 
        else {
            String valueStr = value.toString();
            return valueStr;
        }
    }
}
