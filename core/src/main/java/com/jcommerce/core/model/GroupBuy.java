/**
 * Author: Kylin Soong
 */
package com.jcommerce.core.model;

import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "groupBuy", catalog = "ishop")
public class GroupBuy extends ModelObject {
	
private String id;
    
	@Id 
	@Basic( optional = false )
	@Column( name = "id", nullable = false, length = 32  )
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

	private static final long serialVersionUID = 2557812015704568869L;

	private String goodName;
	
	private Timestamp startTime;
	
	private Timestamp endTime;
	
	private String activityState;
	
	private double secPrice;
	
	private int limitNum;
	
	private int score;
	
	private String priceRange;
	
	private String description;

	@Basic( optional = true )
	@Column( length = 255  )
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

	@Basic( optional = true )
	@Column( length = 255  )
	public String getPriceRange() {
		return priceRange;
	}

	public void setPriceRange(String priceRange) {
		this.priceRange = priceRange;
	}

	@Basic( optional = true )
	@Column( length = 255  )
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Basic( optional = true )
	@Column( length = 255  )
	public String getActivityState() {
		return activityState;
	}

	public void setActivityState(String activityState) {
		this.activityState = activityState;
	}
}
