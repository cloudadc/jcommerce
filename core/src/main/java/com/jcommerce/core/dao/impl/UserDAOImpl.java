/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jcommerce.core.dao.UserDAO;
import com.jcommerce.core.model.User;

@Repository
@SuppressWarnings("unchecked")
public class UserDAOImpl extends DAOImpl implements UserDAO {
    public UserDAOImpl() {
        modelClass = User.class;
    }

    public List<User> getUserList() {
        return getList();
    }

    public User getUser(String id) {
        return (User)getById(id);
    }

    public void saveUser(User obj) {
        save(obj);
    }

    public void removeUser(String id) {
        deleteById(id);
    }
}
