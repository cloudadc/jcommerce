/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao;

import java.util.List;

import com.jcommerce.core.model.User;

public interface UserDAO extends DAO {
    public List<User> getUserList();

    public User getUser(Long id);

    public void saveUser(User obj);

    public void removeUser(Long id);
}
