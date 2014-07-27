/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao;

import java.util.List;

import com.jcommerce.core.model.Navigation;

public interface NavigationDAO extends DAO {
    public List<Navigation> getNavigationList();

    public Navigation getNavigation(Long id);

    public void saveNavigation(Navigation obj);

    public void removeNavigation(Long id);
}
