/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jcommerce.core.dao.GoodsAttributeDAO;
import com.jcommerce.core.model.GoodsAttribute;

@Repository
@SuppressWarnings("unchecked")
public class GoodsAttributeDAOImpl extends DAOImpl implements GoodsAttributeDAO {
    public GoodsAttributeDAOImpl() {
        modelClass = GoodsAttribute.class;
    }

    public List<GoodsAttribute> getGoodsAttributeList() {
        return getList();
    }

    public GoodsAttribute getGoodsAttribute(Long id) {
        return (GoodsAttribute)getById(id);
    }

    public void saveGoodsAttribute(GoodsAttribute obj) {
        save(obj);
    }

    public void removeGoodsAttribute(Long id) {
        deleteById(id);
    }
}
