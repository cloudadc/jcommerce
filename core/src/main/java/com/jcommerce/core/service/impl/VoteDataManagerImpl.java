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

import com.jcommerce.core.dao.VoteDataDAO;
import com.jcommerce.core.model.VoteData;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.VoteDataManager;

@Service("voteDataManager")
public class VoteDataManagerImpl extends ManagerImpl implements VoteDataManager {
    private static Log log = LogFactory.getLog(VoteDataManagerImpl.class);
    
    @Autowired
    private VoteDataDAO dao;

    public void setVoteDataDAO(VoteDataDAO dao) {
        this.dao = dao;
        super.setDao(dao);
    }

    public VoteData initialize(VoteData obj) {
        if (obj != null && !Hibernate.isInitialized(obj)) {
            obj = dao.getVoteData(obj.getId());
        }
        return obj;
    }

    public List<VoteData> getVoteDataList(int firstRow, int maxRow) {
        List list = getList(firstRow, maxRow);
        return (List<VoteData>)list;
    }

    public int getVoteDataCount(Criteria criteria) {
        return getCount(criteria);
    }

    public List<VoteData> getVoteDataList(Criteria criteria) {
        List list = getList(criteria);
        return (List<VoteData>)list;
    }

    public List<VoteData> getVoteDataList(int firstRow, int maxRow, Criteria criteria) {
        List list = getList(firstRow, maxRow, criteria);
        return (List<VoteData>)list;
    }

    public List<VoteData> getVoteDataList() {
        return dao.getVoteDataList();
    }

    public VoteData getVoteData(String id) {
        VoteData obj = dao.getVoteData(id);
        return obj;
    }

    public void saveVoteData(VoteData obj) {
        dao.saveVoteData(obj);
    }

    public void removeVoteData(String id) {
        dao.removeVoteData(id);
    }
}
