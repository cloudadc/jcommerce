/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao;

import java.util.List;

import com.jcommerce.core.model.OrderGoods;

public interface OrderGoodsDAO extends DAO {
    public List<OrderGoods> getOrderGoodsList();

    public OrderGoods getOrderGoods(String id);

    public void saveOrderGoods(OrderGoods obj);

    public void removeOrderGoods(String id);
}
