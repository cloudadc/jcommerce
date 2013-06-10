/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import com.jcommerce.core.dao.AuctionLogDAO;
import com.jcommerce.core.model.AuctionLog;

public class AuctionLogDAOImpl extends DAOImpl implements AuctionLogDAO {
    public AuctionLogDAOImpl() {
        modelClass = AuctionLog.class;
    }

    public List<AuctionLog> getAuctionLogList() {
        return getList();
    }

    public AuctionLog getAuctionLog(String id) {
        return (AuctionLog)getById(id);
    }

    public void saveAuctionLog(AuctionLog obj) {
        save(obj);
    }

    public void removeAuctionLog(String id) {
        deleteById(id);
    }
}
