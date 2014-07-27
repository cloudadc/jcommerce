package com.jcommerce.core.wrapper;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class BaseWrapper implements Serializable{
	public void debug(String s) {
		System.out.println(" in [BaseWrapper]: "+s );
	}
	protected Map<String, Object> values = new HashMap<String, Object>();
	
	public String getString(String key) {
		Object v = get(key);
		String res = v==null? null : v.toString();
		return res;
	}
	public int getInt(String key) {
		Object v = get(key);
		int res = Integer.MIN_VALUE;
		if(v==null) {
		}
		else if(v instanceof String) {
			try {
				res = Integer.valueOf((String)v);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		else if(v instanceof Number) {
			res = ((Number)v).intValue();
		}
		return res;
			
	}
	
	
	public Object get(String key) {

		Object res = null;
		if(values.containsKey(key)) {
			// can intentionally be mapped to null
			res = values.get(key);
		}
		else {
			try {
				String temp = key.substring(0, 1).toUpperCase()+key.substring(1);;
				Method method = this.getClass().getMethod("get"+temp, new Class[0]);
				res = method.invoke(this, new Object[0]);
			} catch (Exception ex) {
				debug("TODO: "+key);
				res = null;
			}
		}
		if(res == null) {
			// default behavior
			res = key;
		}

		return res;
	}
	
	public void put(String key, Object value) {
		values.put(key, value);
	}
	
	public void clear() {
		values.clear();
	}
	
	
}
