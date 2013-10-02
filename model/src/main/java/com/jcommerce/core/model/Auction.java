/**
 * Author: Kylin Soong
 *        
 */

package com.jcommerce.core.model;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "auction")
public class Auction extends ModelObject {
	
private String id;
    
	@Id 
	@GeneratedValue
	@Basic( optional = false )
	@Column( name = "id", nullable = false, length = 32  )
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

	private static final long serialVersionUID = 7966006913768565193L;

	private String auctionname;
	
	private String description;
	
	private String goodName;
	
	private Timestamp startTime;
	
	private Timestamp endTime;
	
	private double startPrice;
	
	private double dirPrice;
	
	private double addRange;
	
	private double secPrice;
	
	private Set<AuctionLog> auctionLogs = new HashSet<AuctionLog>();

	@Basic( optional = true )
	@Column( length = 255  )
	public String getAuctionname() {
		return auctionname;
	}

	public void setAuctionname(String auctionname) {
		this.auctionname = auctionname;
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

	public double getStartPrice() {
		return startPrice;
	}

	public void setStartPrice(double startPrice) {
		this.startPrice = startPrice;
	}

	public double getDirPrice() {
		return dirPrice;
	}

	public void setDirPrice(double dirPrice) {
		this.dirPrice = dirPrice;
	}

	public double getAddRange() {
		return addRange;
	}

	public void setAddRange(double addRange) {
		this.addRange = addRange;
	}

	public double getSecPrice() {
		return secPrice;
	}

	public void setSecPrice(double secPrice) {
		this.secPrice = secPrice;
	}

	@OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "auctionlogs"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( nullable = false  )
	public Set<AuctionLog> getAuctionLogs() {
		return auctionLogs;
	}
	
	public void addAuctionLog(AuctionLog auctionLog) {
		auctionLog.setAuctionlogs(this);
		this.auctionLogs.add(auctionLog);
	}

	public void setAuctionLogs(Set<AuctionLog> auctionLogs) {
		this.auctionLogs = auctionLogs;
	}
}
