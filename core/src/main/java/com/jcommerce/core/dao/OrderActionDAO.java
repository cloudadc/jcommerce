/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao;

import java.util.List;

import com.jcommerce.core.model.OrderAction;

public interface OrderActionDAO extends DAO {
    public List<OrderAction> getOrderActionList();

    public OrderAction getOrderAction(String id);

    public void saveOrderAction(OrderAction obj);

    public void removeOrderAction(String id);
}
