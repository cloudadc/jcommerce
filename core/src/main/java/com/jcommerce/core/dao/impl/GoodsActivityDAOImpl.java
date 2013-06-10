/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import com.jcommerce.core.dao.GoodsActivityDAO;
import com.jcommerce.core.model.GoodsActivity;

public class GoodsActivityDAOImpl extends DAOImpl implements GoodsActivityDAO {
    public GoodsActivityDAOImpl() {
        modelClass = GoodsActivity.class;
    }

    public List<GoodsActivity> getGoodsActivityList() {
        return getList();
    }

    public GoodsActivity getGoodsActivity(String id) {
        return (GoodsActivity)getById(id);
    }

    public void saveGoodsActivity(GoodsActivity obj) {
        save(obj);
    }

    public void removeGoodsActivity(String id) {
        deleteById(id);
    }
}
