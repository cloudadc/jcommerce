package com.jcommerce.gwt.client.panels.system;

import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IShipping;
import com.jcommerce.gwt.client.model.IShippingConfigMeta;

public class ShippingConfigMetaForm extends BeanObject implements IShipping, IShippingConfigMeta{
	public ShippingConfigMetaForm() {
		super(ModelNames.SHIPPING_META);
	}
    public void setID(String id) {
        set(ID, id);
    }
    public String getID() {
        return get(ID);
    }
	public void setShippingCode(String shippingCode) {
		set(CODE, shippingCode);
	}
	public String getShippingCode() {
		return get(CODE);
	}
	public void setShippingName(String shippingName) {
		set(NAME, shippingName);
	}
	public String getShippingName() {
		return get(NAME);
	}
	public void setShippingDesc(String shippingDesc) {
		set(DESCRIPTION, shippingDesc);
	}
	public String getShippingDesc() {
		return get(DESCRIPTION);
	}
	public void setSupportCod(boolean supportCod) {
		set(SUPPORTCOD, supportCod);
	}
	public boolean getSupportCod() {
		return (Boolean)get(SUPPORTCOD);
	}
	public void setAuthor(String author) {
		set(AUTHOR, author);
	}
	public String getAuthor() {
		return get(AUTHOR);
	}
	public void setWebsite(String website) {
		set(WEBSITE, website);
	}
	public String getWebsite() {
		return get(WEBSITE);
	}
	public void setVersion(String version) {
		set(VERSION, version);
	}
	public String getVersion() {
		return get(VERSION);
	}
	public void setInstall(boolean install) {
		set(INSTALL, install);
	}
	public boolean getInstall() {
		return (Boolean)get(INSTALL);
	}
//	public void setShippingPrint(String shippingPrint) {
//		set(PRINT, shippingPrint);
//	}
//	public String getShippingPrint() {
//		return get(PRINT);
//	}
}
