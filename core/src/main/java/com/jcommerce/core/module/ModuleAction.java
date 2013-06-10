package com.jcommerce.core.module;

import java.util.List;
import java.util.Map;

public abstract class ModuleAction {

	private ModuleManager manager;
	
	public ModuleAction(){
		manager = new ModuleManager();
	}
	
	public ModuleManager getManager() {
		return manager;
	}
	
	public abstract List<Map<String, Object>> getModuleInfo();
	
	public abstract boolean updateModuleInfo(List<Map<String, Object>> beanLists);
}
