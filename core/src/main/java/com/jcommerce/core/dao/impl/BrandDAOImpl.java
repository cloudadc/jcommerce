/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import com.jcommerce.core.dao.BrandDAO;
import com.jcommerce.core.model.Brand;

public class BrandDAOImpl extends DAOImpl implements BrandDAO {
    public BrandDAOImpl() {
        modelClass = Brand.class;
    }

    public List<Brand> getBrandList() {
        return getList();
    }

    public Brand getBrand(String id) {
        return (Brand)getById(id);
    }

    public void saveBrand(Brand obj) {
        save(obj);
    }

    public void removeBrand(String id) {
        deleteById(id);
    }
}
