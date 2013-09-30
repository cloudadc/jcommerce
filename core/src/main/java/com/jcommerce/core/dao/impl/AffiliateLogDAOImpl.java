/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jcommerce.core.dao.AffiliateLogDAO;
import com.jcommerce.core.model.AffiliateLog;

@Repository
@SuppressWarnings("unchecked")
public class AffiliateLogDAOImpl extends DAOImpl implements AffiliateLogDAO {
    public AffiliateLogDAOImpl() {
        modelClass = AffiliateLog.class;
    }

    public List<AffiliateLog> getAffiliateLogList() {
        return getList();
    }

    public AffiliateLog getAffiliateLog(String id) {
        return (AffiliateLog)getById(id);
    }

    public void saveAffiliateLog(AffiliateLog obj) {
        save(obj);
    }

    public void removeAffiliateLog(String id) {
        deleteById(id);
    }
}
