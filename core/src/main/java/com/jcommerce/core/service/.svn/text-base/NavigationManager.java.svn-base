/**
* Author: Bob Chen
*/

package com.jcommerce.core.service;

import java.util.List;

import com.jcommerce.core.model.Navigation;
import com.jcommerce.core.service.Criteria;
public interface NavigationManager extends Manager {
    public Navigation initialize(Navigation obj);

    public List<Navigation> getNavigationList(int firstRow, int maxRow);

    public int getNavigationCount(Criteria criteria);

    public List<Navigation> getNavigationList(Criteria criteria);

    public List<Navigation> getNavigationList(int firstRow, int maxRow, Criteria criteria);

    public List<Navigation> getNavigationList();

    public Navigation getNavigation(String id);

    public void saveNavigation(Navigation obj);

    public void removeNavigation(String id);
}
