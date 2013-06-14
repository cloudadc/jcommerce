package com.jcommerce.core.model;

public class GroupGoods extends ModelObject {

	private Goods parent;
	private Goods goods;
	private double goodsPrice;

	public Goods getParent() {
		return parent;
	}

	public void setParent(Goods parent) {
		this.parent = parent;
	}

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public double getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(double goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

}
