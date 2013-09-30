/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jcommerce.core.dao.ShippingDAO;
import com.jcommerce.core.model.Shipping;

@Repository
@SuppressWarnings("unchecked")
public class ShippingDAOImpl extends DAOImpl implements ShippingDAO {
    public ShippingDAOImpl() {
        modelClass = Shipping.class;
    }

    public List<Shipping> getShippingList() {
        return getList();
    }

    public Shipping getShipping(String id) {
        return (Shipping)getById(id);
    }

    public void saveShipping(Shipping obj) {
        save(obj);
    }

    public void removeShipping(String id) {
        deleteById(id);
    }
}
