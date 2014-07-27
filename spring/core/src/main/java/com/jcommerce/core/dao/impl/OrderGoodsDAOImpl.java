/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jcommerce.core.dao.OrderGoodsDAO;
import com.jcommerce.core.model.OrderGoods;

@Repository
@SuppressWarnings("unchecked")
public class OrderGoodsDAOImpl extends DAOImpl implements OrderGoodsDAO {
    public OrderGoodsDAOImpl() {
        modelClass = OrderGoods.class;
    }

    public List<OrderGoods> getOrderGoodsList() {
        return getList();
    }

    public OrderGoods getOrderGoods(Long id) {
        return (OrderGoods)getById(id);
    }

    public void saveOrderGoods(OrderGoods obj) {
        save(obj);
    }

    public void removeOrderGoods(Long id) {
        deleteById(id);
    }
}
