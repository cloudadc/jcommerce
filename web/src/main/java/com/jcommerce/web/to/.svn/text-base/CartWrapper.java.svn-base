package com.jcommerce.web.to;

import com.jcommerce.core.model.Cart;
import com.jcommerce.core.model.ModelObject;
import com.jcommerce.gwt.client.util.URLConstants;
import com.jcommerce.web.util.WebFormatUtils;

public class CartWrapper extends BaseModelWrapper {

	Cart cart;
	double price;
	
	@Override
	protected Object getWrapped() {
		return getCart();
	}
	public CartWrapper(ModelObject cart) {
		super();
		this.cart = (Cart)cart;
	}
	
	public Cart getCart() {
		return cart;
	}
	
	public String getId() {
		return getCart().getId();
	}
	
    public String getThumb() {
        return URLConstants.SERVLET_IMAGE+getCart().getGoodsThumb();
    }
    
	public Cart getParent() {
		return getCart().getParent();
	}
	
	public boolean isGift() {
		return getCart().isGift();
	}
	
    public String getName() {
        return getCart().getGoodsName();
    }
    
    public int getNumber() {
        return getCart().getGoodsNumber();
    }
    
	public String getFormatedPrice() {	
		return WebFormatUtils.priceFormat(getCart().getGoodsPrice());
	}
	
	public String getFormatedSubtotal() {
		return WebFormatUtils.priceFormat(getCart().getGoodsPrice() * getCart().getGoodsNumber());
	}
	
    public String getFormatedMarketPrice(){
    	return WebFormatUtils.priceFormat(getCart().getMarketPrice());
    }
    
    public String getGoodsAttr(){
    	if( getCart().getGoodsAttribute() != null ){
    		return getCart().getGoodsAttribute();
    	}
    	else{
    		return "";
    	}    	
    }
}
