/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jcommerce.core.dao.UserBonusDAO;
import com.jcommerce.core.model.UserBonus;

@Repository
@SuppressWarnings("unchecked")
public class UserBonusDAOImpl extends DAOImpl implements UserBonusDAO {
    public UserBonusDAOImpl() {
        modelClass = UserBonus.class;
    }

    public List<UserBonus> getUserBonusList() {
        return getList();
    }

    public UserBonus getUserBonus(Long id) {
        return (UserBonus)getById(id);
    }

    public void saveUserBonus(UserBonus obj) {
        save(obj);
    }

    public void removeUserBonus(Long id) {
        deleteById(id);
    }
}
