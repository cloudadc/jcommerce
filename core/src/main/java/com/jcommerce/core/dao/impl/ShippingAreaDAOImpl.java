/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jcommerce.core.dao.ShippingAreaDAO;
import com.jcommerce.core.model.ShippingArea;

@Repository
@SuppressWarnings("unchecked")
public class ShippingAreaDAOImpl extends DAOImpl implements ShippingAreaDAO {
    public ShippingAreaDAOImpl() {
        modelClass = ShippingArea.class;
    }

    public List<ShippingArea> getShippingAreaList() {
        return getList();
    }

    public ShippingArea getShippingArea(String id) {
        return (ShippingArea)getById(id);
    }

    public void saveShippingArea(ShippingArea obj) {
        save(obj);
    }

    public void removeShippingArea(String id) {
        deleteById(id);
    }
}
