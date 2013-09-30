package com.jcommerce.core.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcommerce.core.dao.AuctionDAO;
import com.jcommerce.core.model.Auction;
import com.jcommerce.core.service.AuctionManager;
import com.jcommerce.core.service.Criteria;

@Service("auctionManager")
public class AuctionManagerImpl extends ManagerImpl implements AuctionManager {

	private static Log log = LogFactory.getLog(AuctionManagerImpl.class);
	
	@Autowired
    private AuctionDAO dao;

    public void setAuctionDAO(AuctionDAO dao) {
        this.dao = dao;
        super.setDao(dao);
    }

    public Auction initialize(Auction obj) {
        if (obj != null && !Hibernate.isInitialized(obj)) {
            obj = dao.getAuction(obj.getId());
        }
        return obj;
    }

    public List<Auction> getAuctionList(int firstRow, int maxRow) {
        List list = getList(firstRow, maxRow);
        return (List<Auction>)list;
    }

    public int getAuctionCount(Criteria criteria) {
        return getCount(criteria);
    }

    public List<Auction> getAuctionList(Criteria criteria) {
        List list = getList(criteria);
        return (List<Auction>)list;
    }

    public List<Auction> getAuctionList(int firstRow, int maxRow, Criteria criteria) {
        List list = getList(firstRow, maxRow, criteria);
        return (List<Auction>)list;
    }

    public List<Auction> getAuctionList() {
        return dao.getAuctionList();
    }

    public Auction getAuction(String id) {
        Auction obj = dao.getAuction(id);
        return obj;
    }

    public void saveAuction(Auction obj) {
        dao.saveAuction(obj);
    }

    public void removeAuction(String id) {
        dao.removeAuction(id);
    }


}
