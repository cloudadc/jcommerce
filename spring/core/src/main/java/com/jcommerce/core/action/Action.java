/**
* Author: Bob Chen
*/

package com.jcommerce.core.action;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.jcommerce.core.model.ModelObject;
import com.jcommerce.core.service.Manager;

public class Action {
	
    private ApplicationContext ctx = null;
    protected BeanConfig config;
    
    private Map<String, Manager> managers = new HashMap<String, Manager>();
    
    public Action(ApplicationContext ctx, BeanConfig config) {
        this.ctx = ctx;
        this.config = config;
    }
    
    protected String getId(ModelObject obj) {
        return obj.getModelId();
    }

    protected String saveObject(String modelName, ModelObject model) {
        try {
            Manager manager = getManager(modelName);
            System.out.println("-----2-------"+manager);
            String methodName = config.getSaveMethod(modelName);
            Method method = manager.getClass().getMethod(methodName, model.getClass());
//            System.out.println("-----3-------");
//            if (method == null) {
//                throw new RuntimeException("Method not found: "+methodName);
//            }
//            System.out.println("-----4-------");
            method.invoke(manager, model);
//            System.out.println("-----5-------"+model);
            return getId(model);
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
        return null;        
    }  
    
    protected ModelObject getModelObject(String modelName, String id) {
        Manager manager = getManager(modelName);
        String methodName = config.getGetMethod(modelName);
        try {
            Method method = manager.getClass().getMethod(methodName, new Class[] {String.class});
            if (method == null) {
                System.out.println("Method not found: "+methodName);
            }
            ModelObject model = (ModelObject)method.invoke(manager, id);
            return model;
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    protected Object getBeanProperty(ModelObject obj, String field) {
        try {
            Method method = null;
            try {
                method = obj.getClass().getMethod("get" + firstUpperCase(field), new Class[0]);
            } catch (Exception e) {
                method = obj.getClass().getMethod("is" + firstUpperCase(field), new Class[0]);
            }
            
            if (method == null) {
                throw new RuntimeException("Read method not found: " + field);
            }
            
            return method.invoke(obj);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    protected String firstUpperCase(String s) {
        if (s == null || s.length() < 2) {
            return s;
        }
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
    
    protected Manager getManager(String modelName) {
        if (managers.get(modelName) == null) {
            Object manager = ctx.getBean(modelName+"Manager");
            if (manager == null) {
                throw new RuntimeException("Manager not found: "+modelName+"Manager");
            }
            
            managers.put(modelName, (Manager)manager);
        }
        return managers.get(modelName);
    }
    
    protected ModelObject createModelObject(String modelName) {
        try {
            ModelObject model = (ModelObject)Class.forName("com.jcommerce.core.model."+modelName).newInstance();
            return model;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        return null;
    }
}
