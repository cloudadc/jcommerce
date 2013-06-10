/**
* @Author: KingZhao
*/

package com.jcommerce.core.service;

import java.util.List;

import com.jcommerce.core.model.VoteLog;
import com.jcommerce.core.service.Criteria;
public interface VoteLogManager extends Manager {
    public VoteLog initialize(VoteLog obj);

    public List<VoteLog> getVoteLogList(int firstRow, int maxRow);

    public int getVoteLogCount(Criteria criteria);

    public List<VoteLog> getVoteLogList(Criteria criteria);

    public List<VoteLog> getVoteLogList(int firstRow, int maxRow, Criteria criteria);

    public List<VoteLog> getVoteLogList();

    public VoteLog getVoteLog(String id);

    public void saveVoteLog(VoteLog obj);

    public void removeVoteLog(String id);
}
