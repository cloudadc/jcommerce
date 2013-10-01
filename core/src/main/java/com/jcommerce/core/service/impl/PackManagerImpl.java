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

import com.jcommerce.core.dao.PackDAO;
import com.jcommerce.core.model.Pack;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.PackManager;

@Service("PackManager")
public class PackManagerImpl extends ManagerImpl implements PackManager {
    private static Log log = LogFactory.getLog(PackManagerImpl.class);
    
    @Autowired
    private PackDAO dao;

    public void setPackDAO(PackDAO dao) {
        this.dao = dao;
        super.setDao(dao);
    }

    public Pack initialize(Pack obj) {
        if (obj != null && !Hibernate.isInitialized(obj)) {
            obj = dao.getPack(obj.getId());
        }
        return obj;
    }

    public List<Pack> getPackList(int firstRow, int maxRow) {
        List list = getList(firstRow, maxRow);
        return (List<Pack>)list;
    }

    public int getPackCount(Criteria criteria) {
        return getCount(criteria);
    }

    public List<Pack> getPackList(Criteria criteria) {
        List list = getList(criteria);
        return (List<Pack>)list;
    }

    public List<Pack> getPackList(int firstRow, int maxRow, Criteria criteria) {
        List list = getList(firstRow, maxRow, criteria);
        return (List<Pack>)list;
    }

    public List<Pack> getPackList() {
        return dao.getPackList();
    }

    public Pack getPack(String id) {
        Pack obj = dao.getPack(id);
        return obj;
    }

    public void savePack(Pack obj) {
        dao.savePack(obj);
    }

    public void removePack(String id) {
        dao.removePack(id);
    }
}
