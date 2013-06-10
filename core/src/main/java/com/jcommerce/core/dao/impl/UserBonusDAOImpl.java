/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import com.jcommerce.core.dao.UserBonusDAO;
import com.jcommerce.core.model.UserBonus;

public class UserBonusDAOImpl extends DAOImpl implements UserBonusDAO {
    public UserBonusDAOImpl() {
        modelClass = UserBonus.class;
    }

    public List<UserBonus> getUserBonusList() {
        return getList();
    }

    public UserBonus getUserBonus(String id) {
        return (UserBonus)getById(id);
    }

    public void saveUserBonus(UserBonus obj) {
        save(obj);
    }

    public void removeUserBonus(String id) {
        deleteById(id);
    }
}
