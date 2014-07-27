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
public class WSAction extends Action {

	/**
	 * @param ctx
	 * @param config
	 */
	public WSAction(ApplicationContext ctx, BeanConfig config) {
		super(ctx, config);
		// TODO Auto-generated constructor stub
	}

	public String getId(ModelObject obj) {
		return super.getId(obj);
	}

}
