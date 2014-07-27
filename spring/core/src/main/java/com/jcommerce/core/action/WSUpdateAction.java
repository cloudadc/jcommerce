/**
 * 
 */
package com.jcommerce.core.action;

import org.springframework.context.ApplicationContext;

import com.jcommerce.core.model.ModelObject;

/**
 * @author david yang
 *
 */
public class WSUpdateAction extends WSAction {

	/**
	 * @param ctx
	 * @param config
	 */
	public WSUpdateAction(ApplicationContext ctx, BeanConfig config) {
		super(ctx, config);
		// TODO Auto-generated constructor stub
	}
	
	public boolean updateObject(String modelName, ModelObject model) {
		System.out.println("updateObject(" + modelName + ")");
		try {
			if (model != null) {
				String id = saveObject(modelName, model);
				return id != null;
			} else {
				return false;
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return false;
	}

}
