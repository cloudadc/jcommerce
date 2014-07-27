/**
* Author: Bob Chen
*/

package com.jcommerce.core.action;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.jcommerce.core.model.ModelObject;
import com.jcommerce.core.service.Manager;

public class PropertyUpdateAction extends PropertyAction {
    public PropertyUpdateAction(ApplicationContext ctx, BeanConfig config) {
        super(ctx, config);
    }
    
    public boolean updateObject(String modelName, String id, Map<String, String> props) {
        System.out.println("updateObject("+modelName);
        
        try {
            Manager manager = getManager(modelName);
            System.out.println("-----2-------"+manager);
            String methodName = config.getGetMethod(modelName);
            Method method = manager.getClass().getMethod(methodName, String.class);
            System.out.println("-----3-------");
            if (method == null) {
                System.out.println("Method not found: "+methodName);
            }
            System.out.println("-----4-------");
            ModelObject model = (ModelObject)method.invoke(manager, id);
            if (model != null) {
                copyProperties(props, model);
                id = saveObject(modelName, model);
                return id != null;
            } else {
                return false;
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return false;
    }        
}
