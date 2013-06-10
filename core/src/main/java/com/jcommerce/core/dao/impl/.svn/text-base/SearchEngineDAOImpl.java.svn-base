/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import com.jcommerce.core.dao.SearchEngineDAO;
import com.jcommerce.core.model.SearchEngine;

public class SearchEngineDAOImpl extends DAOImpl implements SearchEngineDAO {
    public SearchEngineDAOImpl() {
        modelClass = SearchEngine.class;
    }

    public List<SearchEngine> getSearchEngineList() {
        return getList();
    }

    public SearchEngine getSearchEngine(String id) {
        return (SearchEngine)getById(id);
    }

    public void saveSearchEngine(SearchEngine obj) {
        save(obj);
    }

    public void removeSearchEngine(String id) {
        deleteById(id);
    }
}
