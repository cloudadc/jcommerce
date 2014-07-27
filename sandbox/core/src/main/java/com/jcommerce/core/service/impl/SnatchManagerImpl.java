package com.jcommerce.core.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcommerce.core.dao.SnatchDAO;
import com.jcommerce.core.model.Snatch;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.SnatchManager;

@Service("SnatchManager")
public class SnatchManagerImpl extends ManagerImpl implements SnatchManager {
	private static Log log = LogFactory.getLog(SnatchManagerImpl.class);
	
	@Autowired
    private SnatchDAO dao;

    public void setSnatchDAO(SnatchDAO dao) {
        this.dao = dao;
        super.setDao(dao);
    }

    public Snatch initialize(Snatch obj) {
        if (obj != null && !Hibernate.isInitialized(obj)) {
            obj = dao.getSnatch(obj.getId());
        }
        return obj;
    }

    public List<Snatch> getSnatchList(int firstRow, int maxRow) {
        List list = getList(firstRow, maxRow);
        return (List<Snatch>)list;
    }

    public int getSnatchCount(Criteria criteria) {
        return getCount(criteria);
    }

    public List<Snatch> getSnatchList(Criteria criteria) {
        List list = getList(criteria);
        return (List<Snatch>)list;
    }

    public List<Snatch> getSnatchList(int firstRow, int maxRow, Criteria criteria) {
        List list = getList(firstRow, maxRow, criteria);
        return (List<Snatch>)list;
    }

    public List<Snatch> getSnatchList() {
        return dao.getSnatchList();
    }

    public Snatch getSnatch(Long id) {
        Snatch obj = dao.getSnatch(id);
        return obj;
    }

    public void saveSnatch(Snatch obj) {
        dao.saveSnatch(obj);
    }

    public void removeSnatch(Long id) {
        dao.removeSnatch(id);
    }
}
