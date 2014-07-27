/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jcommerce.core.dao.AdminActionDAO;
import com.jcommerce.core.model.AdminAction;

@Repository
@SuppressWarnings("unchecked")
public class AdminActionDAOImpl extends DAOImpl implements AdminActionDAO {
    public AdminActionDAOImpl() {
        modelClass = AdminAction.class;
    }

    public List<AdminAction> getAdminActionList() {
        return getList();
    }

    public AdminAction getAdminAction(Long id) {
        return (AdminAction)getById(id);
    }

    public void saveAdminAction(AdminAction obj) {
        save(obj);
    }

    public void removeAdminAction(Long id) {
        deleteById(id);
    }
}
