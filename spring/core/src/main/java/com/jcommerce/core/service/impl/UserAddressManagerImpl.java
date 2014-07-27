/**
* Author: Bob Chen
*         Kylin Soong
*/

package com.jcommerce.core.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcommerce.core.dao.UserAddressDAO;
import com.jcommerce.core.model.UserAddress;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.UserAddressManager;

@Service("UserAddressManager")
public class UserAddressManagerImpl extends ManagerImpl implements UserAddressManager {
    private static Log log = LogFactory.getLog(UserAddressManagerImpl.class);
    
    @Autowired
    private UserAddressDAO dao;

    public void setUserAddressDAO(UserAddressDAO dao) {
        this.dao = dao;
        super.setDao(dao);
    }

    public UserAddress initialize(UserAddress obj) {
        if (obj != null && !Hibernate.isInitialized(obj)) {
            obj = dao.getUserAddress(obj.getId());
        }
        return obj;
    }

    public List<UserAddress> getUserAddressList(int firstRow, int maxRow) {
        List list = getList(firstRow, maxRow);
        return (List<UserAddress>)list;
    }

    public int getUserAddressCount(Criteria criteria) {
        return getCount(criteria);
    }

    public List<UserAddress> getUserAddressList(Criteria criteria) {
        List list = getList(criteria);
        return (List<UserAddress>)list;
    }

    public List<UserAddress> getUserAddressList(int firstRow, int maxRow, Criteria criteria) {
        List list = getList(firstRow, maxRow, criteria);
        return (List<UserAddress>)list;
    }

    public List<UserAddress> getUserAddressList() {
        return dao.getUserAddressList();
    }

    public UserAddress getUserAddress(Long id) {
        UserAddress obj = dao.getUserAddress(id);
        return obj;
    }

    public void saveUserAddress(UserAddress obj) {
        dao.saveUserAddress(obj);
    }

    public void removeUserAddress(Long id) {
        dao.removeUserAddress(id);
    }
}
