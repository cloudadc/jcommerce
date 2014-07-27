/**
* Author: Bob Chen
*/

package com.jcommerce.core.service;

import java.util.List;

import com.jcommerce.core.model.Region;
import com.jcommerce.core.service.Criteria;
public interface RegionManager extends Manager {
    public Region initialize(Region obj);

    public List<Region> getRegionList(int firstRow, int maxRow);

    public int getRegionCount(Criteria criteria);

    public List<Region> getRegionList(Criteria criteria);

    public List<Region> getRegionList(int firstRow, int maxRow, Criteria criteria);

    public List<Region> getRegionList();
    
    public List<Region> getChildList(String parent_id);

    public Region getRegion(Long id);

    public void saveRegion(Region obj);

    public void removeRegion(Long id);
    
    public List<Region> getCountries();
}
