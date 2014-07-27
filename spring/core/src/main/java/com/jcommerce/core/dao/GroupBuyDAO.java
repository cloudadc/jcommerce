package com.jcommerce.core.dao;

import java.util.List;

import com.jcommerce.core.model.GroupBuy;

public interface GroupBuyDAO extends DAO {

	public List<GroupBuy> getGroupBuyList();

    public GroupBuy getGroupBuy(Long id);

    public void saveGroupBuy(GroupBuy obj);

    public void removeGroupBuy(Long id);
}
