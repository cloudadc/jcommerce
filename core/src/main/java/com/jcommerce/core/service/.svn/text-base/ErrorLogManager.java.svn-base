/**
* Author: Bob Chen
*/

package com.jcommerce.core.service;

import java.util.List;

import com.jcommerce.core.model.ErrorLog;
import com.jcommerce.core.service.Criteria;
public interface ErrorLogManager extends Manager {
    public ErrorLog initialize(ErrorLog obj);

    public List<ErrorLog> getErrorLogList(int firstRow, int maxRow);

    public int getErrorLogCount(Criteria criteria);

    public List<ErrorLog> getErrorLogList(Criteria criteria);

    public List<ErrorLog> getErrorLogList(int firstRow, int maxRow, Criteria criteria);

    public List<ErrorLog> getErrorLogList();

    public ErrorLog getErrorLog(String id);

    public void saveErrorLog(ErrorLog obj);

    public void removeErrorLog(String id);
}
