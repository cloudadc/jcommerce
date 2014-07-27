package com.jcommerce.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jcommerce.core.dao.GroupBuyDAO;
import com.jcommerce.core.model.GroupBuy;

@Repository
@SuppressWarnings("unchecked")
public class GroupBuyDAOImpl extends DAOImpl implements GroupBuyDAO {

	 public GroupBuyDAOImpl() {
	        modelClass = GroupBuy.class;
	    }

	    public List<GroupBuy> getGroupBuyList() {
	        return getList();
	    }

	    public GroupBuy getGroupBuy(Long id) {
	        return (GroupBuy)getById(id);
	    }

	    public void saveGroupBuy(GroupBuy obj) {
	        save(obj);
	    }

	    public void removeGroupBuy(Long id) {
	        deleteById(id);
	    }

}
