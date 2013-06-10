/**
* @Author: KingZhao
*/

package com.jcommerce.core.service;

import java.util.List;

import com.jcommerce.core.model.VoteData;
import com.jcommerce.core.service.Criteria;
public interface VoteDataManager extends Manager {
    public VoteData initialize(VoteData obj);

    public List<VoteData> getVoteDataList(int firstRow, int maxRow);

    public int getVoteDataCount(Criteria criteria);

    public List<VoteData> getVoteDataList(Criteria criteria);

    public List<VoteData> getVoteDataList(int firstRow, int maxRow, Criteria criteria);

    public List<VoteData> getVoteDataList();

    public VoteData getVoteData(String id);

    public void saveVoteData(VoteData obj);

    public void removeVoteData(String id);
}
