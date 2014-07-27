/**
* @Author: KingZhao
*          Kylin Soong
*/

package com.jcommerce.core.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcommerce.core.dao.KeywordsDAO;
import com.jcommerce.core.model.Keywords;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.KeywordsManager;

@Service("KeywordsManager")
public class KeywordsManagerImpl extends ManagerImpl implements KeywordsManager {
	
    private static Log log = LogFactory.getLog(KeywordsManagerImpl.class);
    
    @Autowired
    private KeywordsDAO dao;

    public void setKeywordsDAO(KeywordsDAO dao) {
        this.dao = dao;
        super.setDao(dao);
    }

    public Keywords initialize(Keywords obj) {
        if (obj != null && !Hibernate.isInitialized(obj)) {
            obj = dao.getKeywords(obj.getId());
        }
        return obj;
    }

    public List<Keywords> getKeywordsList(int firstRow, int maxRow) {
        List list = getList(firstRow, maxRow);
        return (List<Keywords>)list;
    }

    public int getKeywordsCount(Criteria criteria) {
        return getCount(criteria);
    }

    public List<Keywords> getKeywordsList(Criteria criteria) {
        List list = getList(criteria);
        return (List<Keywords>)list;
    }

    public List<Keywords> getKeywordsList(int firstRow, int maxRow, Criteria criteria) {
        List list = getList(firstRow, maxRow, criteria);
        return (List<Keywords>)list;
    }

    public List<Keywords> getKeywordsList() {
        return dao.getKeywordsList();
    }

    public Keywords getKeywords(Long id) {
        Keywords obj = dao.getKeywords(id);
        return obj;
    }

    public void saveKeywords(Keywords obj) {
        dao.saveKeywords(obj);
    }

    public void removeKeywords(Long id) {
        dao.removeKeywords(id);
    }
}
