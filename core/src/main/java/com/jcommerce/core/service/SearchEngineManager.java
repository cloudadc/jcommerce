/**
* Author: Bob Chen
*/

package com.jcommerce.core.service;

import java.util.List;

import com.jcommerce.core.model.SearchEngine;
import com.jcommerce.core.service.Criteria;
public interface SearchEngineManager extends Manager {
    public SearchEngine initialize(SearchEngine obj);

    public List<SearchEngine> getSearchEngineList(int firstRow, int maxRow);

    public int getSearchEngineCount(Criteria criteria);

    public List<SearchEngine> getSearchEngineList(Criteria criteria);

    public List<SearchEngine> getSearchEngineList(int firstRow, int maxRow, Criteria criteria);

    public List<SearchEngine> getSearchEngineList();

    public SearchEngine getSearchEngine(String id);

    public void saveSearchEngine(SearchEngine obj);

    public void removeSearchEngine(String id);
}
