/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao;

import java.util.List;

import com.jcommerce.core.model.ShippingArea;

public interface ShippingAreaDAO extends DAO {
    public List<ShippingArea> getShippingAreaList();

    public ShippingArea getShippingArea(Long id);

    public void saveShippingArea(ShippingArea obj);

    public void removeShippingArea(Long id);
}
