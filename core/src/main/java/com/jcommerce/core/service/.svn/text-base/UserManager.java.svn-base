/**
* Author: Bob Chen
*/

package com.jcommerce.core.service;

import java.util.List;

import com.jcommerce.core.model.User;
import com.jcommerce.core.service.Criteria;
public interface UserManager extends Manager {
    public User initialize(User obj);

    public List<User> getUserList(int firstRow, int maxRow);

    public int getUserCount(Criteria criteria);

    public List<User> getUserList(Criteria criteria);

    public List<User> getUserList(int firstRow, int maxRow, Criteria criteria);

    public List<User> getUserList();

    public User getUser(String id);

    public void saveUser(User obj);

    public void removeUser(String id);
    
    public User getUserByName(String userName);

}
