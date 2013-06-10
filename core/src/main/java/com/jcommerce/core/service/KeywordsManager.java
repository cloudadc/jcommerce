/**
* @Author: KingZhao
*/

package com.jcommerce.core.service;

import java.util.List;

import com.jcommerce.core.model.Keywords;
import com.jcommerce.core.service.Criteria;
public interface KeywordsManager extends Manager {
    public Keywords initialize(Keywords obj);

    public List<Keywords> getKeywordsList(int firstRow, int maxRow);

    public int getKeywordsCount(Criteria criteria);

    public List<Keywords> getKeywordsList(Criteria criteria);

    public List<Keywords> getKeywordsList(int firstRow, int maxRow, Criteria criteria);

    public List<Keywords> getKeywordsList();

    public Keywords getKeywords(String id);

    public void saveKeywords(Keywords obj);

    public void removeKeywords(String id);
}
