/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import com.jcommerce.core.dao.SessionDAO;
import com.jcommerce.core.model.Session;

public class SessionDAOImpl extends DAOImpl implements SessionDAO {
    public SessionDAOImpl() {
        modelClass = Session.class;
    }

    public List<Session> getSessionList() {
        return getList();
    }

    public Session getSession(String id) {
        return (Session)getById(id);
    }

    public void saveSession(Session obj) {
        save(obj);
    }

    public void removeSession(String id) {
        deleteById(id);
    }
}
