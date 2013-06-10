/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import com.jcommerce.core.dao.ShippingAreaDAO;
import com.jcommerce.core.model.ShippingArea;

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
