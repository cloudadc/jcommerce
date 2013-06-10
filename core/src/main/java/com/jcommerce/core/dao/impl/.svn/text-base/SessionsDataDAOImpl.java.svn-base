/**
* @Author: KingZhao
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import com.jcommerce.core.dao.SessionsDataDAO;
import com.jcommerce.core.model.SessionsData;

public class SessionsDataDAOImpl extends DAOImpl implements SessionsDataDAO {
    public SessionsDataDAOImpl() {
        modelClass = SessionsData.class;
    }

    public List<SessionsData> getSessionsDataList() {
        return getList();
    }

    public SessionsData getSessionsData(String id) {
        return (SessionsData)getById(id);
    }

    public void saveSessionsData(SessionsData obj) {
        save(obj);
    }

    public void removeSessionsData(String id) {
        deleteById(id);
    }
}
