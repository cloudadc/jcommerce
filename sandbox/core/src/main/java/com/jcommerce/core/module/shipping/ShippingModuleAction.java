/**
 * Author: Kylin Soong
 */

package com.jcommerce.core.module.shipping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jcommerce.core.module.ModuleAction;
import com.jcommerce.core.module.shipping.IShippingModule;

public class ShippingModuleAction extends ModuleAction{
	
	private static ShippingModuleAction instance = null;
	
	public static ShippingModuleAction getInstance(){
		if(instance == null) {
			instance = new ShippingModuleAction();
		}
		return instance;
	}
	
	private static List<Map<String, Object>>  shippings = null;

	public List<Map<String, Object>> getModuleInfo() {
		
		if(shippings == null) {
			shippings = new ArrayList<Map<String, Object>>();
			Map<String, Object> maps = new HashMap<String, Object>();

			List<IShippingModule> lists = getManager().getShippings();
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
				Map<String,String> data = module.getConfigMetaData(module.getDefaultConfig()).getFieldValues();
				if(data != null){
					maps.put("confdata", data);
				}
				String defaultconf = module.getDefaultConfig();
				if(defaultconf != null) {
					maps.put("defaultconf", defaultconf);
				}
				String insure = "0";
				maps.put("insure", insure);
				boolean supportCod = true;
				maps.put("supportCod", supportCod);
				boolean enabled = false;
				maps.put("enabled", enabled);
				shippings.add(maps);
				i++;
			}
			return shippings;
		} 
		return shippings;
	}

	public boolean updateModuleInfo(List<Map<String, Object>> beanLists) {
		if(shippings == null) {
			throw new RuntimeException("update module info exception");
		}
		for(Map<String, Object> bean : beanLists) {
			updateShippings((String)bean.get(IShipping.NAME), bean);
		}
		return false;
	}

	private void updateShippings(String string, Map<String, Object> bean) {
		for(Map<String, Object> shipping : shippings){
			if(shipping.equals(shipping.get(IShipping.NAME))){
				shipping.put(IShipping.DESCRIPTION, bean.get(IShipping.DESCRIPTION));
				shipping.put(IShipping.INSURE, bean.get(IShipping.INSURE));
				shipping.put(IShipping.SUPPORTCOD,  bean.get(IShipping.INSURE));
			}
		}
	}

//	private void update(File moduleFile, List<BeanObject> beanLists) {
//		File[] zipFiles = moduleFile.listFiles();
//		for(File file : zipFiles) {
//			 if(file.getName().endsWith(".zip")){
//				 parseZipFile(file);
//			 }
//		}
//		for(BeanObject bean : beanLists){
//			
//		}
//	}
//
//	private void parseZipFile(File file) {
//		 try {
//			ZipFile zipFile = new ZipFile(file);
//			Enumeration entries = zipFile.entries();
//			  while (entries.hasMoreElements()) {
//		            ZipEntry entry = (ZipEntry) entries.nextElement();
//
//		            if (entry.isDirectory()) {
//		                continue;
//		            }
//
//		            String name = entry.getName();
//		            if (name.endsWith(".properties")) {
//		            	
//		                System.out.println(name);
//		            }
//		        }
//
//		        zipFile.close();
//		} catch (ZipException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	public static void main(String[] args){
		List<Map<String, Object>> beanLists = ShippingModuleAction.getInstance().getModuleInfo();
		ShippingModuleAction.getInstance().updateModuleInfo(beanLists);
//		PropertyReader pr = new EMS().getpReader();
//		System.out.println(pr.getValue(IShippingConstants.NAME));
//		pr.setValue(IShippingConstants.NAME, "fucking");
//		System.out.println(pr.getValue(IShippingConstants.NAME));
	}

	interface IShipping {
	    public static final String ID = "id";
	    public static final String NAME = "name";
	    public static final String CODE = "code";
	    public static final String DESCRIPTION = "description";
	    public static final String INSURE = "insure";
	    public static final String SUPPORTCOD = "supportCod";
	    public static final String ENABLED = "enabled";

	}
}
