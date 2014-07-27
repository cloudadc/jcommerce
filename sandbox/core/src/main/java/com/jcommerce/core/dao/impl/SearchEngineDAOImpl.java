/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jcommerce.core.dao.SearchEngineDAO;
import com.jcommerce.core.model.SearchEngine;

@Repository
@SuppressWarnings("unchecked")
public class SearchEngineDAOImpl extends DAOImpl implements SearchEngineDAO {
    public SearchEngineDAOImpl() {
        modelClass = SearchEngine.class;
    }

    public List<SearchEngine> getSearchEngineList() {
        return getList();
    }

    public SearchEngine getSearchEngine(Long id) {
        return (SearchEngine)getById(id);
    }

    public void saveSearchEngine(SearchEngine obj) {
        save(obj);
    }

    public void removeSearchEngine(Long id) {
        deleteById(id);
    }
}
