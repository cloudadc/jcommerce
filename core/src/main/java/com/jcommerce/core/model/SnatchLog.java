/**
 * @author KingZhao
 */
package com.jcommerce.core.model;

import java.sql.Timestamp;

public class SnatchLog extends ModelObject {

	private int snatchId;
	private int userId;
	private double bidPrice;
	private Timestamp bidTime;

	public int getSnatchId() {
		return snatchId;
	}

	public void setSnatchId(int snatchId) {
		this.snatchId = snatchId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public double getBidPrice() {
		return bidPrice;
	}

	public void setBidPrice(double bidPrice) {
		this.bidPrice = bidPrice;
	}

	public Timestamp getBidTime() {
		return bidTime;
	}

	public void setBidTime(Timestamp bidTime) {
		this.bidTime = bidTime;
	}

}
