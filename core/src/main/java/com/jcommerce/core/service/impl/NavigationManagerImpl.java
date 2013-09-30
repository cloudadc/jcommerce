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

import com.jcommerce.core.dao.NavigationDAO;
import com.jcommerce.core.model.Navigation;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.NavigationManager;

@Service("navigationManager")
public class NavigationManagerImpl extends ManagerImpl implements NavigationManager {
    private static Log log = LogFactory.getLog(NavigationManagerImpl.class);
    
    @Autowired
    private NavigationDAO dao;

    public void setNavigationDAO(NavigationDAO dao) {
        this.dao = dao;
        super.setDao(dao);
    }

    public Navigation initialize(Navigation obj) {
        if (obj != null && !Hibernate.isInitialized(obj)) {
            obj = dao.getNavigation(obj.getId());
        }
        return obj;
    }

    public List<Navigation> getNavigationList(int firstRow, int maxRow) {
        List list = getList(firstRow, maxRow);
        return (List<Navigation>)list;
    }

    public int getNavigationCount(Criteria criteria) {
        return getCount(criteria);
    }

    public List<Navigation> getNavigationList(Criteria criteria) {
        List list = getList(criteria);
        return (List<Navigation>)list;
    }

    public List<Navigation> getNavigationList(int firstRow, int maxRow, Criteria criteria) {
        List list = getList(firstRow, maxRow, criteria);
        return (List<Navigation>)list;
    }

    public List<Navigation> getNavigationList() {
        return dao.getNavigationList();
    }

    public Navigation getNavigation(String id) {
        Navigation obj = dao.getNavigation(id);
        return obj;
    }

    public void saveNavigation(Navigation obj) {
        dao.saveNavigation(obj);
    }

    public void removeNavigation(String id) {
        dao.removeNavigation(id);
    }
}
