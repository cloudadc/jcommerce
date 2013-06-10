/**
* @Author: KingZhao
*/

package com.jcommerce.core.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;

import com.jcommerce.core.dao.SessionsDataDAO;
import com.jcommerce.core.model.SessionsData;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.SessionsDataManager;

public class SessionsDataManagerImpl extends ManagerImpl implements SessionsDataManager {
    private static Log log = LogFactory.getLog(SessionsDataManagerImpl.class);
    private SessionsDataDAO dao;

    public void setSessionsDataDAO(SessionsDataDAO dao) {
        this.dao = dao;
        super.setDao(dao);
    }

    public SessionsData initialize(SessionsData obj) {
        if (obj != null && !Hibernate.isInitialized(obj)) {
            obj = dao.getSessionsData(obj.getId());
        }
        return obj;
    }

    public List<SessionsData> getSessionsDataList(int firstRow, int maxRow) {
        List list = getList(firstRow, maxRow);
        return (List<SessionsData>)list;
    }

    public int getSessionsDataCount(Criteria criteria) {
        return getCount(criteria);
    }

    public List<SessionsData> getSessionsDataList(Criteria criteria) {
        List list = getList(criteria);
        return (List<SessionsData>)list;
    }

    public List<SessionsData> getSessionsDataList(int firstRow, int maxRow, Criteria criteria) {
        List list = getList(firstRow, maxRow, criteria);
        return (List<SessionsData>)list;
    }

    public List<SessionsData> getSessionsDataList() {
        return dao.getSessionsDataList();
    }

    public SessionsData getSessionsData(String id) {
        SessionsData obj = dao.getSessionsData(id);
        return obj;
    }

    public void saveSessionsData(SessionsData obj) {
        dao.saveSessionsData(obj);
    }

    public void removeSessionsData(String id) {
        dao.removeSessionsData(id);
    }
}
