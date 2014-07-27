/**
 * 
 */
package com.jcommerce.core.action;

import org.springframework.context.ApplicationContext;

import com.jcommerce.core.model.ModelObject;

/**
 * @author dell
 *
 */
public class WSCreateAction extends WSAction {

	/**
	 * @param ctx
	 * @param config
	 */
	public WSCreateAction(ApplicationContext ctx, BeanConfig config) {
		super(ctx, config);
	}
	
	public String newObject(String modelName, ModelObject model) {
		System.out.println("newObject(" + modelName + ")");
		try {
			String id = saveObject(modelName, model);
			System.out.println("-----successful-------id is " + id);
			return id;
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return null;
	}

}
