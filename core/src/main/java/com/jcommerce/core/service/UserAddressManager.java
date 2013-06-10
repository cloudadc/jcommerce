/**
* Author: Bob Chen
*/

package com.jcommerce.core.service;

import java.util.List;

import com.jcommerce.core.model.UserAddress;
import com.jcommerce.core.service.Criteria;
public interface UserAddressManager extends Manager {
    public UserAddress initialize(UserAddress obj);

    public List<UserAddress> getUserAddressList(int firstRow, int maxRow);

    public int getUserAddressCount(Criteria criteria);

    public List<UserAddress> getUserAddressList(Criteria criteria);

    public List<UserAddress> getUserAddressList(int firstRow, int maxRow, Criteria criteria);

    public List<UserAddress> getUserAddressList();

    public UserAddress getUserAddress(String id);

    public void saveUserAddress(UserAddress obj);

    public void removeUserAddress(String id);
}
