/**
 * @author KingZhao
 */
package com.jcommerce.core.model;

import java.sql.Timestamp;

public class FavourableActivity extends ModelObject {

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

	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public String getUserRank() {
		return userRank;
	}

	public void setUserRank(String userRank) {
		this.userRank = userRank;
	}

	public int getActRange() {
		return actRange;
	}

	public void setActRange(int actRange) {
		this.actRange = actRange;
	}

	public String getActRangeExt() {
		return actRangeExt;
	}

	public void setActRangeExt(String actRangeExt) {
		this.actRangeExt = actRangeExt;
	}

	public double getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(double minAmount) {
		this.minAmount = minAmount;
	}

	public double getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(double maxAmount) {
		this.maxAmount = maxAmount;
	}

	public int getActType() {
		return actType;
	}

	public void setActType(int actType) {
		this.actType = actType;
	}

	public double getActTypeExt() {
		return actTypeExt;
	}

	public void setActTypeExt(double actTypeExt) {
		this.actTypeExt = actTypeExt;
	}

	public String getGift() {
		return gift;
	}

	public void setGift(String gift) {
		this.gift = gift;
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

}
