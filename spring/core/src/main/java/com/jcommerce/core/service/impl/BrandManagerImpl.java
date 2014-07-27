/**
* Author: Bob Chen
*         Kylin Soong
*/

package com.jcommerce.core.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcommerce.core.dao.BrandDAO;
import com.jcommerce.core.model.Brand;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.BrandManager;

@Service("BrandManager")
public class BrandManagerImpl extends ManagerImpl implements BrandManager {
    private static Log log = LogFactory.getLog(BrandManagerImpl.class);
    
    @Autowired
    private BrandDAO dao;

    public void setBrandDAO(BrandDAO dao) {
        this.dao = dao;
        super.setDao(dao);
    }

    public Brand initialize(Brand obj) {
        if (obj != null && !Hibernate.isInitialized(obj)) {
            obj = dao.getBrand(obj.getId());
        }
        return obj;
    }

    public List<Brand> getBrandList(int firstRow, int maxRow) {
        List list = getList(firstRow, maxRow);
        return (List<Brand>)list;
    }

    public int getBrandCount(Criteria criteria) {
        return getCount(criteria);
    }

    public List<Brand> getBrandList(Criteria criteria) {
        List list = getList(criteria);
        return (List<Brand>)list;
    }

    public List<Brand> getBrandList(int firstRow, int maxRow, Criteria criteria) {
        List list = getList(firstRow, maxRow, criteria);
        return (List<Brand>)list;
    }

    public List<Brand> getBrandList() {
        return dao.getBrandList();
    }

    public Brand getBrand(Long id) {
        Brand obj = dao.getBrand(id);
        return obj;
    }

    public void saveBrand(Brand obj) {
        dao.saveBrand(obj);
    }

    public void removeBrand(Long id) {
        dao.removeBrand(id);
    }
            
    public String getModelName() {
        return dao.getModelClass().getSimpleName();
    }
}
