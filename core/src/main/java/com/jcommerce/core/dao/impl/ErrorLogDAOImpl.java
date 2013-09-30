/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jcommerce.core.dao.ErrorLogDAO;
import com.jcommerce.core.model.ErrorLog;

@Repository
@SuppressWarnings("unchecked")
public class ErrorLogDAOImpl extends DAOImpl implements ErrorLogDAO {
    public ErrorLogDAOImpl() {
        modelClass = ErrorLog.class;
    }

    public List<ErrorLog> getErrorLogList() {
        return getList();
    }

    public ErrorLog getErrorLog(String id) {
        return (ErrorLog)getById(id);
    }

    public void saveErrorLog(ErrorLog obj) {
        save(obj);
    }

    public void removeErrorLog(String id) {
        deleteById(id);
    }
}
