/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao;

import java.util.List;

import com.jcommerce.core.model.Session;

public interface SessionDAO extends DAO {
    public List<Session> getSessionList();

    public Session getSession(Long id);

    public void saveSession(Session obj);

    public void removeSession(Long id);
}
