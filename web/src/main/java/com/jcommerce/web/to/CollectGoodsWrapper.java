package com.jcommerce.web.to;

import java.util.Date;

import com.jcommerce.core.model.CollectGoods;
import com.jcommerce.core.model.Goods;
import com.jcommerce.core.model.ModelObject;
import com.jcommerce.gwt.client.util.URLConstants;
import com.jcommerce.web.util.WebFormatUtils;

public class CollectGoodsWrapper extends BaseModelWrapper implements URLConstants{

	private static final long serialVersionUID = -1287912993189223626L;
	CollectGoods collectGoods;
	Goods goods;
	
	protected Object getWrapped() {
		return getCollectGoods();
	}
	
	public CollectGoodsWrapper(ModelObject collectGood) {
		super();
		this.collectGoods = (CollectGoods) collectGood;
	}
	
	public CollectGoods getCollectGoods() {
		return collectGoods;
	}
	
	public String getUrl() {
		String goodsId = getCollectGoods().getGoods().getId();
		return "goods.action?id=" + goodsId;
	}
	
	public String getGoodsName() {
		Goods goods = getGoods();
		return goods.getName();
	}
	
	public String getPromotePrice() {
		Goods goods = getGoods();	
		if(!goods.isPromoted()) {
			return "";
		}
		else {
			//判断是否在促销时间内
			Long promoteEndTime = goods.getPromoteEnd().getTime();
			Long promoteStartTime = goods.getPromoteStart().getTime();
			Long nowTime = new Date().getTime();
			if(nowTime > promoteEndTime || nowTime < promoteStartTime) {
				return "";
			}
			else {
				return WebFormatUtils.priceFormat(goods.getPromotePrice());
			}
		}
	}
	
	public String getShopPrice() {
		Goods goods = getGoods();		
		return WebFormatUtils.priceFormat(goods.getShopPrice());
	}
	
	public String getGoodsId() {
		Goods goods = getGoods();
		return goods.getId();
	}
	
	public String getRecId() {
		return getCollectGoods().getId();
	}
	
	public String getIsAttention() {
		if(!getCollectGoods().isAttention()) {
			return null;
		}
		else
			return "";
	}
	
	public void setGoods(Goods goods) {
		this.goods = goods;
	}
	public Goods getGoods() {
		return goods;
	}
//	public Goods getGoods() {
//		String goodsId = getCollectGood().getGoodsId();
//		Goods goods = (Goods) manager.get(ModelNames.GOODS, goodsId);
//		return goods;
//	}
}
