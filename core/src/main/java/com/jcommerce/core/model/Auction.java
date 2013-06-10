package com.jcommerce.core.model;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

public class Auction extends ModelObject {

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

	public String getAuctionname() {
		return auctionname;
	}

	public void setAuctionname(String auctionname) {
		this.auctionname = auctionname;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

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

	public Set<AuctionLog> getAuctionLogs() {
		return auctionLogs;
	}

	public void setAuctionLogs(Set<AuctionLog> auctionLogs) {
		this.auctionLogs = auctionLogs;
	}
}
