/**
* Author: Bob Chen
*         Kylin Soong
*/

package com.jcommerce.core.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcommerce.core.dao.AffiliateLogDAO;
import com.jcommerce.core.model.AffiliateLog;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.AffiliateLogManager;

@Service("AffiliateLogManager")
public class AffiliateLogManagerImpl extends ManagerImpl implements AffiliateLogManager {
    private static Log log = LogFactory.getLog(AffiliateLogManagerImpl.class);
    
    @Autowired
    private AffiliateLogDAO dao;

    public void setAffiliateLogDAO(AffiliateLogDAO dao) {
        this.dao = dao;
        super.setDao(dao);
    }

    public AffiliateLog initialize(AffiliateLog obj) {
        if (obj != null && !Hibernate.isInitialized(obj)) {
            obj = dao.getAffiliateLog(obj.getId());
        }
        return obj;
    }

    public List<AffiliateLog> getAffiliateLogList(int firstRow, int maxRow) {
        List list = getList(firstRow, maxRow);
        return (List<AffiliateLog>)list;
    }

    public int getAffiliateLogCount(Criteria criteria) {
        return getCount(criteria);
    }

    public List<AffiliateLog> getAffiliateLogList(Criteria criteria) {
        List list = getList(criteria);
        return (List<AffiliateLog>)list;
    }

    public List<AffiliateLog> getAffiliateLogList(int firstRow, int maxRow, Criteria criteria) {
        List list = getList(firstRow, maxRow, criteria);
        return (List<AffiliateLog>)list;
    }

    public List<AffiliateLog> getAffiliateLogList() {
        return dao.getAffiliateLogList();
    }

    public AffiliateLog getAffiliateLog(Long id) {
        AffiliateLog obj = dao.getAffiliateLog(id);
        return obj;
    }

    public void saveAffiliateLog(AffiliateLog obj) {
        dao.saveAffiliateLog(obj);
    }

    public void removeAffiliateLog(Long id) {
        dao.removeAffiliateLog(id);
    }
}
