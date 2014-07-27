/**
* Author: Bob Chen
*         Kylin Soong
*/

package com.jcommerce.core.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcommerce.core.dao.GoodsActivityDAO;
import com.jcommerce.core.model.GoodsActivity;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.GoodsActivityManager;

@Service("GoodsActivityManager")
public class GoodsActivityManagerImpl extends ManagerImpl implements GoodsActivityManager {
    private static Log log = LogFactory.getLog(GoodsActivityManagerImpl.class);
    
    @Autowired
    private GoodsActivityDAO dao;

    public void setGoodsActivityDAO(GoodsActivityDAO dao) {
        this.dao = dao;
        super.setDao(dao);
    }

    public GoodsActivity initialize(GoodsActivity obj) {
        if (obj != null && !Hibernate.isInitialized(obj)) {
            obj = dao.getGoodsActivity(obj.getId());
        }
        return obj;
    }

    public List<GoodsActivity> getGoodsActivityList(int firstRow, int maxRow) {
        List list = getList(firstRow, maxRow);
        return (List<GoodsActivity>)list;
    }

    public int getGoodsActivityCount(Criteria criteria) {
        return getCount(criteria);
    }

    public List<GoodsActivity> getGoodsActivityList(Criteria criteria) {
        List list = getList(criteria);
        return (List<GoodsActivity>)list;
    }

    public List<GoodsActivity> getGoodsActivityList(int firstRow, int maxRow, Criteria criteria) {
        List list = getList(firstRow, maxRow, criteria);
        return (List<GoodsActivity>)list;
    }

    public List<GoodsActivity> getGoodsActivityList() {
        return dao.getGoodsActivityList();
    }

    public GoodsActivity getGoodsActivity(Long id) {
        GoodsActivity obj = dao.getGoodsActivity(id);
        return obj;
    }

    public void saveGoodsActivity(GoodsActivity obj) {
        dao.saveGoodsActivity(obj);
    }

    public void removeGoodsActivity(Long id) {
        dao.removeGoodsActivity(id);
    }
}
