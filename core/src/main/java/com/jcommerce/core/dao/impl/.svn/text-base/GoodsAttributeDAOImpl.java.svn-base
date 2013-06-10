/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import com.jcommerce.core.dao.GoodsAttributeDAO;
import com.jcommerce.core.model.GoodsAttribute;

public class GoodsAttributeDAOImpl extends DAOImpl implements GoodsAttributeDAO {
    public GoodsAttributeDAOImpl() {
        modelClass = GoodsAttribute.class;
    }

    public List<GoodsAttribute> getGoodsAttributeList() {
        return getList();
    }

    public GoodsAttribute getGoodsAttribute(String id) {
        return (GoodsAttribute)getById(id);
    }

    public void saveGoodsAttribute(GoodsAttribute obj) {
        save(obj);
    }

    public void removeGoodsAttribute(String id) {
        deleteById(id);
    }
}
