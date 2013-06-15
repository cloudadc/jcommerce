/**
 * @author KingZhao
 *         Kylin Soong
 */
package com.jcommerce.core.model;

import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "snatch_log", catalog = "ishop")
public class SnatchLog extends ModelObject {

	private static final long serialVersionUID = 1386419836960171448L;
	private int snatchId;
	private int userId;
	private double bidPrice;
	private Timestamp bidTime;
	
	private Snatch snatch;

	@Basic( optional = true )
	@Column( name = "snatch_id"  )
	public int getSnatchId() {
		return snatchId;
	}

	public void setSnatchId(int snatchId) {
		this.snatchId = snatchId;
	}

	@Basic( optional = true )
	@Column( name = "user_id"  )
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Basic( optional = true )
	@Column( name = "bid_price"  )
	public double getBidPrice() {
		return bidPrice;
	}

	public void setBidPrice(double bidPrice) {
		this.bidPrice = bidPrice;
	}

	@Basic( optional = true )
	@Column( name = "bid_time"  )
	public Timestamp getBidTime() {
		return bidTime;
	}

	public void setBidTime(Timestamp bidTime) {
		this.bidTime = bidTime;
	}

	@ManyToOne( cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY )
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = true )
	@JoinColumn(name = "snatchid", nullable = true )
	public Snatch getSnatch() {
		return snatch;
	}

	public void setSnatch(Snatch snatch) {
		this.snatch = snatch;
	}

}
