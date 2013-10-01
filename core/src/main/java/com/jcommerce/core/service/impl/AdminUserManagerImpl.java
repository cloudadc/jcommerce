/**
* Author: Bob Chen
*/

package com.jcommerce.core.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcommerce.core.dao.AdminUserDAO;
import com.jcommerce.core.model.AdminUser;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.AdminUserManager;

@Service("AdminUserManager")
public class AdminUserManagerImpl extends ManagerImpl implements AdminUserManager {
    private static Log log = LogFactory.getLog(AdminUserManagerImpl.class);
    
    @Autowired
    private AdminUserDAO dao;

    public void setAdminUserDAO(AdminUserDAO dao) {
        this.dao = dao;
        super.setDao(dao);
    }

    public AdminUser initialize(AdminUser obj) {
        if (obj != null && !Hibernate.isInitialized(obj)) {
            obj = dao.getAdminUser(obj.getId());
        }
        return obj;
    }

    public List<AdminUser> getAdminUserList(int firstRow, int maxRow) {
        List list = getList(firstRow, maxRow);
        return (List<AdminUser>)list;
    }

    public int getAdminUserCount(Criteria criteria) {
        return getCount(criteria);
    }

    public List<AdminUser> getAdminUserList(Criteria criteria) {
        List list = getList(criteria);
        return (List<AdminUser>)list;
    }

    public List<AdminUser> getAdminUserList(int firstRow, int maxRow, Criteria criteria) {
        List list = getList(firstRow, maxRow, criteria);
        return (List<AdminUser>)list;
    }

    public List<AdminUser> getAdminUserList() {
        return dao.getAdminUserList();
    }

    public AdminUser getAdminUser(String id) {
        AdminUser obj = dao.getAdminUser(id);
        return obj;
    }

    public void saveAdminUser(AdminUser obj) {
        dao.saveAdminUser(obj);
    }

    public void removeAdminUser(String id) {
        dao.removeAdminUser(id);
    }
}
