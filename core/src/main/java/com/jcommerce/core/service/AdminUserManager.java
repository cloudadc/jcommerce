/**
* Author: Bob Chen
*/

package com.jcommerce.core.service;

import java.util.List;

import com.jcommerce.core.model.AdminUser;
import com.jcommerce.core.service.Criteria;
public interface AdminUserManager extends Manager {
    public AdminUser initialize(AdminUser obj);

    public List<AdminUser> getAdminUserList(int firstRow, int maxRow);

    public int getAdminUserCount(Criteria criteria);

    public List<AdminUser> getAdminUserList(Criteria criteria);

    public List<AdminUser> getAdminUserList(int firstRow, int maxRow, Criteria criteria);

    public List<AdminUser> getAdminUserList();

    public AdminUser getAdminUser(String id);

    public void saveAdminUser(AdminUser obj);

    public void removeAdminUser(String id);
}
