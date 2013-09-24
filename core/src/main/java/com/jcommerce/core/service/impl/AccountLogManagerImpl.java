/**
* Author: Bob Chen
*/

package com.jcommerce.core.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;

import com.jcommerce.core.dao.AccountLogDAO;
import com.jcommerce.core.model.AccountLog;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.AccountLogManager;

public class AccountLogManagerImpl extends ManagerImpl implements AccountLogManager {
   
	private static Log log = LogFactory.getLog(AccountLogManagerImpl.class);
    private AccountLogDAO dao;

    public void setAccountLogDAO(AccountLogDAO dao) {
        this.dao = dao;
        super.setDao(dao);
    }

    public AccountLog initialize(AccountLog obj) {
        if (obj != null && !Hibernate.isInitialized(obj)) {
            obj = dao.getAccountLog(obj.getId());
        }
        return obj;
    }

    public List<AccountLog> getAccountLogList(int firstRow, int maxRow) {
        List list = getList(firstRow, maxRow);
        return (List<AccountLog>)list;
    }

    public int getAccountLogCount(Criteria criteria) {
        return getCount(criteria);
    }

    public List<AccountLog> getAccountLogList(Criteria criteria) {
        List list = getList(criteria);
        return (List<AccountLog>)list;
    }

    public List<AccountLog> getAccountLogList(int firstRow, int maxRow, Criteria criteria) {
        List list = getList(firstRow, maxRow, criteria);
        return (List<AccountLog>)list;
    }

    public List<AccountLog> getAccountLogList() {
        return dao.getAccountLogList();
    }

    public AccountLog getAccountLog(String id) {
        AccountLog obj = dao.getAccountLog(id);
        return obj;
    }

    public void saveAccountLog(AccountLog obj) {
        dao.saveAccountLog(obj);
    }

    public void removeAccountLog(String id) {
        dao.removeAccountLog(id);
    }
}
