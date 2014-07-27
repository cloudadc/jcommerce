/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jcommerce.core.dao.FailedLoginDAO;
import com.jcommerce.core.model.FailedLogin;

@Repository
@SuppressWarnings("unchecked")
public class FailedLoginDAOImpl extends DAOImpl implements FailedLoginDAO {
    public FailedLoginDAOImpl() {
        modelClass = FailedLogin.class;
    }

    public List<FailedLogin> getFailedLoginList() {
        return getList();
    }

    public FailedLogin getFailedLogin(Long id) {
        return (FailedLogin)getById(id);
    }

    public void saveFailedLogin(FailedLogin obj) {
        save(obj);
    }

    public void removeFailedLogin(Long id) {
        deleteById(id);
    }
}
