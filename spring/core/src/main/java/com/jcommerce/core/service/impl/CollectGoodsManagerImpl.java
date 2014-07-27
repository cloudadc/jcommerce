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

import com.jcommerce.core.dao.CollectGoodsDAO;
import com.jcommerce.core.model.CollectGoods;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.CollectGoodsManager;

@Service("CollectGoodsManager")
public class CollectGoodsManagerImpl extends ManagerImpl implements CollectGoodsManager {
    private static Log log = LogFactory.getLog(CollectGoodsManagerImpl.class);
    
    @Autowired
    private CollectGoodsDAO dao;

    public void setCollectGoodsDAO(CollectGoodsDAO dao) {
        this.dao = dao;
        super.setDao(dao);
    }

    public CollectGoods initialize(CollectGoods obj) {
        if (obj != null && !Hibernate.isInitialized(obj)) {
            obj = dao.getCollectGoods(obj.getId());
        }
        return obj;
    }

    public List<CollectGoods> getCollectGoodsList(int firstRow, int maxRow) {
        List list = getList(firstRow, maxRow);
        return (List<CollectGoods>)list;
    }

    public int getCollectGoodsCount(Criteria criteria) {
        return getCount(criteria);
    }

    public List<CollectGoods> getCollectGoodsList(Criteria criteria) {
        List list = getList(criteria);
        return (List<CollectGoods>)list;
    }

    public List<CollectGoods> getCollectGoodsList(int firstRow, int maxRow, Criteria criteria) {
        List list = getList(firstRow, maxRow, criteria);
        return (List<CollectGoods>)list;
    }

    public List<CollectGoods> getCollectGoodsList() {
        return dao.getCollectGoodsList();
    }

    public CollectGoods getCollectGoods(Long id) {
        CollectGoods obj = dao.getCollectGoods(id);
        return obj;
    }

    public void saveCollectGoods(CollectGoods obj) {
        dao.saveCollectGoods(obj);
    }

    public void removeCollectGoods(Long id) {
        dao.removeCollectGoods(id);
    }
}
