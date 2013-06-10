/**
* Author: Bob Chen
*/

package com.jcommerce.core.service;

import java.util.List;

import com.jcommerce.core.model.AuctionLog;
import com.jcommerce.core.service.Criteria;
public interface AuctionLogManager extends Manager {
    public AuctionLog initialize(AuctionLog obj);

    public List<AuctionLog> getAuctionLogList(int firstRow, int maxRow);

    public int getAuctionLogCount(Criteria criteria);

    public List<AuctionLog> getAuctionLogList(Criteria criteria);

    public List<AuctionLog> getAuctionLogList(int firstRow, int maxRow, Criteria criteria);

    public List<AuctionLog> getAuctionLogList();

    public AuctionLog getAuctionLog(String id);

    public void saveAuctionLog(AuctionLog obj);

    public void removeAuctionLog(String id);
}
