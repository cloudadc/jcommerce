/**
* Author: Bob Chen
*/

package com.jcommerce.core.service;

import java.util.List;

import com.jcommerce.core.model.CollectGoods;
import com.jcommerce.core.service.Criteria;
public interface CollectGoodsManager extends Manager {
    public CollectGoods initialize(CollectGoods obj);

    public List<CollectGoods> getCollectGoodsList(int firstRow, int maxRow);

    public int getCollectGoodsCount(Criteria criteria);

    public List<CollectGoods> getCollectGoodsList(Criteria criteria);

    public List<CollectGoods> getCollectGoodsList(int firstRow, int maxRow, Criteria criteria);

    public List<CollectGoods> getCollectGoodsList();

    public CollectGoods getCollectGoods(String id);

    public void saveCollectGoods(CollectGoods obj);

    public void removeCollectGoods(String id);
}
