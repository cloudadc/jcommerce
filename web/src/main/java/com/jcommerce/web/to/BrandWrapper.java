package com.jcommerce.web.to;

import com.jcommerce.core.model.Brand;
import com.jcommerce.core.model.ModelObject;
import com.jcommerce.gwt.client.util.URLConstants;

public class BrandWrapper extends BaseModelWrapper implements URLConstants{

	private static final long serialVersionUID = -1489148495634202565L;
	Brand brand;
	@Override
	protected Object getWrapped() {
		return getBrand();
	}
	public BrandWrapper(ModelObject brand) {
		super();
		this.brand = (Brand)brand;
	}
	
	public Brand getBrand() {
		return brand;
	}
	
    public String getBrandId() {
    	return getBrand().getId();
    }

    public String getBrandDesc() {
        return getBrand().getDescription();
    }
    
    public String getUrl() {
    	return ACTION_BRAND+ getBrand().getId();    	
    }
}
