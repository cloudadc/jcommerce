/**
* Author: Bob Chen
*/

package com.jcommerce.core.service;

import java.util.List;

import com.jcommerce.core.model.OrderGoods;
import com.jcommerce.core.service.Criteria;
public interface OrderGoodsManager extends Manager {
    public OrderGoods initialize(OrderGoods obj);

    public List<OrderGoods> getOrderGoodsList(int firstRow, int maxRow);

    public int getOrderGoodsCount(Criteria criteria);

    public List<OrderGoods> getOrderGoodsList(Criteria criteria);

    public List<OrderGoods> getOrderGoodsList(int firstRow, int maxRow, Criteria criteria);

    public List<OrderGoods> getOrderGoodsList();

    public OrderGoods getOrderGoods(String id);

    public List<OrderGoods> getOrderGoodsListByOrderId(String id);
    
    public void saveOrderGoods(OrderGoods obj);

    public void removeOrderGoods(String id);

    public void removeOrderGoods(List<OrderGoods> goods);
}
