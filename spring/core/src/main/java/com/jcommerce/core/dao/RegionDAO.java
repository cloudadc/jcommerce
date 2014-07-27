/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao;

import java.util.List;

import com.jcommerce.core.model.Region;

public interface RegionDAO extends DAO {
    public List<Region> getRegionList();

    public Region getRegion(Long id);

    public void saveRegion(Region obj);

    public void removeRegion(Long id);
}
