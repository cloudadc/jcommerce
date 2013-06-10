package com.jcommerce.web.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Navigator {
    List<ComponentUrl> top;
    List<ComponentUrl> middle;
    List<ComponentUrl> bottom;
    Map<String, Object> config = new HashMap<String, Object>();
    
    public Navigator() {
        top = new ArrayList<ComponentUrl>();
        middle = new ArrayList<ComponentUrl>();
        bottom = new ArrayList<ComponentUrl>();
    }

    public void addTop(ComponentUrl url) {
        top.add(url);
    }
    public void addMiddle(ComponentUrl url) {
        middle.add(url);
    }
    public void addBottom(ComponentUrl url) {
    	bottom.add(url);
    }
    public List<ComponentUrl> getTop() {
        return top;
    }
    public void setTop(List<ComponentUrl> top) {
        this.top = top;
    }
    public List<ComponentUrl> getMiddle() {
        return middle;
    }
    public void setMiddle(List<ComponentUrl> middle) {
        this.middle = middle;
    }
	public Map<String, Object> getConfig() {
		return config;
	}
	public void setConfig(Map<String, Object> config) {
		this.config = config;
	}

	public List<ComponentUrl> getBottom() {
		return bottom;
	}

	public void setBottom(List<ComponentUrl> bottom) {
		this.bottom = bottom;
	}


}
