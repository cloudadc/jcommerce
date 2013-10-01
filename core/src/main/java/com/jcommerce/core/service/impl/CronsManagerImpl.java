/**
* @Author: KingZhao
*/

package com.jcommerce.core.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcommerce.core.dao.CronsDAO;
import com.jcommerce.core.model.Crons;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.CronsManager;

@Service("CronsManager")
public class CronsManagerImpl extends ManagerImpl implements CronsManager {
    private static Log log = LogFactory.getLog(CronsManagerImpl.class);
    
    @Autowired
    private CronsDAO dao;

    public void setCronsDAO(CronsDAO dao) {
        this.dao = dao;
        super.setDao(dao);
    }

    public Crons initialize(Crons obj) {
        if (obj != null && !Hibernate.isInitialized(obj)) {
            obj = dao.getCrons(obj.getId());
        }
        return obj;
    }

    public List<Crons> getCronsList(int firstRow, int maxRow) {
        List list = getList(firstRow, maxRow);
        return (List<Crons>)list;
    }

    public int getCronsCount(Criteria criteria) {
        return getCount(criteria);
    }

    public List<Crons> getCronsList(Criteria criteria) {
        List list = getList(criteria);
        return (List<Crons>)list;
    }

    public List<Crons> getCronsList(int firstRow, int maxRow, Criteria criteria) {
        List list = getList(firstRow, maxRow, criteria);
        return (List<Crons>)list;
    }

    public List<Crons> getCronsList() {
        return dao.getCronsList();
    }

    public Crons getCrons(String id) {
        Crons obj = dao.getCrons(id);
        return obj;
    }

    public void saveCrons(Crons obj) {
        dao.saveCrons(obj);
    }

    public void removeCrons(String id) {
        dao.removeCrons(id);
    }
}
