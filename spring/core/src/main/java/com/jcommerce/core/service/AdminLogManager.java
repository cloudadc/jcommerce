/**
* Author: Bob Chen
*/

package com.jcommerce.core.service;

import java.util.List;
import com.jcommerce.core.model.AdminLog;

public interface AdminLogManager extends Manager {
    public AdminLog initialize(AdminLog obj);

    public List<AdminLog> getAdminLogList();

    public AdminLog getAdminLog(Long id);

    public void saveAdminLog(AdminLog obj);

    public void removeAdminLog(Long id);
}
