/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jcommerce.core.dao.OrderDAO;
import com.jcommerce.core.model.Order;

@Repository
@SuppressWarnings("unchecked")
public class OrderDAOImpl extends DAOImpl implements OrderDAO {
    public OrderDAOImpl() {
        modelClass = Order.class;
    }

    public List<Order> getOrderList() {
        return getList();
    }

    public Order getOrder(Long id) {
        return (Order)getById(id);
    }

    public void saveOrder(Order obj) {
        save(obj);
    }
    

    public void removeOrder(Long id) {
        deleteById(id);
    }
}
