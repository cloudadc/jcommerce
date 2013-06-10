/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import com.jcommerce.core.dao.FailedLoginDAO;
import com.jcommerce.core.model.FailedLogin;

public class FailedLoginDAOImpl extends DAOImpl implements FailedLoginDAO {
    public FailedLoginDAOImpl() {
        modelClass = FailedLogin.class;
    }

    public List<FailedLogin> getFailedLoginList() {
        return getList();
    }

    public FailedLogin getFailedLogin(String id) {
        return (FailedLogin)getById(id);
    }

    public void saveFailedLogin(FailedLogin obj) {
        save(obj);
    }

    public void removeFailedLogin(String id) {
        deleteById(id);
    }
}
