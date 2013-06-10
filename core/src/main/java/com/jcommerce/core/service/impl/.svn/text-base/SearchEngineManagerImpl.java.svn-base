/**
* Author: Bob Chen
*/

package com.jcommerce.core.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;

import com.jcommerce.core.dao.SearchEngineDAO;
import com.jcommerce.core.model.SearchEngine;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.SearchEngineManager;

public class SearchEngineManagerImpl extends ManagerImpl implements SearchEngineManager {
    private static Log log = LogFactory.getLog(SearchEngineManagerImpl.class);
    private SearchEngineDAO dao;

    public void setSearchEngineDAO(SearchEngineDAO dao) {
        this.dao = dao;
        super.setDao(dao);
    }

    public SearchEngine initialize(SearchEngine obj) {
        if (obj != null && !Hibernate.isInitialized(obj)) {
            obj = dao.getSearchEngine(obj.getId());
        }
        return obj;
    }

    public List<SearchEngine> getSearchEngineList(int firstRow, int maxRow) {
        List list = getList(firstRow, maxRow);
        return (List<SearchEngine>)list;
    }

    public int getSearchEngineCount(Criteria criteria) {
        return getCount(criteria);
    }

    public List<SearchEngine> getSearchEngineList(Criteria criteria) {
        List list = getList(criteria);
        return (List<SearchEngine>)list;
    }

    public List<SearchEngine> getSearchEngineList(int firstRow, int maxRow, Criteria criteria) {
        List list = getList(firstRow, maxRow, criteria);
        return (List<SearchEngine>)list;
    }

    public List<SearchEngine> getSearchEngineList() {
        return dao.getSearchEngineList();
    }

    public SearchEngine getSearchEngine(String id) {
        SearchEngine obj = dao.getSearchEngine(id);
        return obj;
    }

    public void saveSearchEngine(SearchEngine obj) {
        dao.saveSearchEngine(obj);
    }

    public void removeSearchEngine(String id) {
        dao.removeSearchEngine(id);
    }
}
