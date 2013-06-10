/**
* Author: Bob Chen
*/

package com.jcommerce.core.service;

import java.util.List;

import com.jcommerce.core.model.UserAccount;
import com.jcommerce.core.service.Criteria;
public interface UserAccountManager extends Manager {
    public UserAccount initialize(UserAccount obj);

    public List<UserAccount> getUserAccountList(int firstRow, int maxRow);

    public int getUserAccountCount(Criteria criteria);

    public List<UserAccount> getUserAccountList(Criteria criteria);

    public List<UserAccount> getUserAccountList(int firstRow, int maxRow, Criteria criteria);

    public List<UserAccount> getUserAccountList();

    public UserAccount getUserAccount(String id);

    public void saveUserAccount(UserAccount obj);

    public void removeUserAccount(String id);
}
