/**
* Author: Bob Chen
*/

package com.jcommerce.core.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;

import com.jcommerce.core.dao.ShippingAreaDAO;
import com.jcommerce.core.model.ShippingArea;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.ShippingAreaManager;

public class ShippingAreaManagerImpl extends ManagerImpl implements ShippingAreaManager {
    private static Log log = LogFactory.getLog(ShippingAreaManagerImpl.class);
    private ShippingAreaDAO dao;

    public void setShippingAreaDAO(ShippingAreaDAO dao) {
        this.dao = dao;
        super.setDao(dao);
    }

    public ShippingArea initialize(ShippingArea obj) {
        if (obj != null && !Hibernate.isInitialized(obj)) {
            obj = dao.getShippingArea(obj.getId());
        }
        return obj;
    }

    public List<ShippingArea> getShippingAreaList(int firstRow, int maxRow) {
        List list = getList(firstRow, maxRow);
        return (List<ShippingArea>)list;
    }

    public int getShippingAreaCount(Criteria criteria) {
        return getCount(criteria);
    }

    public List<ShippingArea> getShippingAreaList(Criteria criteria) {
        List list = getList(criteria);
        return (List<ShippingArea>)list;
    }

    public List<ShippingArea> getShippingAreaList(int firstRow, int maxRow, Criteria criteria) {
        List list = getList(firstRow, maxRow, criteria);
        return (List<ShippingArea>)list;
    }

    public List<ShippingArea> getShippingAreaList() {
        return dao.getShippingAreaList();
    }

    public ShippingArea getShippingArea(String id) {
        ShippingArea obj = dao.getShippingArea(id);
        return obj;
    }

    public void saveShippingArea(ShippingArea obj) {
        dao.saveShippingArea(obj);
    }

    public void removeShippingArea(String id) {
        dao.removeShippingArea(id);
    }
}
