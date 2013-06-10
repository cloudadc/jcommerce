/**
* Author: Bob Chen
*/

package com.jcommerce.core.service;

import java.util.List;

import com.jcommerce.core.model.Session;
import com.jcommerce.core.service.Criteria;
public interface SessionManager extends Manager {
    public Session initialize(Session obj);

    public List<Session> getSessionList(int firstRow, int maxRow);

    public int getSessionCount(Criteria criteria);

    public List<Session> getSessionList(Criteria criteria);

    public List<Session> getSessionList(int firstRow, int maxRow, Criteria criteria);

    public List<Session> getSessionList();

    public Session getSession(String id);

    public void saveSession(Session obj);

    public void removeSession(String id);
}
