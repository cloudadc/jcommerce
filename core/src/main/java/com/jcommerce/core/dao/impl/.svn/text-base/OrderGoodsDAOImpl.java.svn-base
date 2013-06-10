/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import com.jcommerce.core.dao.OrderGoodsDAO;
import com.jcommerce.core.model.OrderGoods;

public class OrderGoodsDAOImpl extends DAOImpl implements OrderGoodsDAO {
    public OrderGoodsDAOImpl() {
        modelClass = OrderGoods.class;
    }

    public List<OrderGoods> getOrderGoodsList() {
        return getList();
    }

    public OrderGoods getOrderGoods(String id) {
        return (OrderGoods)getById(id);
    }

    public void saveOrderGoods(OrderGoods obj) {
        save(obj);
    }

    public void removeOrderGoods(String id) {
        deleteById(id);
    }
}
