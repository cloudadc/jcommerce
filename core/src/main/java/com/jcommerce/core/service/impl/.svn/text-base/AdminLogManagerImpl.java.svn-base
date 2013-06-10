/**
* Author: Bob Chen
*/

package com.jcommerce.core.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;

import com.jcommerce.core.dao.AdminLogDAO;
import com.jcommerce.core.model.AdminLog;
import com.jcommerce.core.service.AdminLogManager;

public class AdminLogManagerImpl extends ManagerImpl implements AdminLogManager {
    private static Log log = LogFactory.getLog(AdminLogManagerImpl.class);
    private AdminLogDAO dao;

    public void setAdminLogDAO(AdminLogDAO dao) {
        this.dao = dao;
        super.setDao(dao);
    }

    public AdminLog initialize(AdminLog obj) {
        if (obj != null && !Hibernate.isInitialized(obj)) {
            obj = dao.getAdminLog(obj.getId());
        }
        return obj;
    }

    public List<AdminLog> getAdminLogList() {
        return dao.getAdminLogList();
    }

    public AdminLog getAdminLog(String id) {
        AdminLog obj = dao.getAdminLog(id);
        return obj;
    }

    public void saveAdminLog(AdminLog obj) {
        dao.saveAdminLog(obj);
    }

    public void removeAdminLog(String id) {
        dao.removeAdminLog(id);
    }
}
