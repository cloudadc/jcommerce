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
@Table(name = "auction_log", catalog = "ishop")
public class AuctionLog extends ModelObject {

	private static final long serialVersionUID = 6954476973488375898L;
	private int actId;
	private int bidUser;
	private double bidPrice;
	private Timestamp bidTime;
	
	private Auction auctionlogs;

	@Basic( optional = true )
	@Column( name = "act_id"  )
	public int getActId() {
		return actId;
	}

	public void setActId(int actId) {
		this.actId = actId;
	}

	@Basic( optional = true )
	@Column( name = "bid_user"  )
	public int getBidUser() {
		return bidUser;
	}

	public void setBidUser(int bidUser) {
		this.bidUser = bidUser;
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
	@JoinColumn(name = "auctionLogs", nullable = true )
	public Auction getAuctionlogs() {
		return this.auctionlogs;
	}
	
	public void setAuctionlogs(final Auction auctionlogs) {
		this.auctionlogs = auctionlogs;
	}

}
