/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao;

import java.util.List;

import com.jcommerce.core.model.AdminUser;

public interface AdminUserDAO extends DAO {
    public List<AdminUser> getAdminUserList();

    public AdminUser getAdminUser(String id);

    public void saveAdminUser(AdminUser obj);

    public void removeAdminUser(String id);
}
