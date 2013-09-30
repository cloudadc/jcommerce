/**
* Author: Bob Chen
*/

package com.jcommerce.core.service.impl;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcommerce.core.dao.OrderDAO;
import com.jcommerce.core.model.Order;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.OrderManager;

@Service("orderManager")
public class OrderManagerImpl extends ManagerImpl implements OrderManager {
    private static Log log = LogFactory.getLog(OrderManagerImpl.class);
    
    @Autowired
    private OrderDAO dao;

    public void setOrderDAO(OrderDAO dao) {
        this.dao = dao;
        super.setDao(dao);
    }

    public Order initialize(Order obj) {
        if (obj != null && !Hibernate.isInitialized(obj)) {
            obj = dao.getOrder(obj.getId());
        }
        return obj;
    }

    public List<Order> getOrderList(int firstRow, int maxRow) {
        List list = getList(firstRow, maxRow);
        return (List<Order>)list;
    }

    public int getOrderCount(Criteria criteria) {
        return getCount(criteria);
    }

    public List<Order> getOrderList(Criteria criteria) {
        List list = getList(criteria);
        return (List<Order>)list;
    }

    public List<Order> getOrderList(int firstRow, int maxRow, Criteria criteria) {
        List list = getList(firstRow, maxRow, criteria);
        return (List<Order>)list;
    }

    public List<Order> getOrderList() {
        return dao.getOrderList();
    }

    public Order getOrder(String id) {
        Order obj = dao.getOrder(id);
        return obj;
    }

    public Order getOrderBySN(String SN){
    	List<Order> list = dao.getOrderList();
    	Order order = new Order();
    	for (Iterator it = list.iterator(); it.hasNext();) {
    		Order ord = (Order) it.next();      		
            if (ord.getSN()!=null && ord.getSN().equals(SN)) {
            	order = ord;
            }
        }
    	return order;
    }
    
    public void saveOrder(Order obj) {
        dao.saveOrder(obj);
    }
    

    public void removeOrder(String id) {
        dao.removeOrder(id);
    }
}
