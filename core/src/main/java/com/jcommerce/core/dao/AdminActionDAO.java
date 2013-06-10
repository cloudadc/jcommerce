/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao;

import java.util.List;

import com.jcommerce.core.model.AdminAction;

public interface AdminActionDAO extends DAO {
    public List<AdminAction> getAdminActionList();

    public AdminAction getAdminAction(String id);

    public void saveAdminAction(AdminAction obj);

    public void removeAdminAction(String id);
}
