/**
 * @author KingZhao
 *         Kylin Soong
 */

package com.jcommerce.core.model;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "wholesale", catalog = "ishop")
public class Wholesale extends ModelObject {

	private static final long serialVersionUID = 1911438290059395798L;
	private Goods goods;
	private String goodsName;
	private String rankIds;
	private String prices;
	private boolean enabled;

	@ManyToOne( cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY )
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = true )
	@JoinColumn(name = "goods_id", nullable = true )
	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	@Basic( optional = true )
	@Column( name = "goods_name", length = 255  )
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	@Basic( optional = true )
	@Column( name = "rank_ids", length = 255  )
	public String getRankIds() {
		return rankIds;
	}

	public void setRankIds(String rankIds) {
		this.rankIds = rankIds;
	}

	@Basic( optional = true )
	@Column( length = 255  )
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
