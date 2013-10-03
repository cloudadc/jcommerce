/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jcommerce.core.dao.AdminUserDAO;
import com.jcommerce.core.model.AdminUser;

@Repository
@SuppressWarnings("unchecked")
public class AdminUserDAOImpl extends DAOImpl implements AdminUserDAO {
    public AdminUserDAOImpl() {
        modelClass = AdminUser.class;
    }

    public List<AdminUser> getAdminUserList() {
        return getList();
    }

    public AdminUser getAdminUser(Long id) {
        return (AdminUser)getById(id);
    }

    public void saveAdminUser(AdminUser obj) {
        save(obj);
    }

    public void removeAdminUser(Long id) {
        deleteById(id);
    }
}
