package com.jcommerce.web.to;

import java.lang.reflect.Method;

import com.jcommerce.core.model.ModelObject;
import com.jcommerce.core.wrapper.BaseWrapper;
import com.jcommerce.gwt.client.model.IModelObject;

public abstract class BaseModelWrapper extends BaseWrapper {
	
	public void debug(String s) {
		System.out.println(" in [BaseModelWrapper]: "+s );
	}
	protected abstract Object getWrapped();
	
	public String getPkId() {
		return ((ModelObject)getWrapped()).getId();
	}
	
	public String getPkId2() {
		// this works. but the method names should not be same as getPkId()
		return (String)get(IModelObject.ID);
	}
	
	@Override
	public Object get(String key) {

			Object res = null;
			if(values.containsKey(key)) {
				// can intentionally be mapped to null
				res = values.get(key);
			}
			else {
				try {
					String temp = key.substring(0, 1).toUpperCase()+key.substring(1);;
					String methodName = "get"+temp;
					Method method = null;
					try {
						method = this.getClass().getMethod(methodName, new Class[0]);
					} catch (NoSuchMethodException e) {
						method = getWrapped().getClass().getMethod(methodName, new Class[0]);
					}
					
					res = method.invoke(getWrapped(), new Object[0]);
					
				} catch (Exception ex) {
					debug("TODO: "+key);
					res = null;
				}
				
				if(res == null) {
//					 we allow a null value
					res = "TODO:(blank)"+key;
				}
			}


			return res;
			

	}
	

	
}
