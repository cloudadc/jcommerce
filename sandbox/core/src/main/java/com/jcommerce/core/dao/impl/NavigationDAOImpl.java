/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jcommerce.core.dao.NavigationDAO;
import com.jcommerce.core.model.Navigation;

@Repository
@SuppressWarnings("unchecked")
public class NavigationDAOImpl extends DAOImpl implements NavigationDAO {
    public NavigationDAOImpl() {
        modelClass = Navigation.class;
    }

    public List<Navigation> getNavigationList() {
        return getList();
    }

    public Navigation getNavigation(Long id) {
        return (Navigation)getById(id);
    }

    public void saveNavigation(Navigation obj) {
        save(obj);
    }

    public void removeNavigation(Long id) {
        deleteById(id);
    }
}
