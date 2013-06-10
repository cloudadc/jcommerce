package com.jcommerce.core.dao.impl;

import java.util.List;

import com.jcommerce.core.dao.AuctionDAO;
import com.jcommerce.core.model.Auction;

public class AuctionDAOImpl extends DAOImpl implements AuctionDAO {

	 public AuctionDAOImpl() {
	        modelClass = Auction.class;
	    }

	    public List<Auction> getAuctionList() {
	        return getList();
	    }

	    public Auction getAuction(String id) {
	        return (Auction)getById(id);
	    }

	    public void saveAuction(Auction obj) {
	        save(obj);
	    }

	    public void removeAuction(String id) {
	        deleteById(id);
	    }

}
