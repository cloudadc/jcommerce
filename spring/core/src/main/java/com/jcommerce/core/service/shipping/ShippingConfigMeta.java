package com.jcommerce.core.service.shipping;

import com.jcommerce.core.model.Shipping;

public class ShippingConfigMeta  extends Shipping{
	
	private boolean install;
	private String version;
	private String author;
	private String website;
	
	

	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}


	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public boolean getInstall() {
		return install;
	}
	public void setInstall(boolean install) {
		this.install = install;
	}

	

}
