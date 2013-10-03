/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jcommerce.core.dao.AuctionLogDAO;
import com.jcommerce.core.model.AuctionLog;

@Repository
@SuppressWarnings("unchecked")
public class AuctionLogDAOImpl extends DAOImpl implements AuctionLogDAO {
    public AuctionLogDAOImpl() {
        modelClass = AuctionLog.class;
    }

    public List<AuctionLog> getAuctionLogList() {
        return getList();
    }

    public AuctionLog getAuctionLog(Long id) {
        return (AuctionLog)getById(id);
    }

    public void saveAuctionLog(AuctionLog obj) {
        save(obj);
    }

    public void removeAuctionLog(Long id) {
        deleteById(id);
    }
}
