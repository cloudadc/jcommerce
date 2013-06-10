/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao;

import java.util.List;

import com.jcommerce.core.model.CollectGoods;

public interface CollectGoodsDAO extends DAO {
    public List<CollectGoods> getCollectGoodsList();

    public CollectGoods getCollectGoods(String id);

    public void saveCollectGoods(CollectGoods obj);

    public void removeCollectGoods(String id);
}
