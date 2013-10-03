/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao;

import java.util.List;

import com.jcommerce.core.model.Brand;

public interface BrandDAO extends DAO {
    public List<Brand> getBrandList();

    public Brand getBrand(Long id);

    public void saveBrand(Brand obj);

    public void removeBrand(Long id);
}
