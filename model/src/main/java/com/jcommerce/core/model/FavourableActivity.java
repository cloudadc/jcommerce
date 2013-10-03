/**
 * @author KingZhao
 *         Kylin Soong
 */
package com.jcommerce.core.model;

import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "favourable_activity")
public class FavourableActivity extends ModelObject {
	
	private Long id;
	
	@Id 
	@GeneratedValue
	@Basic(optional = false)
	@Column(name = "id", nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private static final long serialVersionUID = -6115458983506628497L;
	public static final int ACTRANGE_ALL = 0;//所有商品优惠
	public static final int ACTRANGE_FOLLOWCATEGORY = 1;//以下类别优惠
	public static final int ACTRANGE_FOLLOWBRAND = 2;//以下品牌优惠
	public static final int ACTRANGE_FOLLOWGOODS = 3;//以下商品优惠
	public static final int ACTTYPE_GIVEGIFT=0;//优惠方式赠品
	public static final int ACTTYPE_REDUCEMONEY=1;//优惠方式减免现金
	public static final int ACTTYPE_DISCOUNT=2;//优惠方式打折
	
	private String actName;
	private Timestamp startTime;
	private Timestamp endTime;
	private String userRank;
	private int actRange;//优惠范围
	private String actRangeExt;
	private double minAmount;
	private double maxAmount;
	private int actType;//优惠方式
	private double actTypeExt;
	private String gift;
	private int sortOrder;

	@Basic( optional = true )
	@Column( name = "act_name", length = 255  )
	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}

	@Basic( optional = true )
	@Column( name = "start_time"  )
	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	@Basic( optional = true )
	@Column( name = "end_time"  )
	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	@Basic( optional = true )
	@Column( name = "user_rank", length = 255  )
	public String getUserRank() {
		return userRank;
	}

	public void setUserRank(String userRank) {
		this.userRank = userRank;
	}

	@Basic( optional = true )
	@Column( name = "act_range"  )
	public int getActRange() {
		return actRange;
	}

	public void setActRange(int actRange) {
		this.actRange = actRange;
	}

	@Basic( optional = true )
	@Column( name = "act_range_ext", length = 255  )
	public String getActRangeExt() {
		return actRangeExt;
	}

	public void setActRangeExt(String actRangeExt) {
		this.actRangeExt = actRangeExt;
	}

	@Basic( optional = true )
	@Column( name = "min_amount"  )
	public double getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(double minAmount) {
		this.minAmount = minAmount;
	}

	@Basic( optional = true )
	@Column( name = "max_amount"  )
	public double getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(double maxAmount) {
		this.maxAmount = maxAmount;
	}

	@Basic( optional = true )
	@Column( name = "act_type"  )
	public int getActType() {
		return actType;
	}

	public void setActType(int actType) {
		this.actType = actType;
	}

	@Basic( optional = true )
	@Column( name = "act_type_ext"  )
	public double getActTypeExt() {
		return actTypeExt;
	}

	public void setActTypeExt(double actTypeExt) {
		this.actTypeExt = actTypeExt;
	}

	@Basic( optional = true )
	@Column( length = 2147483647  )
	public String getGift() {
		return gift;
	}

	public void setGift(String gift) {
		this.gift = gift;
	}

	@Basic( optional = true )
	@Column( name = "sort_order"  )
	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

}
