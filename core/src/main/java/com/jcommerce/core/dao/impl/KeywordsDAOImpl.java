/**
* @Author: KingZhao
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jcommerce.core.dao.KeywordsDAO;
import com.jcommerce.core.model.Keywords;

@Repository
@SuppressWarnings("unchecked")
public class KeywordsDAOImpl extends DAOImpl implements KeywordsDAO {
    public KeywordsDAOImpl() {
        modelClass = Keywords.class;
    }

    public List<Keywords> getKeywordsList() {
        return getList();
    }

    public Keywords getKeywords(String id) {
        return (Keywords)getById(id);
    }

    public void saveKeywords(Keywords obj) {
        save(obj);
    }

    public void removeKeywords(String id) {
        deleteById(id);
    }
}
