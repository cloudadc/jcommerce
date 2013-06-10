/**
* Author: Bob Chen
*/

package com.jcommerce.core.service;

import java.util.List;

import com.jcommerce.core.model.Shipping;
import com.jcommerce.core.service.Criteria;
public interface ShippingManager extends Manager {
    public Shipping initialize(Shipping obj);

    public List<Shipping> getShippingList(int firstRow, int maxRow);

    public int getShippingCount(Criteria criteria);

    public List<Shipping> getShippingList(Criteria criteria);

    public List<Shipping> getShippingList(int firstRow, int maxRow, Criteria criteria);

    public List<Shipping> getShippingList();

    public Shipping getShipping(String id);

    public void saveShipping(Shipping obj);

    public void removeShipping(String id);
}
