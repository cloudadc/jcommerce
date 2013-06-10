/**
* Author: Bob Chen
*/

package com.jcommerce.core.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;

import com.jcommerce.core.dao.AgencyDAO;
import com.jcommerce.core.model.Agency;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.AgencyManager;

public class AgencyManagerImpl extends ManagerImpl implements AgencyManager {
    private static Log log = LogFactory.getLog(AgencyManagerImpl.class);
    private AgencyDAO dao;

    public void setAgencyDAO(AgencyDAO dao) {
        this.dao = dao;
        super.setDao(dao);
    }

    public Agency initialize(Agency obj) {
        if (obj != null && !Hibernate.isInitialized(obj)) {
            obj = dao.getAgency(obj.getId());
        }
        return obj;
    }

    public List<Agency> getAgencyList(int firstRow, int maxRow) {
        List list = getList(firstRow, maxRow);
        return (List<Agency>)list;
    }

    public int getAgencyCount(Criteria criteria) {
        return getCount(criteria);
    }

    public List<Agency> getAgencyList(Criteria criteria) {
        List list = getList(criteria);
        return (List<Agency>)list;
    }

    public List<Agency> getAgencyList(int firstRow, int maxRow, Criteria criteria) {
        List list = getList(firstRow, maxRow, criteria);
        return (List<Agency>)list;
    }

    public List<Agency> getAgencyList() {
        return dao.getAgencyList();
    }

    public Agency getAgency(String id) {
        Agency obj = dao.getAgency(id);
        return obj;
    }

    public void saveAgency(Agency obj) {
        dao.saveAgency(obj);
    }

    public void removeAgency(String id) {
        dao.removeAgency(id);
    }
}
