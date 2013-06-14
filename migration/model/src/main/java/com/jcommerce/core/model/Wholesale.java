/**
 * @author KingZhao
 */

package com.jcommerce.core.model;

public class Wholesale extends ModelObject {

	private Goods goods;
	private String goodsName;
	private String rankIds;
	private String prices;
	private boolean enabled;

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getRankIds() {
		return rankIds;
	}

	public void setRankIds(String rankIds) {
		this.rankIds = rankIds;
	}

	public String getPrices() {
		return prices;
	}

	public void setPrices(String prices) {
		this.prices = prices;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	

}
