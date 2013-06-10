/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import com.jcommerce.core.dao.AccountLogDAO;
import com.jcommerce.core.model.AccountLog;

public class AccountLogDAOImpl extends DAOImpl implements AccountLogDAO {
    public AccountLogDAOImpl() {
        modelClass = AccountLog.class;
    }

    public List<AccountLog> getAccountLogList() {
        return getList();
    }

    public AccountLog getAccountLog(String id) {
        return (AccountLog)getById(id);
    }

    public void saveAccountLog(AccountLog obj) {
        save(obj);
    }

    public void removeAccountLog(String id) {
        deleteById(id);
    }
}
