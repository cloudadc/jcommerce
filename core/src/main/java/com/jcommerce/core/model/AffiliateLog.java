/**
 * Author: Bob Chen
 */

package com.jcommerce.core.model;

import java.sql.Timestamp;

public class AffiliateLog extends ModelObject {

	public static final int SEPARATE_BY_REGISTER = 0; // 推荐注册分成
	public static final int SEPARATE_BY_ORDER = 1; // 推荐订单分成

	private Order order;
	private Timestamp time;
	private User user;
	private double money;
	private int points;
	/**
	 * 分成类型
	 */
	private int separateType;

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getSeparateType() {
		return separateType;
	}

	public void setSeparateType(int separateType) {
		this.separateType = separateType;
	}

}
