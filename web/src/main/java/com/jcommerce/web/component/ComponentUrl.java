package com.jcommerce.web.component;

public class ComponentUrl {
    private String url;
    private int opennew;
    private String name;
    private int active = 0;
    

    public ComponentUrl(String url, String name, int opennew, int active) {
        this.name = name;
        this.url = url;
        this.opennew = opennew;
        this.active = active;
    }
    public ComponentUrl(String url, String name, int opennew) {
        this(url, name, opennew, 0);
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
	public int getOpennew() {
		return opennew;
	}
	public void setOpennew(int opennew) {
		this.opennew = opennew;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
}
