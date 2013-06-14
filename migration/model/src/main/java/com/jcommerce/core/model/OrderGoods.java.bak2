/**
 * Author: Bob Chen
 */

package com.jcommerce.core.model;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION , detachable="true")
public class OrderGoods extends ModelObject {
    
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
    private String id;
    
    @Persistent
    @Extension(vendorName="datanucleus", key="gae.pk-name", value="true")
    private String keyName;
	
    @Persistent
	private Order order;
    
    @Persistent
	private String goodsId;
    
    @Persistent
	private String goodsName;
    
    @Persistent
	private String goodsSN;
    
    @Persistent
	private int goodsNumber;
    
    @Persistent
	private double marketPrice;
    
    @Persistent
	private double goodsPrice;
    
    @Persistent
	private String goodsAttribute;
    
    @Persistent
	private int sendNumber;
    
    @Persistent
	private boolean realGoods;
    
    @Persistent
	private boolean gift;
    
    @Persistent
	private String extensionCode;
    
    @Persistent
	private String parentId;


    @Override
    public ModelObject getParent() {
    	return order;
    }


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getKeyName() {
		return keyName;
	}


	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}


	public Order getOrder() {
		return order;
	}


	public void setOrder(Order order) {
		this.order = order;
	}


	public String getGoodsId() {
		return goodsId;
	}


	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}


	public String getGoodsName() {
		return goodsName;
	}


	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}


	public String getGoodsSN() {
		return goodsSN;
	}


	public void setGoodsSN(String goodsSN) {
		this.goodsSN = goodsSN;
	}


	public int getGoodsNumber() {
		return goodsNumber;
	}


	public void setGoodsNumber(int goodsNumber) {
		this.goodsNumber = goodsNumber;
	}


	public double getMarketPrice() {
		return marketPrice;
	}


	public void setMarketPrice(double marketPrice) {
		this.marketPrice = marketPrice;
	}


	public double getGoodsPrice() {
		return goodsPrice;
	}


	public void setGoodsPrice(double goodsPrice) {
		this.goodsPrice = goodsPrice;
	}


	public String getGoodsAttribute() {
		return goodsAttribute;
	}


	public void setGoodsAttribute(String goodsAttribute) {
		this.goodsAttribute = goodsAttribute;
	}


	public int getSendNumber() {
		return sendNumber;
	}


	public void setSendNumber(int sendNumber) {
		this.sendNumber = sendNumber;
	}


	public boolean isRealGoods() {
		return realGoods;
	}


	public void setRealGoods(boolean realGoods) {
		this.realGoods = realGoods;
	}


	public boolean isGift() {
		return gift;
	}


	public void setGift(boolean gift) {
		this.gift = gift;
	}


	public String getExtensionCode() {
		return extensionCode;
	}


	public void setExtensionCode(String extensionCode) {
		this.extensionCode = extensionCode;
	}


	public String getParentId() {
		return parentId;
	}


	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
    

}
