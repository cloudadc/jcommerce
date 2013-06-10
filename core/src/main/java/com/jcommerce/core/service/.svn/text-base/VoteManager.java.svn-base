/**
* @Author: KingZhao
*/

package com.jcommerce.core.service;

import java.util.List;

import com.jcommerce.core.model.Vote;
import com.jcommerce.core.service.Criteria;
public interface VoteManager extends Manager {
    public Vote initialize(Vote obj);

    public List<Vote> getVoteList(int firstRow, int maxRow);

    public int getVoteCount(Criteria criteria);

    public List<Vote> getVoteList(Criteria criteria);

    public List<Vote> getVoteList(int firstRow, int maxRow, Criteria criteria);

    public List<Vote> getVoteList();

    public Vote getVote(String id);

    public void saveVote(Vote obj);

    public void removeVote(String id);
}
