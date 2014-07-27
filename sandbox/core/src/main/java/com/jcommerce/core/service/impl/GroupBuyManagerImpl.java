package com.jcommerce.core.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcommerce.core.dao.GroupBuyDAO;
import com.jcommerce.core.model.GroupBuy;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.GroupBuyManager;

@Service("GroupBuyManager")
public class GroupBuyManagerImpl extends ManagerImpl implements GroupBuyManager {

	private static Log log = LogFactory.getLog(GroupBuyManagerImpl.class);
	
	@Autowired
    private GroupBuyDAO dao;

    public void setGroupBuyDAO(GroupBuyDAO dao) {
        this.dao = dao;
        super.setDao(dao);
    }

    public GroupBuy initialize(GroupBuy obj) {
        if (obj != null && !Hibernate.isInitialized(obj)) {
            obj = dao.getGroupBuy(obj.getId());
        }
        return obj;
    }

    public List<GroupBuy> getGroupBuyList(int firstRow, int maxRow) {
        List list = getList(firstRow, maxRow);
        return (List<GroupBuy>)list;
    }

    public int getGroupBuyCount(Criteria criteria) {
        return getCount(criteria);
    }

    public List<GroupBuy> getGroupBuyList(Criteria criteria) {
        List list = getList(criteria);
        return (List<GroupBuy>)list;
    }

    public List<GroupBuy> getGroupBuyList(int firstRow, int maxRow, Criteria criteria) {
        List list = getList(firstRow, maxRow, criteria);
        return (List<GroupBuy>)list;
    }

    public List<GroupBuy> getGroupBuyList() {
        return dao.getGroupBuyList();
    }

    public GroupBuy getGroupBuy(Long id) {
        GroupBuy obj = dao.getGroupBuy(id);
        return obj;
    }

    public void saveGroupBuy(GroupBuy obj) {
        dao.saveGroupBuy(obj);
    }

    public void removeGroupBuy(Long id) {
        dao.removeGroupBuy(id);
    }




}
