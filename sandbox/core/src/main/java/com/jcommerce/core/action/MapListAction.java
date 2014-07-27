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

import org.springframework.context.ApplicationContext;

import com.jcommerce.core.model.ModelObject;

import com.jcommerce.core.module.ModuleManager;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.Manager;


public class MapListAction extends MapAction {
    public MapListAction(ApplicationContext ctx, BeanConfig config) {
        super(ctx, config);
    }
    
    public List<Map<String, Object>> getList(String modelName) {
        return getList(modelName, null);
    }
    
    public List<Map<String, Object>> getList(String modelName, Criteria criteria) {
    	return getList(modelName, criteria, null);
    }
    
    public int getCount(String modelName) {
        return getCount(modelName, null);
    }
    
    public int getCount(String modelName, Criteria criteria) {
        Manager manager = getManager(modelName);
        String methodName = config.getCountMethod(modelName);
        try {
            Method method = manager.getClass().getMethod(methodName, new Class[] {Criteria.class});
            if (method == null) {
                System.out.println("Method not found: "+methodName);
            }
            Integer count = (Integer)method.invoke(manager, criteria);
            return count;
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
        
        return 0;
    }
    
    public List<Map<String, Object>> getList(String modelName, Criteria criteria, List<String> wantedFields) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        
        Manager manager = getManager(modelName);
        String methodName = config.getListMethod(modelName);
        try {
            Method method = manager.getClass().getMethod(methodName, new Class[] {Criteria.class});
            if (method == null) {
                System.out.println("Method not found: "+methodName);
            }
            List ret = (List)method.invoke(manager, criteria);
            for (Iterator it = ret.iterator(); it.hasNext();) {
                ModelObject model = (ModelObject) it.next();
                try {
                    Map<String, Object> props = getProperties(model, wantedFields);
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
    public List<Map<String, Object>> getModuleList(String modelName){
    	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    	ModuleManager moduleManager = new ModuleManager();
		String methodName = "get"+modelName+"List";
		Method aMethod;
		try {
			aMethod = moduleManager.getClass().getMethod(methodName,null);
			List moduleList = (List) aMethod.invoke(moduleManager,null);
					
			for (Iterator it = moduleList.iterator(); it.hasNext();) {
                ModelObject model = (ModelObject) it.next();
                try {
                    Map<String, Object> props = getProperties(model);
                    list.add(props);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
			
		} catch (Exception e) {
			
		} 
		return list;
    }
}
