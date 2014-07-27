/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao;

import java.util.List;

import com.jcommerce.core.model.AdminLog;

public interface AdminLogDAO extends DAO {
    public List<AdminLog> getAdminLogList();

    public AdminLog getAdminLog(Long id);

    public void saveAdminLog(AdminLog obj);

    public void removeAdminLog(Long id);
}
