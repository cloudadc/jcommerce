package com.jcommerce.web.to;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import com.jcommerce.core.model.ModelObject;

public class WrapperUtil {
	
	public static List wrap(List list, Class wrapperClass) {
		List res = new ArrayList();
		try {
			Constructor m = wrapperClass.getConstructor(new Class[]{ModelObject.class});
			for(Object model:list) {
				res.add(m.newInstance(model));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		return res;
		
	}
	
	public static Object wrap(ModelObject obj, Class wrapperClass) {
		try {
			Constructor m = wrapperClass.getConstructor(new Class[]{ModelObject.class});
			return m.newInstance(obj);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
}
