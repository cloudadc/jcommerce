/**
* Author: Bob Chen
*/

package com.jcommerce.core.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;

import com.jcommerce.core.dao.UserDAO;
import com.jcommerce.core.model.User;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.UserManager;

public class UserManagerImpl extends ManagerImpl implements UserManager {
    private static Log log = LogFactory.getLog(UserManagerImpl.class);
    private UserDAO dao;

    public void setUserDAO(UserDAO dao) {
        this.dao = dao;
        super.setDao(dao);
    }

    public User initialize(User obj) {
        if (obj != null && !Hibernate.isInitialized(obj)) {
            obj = dao.getUser(obj.getId());
        }
        return obj;
    }

    public List<User> getUserList(int firstRow, int maxRow) {
        List list = getList(firstRow, maxRow);
        return (List<User>)list;
    }

    public int getUserCount(Criteria criteria) {
        return getCount(criteria);
    }

    public List<User> getUserList(Criteria criteria) {
        List list = getList(criteria);
        return (List<User>)list;
    }

    public List<User> getUserList(int firstRow, int maxRow, Criteria criteria) {
        List list = getList(firstRow, maxRow, criteria);
        return (List<User>)list;
    }

    public List<User> getUserList() {
        return dao.getUserList();
    }

    public User getUser(String id) {
        User obj = dao.getUser(id);
        return obj;
    }

    public void saveUser(User obj) {
        dao.saveUser(obj);
    }

    public void removeUser(String id) {
        dao.removeUser(id);
    }
    
    public User getUserByName(String userName){
    	if(userName.length() != 0){
    	userName.replace("'", "“");
    	userName.replace("=", "");
    	List user = dao.getList("from User t where t.name = '"+userName+"'");
    	if(user.isEmpty()){
    		return null;
    	}
    	else{
    		return (User)user.get(0);
    		}
    	}else{
    		return null;
    	}    	
    }
}
