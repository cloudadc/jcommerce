/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jcommerce.core.dao.UserAddressDAO;
import com.jcommerce.core.model.UserAddress;

@Repository
@SuppressWarnings("unchecked")
public class UserAddressDAOImpl extends DAOImpl implements UserAddressDAO {
    public UserAddressDAOImpl() {
        modelClass = UserAddress.class;
    }

    public List<UserAddress> getUserAddressList() {
        return getList();
    }

    public UserAddress getUserAddress(Long id) {
        return (UserAddress)getById(id);
    }

    public void saveUserAddress(UserAddress obj) {
        save(obj);
    }

    public void removeUserAddress(Long id) {
        deleteById(id);
    }
}
