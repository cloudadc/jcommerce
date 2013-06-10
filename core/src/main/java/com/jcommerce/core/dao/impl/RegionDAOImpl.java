/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import com.jcommerce.core.dao.RegionDAO;
import com.jcommerce.core.model.Region;

public class RegionDAOImpl extends DAOImpl implements RegionDAO {
    public RegionDAOImpl() {
        modelClass = Region.class;
    }

    public List<Region> getRegionList() {
        return getList();
    }

    public Region getRegion(String id) {
        return (Region)getById(id);
    }

    public void saveRegion(Region obj) {
        save(obj);
    }

    public void removeRegion(String id) {
        deleteById(id);
    }
}
