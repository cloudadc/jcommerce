/**
* Author: Bob Chen
*/

package com.jcommerce.core.service;

import java.util.List;

import com.jcommerce.core.model.OrderAction;
public interface OrderActionManager extends Manager {
    public OrderAction initialize(OrderAction obj);

    public List<OrderAction> getOrderActionList(int firstRow, int maxRow);

    public int getOrderActionCount(Criteria criteria);

    public List<OrderAction> getOrderActionList(Criteria criteria);

    public List<OrderAction> getOrderActionList(int firstRow, int maxRow, Criteria criteria);

    public List<OrderAction> getOrderActionList();

    public OrderAction getOrderAction(Long id);

    public void saveOrderAction(OrderAction obj);

    public void removeOrderAction(Long id);
    
    public void removeOrderActions(List<OrderAction> actions);
}
