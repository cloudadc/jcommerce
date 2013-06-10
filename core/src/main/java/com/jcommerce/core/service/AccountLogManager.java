/**
* Author: Bob Chen
*/

package com.jcommerce.core.service;

import java.util.List;

import com.jcommerce.core.model.AccountLog;
import com.jcommerce.core.service.Criteria;
public interface AccountLogManager extends Manager {
    public AccountLog initialize(AccountLog obj);

    public List<AccountLog> getAccountLogList(int firstRow, int maxRow);

    public int getAccountLogCount(Criteria criteria);

    public List<AccountLog> getAccountLogList(Criteria criteria);

    public List<AccountLog> getAccountLogList(int firstRow, int maxRow, Criteria criteria);

    public List<AccountLog> getAccountLogList();

    public AccountLog getAccountLog(String id);

    public void saveAccountLog(AccountLog obj);

    public void removeAccountLog(String id);
}
