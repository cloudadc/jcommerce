/**
 * Author: Bob Chen
 *         Kylin Soong	
 */

package com.jcommerce.core.model;

import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "affiliate_log", catalog = "ishop")
public class AffiliateLog extends ModelObject {
	
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

	private static final long serialVersionUID = -2396249717828311768L;
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

	@ManyToOne( cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY )
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = true )
	@JoinColumn(name = "order_id", nullable = true )
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

	@ManyToOne( cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY )
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = true )
	@JoinColumn(name = "user_id", nullable = true )
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

	@Basic( optional = true )
	@Column( name = "separate_type"  )
	public int getSeparateType() {
		return separateType;
	}

	public void setSeparateType(int separateType) {
		this.separateType = separateType;
	}

}
