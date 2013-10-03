/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jcommerce.core.dao.GoodsActivityDAO;
import com.jcommerce.core.model.GoodsActivity;

@Repository
@SuppressWarnings("unchecked")
public class GoodsActivityDAOImpl extends DAOImpl implements GoodsActivityDAO {
    public GoodsActivityDAOImpl() {
        modelClass = GoodsActivity.class;
    }

    public List<GoodsActivity> getGoodsActivityList() {
        return getList();
    }

    public GoodsActivity getGoodsActivity(Long id) {
        return (GoodsActivity)getById(id);
    }

    public void saveGoodsActivity(GoodsActivity obj) {
        save(obj);
    }

    public void removeGoodsActivity(Long id) {
        deleteById(id);
    }
}
