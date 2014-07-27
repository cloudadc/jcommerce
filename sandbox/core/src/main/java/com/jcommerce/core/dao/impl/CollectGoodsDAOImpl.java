/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jcommerce.core.dao.CollectGoodsDAO;
import com.jcommerce.core.model.CollectGoods;

@Repository
@SuppressWarnings("unchecked")
public class CollectGoodsDAOImpl extends DAOImpl implements CollectGoodsDAO {
    public CollectGoodsDAOImpl() {
        modelClass = CollectGoods.class;
    }

    public List<CollectGoods> getCollectGoodsList() {
        return getList();
    }

    public CollectGoods getCollectGoods(Long id) {
        return (CollectGoods)getById(id);
    }

    public void saveCollectGoods(CollectGoods obj) {
        save(obj);
    }

    public void removeCollectGoods(Long id) {
        deleteById(id);
    }
}
