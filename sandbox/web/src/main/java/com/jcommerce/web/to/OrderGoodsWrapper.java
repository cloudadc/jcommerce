package com.jcommerce.web.to;

import com.jcommerce.core.model.ModelObject;
import com.jcommerce.core.model.OrderGoods;

public class OrderGoodsWrapper extends BaseModelWrapper {

	private static final long serialVersionUID = -4871881541724575837L;
	private OrderGoods orderGoods;

	protected Object getWrapped() {
		return getOrderGoods();
	}
	
	public OrderGoodsWrapper(ModelObject orderGoods) {
		this.orderGoods = (OrderGoods)orderGoods;
	}
	
	public OrderGoods getOrderGoods() {
		return orderGoods;
	}
	
	public OrderGoods getParent() {
		return getOrderGoods().getParent();
	}
	
	public boolean getIsGift() {
		return getOrderGoods().isGift();
	}
}
