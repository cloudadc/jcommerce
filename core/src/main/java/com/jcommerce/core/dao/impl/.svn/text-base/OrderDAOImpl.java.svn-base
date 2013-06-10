/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import com.jcommerce.core.dao.OrderDAO;
import com.jcommerce.core.model.Order;

public class OrderDAOImpl extends DAOImpl implements OrderDAO {
    public OrderDAOImpl() {
        modelClass = Order.class;
    }

    public List<Order> getOrderList() {
        return getList();
    }

    public Order getOrder(String id) {
        return (Order)getById(id);
    }

    public void saveOrder(Order obj) {
        save(obj);
    }
    

    public void removeOrder(String id) {
        deleteById(id);
    }
}
