/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jcommerce.core.dao.GoodsDAO;
import com.jcommerce.core.model.Goods;

@Repository
@SuppressWarnings("unchecked")
public class GoodsDAOImpl extends DAOImpl implements GoodsDAO {
    public GoodsDAOImpl() {
        modelClass = Goods.class;
    }

    public List<Goods> getGoodsList() {
        return getList();
    }

    public Goods getGoods(String id) {
        return (Goods)getById(id);
    }

    public void saveGoods(Goods obj) {
        save(obj);
    }

    public void removeGoods(String id) {
        deleteById(id);
    }
}
