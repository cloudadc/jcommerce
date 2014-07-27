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

import com.jcommerce.core.dao.ErrorLogDAO;
import com.jcommerce.core.model.ErrorLog;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.ErrorLogManager;

@Service("ErrorLogManager")
public class ErrorLogManagerImpl extends ManagerImpl implements ErrorLogManager {
    private static Log log = LogFactory.getLog(ErrorLogManagerImpl.class);
    
    @Autowired
    private ErrorLogDAO dao;

    public void setErrorLogDAO(ErrorLogDAO dao) {
        this.dao = dao;
        super.setDao(dao);
    }

    public ErrorLog initialize(ErrorLog obj) {
        if (obj != null && !Hibernate.isInitialized(obj)) {
            obj = dao.getErrorLog(obj.getId());
        }
        return obj;
    }

    public List<ErrorLog> getErrorLogList(int firstRow, int maxRow) {
        List list = getList(firstRow, maxRow);
        return (List<ErrorLog>)list;
    }

    public int getErrorLogCount(Criteria criteria) {
        return getCount(criteria);
    }

    public List<ErrorLog> getErrorLogList(Criteria criteria) {
        List list = getList(criteria);
        return (List<ErrorLog>)list;
    }

    public List<ErrorLog> getErrorLogList(int firstRow, int maxRow, Criteria criteria) {
        List list = getList(firstRow, maxRow, criteria);
        return (List<ErrorLog>)list;
    }

    public List<ErrorLog> getErrorLogList() {
        return dao.getErrorLogList();
    }

    public ErrorLog getErrorLog(Long id) {
        ErrorLog obj = dao.getErrorLog(id);
        return obj;
    }

    public void saveErrorLog(ErrorLog obj) {
        dao.saveErrorLog(obj);
    }

    public void removeErrorLog(Long id) {
        dao.removeErrorLog(id);
    }
}
