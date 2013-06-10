/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import com.jcommerce.core.dao.AdminMessageDAO;
import com.jcommerce.core.model.AdminMessage;

public class AdminMessageDAOImpl extends DAOImpl implements AdminMessageDAO {
    public AdminMessageDAOImpl() {
        modelClass = AdminMessage.class;
    }

    public List<AdminMessage> getAdminMessageList() {
        return getList();
    }

    public AdminMessage getAdminMessage(String id) {
        return (AdminMessage)getById(id);
    }

    public void saveAdminMessage(AdminMessage obj) {
        save(obj);
    }

    public void removeAdminMessage(String id) {
        deleteById(id);
    }
}
