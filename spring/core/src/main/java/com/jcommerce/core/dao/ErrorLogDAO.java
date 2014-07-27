/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao;

import java.util.List;

import com.jcommerce.core.model.ErrorLog;

public interface ErrorLogDAO extends DAO {
    public List<ErrorLog> getErrorLogList();

    public ErrorLog getErrorLog(Long id);

    public void saveErrorLog(ErrorLog obj);

    public void removeErrorLog(Long id);
}
