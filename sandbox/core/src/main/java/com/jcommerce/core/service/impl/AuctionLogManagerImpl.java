/**
* Author: Bob Chen
*/

package com.jcommerce.core.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcommerce.core.dao.AuctionLogDAO;
import com.jcommerce.core.model.AuctionLog;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.AuctionLogManager;

@Service("AuctionLogManager")
public class AuctionLogManagerImpl extends ManagerImpl implements AuctionLogManager {
    private static Log log = LogFactory.getLog(AuctionLogManagerImpl.class);
    
    @Autowired
    private AuctionLogDAO dao;

    public void setAuctionLogDAO(AuctionLogDAO dao) {
        this.dao = dao;
        super.setDao(dao);
    }

    public AuctionLog initialize(AuctionLog obj) {
        if (obj != null && !Hibernate.isInitialized(obj)) {
            obj = dao.getAuctionLog(obj.getId());
        }
        return obj;
    }

    public List<AuctionLog> getAuctionLogList(int firstRow, int maxRow) {
        List list = getList(firstRow, maxRow);
        return (List<AuctionLog>)list;
    }

    public int getAuctionLogCount(Criteria criteria) {
        return getCount(criteria);
    }

    public List<AuctionLog> getAuctionLogList(Criteria criteria) {
        List list = getList(criteria);
        return (List<AuctionLog>)list;
    }

    public List<AuctionLog> getAuctionLogList(int firstRow, int maxRow, Criteria criteria) {
        List list = getList(firstRow, maxRow, criteria);
        return (List<AuctionLog>)list;
    }

    public List<AuctionLog> getAuctionLogList() {
        return dao.getAuctionLogList();
    }

    public AuctionLog getAuctionLog(Long id) {
        AuctionLog obj = dao.getAuctionLog(id);
        return obj;
    }

    public void saveAuctionLog(AuctionLog obj) {
        dao.saveAuctionLog(obj);
    }

    public void removeAuctionLog(Long id) {
        dao.removeAuctionLog(id);
    }
}
