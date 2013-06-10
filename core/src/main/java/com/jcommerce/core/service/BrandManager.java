/**
* Author: Bob Chen
*/

package com.jcommerce.core.service;

import java.util.List;

import com.jcommerce.core.model.Brand;
import com.jcommerce.core.service.Criteria;
public interface BrandManager extends Manager {
    public Brand initialize(Brand obj);
    
    public List<Brand> getBrandList(int firstRow, int maxRow);

    public int getBrandCount(Criteria criteria);

    public List<Brand> getBrandList(Criteria criteria);

    public List<Brand> getBrandList(int firstRow, int maxRow, Criteria criteria);

    public List<Brand> getBrandList();

    public Brand getBrand(String id);

    public void saveBrand(Brand obj);

    public void removeBrand(String id);
}
