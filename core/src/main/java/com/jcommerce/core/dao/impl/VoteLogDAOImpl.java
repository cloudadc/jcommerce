/**
* @Author: KingZhao
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jcommerce.core.dao.VoteLogDAO;
import com.jcommerce.core.model.VoteLog;

@Repository
@SuppressWarnings("unchecked")
public class VoteLogDAOImpl extends DAOImpl implements VoteLogDAO {
    public VoteLogDAOImpl() {
        modelClass = VoteLog.class;
    }

    public List<VoteLog> getVoteLogList() {
        return getList();
    }

    public VoteLog getVoteLog(String id) {
        return (VoteLog)getById(id);
    }

    public void saveVoteLog(VoteLog obj) {
        save(obj);
    }

    public void removeVoteLog(String id) {
        deleteById(id);
    }
}
