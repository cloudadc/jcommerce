/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao;

import java.util.List;

import com.jcommerce.core.model.AuctionLog;

public interface AuctionLogDAO extends DAO {
    public List<AuctionLog> getAuctionLogList();

    public AuctionLog getAuctionLog(String id);

    public void saveAuctionLog(AuctionLog obj);

    public void removeAuctionLog(String id);
}
