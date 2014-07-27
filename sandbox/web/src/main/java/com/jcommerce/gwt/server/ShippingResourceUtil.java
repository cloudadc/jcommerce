/**
 * Author: Kylin Soong
 */

package com.jcommerce.gwt.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jcommerce.core.module.ModuleManager;
import com.jcommerce.core.module.shipping.IShippingModule;
import com.jcommerce.gwt.client.form.BeanObject;

public class ShippingResourceUtil {
	
	private static List<BeanObject> shippings = null;

	public static List<BeanObject> getShippingBeanObjects() {
		
		if(shippings == null) {
			shippings = new ArrayList<BeanObject>();

			Map<String, Object> maps = new HashMap<String, Object>();
			ModuleManager manager = new ModuleManager();
			List<IShippingModule> lists = manager.getShippings();
			if (lists == null) {
			    lists = new ArrayList<IShippingModule>();
			}
			// FIXME: why 100?
			int i =100;
			for(IShippingModule module : lists){
				String id = "" + i;
				maps.put("id", id);
				String name = module.getAuthor();
				if(name != null) {
					maps.put("name", name);
				}
				String code = module.getVersion();
				if(code != null) {
					maps.put("code", code);
				}
				String description = module.getDescription();
				if(description != null) {
					maps.put("description", description);
				}
				String insure = "0";
				maps.put("insure", insure);
				boolean supportCod = false;
				maps.put("supportCod", supportCod);
				boolean enabled = false;
				maps.put("enabled", enabled);
				shippings.add(new BeanObject("Shipping",maps));
				i++;
			}
			
		}
		return shippings;
	}

}
