/**
* @Author: KingZhao
*/

package com.jcommerce.core.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;

import com.jcommerce.core.dao.GroupGoodsDAO;
import com.jcommerce.core.model.GroupGoods;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.GroupGoodsManager;

public class GroupGoodsManagerImpl extends ManagerImpl implements GroupGoodsManager {
    private static Log log = LogFactory.getLog(GroupGoodsManagerImpl.class);
    private GroupGoodsDAO dao;

    public void setGroupGoodsDAO(GroupGoodsDAO dao) {
        this.dao = dao;
        super.setDao(dao);
    }

    public GroupGoods initialize(GroupGoods obj) {
        if (obj != null && !Hibernate.isInitialized(obj)) {
            obj = dao.getGroupGoods(obj.getId());
        }
        return obj;
    }

    public List<GroupGoods> getGroupGoodsList(int firstRow, int maxRow) {
        List list = getList(firstRow, maxRow);
        return (List<GroupGoods>)list;
    }

    public int getGroupGoodsCount(Criteria criteria) {
        return getCount(criteria);
    }

    public List<GroupGoods> getGroupGoodsList(Criteria criteria) {
        List list = getList(criteria);
        return (List<GroupGoods>)list;
    }

    public List<GroupGoods> getGroupGoodsList(int firstRow, int maxRow, Criteria criteria) {
        List list = getList(firstRow, maxRow, criteria);
        return (List<GroupGoods>)list;
    }

    public List<GroupGoods> getGroupGoodsList() {
        return dao.getGroupGoodsList();
    }

    public GroupGoods getGroupGoods(String id) {
        GroupGoods obj = dao.getGroupGoods(id);
        return obj;
    }

    public void saveGroupGoods(GroupGoods obj) {
        dao.saveGroupGoods(obj);
    }

    public void removeGroupGoods(String id) {
        dao.removeGroupGoods(id);
    }
}
