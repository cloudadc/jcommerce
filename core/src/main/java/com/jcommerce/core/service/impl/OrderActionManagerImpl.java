/**
* Author: Bob Chen
*/

package com.jcommerce.core.service.impl;

import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;

import com.jcommerce.core.dao.OrderActionDAO;
import com.jcommerce.core.model.ModelObject;
import com.jcommerce.core.model.OrderAction;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.OrderActionManager;

public class OrderActionManagerImpl extends ManagerImpl implements OrderActionManager {
    private static Log log = LogFactory.getLog(OrderActionManagerImpl.class);
    private OrderActionDAO dao;

    public void setOrderActionDAO(OrderActionDAO dao) {
        this.dao = dao;
        super.setDao(dao);
    }

    public OrderAction initialize(OrderAction obj) {
        if (obj != null && !Hibernate.isInitialized(obj)) {
            obj = dao.getOrderAction(obj.getId());
        }
        return obj;
    }

    public List<OrderAction> getOrderActionList(int firstRow, int maxRow) {
        List list = getList(firstRow, maxRow);
        return (List<OrderAction>)list;
    }

    public int getOrderActionCount(Criteria criteria) {
        return getCount(criteria);
    }

    public List<OrderAction> getOrderActionList(Criteria criteria) {
        List list = getList(criteria);
        return (List<OrderAction>)list;
    }

    public List<OrderAction> getOrderActionList(int firstRow, int maxRow, Criteria criteria) {
        List list = getList(firstRow, maxRow, criteria);
        return (List<OrderAction>)list;
    }

    public List<OrderAction> getOrderActionList() {
        return dao.getOrderActionList();
    }

    public OrderAction getOrderAction(String id) {
        OrderAction obj = dao.getOrderAction(id);
        return obj;
    }

    public void saveOrderAction(OrderAction obj) {
        dao.saveOrderAction(obj);
    }

    public void removeOrderAction(String id) {
        dao.removeOrderAction(id);
    }
    
    public void removeOrderActions(List<OrderAction> actions) {
        dao.deleteAll((Collection<ModelObject>)(List)actions);
    }
}
