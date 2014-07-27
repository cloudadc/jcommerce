/**
* Author: Bob Chen
*         Kylin Soong
*/

package com.jcommerce.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcommerce.core.dao.GoodsDAO;
import com.jcommerce.core.model.Goods;
import com.jcommerce.core.model.ModelObject;
import com.jcommerce.core.service.Condition;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.GoodsManager;

@Service("GoodsManager")
public class GoodsManagerImpl extends ManagerImpl implements GoodsManager {
    private static Log log = LogFactory.getLog(GoodsManagerImpl.class);
    
    @Autowired
    private GoodsDAO dao;

    public void setGoodsDAO(GoodsDAO dao) {
        this.dao = dao;
        super.setDao(dao);
    }

    public Goods initialize(Goods obj) {
        if (obj != null && !Hibernate.isInitialized(obj)) {
            obj = dao.getGoods(obj.getId());
        }
        return obj;
    }

    public List<Goods> getGoodsList(int firstRow, int maxRow) {
        List list = getList(firstRow, maxRow);
        return (List<Goods>)list;
    }
    

    public List<Goods> getGoodsList(Criteria criteria) {
        List list = getList(criteria);
        return (List<Goods>)list;
    }
    
    public List<Goods> getGoodsList(int firstRow, int maxRow, Criteria criteria) {
        List list = getList(firstRow, maxRow, criteria);
        return (List<Goods>)list;
    }

    public int getGoodsCount() {
        return getCount(null);
    }
    
    public int getGoodsCount(Criteria criteria) {
        return getCount(criteria);
    }
    
    public List<Goods> getGoodsList() {
        return dao.getGoodsList();
    }

    public Goods getGoods(Long id) {
        Goods obj = dao.getGoods(id);
        return obj;
    }

    public void saveGoods(Goods obj) {
        dao.saveGoods(obj);
    }

    public void removeGoods(Long id) {
        Goods goods = getGoods(id);
        goods.setDeleted(true);
        saveGoods(goods);
    }

    public List<Goods> getBestSoldGoodsList() {
        return getGoodsList(getBestSoldGoodsCriteria());
    }

    public List<Goods> getHotSoldGoodsList() {
        return getGoodsList(getHotSoldGoodsCriteria());
    }

    public List<Goods> getNewGoodsList() {
        return getGoodsList(getNewGoodsCriteria());
    }   
    
    public List<Goods> getGoodsListByIds (List<Long> ids) {
        List res = new ArrayList();
        for(Long id : ids) {
            ModelObject obj = getGoods(id);
            if(obj!=null) {
                res.add(obj);
            }
        }
        return res;
    }

    public void purgeGoods(Long id) {
        dao.removeGoods(id);
    }

    public void undoDeletedGoods(Long id) {
        Goods goods = getGoods(id);
        goods.setDeleted(false);
        saveGoods(goods);
    }

    public Criteria getBestSoldGoodsCriteria() {
        Criteria criteria = new Criteria();
        criteria.addCondition(new Condition("bestSold", Condition.EQUALS, new Long(1)));
        criteria.addCondition(new Condition("deleted", Condition.EQUALS, new Long(0)));
        criteria.addCondition(new Condition("onSale", Condition.EQUALS, new Long(1)));
        return criteria;
    }

    public Criteria getHotSoldGoodsCriteria() {
        Criteria criteria = new Criteria();
        criteria.addCondition(new Condition("hotSold", Condition.EQUALS, new Long(1)));
        criteria.addCondition(new Condition("deleted", Condition.EQUALS, new Long(0)));
        criteria.addCondition(new Condition("onSale", Condition.EQUALS, new Long(1)));
        return criteria;
    }

    public Criteria getNewGoodsCriteria() {
        Criteria criteria = new Criteria();
        criteria.addCondition(new Condition("newAdded", Condition.EQUALS, new Long(1)));
        criteria.addCondition(new Condition("deleted", Condition.EQUALS, new Long(0)));
        criteria.addCondition(new Condition("onSale", Condition.EQUALS, new Long(1)));
        return criteria;
    }
}
