/**
* @Author: KingZhao
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jcommerce.core.dao.VoteDataDAO;
import com.jcommerce.core.model.VoteData;

@Repository
@SuppressWarnings("unchecked")
public class VoteDataDAOImpl extends DAOImpl implements VoteDataDAO {
    public VoteDataDAOImpl() {
        modelClass = VoteData.class;
    }

    public List<VoteData> getVoteDataList() {
        return getList();
    }

    public VoteData getVoteData(Long id) {
        return (VoteData)getById(id);
    }

    public void saveVoteData(VoteData obj) {
        save(obj);
    }

    public void removeVoteData(Long id) {
        deleteById(id);
    }
}
