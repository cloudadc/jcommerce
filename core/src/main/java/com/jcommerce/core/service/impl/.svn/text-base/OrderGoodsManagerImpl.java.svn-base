/**
* Author: Bob Chen
*/

package com.jcommerce.core.service.impl;

import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;

import com.jcommerce.core.dao.OrderGoodsDAO;
import com.jcommerce.core.model.ModelObject;
import com.jcommerce.core.model.OrderGoods;
import com.jcommerce.core.service.Condition;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.OrderGoodsManager;

public class OrderGoodsManagerImpl extends ManagerImpl implements OrderGoodsManager {
    private static Log log = LogFactory.getLog(OrderGoodsManagerImpl.class);
    private OrderGoodsDAO dao;

    public void setOrderGoodsDAO(OrderGoodsDAO dao) {
        this.dao = dao;
        super.setDao(dao);
    }

    public OrderGoods initialize(OrderGoods obj) {
        if (obj != null && !Hibernate.isInitialized(obj)) {
            obj = dao.getOrderGoods(obj.getId());
        }
        return obj;
    }

    public List<OrderGoods> getOrderGoodsList(int firstRow, int maxRow) {
        List list = getList(firstRow, maxRow);
        return (List<OrderGoods>)list;
    }

    public int getOrderGoodsCount(Criteria criteria) {
        return getCount(criteria);
    }

    public List<OrderGoods> getOrderGoodsList(Criteria criteria) {
        List list = getList(criteria);
        return (List<OrderGoods>)list;
    }

    public List<OrderGoods> getOrderGoodsList(int firstRow, int maxRow, Criteria criteria) {
        List list = getList(firstRow, maxRow, criteria);
        return (List<OrderGoods>)list;
    }

    public List<OrderGoods> getOrderGoodsList() {
        return dao.getOrderGoodsList();
    }

    public OrderGoods getOrderGoods(String id) {
        OrderGoods obj = dao.getOrderGoods(id);
        return obj;
    }

    public void saveOrderGoods(OrderGoods obj) {
        dao.saveOrderGoods(obj);
    }

    public void removeOrderGoods(String id) {
        dao.removeOrderGoods(id);
    }
    
    public void removeOrderGoods(List<OrderGoods> goods) {
        dao.deleteAll((Collection<ModelObject>)(List)goods);
    }
    
    public List<OrderGoods> getOrderGoodsListByOrderId(String id) {
        Criteria criteria = new Criteria();
        criteria.addCondition(new Condition("order", Condition.EQUALS, id));
        return getOrderGoodsList(criteria);
    }
}
