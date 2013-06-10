package com.jcommerce.core.service;

import java.util.List;

import com.jcommerce.core.model.GroupBuy;

public interface GroupBuyManager extends Manager {
	public GroupBuy initialize(GroupBuy obj);

    public List<GroupBuy> getGroupBuyList(int firstRow, int maxRow);

    public int getGroupBuyCount(Criteria criteria);

    public List<GroupBuy> getGroupBuyList(Criteria criteria);

    public List<GroupBuy> getGroupBuyList(int firstRow, int maxRow, Criteria criteria);

    public List<GroupBuy> getGroupBuyList();

    public GroupBuy getGroupBuy(String id);

    public void saveGroupBuy(GroupBuy obj);

    public void removeGroupBuy(String id);
}
