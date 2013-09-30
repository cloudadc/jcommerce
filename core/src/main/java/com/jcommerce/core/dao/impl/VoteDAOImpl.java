/**
* @Author: KingZhao
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jcommerce.core.dao.VoteDAO;
import com.jcommerce.core.model.Vote;

@Repository
@SuppressWarnings("unchecked")
public class VoteDAOImpl extends DAOImpl implements VoteDAO {
    public VoteDAOImpl() {
        modelClass = Vote.class;
    }

    public List<Vote> getVoteList() {
        return getList();
    }

    public Vote getVote(String id) {
        return (Vote)getById(id);
    }

    public void saveVote(Vote obj) {
        save(obj);
    }

    public void removeVote(String id) {
        deleteById(id);
    }
}
