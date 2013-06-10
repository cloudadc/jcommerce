/**
* Author: Bob Chen
*/

package com.jcommerce.core.action;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.context.ApplicationContext;

import com.jcommerce.core.model.ModelObject;
import com.jcommerce.core.service.Manager;

public class PropertyListAction extends PropertyAction {
    public PropertyListAction(ApplicationContext ctx, BeanConfig config) {
        super(ctx, config);
    }
    
    public List<Map<String, String>> getList(String modelName) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        
        Manager manager = getManager(modelName);
        String methodName = config.getListMethod(modelName);
        try {
            Method method = manager.getClass().getMethod(methodName, new Class[0]);
            if (method == null) {
                System.out.println("Method not found: "+methodName);
            }
            List ret = (List)method.invoke(manager);
            for (Iterator it = ret.iterator(); it.hasNext();) {
                ModelObject model = (ModelObject) it.next();
                try {
                    Map<String, String> props = getProperties(model);
                    list.add(props);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        
        return list;
    }
}
