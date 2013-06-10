/**
* Author: Bob Chen
*/

package com.jcommerce.core.service;

import java.util.List;

import com.jcommerce.core.model.Order;
public interface OrderManager extends Manager {
    public Order initialize(Order obj);

    public List<Order> getOrderList(int firstRow, int maxRow);

    public int getOrderCount(Criteria criteria);

    public List<Order> getOrderList(Criteria criteria);

    public List<Order> getOrderList(int firstRow, int maxRow, Criteria criteria);

    public List<Order> getOrderList();

    public Order getOrder(String id);
    
    public Order getOrderBySN(String SN);

    public void saveOrder(Order obj);
    
    public void removeOrder(String id);
}
