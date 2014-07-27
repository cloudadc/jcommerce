package com.jcommerce.core.dao;

import java.util.List;

import com.jcommerce.core.model.Auction;

public interface AuctionDAO extends DAO {

	public List<Auction> getAuctionList();

    public Auction getAuction(Long id);

    public void saveAuction(Auction obj);

    public void removeAuction(Long id);
}
