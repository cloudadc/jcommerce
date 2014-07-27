package com.jcommerce.core.service;

import java.util.List;

import com.jcommerce.core.model.Auction;

public interface AuctionManager extends Manager {
	public Auction initialize(Auction obj);

    public List<Auction> getAuctionList(int firstRow, int maxRow);

    public int getAuctionCount(Criteria criteria);

    public List<Auction> getAuctionList(Criteria criteria);

    public List<Auction> getAuctionList(int firstRow, int maxRow, Criteria criteria);

    public List<Auction> getAuctionList();

    public Auction getAuction(Long id);

    public void saveAuction(Auction obj);

    public void removeAuction(Long id);
}
