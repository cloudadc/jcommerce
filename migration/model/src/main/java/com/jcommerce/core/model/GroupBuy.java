package com.jcommerce.core.model;

import java.sql.Timestamp;

public class GroupBuy extends ModelObject {

	private String goodName;
	
	private Timestamp startTime;
	
	private Timestamp endTime;
	
	private String activityState;
	
	private double secPrice;
	
	private int limitNum;
	
	private int score;
	
	private String priceRange;
	
	private String description;

	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
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

	public double getSecPrice() {
		return secPrice;
	}

	public void setSecPrice(double secPrice) {
		this.secPrice = secPrice;
	}

	public int getLimitNum() {
		return limitNum;
	}

	public void setLimitNum(int limitNum) {
		this.limitNum = limitNum;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getPriceRange() {
		return priceRange;
	}

	public void setPriceRange(String priceRange) {
		this.priceRange = priceRange;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getActivityState() {
		return activityState;
	}

	public void setActivityState(String activityState) {
		this.activityState = activityState;
	}
}
