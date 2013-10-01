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

import com.jcommerce.core.dao.StatsDAO;
import com.jcommerce.core.model.Stats;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.StatsManager;

@Service("StatsManager")
public class StatsManagerImpl extends ManagerImpl implements StatsManager {
    private static Log log = LogFactory.getLog(StatsManagerImpl.class);
    
    @Autowired
    private StatsDAO dao;

    public void setStatsDAO(StatsDAO dao) {
        this.dao = dao;
        super.setDao(dao);
    }

    public Stats initialize(Stats obj) {
        if (obj != null && !Hibernate.isInitialized(obj)) {
            obj = dao.getStats(obj.getId());
        }
        return obj;
    }

    public List<Stats> getStatsList(int firstRow, int maxRow) {
        List list = getList(firstRow, maxRow);
        return (List<Stats>)list;
    }

    public int getStatsCount(Criteria criteria) {
        return getCount(criteria);
    }

    public List<Stats> getStatsList(Criteria criteria) {
        List list = getList(criteria);
        return (List<Stats>)list;
    }

    public List<Stats> getStatsList(int firstRow, int maxRow, Criteria criteria) {
        List list = getList(firstRow, maxRow, criteria);
        return (List<Stats>)list;
    }

    public List<Stats> getStatsList() {
        return dao.getStatsList();
    }

    public Stats getStats(String id) {
        Stats obj = dao.getStats(id);
        return obj;
    }

    public void saveStats(Stats obj) {
        dao.saveStats(obj);
    }

    public void removeStats(String id) {
        dao.removeStats(id);
    }
}
