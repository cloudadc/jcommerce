package com.jcommerce.web.to;

import org.apache.commons.lang.StringUtils;

import com.jcommerce.core.model.Goods;
import com.jcommerce.core.model.ModelObject;
import com.jcommerce.gwt.client.util.URLConstants;
import com.jcommerce.web.util.WebFormatUtils;

public class GoodsWrapper extends BaseModelWrapper implements URLConstants{
	
	public static final String GOODS_BRAND = "goodsBrand";
	
	Goods goods;
	@Override
	protected Object getWrapped() {
		return getGoods();
	}
	public GoodsWrapper(ModelObject goods) {
		super();
		this.goods = (Goods)goods;
	}
	
	public Goods getGoods() {
		return goods;
	}
	
    // for template
    public String getId() {
    	return getGoods().getId();
    }
    
    public String getName() {
    	return getGoods().getName();
    }
    
    public String getImage() {
    	return SERVLET_IMAGE+getGoods().getImage();
    }
    public String getUrl() {
    	return ACTION_GOODS+getGoods().getId();    	
    }
    public String getShortStyleName() {
    	return getGoods().getName().length()>17? getGoods().getName().substring(0, 10)+"...":getGoods().getName();
    }
    public String getStyleName() {
    	return StringUtils.defaultIfEmpty(getGoods().getNameStyle(), getGoods().getName());
    }
    public String getMeasureUnit() {
    	return "TODO measureUnit";
    	
    }
    public String getBrandUrl() {
    	return ACTION_BRAND+getGoods().getBrand().getId();  
    }

    public String getFormatedShopPrice() {
    	return WebFormatUtils.priceFormat(getGoods().getShopPrice());
    }
    
    public double getBonusMoney() {
        if (getGoods().getBonusType() == null) {
            return 0;
        }
        return getGoods().getBonusType().getMoney();
    }
    
    public String getThumb() {
    	return SERVLET_IMAGE+getGoods().getThumb();
    }

    public double getPromotePrice() {
		return getGoods().getPromotePrice();
    }
    
    public boolean isPromoted() {
    	return getGoods().isPromoted();
    }
    
    public double getMarketPrice(){
    	return getGoods().getMarketPrice();
    }
        
    public double getShopPrice(){
    	return getGoods().getShopPrice();
    }
    
    public int getNumber() {
    	return getGoods().getNumber();
    }
    
    public int getClickCount() {
        return getGoods().getClickCount();
    }
    
    public double getWeight() {
        return getGoods().getWeight();
    }
    
    public String getSn() {
        return getGoods().getSN();
    }
    
    public String getDesc() {
        return getGoods().getDescription();
    }
    
    public String getType(){
    	return goods.getType().getId();
    }
    
    public String getShortName(){
    	return getShortStyleName();
    }
    
    public String getFormatedSubtotal() {
        // FIXME
        return "";
    }
    
    public String getFormatedGoodsPrice() {
        return WebFormatUtils.priceFormat(getPrice());
    }
    
    public String getFormatedMarketPrice() {
        return WebFormatUtils.priceFormat(getMarketPrice());
    }
    
    public String getAddTime() {
        return goods.getAddTime().toString();
    }
    
    public String getGmtEndTime() {
        return goods.getPromoteEnd() == null ? null : goods.getPromoteEnd().toGMTString();
    }
    
    public int getGiveIntegral() {
        return goods.getGiveIntegral();
    }
    
    public int getIntegral() {
        return goods.getIntegral();
    }
    
    public boolean isGift() {
        // FIXME
        return false;
    }
    
    public double getPrice() {
        if (isPromoted()) {
            return goods.getPromotePrice();
        } else {
            return goods.getShopPrice();
        }
    }
    
    public String getParentId() {
        // FIXME
        return "";
    }
}
