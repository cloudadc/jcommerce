/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import com.jcommerce.core.dao.AdminUserDAO;
import com.jcommerce.core.model.AdminUser;

public class AdminUserDAOImpl extends DAOImpl implements AdminUserDAO {
    public AdminUserDAOImpl() {
        modelClass = AdminUser.class;
    }

    public List<AdminUser> getAdminUserList() {
        return getList();
    }

    public AdminUser getAdminUser(String id) {
        return (AdminUser)getById(id);
    }

    public void saveAdminUser(AdminUser obj) {
        save(obj);
    }

    public void removeAdminUser(String id) {
        deleteById(id);
    }
}
