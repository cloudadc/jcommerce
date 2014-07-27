/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jcommerce.core.model.AdminLog;
import com.jcommerce.core.dao.AdminLogDAO;

@Repository
@SuppressWarnings("unchecked")
public class AdminLogDAOImpl extends DAOImpl implements AdminLogDAO {
    public AdminLogDAOImpl() {
        modelClass = AdminLog.class;
    }

    public List<AdminLog> getAdminLogList() {
        return getList();
    }

    public AdminLog getAdminLog(Long id) {
        return (AdminLog)getById(id);
    }

    public void saveAdminLog(AdminLog obj) {
        save(obj);
    }

    public void removeAdminLog(Long id) {
        deleteById(id);
    }
}
