/**
* @Author: KingZhao
*/

package com.jcommerce.core.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcommerce.core.dao.VoteLogDAO;
import com.jcommerce.core.model.VoteLog;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.VoteLogManager;

@Service("VoteLogManager")
public class VoteLogManagerImpl extends ManagerImpl implements VoteLogManager {
    private static Log log = LogFactory.getLog(VoteLogManagerImpl.class);
    
    @Autowired
    private VoteLogDAO dao;

    public void setVoteLogDAO(VoteLogDAO dao) {
        this.dao = dao;
        super.setDao(dao);
    }

    public VoteLog initialize(VoteLog obj) {
        if (obj != null && !Hibernate.isInitialized(obj)) {
            obj = dao.getVoteLog(obj.getId());
        }
        return obj;
    }

    public List<VoteLog> getVoteLogList(int firstRow, int maxRow) {
        List list = getList(firstRow, maxRow);
        return (List<VoteLog>)list;
    }

    public int getVoteLogCount(Criteria criteria) {
        return getCount(criteria);
    }

    public List<VoteLog> getVoteLogList(Criteria criteria) {
        List list = getList(criteria);
        return (List<VoteLog>)list;
    }

    public List<VoteLog> getVoteLogList(int firstRow, int maxRow, Criteria criteria) {
        List list = getList(firstRow, maxRow, criteria);
        return (List<VoteLog>)list;
    }

    public List<VoteLog> getVoteLogList() {
        return dao.getVoteLogList();
    }

    public VoteLog getVoteLog(String id) {
        VoteLog obj = dao.getVoteLog(id);
        return obj;
    }

    public void saveVoteLog(VoteLog obj) {
        dao.saveVoteLog(obj);
    }

    public void removeVoteLog(String id) {
        dao.removeVoteLog(id);
    }
}
