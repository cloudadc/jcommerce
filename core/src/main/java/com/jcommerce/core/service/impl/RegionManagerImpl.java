/**
* Author: Bob Chen
*/

package com.jcommerce.core.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcommerce.core.dao.RegionDAO;
import com.jcommerce.core.model.Region;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.RegionManager;

@Service("regionManager")
public class RegionManagerImpl extends ManagerImpl implements RegionManager {
    private static Log log = LogFactory.getLog(RegionManagerImpl.class);
    
    @Autowired
    private RegionDAO dao;

    public void setRegionDAO(RegionDAO dao) {
        this.dao = dao;
        super.setDao(dao);
    }

    public Region initialize(Region obj) {
        if (obj != null && !Hibernate.isInitialized(obj)) {
            obj = dao.getRegion(obj.getId());
        }
        return obj;
    }

    public List<Region> getRegionList(int firstRow, int maxRow) {
        List list = getList(firstRow, maxRow);
        return (List<Region>)list;
    }

    public int getRegionCount(Criteria criteria) {
        return getCount(criteria);
    }

    public List<Region> getRegionList(Criteria criteria) {
        List list = getList(criteria);
        return (List<Region>)list;
    }

    public List<Region> getRegionList(int firstRow, int maxRow, Criteria criteria) {
        List list = getList(firstRow, maxRow, criteria);
        return (List<Region>)list;
    }

    public List<Region> getRegionList() {
        return dao.getRegionList();
    }

    public List<Region> getChildList(String parent_id){
    	List<Region> list = getList("from Region t where t.parent = "+parent_id);
    	return list;
    }
    
    public Region getRegion(String id) {
        Region obj = dao.getRegion(id);
        return obj;
    }

    public void saveRegion(Region obj) {
        dao.saveRegion(obj);
    }

    public void removeRegion(String id) {
        dao.removeRegion(id);
    }
    
    public List<Region> getCountries(){
    	List<Region> countries = getList("from Region t where t.type = 0");
    	return countries;
    }
}
