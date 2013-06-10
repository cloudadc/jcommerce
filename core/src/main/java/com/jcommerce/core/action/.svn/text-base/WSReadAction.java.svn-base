/**
 * 
 */
package com.jcommerce.core.action;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.springframework.context.ApplicationContext;

import com.jcommerce.core.model.ModelObject;
import com.jcommerce.core.service.Manager;

/**
 * @author david yang
 *
 */
public class WSReadAction extends WSAction {

	/**
	 * @param ctx
	 * @param config
	 */
	public WSReadAction(ApplicationContext ctx, BeanConfig config) {
		super(ctx, config);
		// TODO Auto-generated constructor stub
	}
	
	public ModelObject getBean(String modelName, String id) {
		System.out.println("getBean(" + modelName);
		Manager manager = getManager(modelName);
		String methodName = config.getGetMethod(modelName);
		try {
			Method method = manager.getClass().getMethod(methodName, new Class[] { String.class });
			if (method == null) {
				System.out.println("Method not found: " + methodName);
			}
			ModelObject model = (ModelObject) method.invoke(manager, id);
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

}
