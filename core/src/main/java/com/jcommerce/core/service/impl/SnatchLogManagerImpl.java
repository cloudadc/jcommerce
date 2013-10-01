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

import com.jcommerce.core.dao.SnatchLogDAO;
import com.jcommerce.core.model.SnatchLog;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.SnatchLogManager;

@Service("SnatchLogManager")
public class SnatchLogManagerImpl extends ManagerImpl implements SnatchLogManager {
    private static Log log = LogFactory.getLog(SnatchLogManagerImpl.class);
    
    @Autowired
    private SnatchLogDAO dao;

    public void setSnatchLogDAO(SnatchLogDAO dao) {
        this.dao = dao;
        super.setDao(dao);
    }

    public SnatchLog initialize(SnatchLog obj) {
        if (obj != null && !Hibernate.isInitialized(obj)) {
            obj = dao.getSnatchLog(obj.getId());
        }
        return obj;
    }

    public List<SnatchLog> getSnatchLogList(int firstRow, int maxRow) {
        List list = getList(firstRow, maxRow);
        return (List<SnatchLog>)list;
    }

    public int getSnatchLogCount(Criteria criteria) {
        return getCount(criteria);
    }

    public List<SnatchLog> getSnatchLogList(Criteria criteria) {
        List list = getList(criteria);
        return (List<SnatchLog>)list;
    }

    public List<SnatchLog> getSnatchLogList(int firstRow, int maxRow, Criteria criteria) {
        List list = getList(firstRow, maxRow, criteria);
        return (List<SnatchLog>)list;
    }

    public List<SnatchLog> getSnatchLogList() {
        return dao.getSnatchLogList();
    }

    public SnatchLog getSnatchLog(String id) {
        SnatchLog obj = dao.getSnatchLog(id);
        return obj;
    }

    public void saveSnatchLog(SnatchLog obj) {
        dao.saveSnatchLog(obj);
    }

    public void removeSnatchLog(String id) {
        dao.removeSnatchLog(id);
    }
}
