package com.jcommerce.gwt.server;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.extjs.gxt.ui.client.data.BaseListLoadResult;

import com.extjs.gxt.ui.client.data.ListLoadConfig;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.ModelData;
import com.jcommerce.core.action.BeanConfig;
import com.jcommerce.core.action.MapAction;
import com.jcommerce.core.model.ModelObject;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.Manager;
import com.jcommerce.gwt.client.form.BeanObject;


public class TreeAction extends MapAction{
	 public TreeAction(ApplicationContext ctx, BeanConfig config) {
	        super(ctx, config);
	    }
	 
	 public List<ModelData> getTreeList(String modelName, Criteria criteria,BeanObject loadconfig){
		 List<ModelData> list = new ArrayList<ModelData>();
		 Manager manager = getManager(modelName);
	     String methodName = config.getListMethod(modelName);
	     System.out.println("");
	     System.out.println(methodName);
	     System.out.println("");
	     try {
	            Method method = manager.getClass().getMethod(methodName, null);
	            if (method == null) {
	                throw new RuntimeException("Method with paging not found: "+methodName);
	            }
	            
	            List ret = (List)method.invoke(manager, null);
	           

	            for (Iterator it = ret.iterator(); it.hasNext();) {
	                ModelObject model = (ModelObject) it.next();
	                try {
	                    Map<String, Object> props = getProperties(model);
	                    list.add(new BeanObject(model.getClass().getSimpleName(), props));
	                    System.out.println("childstart");
	                    System.out.println(list.get(0).getProperties().get("children"));
	                    System.out.println("childend");
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
